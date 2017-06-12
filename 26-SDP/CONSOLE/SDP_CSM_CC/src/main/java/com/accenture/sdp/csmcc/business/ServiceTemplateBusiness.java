/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.sdp.csmcc.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.accenture.sdp.csmcc.beans.ServiceTemplateBean;
import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.constants.MessageConstants;
import com.accenture.sdp.csmcc.common.constants.PathConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmcc.common.utils.ValidationUtilities;
import com.accenture.sdp.csmcc.converters.ServiceTemplateConverter;
import com.accenture.sdp.csmcc.managers.ServiceTemplateManager;
import com.accenture.sdp.csmcc.popups.PopupBean;
import com.accenture.sdp.csmcc.services.ServiceTemplateService;
import com.accenture.sdp.csmfe.webservices.clients.servicetemplate.SearchAllServiceTemplatesResp;
import com.accenture.sdp.csmfe.webservices.clients.servicetemplate.SearchServiceTemplatesByPlatformResp;

public final class ServiceTemplateBusiness  {
	
	private ServiceTemplateBusiness(){
		
	}
		
	public static List<ServiceTemplateBean> getBufferedData(long startRow, long numElementToFind) throws ServiceErrorException{
		
		ServiceTemplateService service = Utilities.findBean(ApplicationConstants.SERVICETEMPLATE_SERVICE_BEAN_NAME, ServiceTemplateService.class);
		ServiceTemplateManager tableBean = Utilities.findBean(ApplicationConstants.SERVICETEMPLATE_TABLE_BEAN_NAME, ServiceTemplateManager.class);
		List<ServiceTemplateBean> tableResult= new ArrayList<ServiceTemplateBean>();
		
		if (tableBean.getParentPlatform()!=null){
			SearchServiceTemplatesByPlatformResp resp=
					service.searchServiceTemplatesByPlatform(tableBean.getParentPlatform().getPlatformName(), startRow, numElementToFind, Utilities.getTenantName());
			if (resp.getResultCode().equals(ApplicationConstants.CODE_OK)) {
				tableResult=(List<ServiceTemplateBean>) ServiceTemplateConverter.buildServiceTemplateTable(resp);
			}
		}
		else {
			SearchAllServiceTemplatesResp resp=service.searchAllServiceTemplates(startRow, numElementToFind, Utilities.getTenantName());
			if (resp.getResultCode().equals(ApplicationConstants.CODE_OK)) {
				tableResult=(List<ServiceTemplateBean>) ServiceTemplateConverter.buildServiceTemplateTable(resp);
			}
		}
	
		return tableResult;
		
	}
	
	
	public static List<ServiceTemplateBean> searchServiceTemplatesByPlatform(String platformName) throws ServiceErrorException{
		ServiceTemplateService service = Utilities.findBean(ApplicationConstants.SERVICETEMPLATE_SERVICE_BEAN_NAME, ServiceTemplateService.class);
		SearchServiceTemplatesByPlatformResp resp=service.searchServiceTemplatesByPlatform(platformName, 0L, 0L, Utilities.getTenantName());
		if (resp.getResultCode().equals(ApplicationConstants.CODE_OK)) {
			return ServiceTemplateConverter.buildServiceTemplateTable(resp);
		} else {
			return null;
		}
	}
	
	
	public static void addServiceTemplate(ServiceTemplateBean serviceTemplateBean) {
		
		Long platformId = serviceTemplateBean.getPlatformId();
		String platformName = serviceTemplateBean.getPlatformName();
		String serviceTemplateName = serviceTemplateBean.getServiceTemplateName();
		String serviceTemplateDesc = serviceTemplateBean.getServiceTemplateDesc();
		String serviceTemplateExtId = serviceTemplateBean.getServiceTemplateExtId();
		String serviceTemplateProfile = serviceTemplateBean.getServiceTemplateProfile();
		String serviceTemplateTypeName = serviceTemplateBean.getServiceTemplateTypeName();
		Long serviceTemplateTypeId = serviceTemplateBean.getServiceTemplateTypeId();
		
		LoggerWrapper log = new LoggerWrapper(ServiceTemplateBean.class);
		HashMap<String,Object> validationMap = new HashMap<String,Object>();
		validationMap.put(ApplicationConstants.SERVICETEMPLATE_VALIDATION_NAME_FIELD, serviceTemplateName);
		validationMap.put(ApplicationConstants.SERVICETEMPLATE_VALIDATION_PLATFORM_FIELD, platformId);
		validationMap.put(ApplicationConstants.SERVICETEMPLATE_VALIDATION_TYPE_FIELD, serviceTemplateTypeId);

		if (ValidationUtilities.validateMandatoryFields(validationMap)){
			// logging
			String serviceTemplateNameLbl = 
					MessageConstants.SERVICETEMPLATE_NAME_LBL;
			String serviceTemplateDescLbl = 
					MessageConstants.SERVICETEMPLATE_DESC_LBL;
			String platformNameLbl = 
					MessageConstants.SERVICETEMPLATE_PLATFORMNAME_LBL;
			String serviceTemplateExtIdLbl = 
					MessageConstants.SERVICETEMPLATE_EXTID_LBL;
			String serviceTemplateProfileLbl = 
					MessageConstants.SERVICETEMPLATE_PROFILE_LBL;
			String serviceTemplateTypeNameLbl = 
					MessageConstants.SERVICETEMPLATE_TYPENAME_LBL;
			Map<String,Object> logMap = new HashMap<String,Object>();
			logMap.put(serviceTemplateNameLbl, serviceTemplateName);
			logMap.put(serviceTemplateDescLbl, serviceTemplateDesc);
			logMap.put(platformNameLbl, platformName);
			logMap.put(serviceTemplateExtIdLbl, serviceTemplateExtId);
			logMap.put(serviceTemplateProfileLbl, serviceTemplateProfile);
			logMap.put(serviceTemplateTypeNameLbl, serviceTemplateTypeName);
			log.logStartFeature(logMap);
			logMap.clear();
			String mex;
			String code;
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			try {
				ServiceTemplateService service = Utilities.findBean(ApplicationConstants.SERVICETEMPLATE_SERVICE_BEAN_NAME, ServiceTemplateService.class);
				service.createServiceTemplate(serviceTemplateName, serviceTemplateDesc, serviceTemplateExtId, 
						serviceTemplateProfile, platformId,serviceTemplateTypeId, Utilities.getTenantName());

				mex = String.format(
						Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE,MessageConstants.OK_MESSAGE),
						Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE,MessageConstants.ADD_SERVICETEMPLATE_MESSAGE),
						serviceTemplateName);
				code = ApplicationConstants.CODE_OK;
				msgBean.setNextParam(PathConstants.SERVICETEMPLATE);
			} catch (ServiceErrorException e) {
				code = e.getCode();
				mex = e.getMessage();
				
			}
			msgBean.openPopup(mex);

			// logging
			log.logEndFeature(code,mex);
		}
	}

	public static void updateServiceTemplate(ServiceTemplateBean serviceTemplateBean) {
		
		Long platformId = serviceTemplateBean.getPlatformId();
		Long serviceTemplateId = serviceTemplateBean.getServiceTemplateId();
		String serviceTemplateName = serviceTemplateBean.getServiceTemplateName();
		String serviceTemplateDesc = serviceTemplateBean.getServiceTemplateDesc();
		String serviceTemplateExtId = serviceTemplateBean.getServiceTemplateExtId();
		String serviceTemplateProfile = serviceTemplateBean.getServiceTemplateProfile();
		String serviceTemplateTypeName = serviceTemplateBean.getServiceTemplateTypeName();
		Long serviceTemplateTypeId = serviceTemplateBean.getServiceTemplateTypeId();
		
		LoggerWrapper log = new LoggerWrapper(ServiceTemplateBean.class);
		HashMap<String,Object> validationMap = new HashMap<String,Object>();
		
		validationMap.put(ApplicationConstants.SERVICETEMPLATE_VALIDATION_NAME_FIELD, serviceTemplateName);
		validationMap.put(ApplicationConstants.SERVICETEMPLATE_VALIDATION_TYPE_FIELD, serviceTemplateTypeId);

		if (ValidationUtilities.validateMandatoryFields(validationMap)){
			// logging
			String serviceTemplateNameLbl = 
					MessageConstants.SERVICETEMPLATE_NAME_LBL;
			String serviceTemplateDescLbl = 
					MessageConstants.SERVICETEMPLATE_DESC_LBL;
			String serviceTemplateExtIdLbl = 
					MessageConstants.SERVICETEMPLATE_EXTID_LBL;
			String serviceTemplateProfileLbl = 
					MessageConstants.SERVICETEMPLATE_PROFILE_LBL;
			String serviceTemplateTypeNameLbl = 
					MessageConstants.SERVICETEMPLATE_TYPENAME_LBL;
			Map<String,Object> logMap = new HashMap<String,Object>();
			logMap.put(serviceTemplateNameLbl, serviceTemplateName);
			logMap.put(serviceTemplateDescLbl, serviceTemplateDesc);
			logMap.put(serviceTemplateExtIdLbl, serviceTemplateExtId);
			logMap.put(serviceTemplateProfileLbl, serviceTemplateProfile);
			logMap.put(serviceTemplateTypeNameLbl, serviceTemplateTypeName);
			log.logStartFeature(logMap);
			logMap.clear();

			String mex;
			String code;
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			try {
				ServiceTemplateService service = Utilities.findBean(ApplicationConstants.SERVICETEMPLATE_SERVICE_BEAN_NAME, ServiceTemplateService.class);
				service.modifyServiceTemplate(serviceTemplateId,serviceTemplateName, serviceTemplateDesc, serviceTemplateExtId, serviceTemplateProfile, 
						platformId,serviceTemplateTypeId, Utilities.getTenantName());
				mex = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE,MessageConstants.OK_MESSAGE),
						Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE,MessageConstants.UPDATE_SERVICETEMPLATE_MESSAGE),
						serviceTemplateName);
				code = ApplicationConstants.CODE_OK;
				
				msgBean.setUpdateFlag(true);
				msgBean.setNextParam(PathConstants.SERVICETEMPLATE);
			} catch (ServiceErrorException e) {
				code = e.getCode();
				mex = e.getMessage();
				
			}
			msgBean.openPopup(mex);

			// logging
			log.logEndFeature(code,mex);
		}
	}
		

}
