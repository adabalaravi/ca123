/**
 * 
 */
package com.accenture.ams.accountmgmtservice.sdpAccountProfileDataService;

import wsclient.types.accountmgmt.avs.accenture.SdpAccountProfileDataResponse;
import wsclient.types.accountmgmt.avs.accenture.SdpDataRequest;

import com.accenture.ams.accountmgmtservice.AccountMgmtServiceConstant;
import com.accenture.ams.accountmgmtservice.DbErrorException;
import com.accenture.ams.accountmgmtservice.Utils;
import com.accenture.ams.db.dao.SdpAccountProfileDAO;
import com.accenture.ams.db.util.LogUtil;
import com.accenture.ams.accountmgmtservice.GenericException;
/**
 * @author aditya.madhav.k
 *
 */
public class SdpAccountProfileDataService {

	private Long userId=null;
	String tenantId;
	
	public SdpAccountProfileDataService(SdpDataRequest sdpDataRequestProfile){
		
		this.userId=Long.parseLong(sdpDataRequestProfile.getUserId());
		tenantId=sdpDataRequestProfile.getTenantName();
	}
	
	public SdpAccountProfileDataResponse getAcoountProfileDetails() throws DbErrorException, GenericException{
		SdpAccountProfileDataResponse response=new SdpAccountProfileDataResponse();
		SdpAccountProfileDataBean sdpAccountProfileDataBean=new SdpAccountProfileDataBean();
		
		LogUtil.writeCommonLog("INFO", SdpAccountProfileDataService.class,
				"WS",
				"I'm in SdpAccountProfileDataService.getAcoountProfileDetails for userId: "
						+ userId + " and tenantId: " + tenantId);
		
		try{
			
		//Get Device ID
		sdpAccountProfileDataBean.setListOfCrmAccountDevice(SdpAccountProfileDAO.getAccountDevice(userId,tenantId));
		
		//User Retailer Domain
		sdpAccountProfileDataBean.setRetailerDomain(SdpAccountProfileDAO.getAccountAttributeDetails(userId, 54,tenantId));
		/*if (sdpAccountProfileDataBean.getListOfCrmAccountDevice() == null){
			throw new DbErrorException("Error retrieving List of CRM AccountDevice details");		
		}*/
		
		//UserRememberPinFlag
		sdpAccountProfileDataBean.setUserRememberPinFlag(SdpAccountProfileDAO.getAccountAttributeDetails(userId, 4,tenantId));
		
		/*if (sdpAccountProfileDataBean.getUserRememberPinFlag() == null){
			throw new DbErrorException("Error retrieving List of CRM AccountDevice details");		
		}*/
		
		//Get User PC Level
		sdpAccountProfileDataBean.setUserPCLevel(SdpAccountProfileDAO.getAccountAttributeDetails(userId, 3,tenantId));
		
		/*if (sdpAccountProfileDataBean.getListOfCrmAccountDevice() == null){
			throw new DbErrorException("Error retrieving List of CRM AccountDevice details");		
		}*/
		
		//Get User Pin Purchase & Pin Parental Control
		String pinPurchaseParentalControl = SdpAccountProfileDAO.getAccountAttributeDetails(userId, 1,tenantId); 
		if(pinPurchaseParentalControl != null){
			sdpAccountProfileDataBean.setUserPin("****");
			sdpAccountProfileDataBean.setParentalPin("****");
		}
		
		/*if (sdpAccountProfileDataBean.getListOfCrmAccountDevice() == null){
			throw new DbErrorException("Error retrieving List of CRM AccountDevice details");		
		}*/
			
		
		/*if (sdpAccountProfileDataBean.getListOfCrmAccountDevice() == null){
			throw new DbErrorException("Error retrieving List of CRM AccountDevice details");		
		}*/
		
		//Get User PC Extended Rating 
		sdpAccountProfileDataBean.setPcExtendedRating(SdpAccountProfileDAO.getAccountAttributeDetails(userId, 48,tenantId));
		
		/*if (sdpAccountProfileDataBean.getListOfCrmAccountDevice() == null){
			throw new DbErrorException("Error retrieving List of CRM AccountDevice details");		
		}*/
		
		response = Utils.getSdpAccountProfileDataResponse(AccountMgmtServiceConstant.RET_OK, null, sdpAccountProfileDataBean);	
		
		}catch(Exception e){
			LogUtil.writeErrorLog(SdpAccountProfileDataService.class, "RET_GENERIC_ERROR", e);
			throw new GenericException(e.getMessage());
		}
		return response;
	}
}
