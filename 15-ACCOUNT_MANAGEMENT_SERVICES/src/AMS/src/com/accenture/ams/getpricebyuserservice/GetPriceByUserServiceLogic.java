package com.accenture.ams.getpricebyuserservice;

import com.accenture.ams.getpricebyuserservice.DbErrorException;
import com.accenture.ams.getpricebyuserservice.GenericException;
import com.accenture.ams.db.dao.GetPriceByUserDAO;
import com.accenture.ams.db.util.LogUtil;
import com.accenture.ams.getpricebyuserservice.bean.GetPriceByUserServiceBean;
import com.accenture.ams.getpricebyuserservice.stub.component.ItemPrice;
import com.accenture.ams.getpricebyuserservice.stub.util.PaymentGatewayUtil;

import types.getpricebyuser.avs.accenture.GetPriceByUserResponse;

public class GetPriceByUserServiceLogic {
	private String tenantName;

	/**
	 * 
	 * @param _tenantName
	 */
	public GetPriceByUserServiceLogic(String _tenantName){
		this.tenantName = _tenantName;
	}
	
	
	/**
	 * 
	 * @param sItemId
	 * @param sUserId
	 * @return
	 * @throws GenericException 
	 * @throws DbErrorException 
	 */
	public GetPriceByUserResponse getPPVPrice(String sItemId, String sUserId) throws GenericException, DbErrorException {
		GetPriceByUserResponse getPriceByUserResponse = null;
		GetPriceByUserServiceBean getPriceByUserServiceBean = null;
		
		LogUtil.writeCommonLog("INFO", GetPriceByUserServiceLogic.class,
				"WS",
				"I'm in GetPriceByUserServiceLogic.getPPVPrice for userId: "
						+ sUserId + " itemId: " + sItemId);
		
        /***************************************************/		

		checkRequestParameters(sItemId, sUserId, GetPriceByUserServiceEnum.GET_PPV_PRICE);
		
        /***************************************************/		
		
		//retrieve data from stub component
		ItemPrice itemPrice = (ItemPrice) PaymentGatewayUtil.getPPVPrice(Utils.stringToLong(sItemId), Utils.stringToLong(sUserId));
		
		
		try {		
			getPriceByUserServiceBean = new GetPriceByUserServiceBean();
			
			getPriceByUserServiceBean.setCurrency(itemPrice.getCurrency());
			getPriceByUserServiceBean.setPrice(itemPrice.getPrice());
			getPriceByUserServiceBean.setPriceDiscount(itemPrice.getPriceDiscounted());
	
			LogUtil.writeCommonLog("INFO", GetPriceByUserServiceLogic.class,"INTERNAL",	"final result getPriceByUserServiceBean: " + getPriceByUserServiceBean);
			
		}
		catch (Exception e) {
			LogUtil.writeErrorLog(GetPriceByUserServiceLogic.class, "RET_GENERIC_ERROR", e);
			throw new GenericException(e.getMessage());
		}
	
	
	/***************************************************/
		
		//generate data response
		getPriceByUserResponse = Utils.getResponse(GetPriceByUserServiceConstant.RET_OK, null, getPriceByUserServiceBean);
		
		return getPriceByUserResponse;
	}	
	
	
	/**
	 * 
	 * @param sItemId
	 * @param sUserId
	 * @return
	 * @throws GenericException 
	 * @throws DbErrorException 
	 */
	public GetPriceByUserResponse getContentPrice(String sItemId, String sUserId) throws GenericException, DbErrorException {
		GetPriceByUserResponse getPriceByUserResponse = null;
		GetPriceByUserServiceBean getPriceByUserServiceBean = null;
		
		LogUtil.writeCommonLog("INFO", GetPriceByUserServiceLogic.class,
				"WS",
				"I'm in GetPriceByUserServiceLogic.getContentPrice for userId: "
						+ sUserId + " itemId: " + sItemId);
		

        /***************************************************/		

		checkRequestParameters(sItemId, sUserId, GetPriceByUserServiceEnum.GET_CONTENT_PRICE);

        /***************************************************/		
		
		
		//retrieve data from stub component
		ItemPrice itemPrice = (ItemPrice) PaymentGatewayUtil.getContentPrice(Utils.stringToLong(sItemId), Utils.stringToLong(sUserId));
		
		try {		
			getPriceByUserServiceBean = new GetPriceByUserServiceBean();
			
			getPriceByUserServiceBean.setCurrency(itemPrice.getCurrency());
			getPriceByUserServiceBean.setPrice(itemPrice.getPrice());
			getPriceByUserServiceBean.setPriceDiscount(itemPrice.getPriceDiscounted());
	
			LogUtil.writeCommonLog("INFO", GetPriceByUserServiceLogic.class,"INTERNAL",	"final result getPriceByUserServiceBean: " + getPriceByUserServiceBean);
			
		}
		catch (Exception e) {
			LogUtil.writeErrorLog(GetPriceByUserServiceLogic.class, "RET_GENERIC_ERROR", e);
			throw new GenericException(e.getMessage());
		}
	
	
	/***************************************************/
		
		//generate data response
		getPriceByUserResponse = Utils.getResponse(GetPriceByUserServiceConstant.RET_OK, null, getPriceByUserServiceBean);
		
		return getPriceByUserResponse;
	}
	
	/**
	 * 
	 * @param sItemId
	 * @param sUserId
	 * @return
	 * @throws GenericException 
	 * @throws DbErrorException 
	 */
	public GetPriceByUserResponse getProductPrice(String sItemId, String sUserId) throws GenericException, DbErrorException {
		GetPriceByUserResponse getPriceByUserResponse = null;
		GetPriceByUserServiceBean getPriceByUserServiceBean = null;
		
		LogUtil.writeCommonLog("INFO", GetPriceByUserServiceLogic.class,
				"WS",
				"I'm in GetPriceByUserServiceLogic.getProductPrice for userId: "
						+ sUserId + " itemId: " + sItemId);
		
        /***************************************************/		

		checkRequestParameters(sItemId, sUserId, GetPriceByUserServiceEnum.GET_PRODUCT_PRICE);
			
        /***************************************************/		
		
		//retrieve data from stub component
		ItemPrice itemPrice = (ItemPrice) PaymentGatewayUtil.getProductPrice(Utils.stringToLong(sItemId), Utils.stringToLong(sUserId));
		
		try {		
			getPriceByUserServiceBean = new GetPriceByUserServiceBean();
			
			getPriceByUserServiceBean.setCurrency(itemPrice.getCurrency());
			getPriceByUserServiceBean.setPrice(itemPrice.getPrice());
			getPriceByUserServiceBean.setPriceDiscount(itemPrice.getPriceDiscounted());
	
			LogUtil.writeCommonLog("INFO", GetPriceByUserServiceLogic.class,"INTERNAL",	"final result getPriceByUserServiceBean: " + getPriceByUserServiceBean);
			
		}
		catch (Exception e) {
			LogUtil.writeErrorLog(GetPriceByUserServiceLogic.class, "RET_GENERIC_ERROR", e);
			throw new GenericException(e.getMessage());
		}
	
	
	/***************************************************/
		
		//generate data response
		getPriceByUserResponse = Utils.getResponse(GetPriceByUserServiceConstant.RET_OK, null, getPriceByUserServiceBean);
		
		return getPriceByUserResponse;
	}
	
	
	/**
	 * 
	 * @param sItemId
	 * @param sUserId
	 * @return
	 * @throws GenericException 
	 * @throws DbErrorException 
	 */
	public GetPriceByUserResponse getBundlePrice(String sItemId, String sUserId) throws GenericException, DbErrorException {
		GetPriceByUserResponse getPriceByUserResponse = null;
		GetPriceByUserServiceBean getPriceByUserServiceBean = null;
		
		LogUtil.writeCommonLog("INFO", GetPriceByUserServiceLogic.class,
				"WS",
				"I'm in GetPriceByUserServiceLogic.getBundlePrice for userId: "
						+ sUserId + " itemId: " + sItemId);
		
        /***************************************************/		

		checkRequestParameters(sItemId, sUserId, GetPriceByUserServiceEnum.GET_BUNDLE_PRICE);

        /***************************************************/		
		
		//retrieve data from stub component
		ItemPrice itemPrice = (ItemPrice) PaymentGatewayUtil.getBundlePrice(Utils.stringToLong(sItemId), Utils.stringToLong(sUserId));
		
		try {		
			getPriceByUserServiceBean = new GetPriceByUserServiceBean();
			
			getPriceByUserServiceBean.setCurrency(itemPrice.getCurrency());
			getPriceByUserServiceBean.setPrice(itemPrice.getPrice());
			getPriceByUserServiceBean.setPriceDiscount(itemPrice.getPriceDiscounted());
	
			LogUtil.writeCommonLog("INFO", GetPriceByUserServiceLogic.class,"INTERNAL",	"final result getPriceByUserServiceBean: " + getPriceByUserServiceBean);
			
		}
		catch (Exception e) {
			LogUtil.writeErrorLog(GetPriceByUserServiceLogic.class, "RET_GENERIC_ERROR", e);
			throw new GenericException(e.getMessage());
		}
	
	
	/***************************************************/
		
		//generate data response
		getPriceByUserResponse = Utils.getResponse(GetPriceByUserServiceConstant.RET_OK, null, getPriceByUserServiceBean);

		return getPriceByUserResponse;
	}
	
	
	/**
	 * 
	 * @param sItemId
	 * @param sUserId
	 * @param getPriceByUserServiceEnum
	 * @throws DbErrorException
	 * @throws GenericException
	 */
	private void checkRequestParameters(String sItemId, String sUserId, GetPriceByUserServiceEnum getPriceByUserServiceEnum) throws DbErrorException, GenericException{
		boolean isUserPresent = false;
		boolean iItemIspresent = false;
		
		LogUtil.writeCommonLog("INFO", GetPriceByUserServiceLogic.class,
				"WS",
				"I'm in GetPriceByUserServiceLogic.checkRequestParameters for userId: "
						+ sUserId + " itemId: " + sItemId);
		

		try {
			//check if userId is present on table account_user
			isUserPresent = GetPriceByUserDAO.isUserIdPresentAccountUser(Utils.stringToLong(sUserId), tenantName);			
		} 
		catch (Exception e) {
			LogUtil.writeErrorLog(GetPriceByUserServiceLogic.class, "RET_DB_ERROR", e);
			throw new DbErrorException(e.getMessage());
		}
			

		if(!isUserPresent){
			LogUtil.writeCommonLog("INFO", GetPriceByUserServiceLogic.class,"DB_QUERY",	"UserId is not present: " + sUserId);
			throw new GenericException("RET_USER_NOT_PRESENT");
		}
			
        /***************************************************/		
		
		try {
			//check if itemId is present on table
			iItemIspresent = GetPriceByUserDAO.isItemIdPresent(getPriceByUserServiceEnum, sItemId, tenantName);			
		} 
		catch (Exception e) {
			LogUtil.writeErrorLog(GetPriceByUserServiceLogic.class, "RET_DB_ERROR", e);
			throw new DbErrorException(e.getMessage());
		}
		
		if(!iItemIspresent){
			LogUtil.writeCommonLog("INFO", GetPriceByUserServiceLogic.class,"DB_QUERY",	"ItemId doesn't exist: " + sItemId);
			throw new GenericException("ItemId doesn't exist");
		}
	}
	
	
}
