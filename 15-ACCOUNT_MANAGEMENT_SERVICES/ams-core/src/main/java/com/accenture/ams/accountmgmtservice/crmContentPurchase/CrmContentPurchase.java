package com.accenture.ams.accountmgmtservice.crmContentPurchase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import wsclient.commontypes.accountmgmt.avs.accenture.ExternalOfferType;
import wsclient.commontypes.accountmgmt.avs.accenture.ExternalSolutionOffertType;
import wsclient.types.accountmgmt.avs.accenture.AccountMgmtResponse;
import wsclient.types.accountmgmt.avs.accenture.CrmContentPurchaseRequest;

import com.accenture.ams.accountmgmtservice.AccountMgmtServiceConstant;
import com.accenture.ams.accountmgmtservice.Utils;
import com.accenture.ams.db.bean.AccountContent;
import com.accenture.ams.db.bean.AccountPPV;
import com.accenture.ams.db.bean.AccountTechnicalPkg;
import com.accenture.ams.db.bean.LiveContent;
import com.accenture.ams.db.bean.LivePPV;
import com.accenture.ams.db.bean.PurchaseTransaction;
import com.accenture.ams.db.bean.StatusType;
import com.accenture.ams.db.dao.CrmPurchaseDAO;
import com.accenture.ams.db.util.LogUtil;
import com.accenture.ams.getpricebyuserservice.stub.component.ItemPrice;
import com.accenture.ams.getpricebyuserservice.stub.util.PaymentGatewayUtil;

public class CrmContentPurchase {

	/**
	 * tenantName for access right DB
	 */
	private String tenantName = null;

	/**
	 * operationType ( PURCHASE or REFUND )
	 */
	private String opType = null;

	/**
	 * operationDescription
	 */
	private String opDescription = null;

	/**
	 * user crmAccountId
	 */
	private String crmAccountId = null;

	/**
	 * purchase Channel ( default = CONSOLE )
	 */
	private String purchaseChannel = null;

	private Long externalSolutionOfferId = null;
	private ArrayList<ExternalOfferType> listOfOfferType = null;

	/**
	 * Map of packageId/externalOfferId involved in the call map key : packagId
	 * ( String ) map value : externalOfferId ( String )
	 */
	private HashMap<String, String> mapOfPackageExtOfferId = null;
	private ArrayList<String> listOfPackageId = null;
	private ArrayList<String> listOfExternaOfferId = null;

	/**
	 * Object storing all needed DB data for business logic
	 */
	private ConfigurationData cd = null;

	public CrmContentPurchase() {
		LogUtil.writeCommonLog("INFO", CrmContentPurchase.class,
				"CrmContentPurchase()",
				"CrmContentPurchase service object creation...");
	}

	public void readRequest(CrmContentPurchaseRequest crmContentPurchaseRequest)
			throws Exception {
		LogUtil.writeCommonLog("INFO", CrmContentPurchase.class,
				"readRequest()",
				"CrmContentPurchase service : start reading request...");
		try {
			tenantName = crmContentPurchaseRequest.getTenantName();

			opType = AccountMgmtServiceConstant.PURCHASE_DEF_OP;
			if (crmContentPurchaseRequest.getOperationType() != null)
				opType = crmContentPurchaseRequest.getOperationType().value();

			opDescription = crmContentPurchaseRequest.getOperationDescription();
			crmAccountId = crmContentPurchaseRequest.getCrmAccountId();
			purchaseChannel = crmContentPurchaseRequest.getPurchaseChannel();

			ExternalSolutionOffertType extOfferType = crmContentPurchaseRequest
					.getExternalSolutionOffer();
			if (extOfferType != null) {
				externalSolutionOfferId = extOfferType
						.getExternalSolutionOfferId();
				listOfOfferType = (ArrayList<ExternalOfferType>) extOfferType
						.getExternalOfferList().getExternalOffer();

				if (listOfOfferType != null && listOfOfferType.size() > 0) {
					mapOfPackageExtOfferId = new HashMap<String, String>();
					listOfPackageId = new ArrayList<String>();
					listOfExternaOfferId = new ArrayList<String>();

					Iterator<ExternalOfferType> it = listOfOfferType.iterator();

					while (it.hasNext()) {
						ExternalOfferType item = it.next();
						listOfPackageId.add(item.getPackageId().split("_")[1]);
						listOfExternaOfferId.add(item.getExternalOfferId());
						mapOfPackageExtOfferId.put(item.getPackageId(),
								item.getExternalOfferId());
					}
				}
			}
			LogUtil.writeCommonLog("INFO", CrmContentPurchase.class,
					"readRequest()",
					"CrmContentPurchase service : End reading request.");
		} catch (Exception e) {
			LogUtil.writeCommonLog("INFO", CrmContentPurchase.class,
					"readRequest()",
					"Error in request reading : " + e.getMessage());
			throw new Exception(e);
		}
	}

	public void loadConfigurationData() throws Exception {
		try {
			cd = new ConfigurationData(tenantName, listOfPackageId,
					listOfExternaOfferId, crmAccountId);
			cd.load();
		} catch (Exception e) {
			LogUtil.writeCommonLog("INFO", CrmContentPurchase.class,
					"loadConfigurationData()",
					"Error in data loading from DB : " + e.getMessage());
			throw new Exception(e);
		}
	}

	public AccountMgmtResponse execute() throws Exception {
		if (opType
				.equalsIgnoreCase(AccountMgmtServiceConstant.PURCHASE_OP_PURCHASE))
			return executePurchase();
		else if (opType
				.equalsIgnoreCase(AccountMgmtServiceConstant.PURCHASE_OP_REFUND))
			return executeRefund();
		else
			return Utils.getResponse(
					AccountMgmtServiceConstant.RET_REQ_NOT_ALLOWED,
					"operationType MUST BE PURCHASE or REFUND");

	}

	public AccountMgmtResponse executeRefund() throws Exception {
		Set<String> pkgList = mapOfPackageExtOfferId.keySet();
		Iterator<String> packageIdIterator = pkgList.iterator();

		PurchaseTransaction bean = null;
		List<Object> hbnBean = new ArrayList<Object>();
		while (packageIdIterator.hasNext()) {
			String packageItem = packageIdIterator.next();
			try {
				bean = updatePurchaseTransaction(packageItem);
			} catch (Exception e) {
				LogUtil.writeCommonLog(
						"INFO",
						CrmContentPurchase.class,
						"executeRefund()",
						"Error in execute request for refund : "
								+ e.getMessage());
				throw new Exception(e);
			}
			hbnBean.add(bean);
		}
		try {
			CrmPurchaseDAO.insertUpdate(hbnBean, tenantName);
		} catch (Exception e) {
			LogUtil.writeCommonLog("INFO", CrmContentPurchase.class,
					"execute()", "Error in execute request : " + e.getMessage());
			throw new Exception(e);
		}
		return Utils.getResponse(AccountMgmtServiceConstant.RET_OK, "");

	}

	public AccountMgmtResponse executePurchase() throws Exception {
		ArrayList<Object> hbnBean = new ArrayList<Object>();
		try {
			Set<String> pkgList = mapOfPackageExtOfferId.keySet();
			Iterator<String> packageIdIterator = pkgList.iterator();

			while (packageIdIterator.hasNext()) {
				String packageItem = packageIdIterator.next();

				String[] pkgInfo = packageItem.split("_");

				Object bean = null;
				Object beanOld = null;
				if (pkgInfo[0]
						.equalsIgnoreCase(AccountMgmtServiceConstant.PKG_TYPE_PPV)) {
					String externalId = mapOfPackageExtOfferId.get(packageItem);
					bean = getPurchaseTransactionBeanPpv(packageItem);
					beanOld = getAccountPPVBean(packageItem, externalId);

				} else if (pkgInfo[0]
						.equalsIgnoreCase(AccountMgmtServiceConstant.PKG_TYPE_VOD)) {
					bean = getPurchaseTransactionBeanVod(packageItem);
					beanOld = getAccountContentBean(packageItem);
				} else {
					bean = getPurchaseTransactionBeanBundle(packageItem);
					beanOld = getAccountTechnicalPkgBean(packageItem);
				}
				if (bean != null)
					hbnBean.add(bean);
				if (beanOld != null)
					hbnBean.add(beanOld);
			}
		} catch (Exception e) {
			LogUtil.writeCommonLog("INFO", CrmContentPurchase.class,
					"execute()", "Error in execute request : " + e.getMessage());
			throw new Exception(e);
		}

		try {
			CrmPurchaseDAO.insertUpdate(hbnBean, tenantName);
		} catch (Exception e) {
			LogUtil.writeCommonLog("INFO", CrmContentPurchase.class,
					"execute()", "Error in execute request : " + e.getMessage());
			throw new Exception(e);
		}
		return Utils.getResponse(AccountMgmtServiceConstant.RET_OK, "");
	}

	private AccountContent getAccountContentBean(String pkgId) {

		AccountContent ac = new AccountContent();
		ac.setCreationDate(Utils.getDate(null));

		ac.setPriceCategoryId("");

		StatusType type = new StatusType();
		type.setStatusId(new Long(1));
		type.setStatusName("Active");
		type.setStatusDescription("Active");
		ac.setStatusType(type);

		Long avsContentId = Long.parseLong(pkgId.split("_")[1]);
		String externalId = mapOfPackageExtOfferId.get(pkgId);
		ac.setContentId(avsContentId);
		ac.setExternalId(externalId);
		ac.setCrmaccountid(crmAccountId);

		ac.setOfferDescription(opDescription);
		return ac;
	}

	private AccountPPV getAccountPPVBean(String pkgId, String externalId)
			throws Exception {


		LivePPV livePpvInfo = CrmPurchaseDAO.getLivePpvByContentId(externalId,
				tenantName);
		LiveContent liveContentInfo = CrmPurchaseDAO.getLiveContentByContentId(
				livePpvInfo.getLiveContentId(), tenantName);

		if (livePpvInfo == null || liveContentInfo == null)
			throw new Exception("pkgId : '" + externalId
					+ "' not found in platform!!");
		
		AccountPPV appv = new AccountPPV();
		appv.setCrmAccountId(crmAccountId);	
		appv.setLiveContentId(liveContentInfo.getLiveContentId());
		appv.setPackageId(livePpvInfo.getPackageId());
		appv.setPrice(livePpvInfo.getPrice());
		appv.setStartDate(liveContentInfo.getStartTime());
		appv.setEndDate(liveContentInfo.getEndTime());
		appv.setExternalId(externalId);
		appv.setUserId(cd.getUserId());
		return appv;
	}

	private PurchaseTransaction getPurchaseTransactionBeanVod(String pkgId) {
		String itemId = mapOfPackageExtOfferId.get(pkgId);
		
		PurchaseTransaction ptb = new PurchaseTransaction();

		ItemPrice ip = (ItemPrice)PaymentGatewayUtil.getContentPrice(Long.parseLong(itemId), cd.getUserId());
		ptb.setPrice( Double.toString(ip.getPrice()) );
		ptb.setDiscountedPrice( Double.toString(ip.getPriceDiscounted() ) );
		ptb.setCurrency( ip.getCurrency() );

		ptb.setStartDate(Utils.getDate(null));
		// ptb.setEndDate(Utils.getDate(15));

		ptb.setUserId(cd.getUserId());
		
		ptb.setItemId(itemId);
		Long itemType = cd.getPurchaseTransactionTypeMap().get("TVOD");
		ptb.setItemType(itemType);

		Long stateId = cd.getStatusTypeMap().get(
				AccountMgmtServiceConstant.PURCHASE_STATUS_COMPLETED);
		ptb.setState(stateId);

		Long paymentTypeId = cd.getPaymentTypeMap().get(
				AccountMgmtServiceConstant.DEF_PURCHASE_TYPE);
		ptb.setPaymentTypeId(paymentTypeId);

		return ptb;
	}

	private PurchaseTransaction getPurchaseTransactionBeanPpv(String pkgId)
			throws Exception {
		

		Long itemType = cd.getPurchaseTransactionTypeMap().get("PPV");
		Long stateId = cd.getStatusTypeMap().get(
				AccountMgmtServiceConstant.PURCHASE_STATUS_COMPLETED);
		Long paymentTypeId = cd.getPaymentTypeMap().get(
				AccountMgmtServiceConstant.DEF_PURCHASE_TYPE);
		String itemIdLong = mapOfPackageExtOfferId.get(pkgId);

		LivePPV contentInfo = CrmPurchaseDAO.getLivePpvByContentId(itemIdLong,
				tenantName);
		
		if (contentInfo == null)
			throw new Exception("externalId : '" + itemIdLong
					+ "' not found in platform!!");		
		
		LiveContent liveContentInfo = CrmPurchaseDAO.getLiveContentByContentId(
				contentInfo.getLiveContentId(), tenantName);
		

		if (liveContentInfo == null)
			throw new Exception("externalId : '" + itemIdLong
					+ "' not found in platform!!");

		PurchaseTransaction ptb = new PurchaseTransaction();
		ItemPrice ip = (ItemPrice)PaymentGatewayUtil.getPPVPrice(Long.parseLong(itemIdLong), cd.getUserId());
		ptb.setPrice( Double.toString(ip.getPrice()) );
		ptb.setDiscountedPrice( Double.toString(ip.getPriceDiscounted() ) );
		ptb.setCurrency( ip.getCurrency() );
		ptb.setStartDate(liveContentInfo.getStartTime());
		ptb.setEndDate(liveContentInfo.getEndTime());
		ptb.setUserId(cd.getUserId());
		ptb.setItemId(itemIdLong);
		ptb.setItemType(itemType);
		ptb.setState(stateId);
		ptb.setPaymentTypeId(paymentTypeId);
		return ptb;
	}

	private PurchaseTransaction getPurchaseTransactionBeanBundle(String pkgId) {
		Long stateId = cd.getStatusTypeMap().get(
				AccountMgmtServiceConstant.PURCHASE_STATUS_COMPLETED);
		Long paymentTypeId = cd.getPaymentTypeMap().get(
				AccountMgmtServiceConstant.DEF_PURCHASE_TYPE);
		TechPackageBean tpb = cd.getMapOfTechPkg().get(pkgId.split("_")[1]);
		String itemId = tpb.getExternalId();
		String startDate = tpb.getStartDate();
		String endDate = null;

		if (tpb.getEndDate() != null && tpb.getEndDate().length() > 0) {
			endDate = tpb.getEndDate();
		} else if (tpb.getValidityPeriod() != null
				&& tpb.getValidityPeriod().length() > 0) {
			Utils.getDateString(null);
		}
		PurchaseTransaction ptb = new PurchaseTransaction();
		ItemPrice ip = (ItemPrice)PaymentGatewayUtil.getBundlePrice(Long.parseLong(itemId), cd.getUserId());
		ptb.setPrice( Double.toString(ip.getPrice()) );
		ptb.setDiscountedPrice( Double.toString(ip.getPriceDiscounted() ) );
		ptb.setCurrency( ip.getCurrency() );		
		ptb.setItemId(itemId);
		ptb.setState(stateId);
		// ptb.setStartDate(Utils.getDate(null));
		// ptb.setEndDate(Utils.getDate(15));
		ptb.setUserId(cd.getUserId());
		ptb.setPaymentTypeId(paymentTypeId);
		return ptb;
	}

	private AccountTechnicalPkg getAccountTechnicalPkgBean(String pkgId) {
		AccountTechnicalPkg atp = new AccountTechnicalPkg();
		atp.setTechPackageId(Long.parseLong(pkgId));
		atp.setUserId(cd.getUserId());
		return atp;
	}

	private PurchaseTransaction updatePurchaseTransaction(String pkgId)
			throws Exception {
		String itemId = mapOfPackageExtOfferId.get(pkgId);
		Long userId = cd.getUserId();
		PurchaseTransaction pt = CrmPurchaseDAO.getPurchaseTransaction(itemId,
				userId, tenantName);
		Long state = cd.getStatusTypeMap().get(
				AccountMgmtServiceConstant.PURCHASE_STATUS_REFUNDED);
		pt.setState(state);
		pt.setRefundDate(Utils.getDate(null));
		pt.setRefundDescription(opDescription);
		// pt.setRefundPrice(refundPrice);
		// pt.setRefundTransactionId(refundTransactionId);
		return pt;
	}
}
