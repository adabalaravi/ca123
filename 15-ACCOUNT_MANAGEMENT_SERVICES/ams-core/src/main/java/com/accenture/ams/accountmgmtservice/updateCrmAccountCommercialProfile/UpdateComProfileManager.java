package com.accenture.ams.accountmgmtservice.updateCrmAccountCommercialProfile;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import wsclient.commontypes.accountmgmt.avs.accenture.ExternalOfferType;
import wsclient.commontypes.accountmgmt.avs.accenture.UpdateProfileType;

import com.accenture.ams.accountmgmtservice.AccountMgmtServiceConstant;
import com.accenture.ams.accountmgmtservice.ProductBuyedException;
import com.accenture.ams.accountmgmtservice.Utils;
import com.accenture.ams.accountmgmtservice.crmContentPurchase.ConfigurationData;
import com.accenture.ams.db.bean.AccountTechnicalPkg;
import com.accenture.ams.db.bean.PurchaseTransaction;
import com.accenture.ams.db.bean.SdpSolutionOfferView;
import com.accenture.ams.db.bean.SdpVoucherCampaignView;
import com.accenture.ams.db.bean.SdpVoucherView;
import com.accenture.ams.db.dao.AccountManagementDAO;
import com.accenture.ams.db.util.LogUtil;

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
		Timestamp startDate = null;
		boolean useVoucherFlag = false;
		BigDecimal transactionPrice = new BigDecimal(0);
		while (profileIterator.hasNext()) {

			UpdateProfileType item = profileIterator.next();

			if (item.getOperationType().value().equalsIgnoreCase(AccountMgmtServiceConstant.ADD_COMMERCIAL_PROFILE)) {
				/*
				 * check if user had previous purchased this productCALL TYPE: WS | Generic Exception adding subscription/packages : null
				 */
				List<PurchaseTransaction> ptList = AccountManagementDAO.getPurchaseTransactionBean(item.getCrmProductId(),null, userId, tenantName);
				
				PurchaseTransaction ptBean=null;
				if ( ptList==null || ptList.size() == 0)
					ptBean=null;
				else
					ptBean = ptList.get(0);
				if ( ptBean!=null ){
					// product/solution already buyed by user. Exit
					LogUtil.writeCommonLog("INFO", UpdateComProfileManager.class, "WS",	"User :"+userId+" already buyed product/subscription : "+item.getCrmProductId());
					throw new ProductBuyedException();
				}
				PurchaseTransaction parent = getParentTransactionBean(item);

				String voucherCode = null;
				SdpVoucherView sdpVoucherView = null;
				if (item.getExternalOfferList() != null) {
					List<ExternalOfferType> listOfferType = item.getExternalOfferList().getExternalOffer();

					voucherCode = item.getVoucherCode();

					if (voucherCode != null && voucherCode.length() > 0) {
						LogUtil.writeCommonLog("INFO",	UpdateComProfileManager.class, "INTERNAL","Use Voucher for purchase");

						sdpVoucherView = AccountManagementDAO.getVoucherDetails(voucherCode, tenantName);
						
						if(sdpVoucherView == null){
							LogUtil.writeCommonLog("ERROR",UpdateComProfileManager.class, "WS", "Error retrieving voucher details for voucherCode"+voucherCode);
							throw new Exception("Error retrieving voucher details for voucherCode : "+ voucherCode);
						} else {
							useVoucherFlag = true;
							parent.setToken(VOUCHER_PREFIX + voucherCode);
							parent.setItemType(5L);
						}
					}
					Iterator<ExternalOfferType> listOfferTypeIterator = listOfferType.iterator();

					List<Object> childList = new ArrayList<Object>();

					while (listOfferTypeIterator.hasNext()) {
						Timestamp currentDay = new Timestamp(System.currentTimeMillis());

						ExternalOfferType offerItem = listOfferTypeIterator.next();
						
						//retrieve sdp solution offer information bean
						SdpSolutionOfferView sso = AccountManagementDAO.getSolutionOffer(item.getCrmProductId(), tenantName);
						if ( sso==null ){
							throw new Exception("SolutionOffer(crmProductId) '"+item.getCrmProductId()+"' is NOT valid!!");							
						}
						
						if ( sso.getEndDate()!=null)
							endDate = new Timestamp(sso.getEndDate().getTime());
						
						if ( sso.getStartDate()!=null ) {
							/*if(sso.getStartDate().after(currentDay)) {
								LogUtil.writeCommonLog("ERROR",UpdateComProfileManager.class, "WS", "SOLUTION OFFER's START DATE IS IN FUTURE;");
									throw new Exception("COMM PACKAGE START DATE IS IN FUTURE");
							}*/
							startDate = new Timestamp(sso.getStartDate().getTime());
						}
						
						// Check for the Duration of SolutionOffer & set endDate based on Duration
							endDate = getEndDateofSolutionOffer(sso);
						
						// If Voucher is used for purchase, startDate and EndDate in PurchaseTransaction will NOT be the StartDate and EndDate of SolutionOffer.
						if(useVoucherFlag) {

							LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class, "WS", "USING VOUCHER;");
							SdpVoucherCampaignView sdpVoucherCampaignView = sdpVoucherView.getSdpVoucherCampaignView();

							Timestamp solutionOfferStartDate = null;
							Timestamp solutionOfferEndDate = null;
							
							/*if(sso.getStartDate()==null && sso.getEndDate()==null) {
								LogUtil.writeCommonLog("ERROR",UpdateComProfileManager.class, "WS", "SOLUTION OFFER START DATE AND END DATE ARE NULL;");
								throw new Exception("Solution Offer Start Date and End Date are Null");
							}*/
							
							if(sso.getStartDate()==null) {
								LogUtil.writeCommonLog("ERROR",UpdateComProfileManager.class, "WS", "SOLUTION OFFER START DATE IS NULL;");
								solutionOfferStartDate = currentDay;
							} else {
								solutionOfferStartDate = new Timestamp(sso.getStartDate().getTime());
							}
							if(sso.getEndDate()!=null) {
								solutionOfferEndDate = new Timestamp(sso.getEndDate().getTime());
							}
							
							LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class, "WS", "SOLUTION OFFER START DATE --- "+sso.getStartDate()+";" +
																								"SOLUTION OFFER END DATE --- "+sso.getEndDate()+";"+
																								"SOLUTION OFFER DURATION --- "+sso.getDuration()+";"+
																								"VOUCHER CAMP START DATE --- "+sdpVoucherCampaignView.getStartDate()+";"+
																								"VOUCHER CAMP END DATE --- "+sdpVoucherCampaignView.getEndDate()+";"+
																								"VOUCHER CAMP VALIDTY PERIOD --- "+sdpVoucherCampaignView.getValidityPeriod()+";");
							// Start Date for Voucher Purchase
							// When Voucher Camp's StartDate and Validity are NULL
							if(sdpVoucherCampaignView.getStartDate() == null && (sdpVoucherCampaignView.getValidityPeriod() == null || sdpVoucherCampaignView.getValidityPeriod() == 0)) {
								LogUtil.writeCommonLog("ERROR",UpdateComProfileManager.class, "WS", "VOUCHER CAMPAIGN START DATE AND VALIDITYPERIOD ARE NULL");
								throw new Exception("Voucher Campaign Start Date and ValidityPeriod are Null");
							}
							
							// Voucher Camp StartDate is NULL
							if(sdpVoucherCampaignView.getStartDate() == null) {
								LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class, "WS", "VOUCHER CAMP START DATE IS NULL;");
								startDate = currentDay;
							 }
							
							// If StartDate is CurrentDay then check with SolutionOffer StartDate
							if(startDate!=null && startDate.equals(currentDay)) {
								// If CurrentDay is before SolutionOffer StartDate
								if(checkBeforeDate(currentDay, solutionOfferStartDate)) {
									startDate = solutionOfferStartDate;
									LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class, "WS", "CURRENTDAY IS BEFORE SOLUTIONOFFER STARTDATE --- "+startDate+";");
								}	
								// If CurrentDay is after SolutionOffer StartDate and before SolutionOffer EndDate
								if(checkAfterDate(currentDay, solutionOfferStartDate) && checkBeforeDate(currentDay, solutionOfferEndDate)) {
									startDate = currentDay;
									LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class, "WS", "CURRENTDAY IS AFTER SOLUTIONOFFER STARTDATE AND BEFORE SOLUTIONOFFER ENDDATE--- "+startDate+";");
								}	
								// If CurrentDay is after SolutionOffer StartDate and after SolutionOffer EndDate
								if(checkAfterDate(currentDay, solutionOfferStartDate) && solutionOfferEndDate!=null && checkAfterDate(currentDay, solutionOfferEndDate))
									throw new Exception("Solution Offer has Expired");
							}

							// Voucher Camp StartDate is NOT NULL
							// Cannot use the voucher which starts in Future. This check is already done at the SDP FE.
							
							if(sdpVoucherCampaignView.getStartDate()!=null) {
								if(checkAfterDate(sdpVoucherCampaignView.getStartDate(), currentDay)) {
									LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class, "WS", "VOUCHER CAMP START DATE IS NOT NULL---- "+startDate+";");
									throw new Exception("VOUCHER CAMP START DATE IS IN FUTURE");
								}
							}
							if(sdpVoucherCampaignView.getStartDate()!=null) {
								startDate = getStartDateforVoucherPurchase(sso,sdpVoucherCampaignView.getStartDate(),currentDay);
								LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class, "WS", "VOUCHER CAMP START DATE IS NOT NULL ---- "+startDate+";");
							}
							
							if(startDate!=null && solutionOfferEndDate!=null) {
								// If Purchase StartDate is after SolutionOffer EndDate
								if(checkAfterDate(startDate, solutionOfferEndDate)) {
									LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class, "WS", "VOUCHER CAMP STARTDATE IS AFTER SOLUTIONOFFER ENDDATE;");
									throw new Exception("Voucher's Start Date is after End Date of Solution Offer");
								}
							}
							// If still startDate is null
							if(startDate == null) {
								startDate = currentDay;
							}
							
							
							// End Date for Voucher Purchase
							if(sdpVoucherCampaignView.getEndDate() == null && sdpVoucherCampaignView.getValidityPeriod() != null) {
								// Calculate the EndDate based on ValidityPeriod
								endDate = getVoucherPurchaseEndDate(startDate,sdpVoucherCampaignView.getEndDate(),sdpVoucherCampaignView.getValidityPeriod(),sso);
								LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class, "WS", "VOUCHER CAMP END DATE IS NULL AND VALIDITY IS NOT NULL ---- "+endDate+";");
							}
							if(sdpVoucherCampaignView.getEndDate() != null && sdpVoucherCampaignView.getValidityPeriod() != null) {
								// Calculate the EndDate based on ValidityPeriod
								endDate = getVoucherPurchaseEndDate(startDate,sdpVoucherCampaignView.getEndDate(),sdpVoucherCampaignView.getValidityPeriod(),sso);
								LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class, "WS", "VOUCHER CAMP END DATE IS NOT NULL AND VALIDITY IS NOT NULL ---- "+endDate+";");
							}
							if(sdpVoucherCampaignView.getEndDate()!=null && sdpVoucherCampaignView.getValidityPeriod() == null) {
								endDate = sdpVoucherCampaignView.getEndDate();
								LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class, "WS", "VOUCHER CAMP END DATE IS NOT NULL AND VALIDITY IS NULL ---- "+endDate+";");
							}
							if(sdpVoucherCampaignView.getValidityPeriod() == null && sdpVoucherCampaignView.getEndDate() == null) {
								endDate = getEndDateofSolutionOffer(sso);
								LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class, "WS", "VOUCHER CAMP END DATE AND VALIDITY ARE NULL ---- "+endDate+";");
							}
							
							//Check if Voucher Purchase EndDate after adding ValidityPeriod is after SolutionOffer's EndDate 
							if(endDate !=null && solutionOfferEndDate!=null) {
								if(checkAfterDate(endDate, solutionOfferEndDate)) {
									endDate = getEndDateofSolutionOffer(sso);
									LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class, "WS", "PURCHASE END DATE ID AFTER SOLUTIONOFFER'S END DATE---- "+endDate+";");
								}
							}	
						}

						transactionPrice = transactionPrice.add(offerItem.getPrice());
						LogUtil.writeCommonLog("INFO", UpdateComProfileManager.class, "WS","after transactionPrice :"+transactionPrice.toString()+" GetPrice: "+offerItem.getPrice().toString());
						PurchaseTransaction child = getChildTransactionBean(offerItem, item.getCrmProductId(), endDate, startDate);

						if (offerItem.getPackageId().contains("_"))
							avsTechnicalPackageId = Long.parseLong(offerItem.getPackageId().split("_")[1]);
						else
							avsTechnicalPackageId = Long.parseLong(offerItem.getPackageId());

						AccountTechnicalPkg childTp = getAccountTechnicalPkgBean(avsTechnicalPackageId, cd.getUserId(), endDate, startDate);

						childList.add(child);

						childList.add(childTp);
					}
					if ( endDate!=null)
						parent.setEndDate(endDate);
					
					if ( startDate==null)
						startDate = new Timestamp( System.currentTimeMillis() );
					
					parent.setStartDate(startDate);
					parent.setDiscountedPrice(transactionPrice.toString());
					parent.setOriginalPrice(transactionPrice.toString());
					LogUtil.writeCommonLog("INFO", UpdateComProfileManager.class, "WS",	"PUCHASE TRAN PARENT STARTDATE AND ENDDATE :"+parent.getStartDate()+"----"+parent.getEndDate()+";");
					LogUtil.writeCommonLog("INFO", UpdateComProfileManager.class, "WS",	"parent getTransactionPrice:"+parent.getDiscountedPrice().toString());
					childList.add(parent);
					// childList.add(parentTp);
					// store data just readed from xml to Db...
					try {
						AccountManagementDAO.addPurchaseTransaction(childList,tenantName);
					} catch (Exception e) {
						LogUtil.writeErrorLog(UpdateComProfileManager.class,"Error insert purchaseTransaction for productId : "	+ item.getCrmProductId(), e);
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
					List<PurchaseTransaction> ptList = AccountManagementDAO.getPurchaseTransactionBean(
							productId, null, userId, tenantName);
					
					if ( ptList==null || ptList.size()==0 )
						throw new Exception("Unable to retrieve purchaseItem : "+productId);
					
					parent = ptList.get(0);
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
							List<PurchaseTransaction> ptList = AccountManagementDAO
									.getPurchaseTransactionBean(realPackageId,
											productId, userId, tenantName);
							
							if ( ptList==null || ptList.size()==0 )
								throw new Exception("Unable to retrieve purchaseItem : "+productId);
							
							child = ptList.get(0);
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
		
		LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class,"METHOD : getParentTransactionBean",";");
		PurchaseTransaction ptb = new PurchaseTransaction();

		// from ws getPricyByUser
		String currency = "AUD";
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
		//ptb.setEndDate(Utils.getDate(cal));
		ptb.setState(cd.getStatusTypeMap().get(STATUS_TYPE_ACTIVE));
		ptb.setItemType(cd.getPurchaseTransactionTypeMap().get(
				TYPE_SUBSCRIPTION));
		ptb.setUserId(userId);
		ptb.setPaymentTypeId(cd.getPaymentTypeMap().get(PAYMENT_TYPE_INVOICE));
		LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class,"METHOD : getParentTransactionBean ENDS",";");
		return ptb;
	}

	private PurchaseTransaction getChildTransactionBean(ExternalOfferType item,
			Long parentId, Timestamp endDate, Timestamp startDate) {
		LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class,"METHOD : getChildTransactionBean STARTS",";");
		LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class,"METHOD : getChildTransactionBean START DATE ---- "+startDate,";");
		LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class,"METHOD : getChildTransactionBean END DATE ---- "+endDate,";");
		PurchaseTransaction ptb = new PurchaseTransaction();

		// from ws getPricyByUser
		String currency = "AUD";
		String price = item.getPrice().toString();

		ptb.setCurrency(currency);
		ptb.setDiscountedPrice(price);
		ptb.setOriginalPrice(price);
		ptb.setDiscountedPrice(price);
		
		String realPackageId = item.getPackageId().split("_")[1];
		ptb.setItemId(realPackageId);
		
		if ( startDate==null)
			startDate = new Timestamp(System.currentTimeMillis());
		ptb.setStartDate(startDate);

		if (endDate != null)
			ptb.setEndDate(endDate);
//		else {
//			Calendar cal = Calendar.getInstance();
//			cal.set(Calendar.YEAR, 2013);
//			cal.set(Calendar.MONTH, 10);
//			cal.set(Calendar.DAY_OF_MONTH, 1);
//			ptb.setEndDate(Utils.getDate(cal));
//		}
		ptb.setState(cd.getStatusTypeMap().get(STATUS_TYPE_ACTIVE));
		ptb.setItemType(cd.getPurchaseTransactionTypeMap().get(TYPE_SUBSCRIPTION));
		ptb.setParentItemId(parentId);
		ptb.setPaymentTypeId(cd.getPaymentTypeMap().get(PAYMENT_TYPE_INVOICE));
		ptb.setUserId(userId);
		LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class,"METHOD : getChildTransactionBean ENDS",";");
		return ptb;
	}

	private AccountTechnicalPkg getAccountTechnicalPkgBean(Long packageId,
			Long userId, Timestamp endDate, Timestamp startDate) {
		AccountTechnicalPkg atp = new AccountTechnicalPkg();
		LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class,"METHOD : getAccountTechnicalPkgBean STARTS",";");
		LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class,"METHOD : getAccountTechnicalPkgBean START DATE ---- "+startDate,";");
		LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class,"METHOD : getAccountTechnicalPkgBean END DATE ---- "+endDate,";");
		
		atp.setTechPackageId(packageId);
		atp.setUserId(userId);
		atp.setCrmAccountId(crmAccountId);
		
		if (startDate==null) {
			LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class,"METHOD : getAccountTechnicalPkgBean START DATE IS NULL",";");
			startDate = new Timestamp( System.currentTimeMillis() );
		}
		atp.setCreationDate(startDate);

		if (endDate != null)
			atp.setValidityPeriod(endDate);
		LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class,"METHOD : getAccountTechnicalPkgBean ENDS",";");
		return atp;
	}

	private Timestamp getEndDateFromNow(Long deltaDay) {
		Calendar cal = GregorianCalendar.getInstance();

		cal.setTimeInMillis(System.currentTimeMillis());
		cal.add(Calendar.DAY_OF_MONTH, deltaDay.intValue());

		return new Timestamp(cal.getTimeInMillis());
	}
	
	/**
	 * @param vcstatDate
	 * @param vcendDate
	 * @param voucherValidity
	 * @return
	 * @throws Exception
	 */
	/* This method returns the end date for the purchase when purchase is made using voucher */
	private Timestamp getVoucherPurchaseEndDate(Timestamp vcstartDate,Timestamp vcendDate,Long voucherValidity,SdpSolutionOfferView sso) throws Exception{
		LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class, "WS", "Voucher Campaign StartDate and DURATION and ENDDATE: "
								+vcstartDate+" ------ "+voucherValidity+" ---------- "+vcendDate+";");
		
		if(voucherValidity!=null && voucherValidity > 0 ) { 
			LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class, "WS", "Voucher Campaign StartDate and DURATION : "+vcstartDate+" ------ "+voucherValidity+";");
			Calendar c = Calendar.getInstance(TimeZone.getTimeZone(AccountMgmtServiceConstant.REFERENCE_TIMEZONE));
			c.setTimeInMillis(vcstartDate.getTime()); // here vcstartDate will always be NOT NULL
			c.add(Calendar.DAY_OF_WEEK, voucherValidity.intValue());
			vcendDate = new java.sql.Timestamp(c.getTimeInMillis());
			LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class, "WS","ENDDATE OF VOUCHER CAMPAIGN AFTER ADDING VALIDITYPERIOD: "+vcendDate+";");
		}
		
		if(vcendDate == null) { // when Validity & EndDate of VoucherCampaign are null
			vcendDate = getEndDateofSolutionOffer(sso);
		}
		return vcendDate;
	}
	
	/**
	 * @param sso
	 * @return
	 */
	private Timestamp getEndDateofSolutionOffer(SdpSolutionOfferView sso){
		LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class, "WS", "getEndDateofSolutionOffer STARTS;");
		Timestamp endDatewithDuration = null;
		Timestamp currentDay = new Timestamp(System.currentTimeMillis());
		Long duration = sso.getDuration();
		if(duration !=null && duration >0){
			Timestamp startDate = null;
			if(sso.getStartDate() == null) { 
				startDate = currentDay;
				LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class, "WS", "SOLUTION OFFER StartDate IS NULL;");
				LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class, "WS", "ContentId  --- SOLUTION OFFER StartDate and SOLUTION OFFER DURATION : "+sso.getSolutionOfferID()+" ------ "+sso.getStartDate() +"---------"+duration.intValue() +";");
			}else {
				startDate = new java.sql.Timestamp(sso.getStartDate().getTime());
			}
			LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class, "WS", "ContentId  --- SOLUTION OFFER StartDate and SOLUTION OFFER DURATION : "+sso.getSolutionOfferID()+" ------ "+startDate +"---------"+duration.intValue() +";");
			Calendar c = Calendar.getInstance(TimeZone.getTimeZone(AccountMgmtServiceConstant.REFERENCE_TIMEZONE));
			c.setTimeInMillis(startDate.getTime());
			c.add(Calendar.DAY_OF_WEEK, duration.intValue());
			endDatewithDuration = new java.sql.Timestamp(c.getTimeInMillis());
			LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class, "WS","EndDate of comm pkg after adding duration : "+sso.getSolutionOfferID() +" ------ "+endDatewithDuration +";");
		}
		else {
			if(sso.getEndDate()!=null)
				endDatewithDuration = new java.sql.Timestamp(sso.getEndDate().getTime());
			else
				endDatewithDuration = null;
		}
		LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class, "WS","FINAL EndDate of comm pkg after adding duration : ------ "+endDatewithDuration +";");
		LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class, "WS", "getEndDateofSolutionOffer ENDS;");
		return endDatewithDuration;
	}
	
	private Timestamp getStartDateforVoucherPurchase(SdpSolutionOfferView sdpSolutionOfferView, Timestamp voucherStartDate,Timestamp currentDay) throws Exception {

			Timestamp purchaseStartDate = null;
			
			Timestamp solutionOfferStartDate = null;
			
				if(sdpSolutionOfferView.getStartDate()==null) {
					solutionOfferStartDate = currentDay;
				}else {
					solutionOfferStartDate = new Timestamp(sdpSolutionOfferView.getStartDate().getTime());
				}
			
				if(sdpSolutionOfferView.getEndDate()!=null) {
					 if(checkAfterDate(voucherStartDate,new Timestamp(sdpSolutionOfferView.getEndDate().getTime()))) {
						 throw new Exception("Voucher StartDate is after the EndDate of Solution Offer");
					 }
				} 
				LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class,"METHOD : getStartDateforVoucherPurchase STARTS --- VOUCHER START DATE ---- "+voucherStartDate,";");
						 
				  /* V - Voucher StartDate
				  *  C - CurrentDay
				  *  S - SolutionOffer StartDate
				  */
				 
				 if(checkBeforeDate(voucherStartDate,solutionOfferStartDate) && checkBeforeDate(solutionOfferStartDate,currentDay)) {  // V S C
					 purchaseStartDate = currentDay;
				 }
				 
				 if(checkBeforeDate(voucherStartDate,currentDay) && checkBeforeDate(currentDay,solutionOfferStartDate)) { // V C S
					 purchaseStartDate = solutionOfferStartDate;
				 }
				 
				 if(checkBeforeDate(solutionOfferStartDate,voucherStartDate) && checkBeforeDate(voucherStartDate,currentDay)) { // S V C
					 purchaseStartDate = currentDay;
				 }
				 
				 if(checkBeforeDate(solutionOfferStartDate,currentDay) && checkBeforeDate(currentDay,voucherStartDate)) { // S C V  // Not Possible
					 purchaseStartDate = voucherStartDate;
				 }
				 
				 if(checkBeforeDate(currentDay,voucherStartDate) && checkBeforeDate(voucherStartDate,solutionOfferStartDate)) { // C V S // Not Possible
					 purchaseStartDate = solutionOfferStartDate;
				 }
				 
				 if(checkBeforeDate(currentDay,solutionOfferStartDate) && checkBeforeDate(solutionOfferStartDate,voucherStartDate)) { // C S V // Not Possible
					 purchaseStartDate = voucherStartDate;
				 }
				if(purchaseStartDate == null) {
					LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class,"METHOD : getStartDateforVoucherPurchase  --- VOUCHER START DATE IS NULL AFTER ALL CHECKS",";");
					purchaseStartDate = voucherStartDate;
				}	
				LogUtil.writeCommonLog("INFO",UpdateComProfileManager.class,"METHOD : getStartDateforVoucherPurchase ENDS --- VOUCHER START DATE ---- "+purchaseStartDate,";");		 
		return purchaseStartDate;
	}
		
	private boolean checkBeforeDate(Timestamp t1, Timestamp t2) {
		if(t1 == null) {
			return false;
		}
		if(t2 == null) {
			return true;
		}
		if(t1.before(t2) || t1.equals(t2)){
			return true;
		}
		return false;
	}
	private boolean checkAfterDate(Timestamp t1, Timestamp t2) {
		if(t1 == null) {
			return false;
		}
		if(t2 == null) {
			return true;
		}
		if(t1.equals(t2) || t1.after(t2)){
			return true;
		}
		return false;
	}
	
}