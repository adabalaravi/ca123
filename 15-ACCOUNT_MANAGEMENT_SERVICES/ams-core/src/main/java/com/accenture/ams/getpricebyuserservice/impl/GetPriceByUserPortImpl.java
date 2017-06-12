package com.accenture.ams.getpricebyuserservice.impl;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import javax.jws.WebService;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import wsclient.types.getpricebyuser.avs.accenture.GetPriceByUserRequest;
import wsclient.types.getpricebyuser.avs.accenture.GetPriceByUserResponse;

import com.accenture.ams.db.util.HibernateUtil;
import com.accenture.ams.db.util.LogUtil;
import com.accenture.ams.getpricebyuserservice.DbErrorException;
import com.accenture.ams.getpricebyuserservice.GenericException;
import com.accenture.ams.getpricebyuserservice.GetPriceByUserPort;
import com.accenture.ams.getpricebyuserservice.GetPriceByUserServiceConstant;
import com.accenture.ams.getpricebyuserservice.GetPriceByUserServiceLogic;
import com.accenture.ams.getpricebyuserservice.Utils;
import com.sun.xml.ws.developer.SchemaValidation;

@SchemaValidation(handler = com.accenture.ams.accountmgmtservice.InputRequestError.class)
@WebService(serviceName = "GetPriceByUserService", endpointInterface = "com.accenture.ams.getpricebyuserservice.GetPriceByUserPort", targetNamespace = "http://ws.be.avs.accenture.com/GetPriceByUserService-v1")
public class GetPriceByUserPortImpl implements GetPriceByUserPort, ErrorHandler {
	
	private boolean isRequestValid = true;
	private String requestError = "";
	
	public GetPriceByUserResponse getPPVPrice(
			GetPriceByUserRequest getPpvPriceByUserRequest) {
		
		try{
			init();
		}
		catch(Exception e){
			return Utils.getResponse(GetPriceByUserServiceConstant.RET_MISSING_PARAM, e.getMessage(), null);
		}
		//if (!init())
			//return Utils.getResponse("-1", "-1", "-1");
		
		GetPriceByUserResponse response = null;
		String tenantName = getPpvPriceByUserRequest.getTenantName();
		String itemId = getPpvPriceByUserRequest.getItemId();
		String userId = getPpvPriceByUserRequest.getUserId();
		
		GetPriceByUserServiceLogic getPriceByUserServiceLogic = new GetPriceByUserServiceLogic(tenantName);
		try {
			response = getPriceByUserServiceLogic.getPPVPrice(itemId, userId);
		} 
		catch (GenericException ge) {			
			if(ge.getMessage().equals("RET_USER_NOT_PRESENT")){
				return Utils.getResponse(GetPriceByUserServiceConstant.RET_USER_NOT_PRESENT, null, null);
			}
			else{
				return Utils.getResponse(GetPriceByUserServiceConstant.RET_GENERIC_ERROR, ge.getMessage(),null);
			}
		} 
		catch (DbErrorException dbe) {
			return Utils.getResponse(GetPriceByUserServiceConstant.RET_DB_ERROR, dbe.getMessage(),null);		
		}
		
		return response;
	}

	public GetPriceByUserResponse getContentPrice(
			GetPriceByUserRequest getContentPriceByUserRequest) {
		
		try{
			init();
		}
		catch(Exception e){
			return Utils.getResponse(GetPriceByUserServiceConstant.RET_MISSING_PARAM, e.getMessage(), null);
		}
		
		GetPriceByUserResponse response = null;
		String tenantName = getContentPriceByUserRequest.getTenantName();
		String itemId = getContentPriceByUserRequest.getItemId();
		String userId = getContentPriceByUserRequest.getUserId();
		
		GetPriceByUserServiceLogic getPriceByUserServiceLogic = new GetPriceByUserServiceLogic(tenantName);
		try {
			response = getPriceByUserServiceLogic.getContentPrice(itemId, userId);
		} 
		catch (GenericException ge) {
			if(ge.getMessage().equals("RET_USER_NOT_PRESENT")){
				return Utils.getResponse(GetPriceByUserServiceConstant.RET_USER_NOT_PRESENT, null, null);
			}
			else{
				return Utils.getResponse(GetPriceByUserServiceConstant.RET_GENERIC_ERROR, ge.getMessage(),null);
			}
		}
		catch (DbErrorException dbe) {
			return Utils.getResponse(GetPriceByUserServiceConstant.RET_DB_ERROR, dbe.getMessage(),null);
		}
		
		
		return response;
	}

	public GetPriceByUserResponse getProductPrice(
			GetPriceByUserRequest getProductPriceByUserRequest) {
		
		try{
			init();
		}
		catch(Exception e){
			return Utils.getResponse(GetPriceByUserServiceConstant.RET_MISSING_PARAM, e.getMessage(), null);
		}
		
		GetPriceByUserResponse response = null;
		String tenantName = getProductPriceByUserRequest.getTenantName();
		String itemId = getProductPriceByUserRequest.getItemId();
		String userId = getProductPriceByUserRequest.getUserId();
		
		GetPriceByUserServiceLogic getPriceByUserServiceLogic = new GetPriceByUserServiceLogic(tenantName);
		try {
			response = getPriceByUserServiceLogic.getProductPrice(itemId, userId);
		} 
		catch (GenericException ge) {
			if(ge.getMessage().equals("RET_USER_NOT_PRESENT")){
				return Utils.getResponse(GetPriceByUserServiceConstant.RET_USER_NOT_PRESENT, null, null);
			}
			else{
				return Utils.getResponse(GetPriceByUserServiceConstant.RET_GENERIC_ERROR, ge.getMessage(),null);
			}
		}
		catch (DbErrorException dbe) {
			return Utils.getResponse(GetPriceByUserServiceConstant.RET_DB_ERROR, dbe.getMessage(),null);
		}
		
		return response;
	}

	public GetPriceByUserResponse getBundlePrice(
			GetPriceByUserRequest getBundlePriceByUserRequest) {
		
		try{
			init();
		}
		catch(Exception e){
			return Utils.getResponse(GetPriceByUserServiceConstant.RET_MISSING_PARAM, e.getMessage(), null);
		}
		
		GetPriceByUserResponse response = null;
		String tenantName = getBundlePriceByUserRequest.getTenantName();
		String itemId = getBundlePriceByUserRequest.getItemId();
		String userId = getBundlePriceByUserRequest.getUserId();
		
		GetPriceByUserServiceLogic getPriceByUserServiceLogic = new GetPriceByUserServiceLogic(tenantName);
		try {
			response = getPriceByUserServiceLogic.getBundlePrice(itemId, userId);
		} 
		catch (GenericException ge) {
			if(ge.getMessage().equals("RET_USER_NOT_PRESENT")){
				return Utils.getResponse(GetPriceByUserServiceConstant.RET_USER_NOT_PRESENT, null, null);
			}
			else{
				return Utils.getResponse(GetPriceByUserServiceConstant.RET_GENERIC_ERROR, ge.getMessage(),null);
			}
		}
		catch (DbErrorException dbe) {
			return Utils.getResponse(GetPriceByUserServiceConstant.RET_DB_ERROR, dbe.getMessage(),null);
		}
		
		return response;
	}
	
	
	private void init() throws Exception{
		LogUtil.writeCommonLog("DEBUG", GetPriceByUserPortImpl.class, "INTERNAL", "GetPriceByUserService WS");
		// check request validity
		if (!isRequestValid){
			throw new Exception("Invalid Request");
			//return false;
		}
		
		Map<String, String> menv = System.getenv();
		String propertyPath = menv.get(GetPriceByUserServiceConstant.AMS_PROPERTIES_PATH_ENV);
		
		if (!HibernateUtil.getInstance().isInitialized()){
			if (!initTenantConfiguration(propertyPath)){
				throw new Exception("Hibernate Error");
				//return false;
			}
		}
		//return true;
	}
	
	private boolean initTenantConfiguration(String propertyPath) {
		FileInputStream isFile = null;
		/*
		 * check if tenant.properties exist under AMS properties file path first
		 * elsewhere use the defualt tenant.properties under classpath
		 */
		String fullPath = propertyPath + GetPriceByUserServiceConstant.TENANT_PROPERTIES;;
		File tenantPropertyFile = new File(fullPath);
		Properties prop = new Properties();
		
		try {
			isFile = new FileInputStream(tenantPropertyFile);
			prop.load(isFile);
		} 
		catch (FileNotFoundException e1) {
			LogUtil.writeCommonLog("DEBUG",
					GetPriceByUserPortImpl.class,
					"INTERNAL",
					"tenant.properties not found und properties path...use default!");
		}
		catch (IOException e1) {
			LogUtil.writeCommonLog("ERROR",
					GetPriceByUserPortImpl.class,
					"INTERNAL", "ERROR: Cannot read properties file. Cause : " + e1);
			return false;
		}
		return HibernateUtil.getInstance().initSessionFactories(prop);
	}
	
	public void error(SAXParseException exception) throws SAXException {
		LogUtil.writeCommonLog("DEBUG", GetPriceByUserPortImpl.class, "INTERNAL", "Validation error : " + exception.getMessage());
		isRequestValid=false;
		requestError=exception.getMessage();
	}

	public void fatalError(SAXParseException exception) throws SAXException {
		LogUtil.writeCommonLog("DEBUG", GetPriceByUserPortImpl.class, "INTERNAL", "Validation fatal error" + exception.getMessage());
		isRequestValid=false;
		requestError=exception.getMessage();
	}

	public void warning(SAXParseException exception) throws SAXException {
		LogUtil.writeCommonLog("DEBUG", GetPriceByUserPortImpl.class, "INTERNAL", "Validation warning" + exception.getMessage());	
	}
}