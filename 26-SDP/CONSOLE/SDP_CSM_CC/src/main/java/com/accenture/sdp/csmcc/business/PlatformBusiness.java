/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.sdp.csmcc.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.accenture.sdp.csmcc.beans.PlatformBean;
import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.constants.MessageConstants;
import com.accenture.sdp.csmcc.common.constants.PathConstants;
import com.accenture.sdp.csmcc.common.constants.StatusConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmcc.common.utils.ValidationUtilities;
import com.accenture.sdp.csmcc.controllers.SessionController;
import com.accenture.sdp.csmcc.converters.PlatformConverter;
import com.accenture.sdp.csmcc.managers.PlatformManager;
import com.accenture.sdp.csmcc.popups.PopupBean;
import com.accenture.sdp.csmcc.services.PlatformService;
import com.accenture.sdp.csmfe.webservices.clients.platform.SearchPlatformComplexRespPaginated;

public final class PlatformBusiness {
	
	
	private PlatformBusiness(){
		
	}
		
	public  static List<PlatformBean> getBufferedData(long startRow, long numElementToFind) throws ServiceErrorException{
		PlatformService service = Utilities.findBean(ApplicationConstants.PLATFORM_SERVICE_BEAN_NAME, PlatformService.class);
		SearchPlatformComplexRespPaginated resp=service.searchAllPlatforms(startRow, numElementToFind, Utilities.getTenantName());
		if (resp.getResultCode().equals(ApplicationConstants.CODE_OK)) {
			return PlatformConverter.buildPlatformTable(resp);		
		} else {
			return new ArrayList<PlatformBean>();
		}
	}
	
	public static void addPlatform(PlatformBean platformBean) {
		LoggerWrapper log = new LoggerWrapper(PlatformBusiness.class);
		HashMap<String,Object> validationMap = new HashMap<String,Object>();
		validationMap.put(ApplicationConstants.PLATFORM_VALIDATION_NAME_FIELD, platformBean.getPlatformName());
		if (ValidationUtilities.validateMandatoryFields(validationMap)){
			// logging
			String platformNameLbl = MessageConstants.PLATFORM_NAME_LBL;
			String platformDescLbl = MessageConstants.PLATFORM_DESC_LBL;
			String platformExtIdLbl = MessageConstants.PLATFORM_EXTID_LBL;
			String platformProfileLbl = MessageConstants.PLATFORM_PROFILE_LBL;
			Map<String,Object> logMap = new HashMap<String,Object>();
			logMap.put(platformNameLbl, platformBean.getPlatformName());
			logMap.put(platformDescLbl, platformBean.getPlatformDesc());
			logMap.put(platformExtIdLbl, platformBean.getPlatformExtId());
			logMap.put(platformProfileLbl,platformBean.getPlatformProfile());
			log.logStartFeature(logMap);
			logMap.clear();
			PlatformService service = Utilities.findBean(ApplicationConstants.PLATFORM_SERVICE_BEAN_NAME, PlatformService.class);
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			
			String code;
			String mex;
			try{
				service.createPlatform(platformBean.getPlatformName(), platformBean.getPlatformDesc(), platformBean.getPlatformExtId(), platformBean.getPlatformProfile(), Utilities.getTenantName());
			
				mex = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE,MessageConstants.OK_MESSAGE), 
	            		Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE,MessageConstants.ADD_PLATFORM),platformBean.getPlatformName());
				
				msgBean.openPopup(mex);
				
				msgBean.setNextParam(PathConstants.PLATFORM);
				code = ApplicationConstants.CODE_OK;
			} catch (ServiceErrorException see) {
				code = see.getCode();
				mex = see.getMessage();
			}
			msgBean.openPopup(mex);
			log.logEndFeature(code, mex);
		}
	}

	public static void updatePlatform(PlatformBean platformBean) {
		LoggerWrapper log = new LoggerWrapper(PlatformBusiness.class);
		HashMap<String,Object> validationMap = new HashMap<String,Object>();
		validationMap.put(ApplicationConstants.PLATFORM_VALIDATION_NAME_FIELD, platformBean.getPlatformName());
		if (ValidationUtilities.validateMandatoryFields(validationMap)){
			// logging
			String platformNameLbl = MessageConstants.PLATFORM_NAME_LBL;
			String platformDescLbl = MessageConstants.PLATFORM_DESC_LBL;
			String platformExtIdLbl = MessageConstants.PLATFORM_EXTID_LBL;
			String platformProfileLbl = MessageConstants.PLATFORM_PROFILE_LBL;
			
			


			Map<String,Object> logMap = new HashMap<String,Object>();
			logMap.put(platformNameLbl, platformBean.getPlatformName());
			logMap.put(platformDescLbl, platformBean.getPlatformDesc());
			logMap.put(platformExtIdLbl, platformBean.getPlatformExtId());
			logMap.put(platformProfileLbl, platformBean.getPlatformProfile());
			log.logStartFeature(logMap);
			logMap.clear();
			PlatformManager tableBean = Utilities.findBean(ApplicationConstants.PLATFORM_TABLE_BEAN_NAME, PlatformManager.class);
			PlatformService service = Utilities.findBean(ApplicationConstants.PLATFORM_SERVICE_BEAN_NAME, PlatformService.class);
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			String code;
			String mex;
			try{
				service.modifyPlatform(platformBean.getPlatformName(), platformBean.getPlatformDesc(),platformBean.getPlatformExtId(), platformBean.getPlatformProfile(), platformBean.getPlatformId(), Utilities.getTenantName());
			
				 mex = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE,MessageConstants.OK_MESSAGE), 
	            		Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE,MessageConstants.UPDATE_PLATFORM_MESSAGE),platformBean.getPlatformName());
				
				tableBean.setUpdatesFlag(true);
				
				msgBean.setUpdateFlag(true);
				msgBean.setNextParam(PathConstants.PLATFORM);
				code = ApplicationConstants.CODE_OK;
			} catch (ServiceErrorException see) {
				
				code = see.getCode();
				mex = see.getMessage();
			}
			msgBean.openPopup(mex);
			log.logEndFeature(code, mex);

		}
	}
	
	public static void changeStatus(PlatformBean platformBean, String changeStatusValue) {
		LoggerWrapper log = new LoggerWrapper(PlatformBusiness.class);
		SessionController infoSessionBean = Utilities.findBean(ApplicationConstants.INFO_SESSION_BEAN_NAME, SessionController.class);
		PlatformService service = Utilities.findBean(ApplicationConstants.PLATFORM_SERVICE_BEAN_NAME, PlatformService.class);
		String entityName = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.PLATFORM);
		String operation = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, "statusName." + changeStatusValue);
		log.logStartFeature(infoSessionBean.getBreadCrumbAsString()+"->" + operation + " " + entityName, null);

		String mex;
		String code;
		PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
		try {
			if (StatusConstants.DELETED.equals(changeStatusValue)){
				service.deletePlatform(platformBean.getPlatformId(), Utilities.getTenantName());
			} else {
				service.changeStatus(platformBean.getPlatformId(), changeStatusValue, Utilities.getTenantName());	
			}
			platformBean.setPlatformStatus(changeStatusValue);
			String message = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.OK_MESSAGE);
			mex = String.format(message, operation + " " + entityName, platformBean.getPlatformName());
			code = ApplicationConstants.CODE_OK;
			
			msgBean.setUpdateFlag(true);
		} catch (ServiceErrorException e) {
			code = e.getCode();
			mex = e.getMessage();
			
		}
		msgBean.openPopup(mex);
		//logging
		log.logEndFeature(infoSessionBean.getBreadCrumbAsString()+"->"+operation, code,mex);
	}

	
}
