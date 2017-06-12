package com.accenture.ams.accountmgmtservice.sdpPartyProfileDataService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.accenture.ams.accountmgmtservice.AccountMgmtServiceConstant;
import com.accenture.ams.accountmgmtservice.DbErrorException;
import com.accenture.ams.accountmgmtservice.GenericException;
import com.accenture.ams.accountmgmtservice.Utils;
import com.accenture.ams.accountmgmtservice.sdpPartyProfileDataService.bean.SdpPartyProfileDataServiceBean;
import com.accenture.ams.db.bean.AccountAttribute;
import com.accenture.ams.db.bean.PaymentType;
import com.accenture.ams.db.dao.SdpPartyProfileDAO;
import com.accenture.ams.db.util.LogUtil;

import types.accountmgmt.avs.accenture.SdpPartyProfileDataResponse;


public class SdpPartyProfileDataService {
	private String tenantName;

	/**
	 * 
	 * @param _tenantName
	 */
	public SdpPartyProfileDataService(String _tenantName) {
		this.tenantName = _tenantName;
	}
	
	
	/**
	 * 
	 * @param crmAccountId
	 * @param userId
	 * @return
	 * @throws DbErrorException
	 */
	public SdpPartyProfileDataResponse getSdpPartyProfileDataService(String crmAccountId, Long userId) throws DbErrorException, GenericException {
		SdpPartyProfileDataResponse sdpPartyProfileDataResponse = null;
		ArrayList<AccountAttribute> accountAttributesList = new ArrayList<AccountAttribute>();
		ArrayList<PaymentType> paymentTypeList = new ArrayList<PaymentType>();
		List<Object[]> crmAccUsr = null;
		String crmAccountVip = "N";
		boolean isUserPresent = false;
		boolean isCrmAccountPresent = false;
		
		SdpPartyProfileDataServiceBean sdpPartyProfileDataServiceBean = null;
		
		
		LogUtil.writeCommonLog("INFO", SdpPartyProfileDataService.class,
				"WS",
				"I'm in SdpPartyProfileDataService.getSdpPartyProfileDataService for userId: "
						+ userId + " crmAccountId: " + crmAccountId);
		
        /***************************************************/		

		try {
			//check if userId is present on table account_user
			isUserPresent = SdpPartyProfileDAO.isUserIdPresentAccountUser(userId, tenantName);			
		} 
		catch (Exception e) {
			LogUtil.writeErrorLog(SdpPartyProfileDataService.class, "RET_DB_ERROR", e);
			throw new DbErrorException(e.getMessage());
		}
			

		if(!isUserPresent){
			LogUtil.writeCommonLog("INFO", SdpPartyProfileDataService.class,"DB_QUERY",	"UserId is not present: " + userId);
			throw new GenericException("RET_USER_NOT_PRESENT");
		}
			
        /***************************************************/		

		try {			
			//check if crmAccountId is present on table crm_account
			isCrmAccountPresent = SdpPartyProfileDAO.isCrmAccountIdPresentCrmAccount(crmAccountId, tenantName);			
		} 
		catch (Exception e) {
			LogUtil.writeErrorLog(SdpPartyProfileDataService.class, "RET_DB_ERROR", e);
			throw new DbErrorException(e.getMessage());
		}
			
			
		if(!isCrmAccountPresent){
			LogUtil.writeCommonLog("INFO", SdpPartyProfileDataService.class,"DB_QUERY",	"crmAccountId is not present: " + crmAccountId);
			throw new GenericException("RET_CRM_ACC_NOT_EXIST");
		}
					
        /***************************************************/
			
		try {			
			//retrieving all paymentType configured on the system
			paymentTypeList = (ArrayList<PaymentType>)  SdpPartyProfileDAO.getPaymentTypeBean(tenantName);
		} 
		catch (Exception e) {
			LogUtil.writeErrorLog(SdpPartyProfileDataService.class, "RET_DB_ERROR", e);
			throw new DbErrorException(e.getMessage());
		}
			
			
		if (paymentTypeList == null){
			throw new DbErrorException("Error retrieving PaymentType");		
		}
		
		
        /***************************************************/	

		try {			
			//retrieving data from table crm_account and account_user 
			crmAccUsr = SdpPartyProfileDAO.getCrmAccountUser(userId, crmAccountId, tenantName);			
		} 
		catch (Exception e) {
			LogUtil.writeErrorLog(SdpPartyProfileDataService.class, "RET_DB_ERROR", e);
			throw new DbErrorException(e.getMessage());
		}
		
		if (crmAccUsr == null){
			throw new DbErrorException("Error retriving CrmAccount - AccountUser for userId: " + userId);		
		}
		
		/***************************************************/
		
		try {			
			sdpPartyProfileDataServiceBean = new SdpPartyProfileDataServiceBean();
			
			Iterator<Object[]> crmAccUsrIterator = crmAccUsr.iterator();
			
			//iterate data crm_account/account_user and setting a result bean
			while (crmAccUsrIterator.hasNext() ){
				Object[] currRec = crmAccUsrIterator.next();
				crmAccountVip = (String)currRec[0];				
				sdpPartyProfileDataServiceBean.setCrmAccountPurchaseBlockingFlag(Utils.stringToBoolean((String)currRec[1]));
				sdpPartyProfileDataServiceBean.setCrmAccountPurchaseBlockingThresholdValue(Utils.doubleToBigDecimal((Double)currRec[2]));
				sdpPartyProfileDataServiceBean.setCrmAccountPurchaseValue(Utils.doubleToBigDecimal((Double)currRec[3]));
				sdpPartyProfileDataServiceBean.setCrmAccountConsumption((Integer)currRec[4]);				
				sdpPartyProfileDataServiceBean.setCrmAccountPurchaseBlockingIntermediateThreshold(Utils.doubleToBigDecimal((Double)currRec[5]));
				sdpPartyProfileDataServiceBean.setCrmAccountConsumptionBlockingThresholdValue((Integer)currRec[6]);
			}	
			
			LogUtil.writeCommonLog("INFO", SdpPartyProfileDataService.class,"INTERNAL",	"partial result sdpPartyProfileDataServiceBean: " + sdpPartyProfileDataServiceBean);
			
		
		} 
		catch (Exception e) {
			LogUtil.writeErrorLog(SdpPartyProfileDataService.class, "RET_GENERIC_ERROR", e);
			throw new GenericException(e.getMessage());
		}
		
		
		/***************************************************/
		
		try {
			accountAttributesList = (ArrayList<AccountAttribute>) SdpPartyProfileDAO.getAccountAttributesBean(userId, tenantName);

		} catch (Exception e) {
			LogUtil.writeErrorLog(SdpPartyProfileDataService.class, "RET_DB_ERROR", e);
			throw new DbErrorException(e.getMessage());
		}
		
		if (accountAttributesList == null){
			throw new DbErrorException("Error retriving AccountAttribute for userId: " + userId);		
		}

		
		try {			
			Iterator<AccountAttribute> accountAttributeit = accountAttributesList.iterator();
			
			//iterate data account_attribute and setting a result bean
			while (accountAttributeit.hasNext()) {
				AccountAttribute aaItem = accountAttributeit.next();				
				if(aaItem.getAttributeDetailId()==2 && crmAccountVip != null && aaItem.getAttributeValue()!= null){
					if(aaItem.getAttributeValue().equalsIgnoreCase("N") && crmAccountVip.equalsIgnoreCase("N")){
						//setting UserType=1
						sdpPartyProfileDataServiceBean.setUserType("1");
					}
					else if(aaItem.getAttributeValue().equalsIgnoreCase("Y") && crmAccountVip.equalsIgnoreCase("N")){
						//setting UserType=2
						sdpPartyProfileDataServiceBean.setUserType("2");
					}
					else if(aaItem.getAttributeValue().equalsIgnoreCase("N") && crmAccountVip.equalsIgnoreCase("Y")){
						//setting UserType=3
						sdpPartyProfileDataServiceBean.setUserType("3");
					}
					else{
						//setting default value (1)
						sdpPartyProfileDataServiceBean.setUserType("1"); 
					}				
				}
				else
				{
					sdpPartyProfileDataServiceBean.setUserType("1");
				}
				
			
				if(aaItem.getAttributeDetailId()==40 && aaItem.getAttributeValue() != null){
					sdpPartyProfileDataServiceBean.setUserLanguage(aaItem.getAttributeValue());
				}
				
				
				if(aaItem.getAttributeDetailId()==6 && aaItem.getAttributeValue() != null){
					sdpPartyProfileDataServiceBean.setQualitySetting(aaItem.getAttributeValue());
				}
				
				
				if(aaItem.getAttributeDetailId()==15 && aaItem.getAttributeValue() != null){
					Integer paymentTypeId = Utils.stringToInteger(aaItem.getAttributeValue());
					
					//check if paymentTypeId retrieved it exist in table PAYMENT_TYPE
					if (paymentTypeId != null)
					{
						Iterator<PaymentType> paymentTypeit = paymentTypeList.iterator();
						while (paymentTypeit.hasNext()) {
							PaymentType pItem = paymentTypeit.next();
							
							if(pItem.getPaymentTypeId().longValue()==paymentTypeId.longValue()){
								sdpPartyProfileDataServiceBean.setUserPaymentMethod(paymentTypeId);
								break;
							}
						}//while
					}
				}
				
				
				if(aaItem.getAttributeDetailId()==27 && aaItem.getAttributeValue() != null){
					sdpPartyProfileDataServiceBean.setCrmAccountPurchaseBlockingThresholdCurrency(aaItem.getAttributeValue());
				}
				
				if(aaItem.getAttributeDetailId()==29 && aaItem.getAttributeValue() != null){
					sdpPartyProfileDataServiceBean.setCrmAccountPurchaseBlockingThresholdPeriod(aaItem.getAttributeValue());
				}
				
				if(aaItem.getAttributeDetailId()==26 && aaItem.getAttributeValue() != null){
					sdpPartyProfileDataServiceBean.setCrmAccountPurchaseBlockingAlertEnabledFlag(Utils.stringToBoolean(aaItem.getAttributeValue()));
				}
				
				if(aaItem.getAttributeDetailId()==30 && aaItem.getAttributeValue() != null){
					sdpPartyProfileDataServiceBean.setCrmAccountPurchaseBlockingAlertChannel(aaItem.getAttributeValue());
				}

				if(aaItem.getAttributeDetailId()==31 && aaItem.getAttributeValue() != null){
					sdpPartyProfileDataServiceBean.setCrmAccountPurchaseBlockingAlertMode(aaItem.getAttributeValue());
				}
				
				if(aaItem.getAttributeDetailId()==32 && aaItem.getAttributeValue() != null){
					// re-setting del valore già ricavato da ACCOUNT_USER.PURCHASE_ALERTING
					sdpPartyProfileDataServiceBean.setCrmAccountPurchaseBlockingIntermediateThreshold(Utils.stringToBigDecimal(aaItem.getAttributeValue()));
				}
				
				if(aaItem.getAttributeDetailId()==33 && aaItem.getAttributeValue() != null){
					sdpPartyProfileDataServiceBean.setCrmAccountConsumptionBlockingFlag(Utils.stringToBoolean(aaItem.getAttributeValue()));
				}
				
				if(aaItem.getAttributeDetailId()==34 && aaItem.getAttributeValue() != null){
					// re-setting del valore già ricavato da ACCOUNT_USER.CONSUMPTION_THRESHOLD
					sdpPartyProfileDataServiceBean.setCrmAccountConsumptionBlockingThresholdValue(Utils.stringToInteger(aaItem.getAttributeValue()));
				}
				
				if(aaItem.getAttributeDetailId()==35 && aaItem.getAttributeValue() != null){
					sdpPartyProfileDataServiceBean.setCrmAccountConsumptionBlockingThresholdPeriod(aaItem.getAttributeValue());
				}
				
				if(aaItem.getAttributeDetailId()==36 && aaItem.getAttributeValue() != null){
					sdpPartyProfileDataServiceBean.setCrmAccountConsumptionBlockingEnabledFlag(Utils.stringToBoolean(aaItem.getAttributeValue()));
				}
				
				if(aaItem.getAttributeDetailId()==37 && aaItem.getAttributeValue() != null){
					sdpPartyProfileDataServiceBean.setCrmAccountConsumptionBlockingAlertChannel(aaItem.getAttributeValue());
				}
				
				if(aaItem.getAttributeDetailId()==38 && aaItem.getAttributeValue() != null){
					sdpPartyProfileDataServiceBean.setCrmAccountConsumptionBlockingAlertMode(aaItem.getAttributeValue());
				}
				
				if(aaItem.getAttributeDetailId()==39 && aaItem.getAttributeValue() != null){
					sdpPartyProfileDataServiceBean.setCrmAccountConsumptionBlockingIntermediateThreshold(Utils.stringToInteger(aaItem.getAttributeValue()));
				}

			
			}//while
			
			LogUtil.writeCommonLog("INFO", SdpPartyProfileDataService.class,"INTERNAL",	"final result sdpPartyProfileDataServiceBean: " + sdpPartyProfileDataServiceBean);
		
		}
		catch (Exception e) {
			LogUtil.writeErrorLog(SdpPartyProfileDataService.class, "RET_GENERIC_ERROR", e);
			throw new GenericException(e.getMessage());
		}
		
		
		/***************************************************/
		
		//generate data response
		sdpPartyProfileDataResponse = Utils.getSdpPartyProfileDataResponse(AccountMgmtServiceConstant.RET_OK, null, sdpPartyProfileDataServiceBean);		
		
		return sdpPartyProfileDataResponse;
	}
	
	
	
}
