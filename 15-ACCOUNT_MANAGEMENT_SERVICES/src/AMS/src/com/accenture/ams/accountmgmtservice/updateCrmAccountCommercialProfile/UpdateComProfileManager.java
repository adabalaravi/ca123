package com.accenture.ams.accountmgmtservice.updateCrmAccountCommercialProfile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.accenture.ams.accountmgmtservice.AccountMgmtServiceConstant;
import com.accenture.ams.accountmgmtservice.ProductBuyedException;
import com.accenture.ams.accountmgmtservice.Utils;
import com.accenture.ams.accountmgmtservice.crmContentPurchase.ConfigurationData;
import com.accenture.ams.db.bean.AccountTechnicalPkg;
import com.accenture.ams.db.bean.AccountTechnicalPkgPK;
import com.accenture.ams.db.bean.PurchaseTransaction;
import com.accenture.ams.db.dao.AccountManagementDAO;
import com.accenture.ams.db.util.LogUtil;

import commontypes.accountmgmt.avs.accenture.ExternalOfferType;
import commontypes.accountmgmt.avs.accenture.UpdateProfileType;

public class UpdateComProfileManager {

	private static final String TYPE_SUBSCRIPTION = "SUBSCRIPTION";
	private static final String STATUS_TYPE_CANCELLED = "CANCELED";
	private static final String STATUS_TYPE_ACTIVE = "ACTIVE";
	private static final String PAYMENT_TYPE_INVOICE = "INVOICE";
	private static final String VOUCHER_PREFIX = "VID_";

	private List<UpdateProfileType> profileList;
	private String crmAccountId;
	private Long userId;
	private String tenantName;
	ConfigurationData cd = null;

	public UpdateComProfileManager(List<UpdateProfileType> _profileList,
			String _crmAccountId, Long _userId, String _tenantName)
			throws Exception {
		profileList = _profileList;
		crmAccountId = _crmAccountId;
		userId = _userId;
		tenantName = _tenantName;
		cd = new ConfigurationData(tenantName, null, null, crmAccountId);
		cd.load();
	}

	// TODO: Check if solutionOfferId(crmProductId) exists on AVS DB
	public void doInsert() throws ProductBuyedException,Exception {
		Iterator<UpdateProfileType> profileIterator = profileList.iterator();
		Long avsTechnicalPackageId = 1L;
		Timestamp endDate = null;
		boolean useVoucherFlag = false;
		while (profileIterator.hasNext()) {

			UpdateProfileType item = profileIterator.next();

			if (item.getOperationType()
					.value()
					.equalsIgnoreCase(
							AccountMgmtServiceConstant.ADD_COMMERCIAL_PROFILE)) {
				/*
				 * check if user had previous purchased this product
				 */
				PurchaseTransaction ptBean = (PurchaseTransaction) AccountManagementDAO
						.getPurchaseTransactionBean(item.getCrmProductId(),
								null, userId, tenantName);
				if ( ptBean!=null ){
					// product/solution already buyed by user. Exit
					LogUtil.writeCommonLog("INFO", UpdateComProfileManager.class, "WS",
							"User :"+userId+" alredy buyed product/subscription : "+item.getCrmProductId());
					throw new ProductBuyedException();
				}
				PurchaseTransaction parent = getParentTransactionBean(item);

				String voucherCode = null;
				if (item.getExternalOfferList() != null) {
					List<ExternalOfferType> listOfferType = item
							.getExternalOfferList().getExternalOffer();

					voucherCode = item.getVoucherCode();

					if (voucherCode != null && voucherCode.length() > 0) {
						LogUtil.writeCommonLog("INFO",
								UpdateComProfileManager.class, "INTERNAL",
								"Use Voucher for purchase");
						Long voucherDuration = AccountManagementDAO
								.getVoucherDuration(voucherCode, tenantName);
						if (voucherDuration == null || voucherDuration < 1L)
							throw new Exception(
									"Error retrieving voucher duration for voucherCode : "
											+ voucherCode);

						endDate = getEndDateFromNow(voucherDuration);

						parent.setToken(VOUCHER_PREFIX + voucherCode);
						parent.setEndDate(endDate);
						parent.setItemType(5L);
						useVoucherFlag = true;

					}
					Iterator<ExternalOfferType> listOfferTypeIterator = listOfferType
							.iterator();

					List<Object> childList = new ArrayList<Object>();

					while (listOfferTypeIterator.hasNext()) {
						ExternalOfferType offerItem = listOfferTypeIterator
								.next();
						PurchaseTransaction child = getChildTransactionBean(
								offerItem, item.getCrmProductId(), endDate);

						if (offerItem.getPackageId().contains("_"))
							avsTechnicalPackageId = Long.parseLong(offerItem
									.getPackageId().split("_")[1]);
						else
							avsTechnicalPackageId = Long.parseLong(offerItem
									.getPackageId());

						AccountTechnicalPkg childTp = getAccountTechnicalPkgBean(
								avsTechnicalPackageId, cd.getUserId(), endDate);

						if (useVoucherFlag)
							child.setItemType(5L);
						childList.add(child);

						// if useVoucher is set true, then check if
						// accountTechnicalPackage exists...
						// if yes, do nothing
//						if (useVoucherFlag) {
//							List<AccountTechnicalPkg> list = AccountManagementDAO
//									.getAccountTechPackage(
//											childTp.getTechPackageId(),
//											cd.getUserId(), tenantName);
//							if (list == null || list.size() == 0)
//								childList.add(childTp);
//						} else
							childList.add(childTp);
					}
					childList.add(parent);
					// childList.add(parentTp);
					// store data just readed from xml to Db...
					try {
						AccountManagementDAO.addPurchaseTransaction(childList,
								tenantName);
					} catch (Exception e) {
						LogUtil.writeErrorLog(UpdateComProfileManager.class,
								"Error insert purchaseTransaction for productId : "
										+ item.getCrmProductId(), e);
						throw e;
					}
				}

			}
		}

	}

	public void doDelete() throws Exception {
		Iterator<UpdateProfileType> profileIterator = profileList.iterator();

		while (profileIterator.hasNext()) {

			UpdateProfileType item = profileIterator.next();

			if (item.getOperationType()
					.value()
					.equalsIgnoreCase(
							AccountMgmtServiceConstant.DELETE_COMMERCIAL_PROFILE)) {

				List<Object> dbBean = new ArrayList<Object>();
				List<AccountTechnicalPkg> atpBean = new ArrayList<AccountTechnicalPkg>();
				PurchaseTransaction parent = null;
				Long productId = item.getCrmProductId();
				// AccountTechnicalPkg parentATP = null;
				try {
					parent = AccountManagementDAO.getPurchaseTransactionBean(
							productId, null, userId, tenantName);
				} catch (Exception e) {
					LogUtil.writeErrorLog(UpdateComProfileManager.class,
							"Error retrieving purchaseTransaction for productId : "
									+ item.getCrmProductId(), e);
					throw new Exception(e);
				}
				//TODO: Check technical package on voucher
				// try {
				// parentATP = AccountManagementDAO.getAccountTechPackage(
				// item.getCrmProductId(), userId, tenantName);
				// } catch (Exception e) {
				// LogUtil.writeErrorLog(UpdateComProfileManager.class,
				// "Error retrieving AccountTechnicalPkg for packageId : "
				// + item.getCrmProductId(), e);
				// throw new Exception(e);
				// }
				if (parent == null) {
					LogUtil.writeErrorLog(UpdateComProfileManager.class,
							"No purchaseTransaction/AccountTechnicalPkg for packageId : "
									+ item.getCrmProductId(), null);
					throw new Exception(
							"No purchaseTransaction/AccountTechnicalPkg for packageId : "
									+ item.getCrmProductId());
				}

				parent.setEndDate(Utils.getDate(null));
				parent.setState(cd.getStatusTypeMap()
						.get(STATUS_TYPE_CANCELLED));

				dbBean.add(parent);
				// atpBean.add(parentATP);

				if (item.getExternalOfferList() != null) {
					List<ExternalOfferType> listOfferType = item
							.getExternalOfferList().getExternalOffer();

					Iterator<ExternalOfferType> listOfferTypeIterator = listOfferType
							.iterator();

					List<PurchaseTransaction> childList = null;

					while (listOfferTypeIterator.hasNext()) {
						ExternalOfferType offerItem = listOfferTypeIterator
								.next();
						Long realPackageId = Long.parseLong(offerItem
								.getPackageId().split("_")[1]);
						PurchaseTransaction child = null;
						AccountTechnicalPkg childATP = null;
						try {

							child = AccountManagementDAO
									.getPurchaseTransactionBean(realPackageId,
											productId, userId, tenantName);
						} catch (Exception e) {
							LogUtil.writeErrorLog(
									UpdateComProfileManager.class,
									"No purchaseTransaction for child productId : "
											+ offerItem.getPackageId(), null);
							throw new Exception(
									"No purchaseTransaction for child productId : "
											+ offerItem.getPackageId());
						}
						try {
							childATP = AccountManagementDAO
									.getAccountTechPackage(realPackageId,
											userId, tenantName).get(0);
						} catch (Exception e) {
							LogUtil.writeErrorLog(
									UpdateComProfileManager.class,
									"No AccountTechnicalPkg for child packageId : "
											+ offerItem.getPackageId(), null);
							throw new Exception(
									"No AccountTechnicalPkg for child packageId : "
											+ offerItem.getPackageId());
						}
						if (child == null || childATP == null) {
							LogUtil.writeErrorLog(
									UpdateComProfileManager.class,
									"No purchaseTransaction/AccountTechnicalPkg for child productId : "
											+ offerItem.getPackageId(), null);
							throw new Exception(
									"No purchaseTransaction/AccountTechnicalPkg for child productId : "
											+ offerItem.getPackageId());
						}
						child.setEndDate(Utils.getDate(null));
						child.setState(cd.getStatusTypeMap().get(
								STATUS_TYPE_CANCELLED));
						dbBean.add(child);
						atpBean.add(childATP);
					}
					// store data just readed from xml to Db...
					try {
						AccountManagementDAO.addPurchaseTransaction(dbBean,
								tenantName);
						AccountManagementDAO.deleteAccountTechPackage(atpBean,
								tenantName);
					} catch (Exception e) {
						LogUtil.writeErrorLog(UpdateComProfileManager.class,
								"Error removing purchaseTransaction for productId : "
										+ item.getCrmProductId(), e);
					}
				}

			}
		}

	}

	private PurchaseTransaction getParentTransactionBean(UpdateProfileType item) {
		PurchaseTransaction ptb = new PurchaseTransaction();

		// from ws getPricyByUser
		String currency = "EUR";
		String price = "0.0";

		ptb.setCurrency(currency);
		ptb.setDiscountedPrice(price);
		ptb.setOriginalPrice(price);

		ptb.setItemId(Long.toString(item.getCrmProductId()));
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2013);
		cal.set(Calendar.MONTH, 10);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		ptb.setStartDate(Utils.getDate(null));
		ptb.setEndDate(Utils.getDate(cal));
		ptb.setState(cd.getStatusTypeMap().get(STATUS_TYPE_ACTIVE));
		ptb.setItemType(cd.getPurchaseTransactionTypeMap().get(
				TYPE_SUBSCRIPTION));
		ptb.setUserId(userId);
		ptb.setPaymentTypeId(cd.getPaymentTypeMap().get(PAYMENT_TYPE_INVOICE));
		return ptb;
	}

	private PurchaseTransaction getChildTransactionBean(ExternalOfferType item,
			Long parentId, Timestamp endDate) {
		PurchaseTransaction ptb = new PurchaseTransaction();

		// from ws getPricyByUser
		String currency = "EUR";
		String price = item.getPrice().toString();

		ptb.setCurrency(currency);
		ptb.setDiscountedPrice(price);
		ptb.setOriginalPrice(price);

		String realPackageId = item.getPackageId().split("_")[1];
		ptb.setItemId(realPackageId);
		ptb.setStartDate(Utils.getDate(null));

		if (endDate != null)
			ptb.setEndDate(endDate);
		else {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, 2013);
			cal.set(Calendar.MONTH, 10);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			ptb.setEndDate(Utils.getDate(cal));
		}
		ptb.setState(cd.getStatusTypeMap().get(STATUS_TYPE_ACTIVE));
		ptb.setItemType(cd.getPurchaseTransactionTypeMap().get(
				TYPE_SUBSCRIPTION));
		ptb.setParentItemId(parentId);
		ptb.setPaymentTypeId(cd.getPaymentTypeMap().get(PAYMENT_TYPE_INVOICE));
		ptb.setUserId(userId);
		return ptb;
	}

	private AccountTechnicalPkg getAccountTechnicalPkgBean(Long packageId,
			Long userId, Timestamp endDate) {
		AccountTechnicalPkg atp = new AccountTechnicalPkg();

		atp.setTechPackageId(packageId);
		atp.setUserId(userId);
		atp.setCrmAccountId(crmAccountId);
		atp.setCreationDate(Utils.getDate(null));
		if (endDate != null)
			atp.setValidityPeriod(endDate);
		return atp;
	}

	private Timestamp getEndDateFromNow(Long deltaDay) {
		Calendar cal = GregorianCalendar.getInstance();

		cal.setTimeInMillis(System.currentTimeMillis());
		cal.add(Calendar.DAY_OF_MONTH, deltaDay.intValue());

		return new Timestamp(cal.getTimeInMillis());
	}
}