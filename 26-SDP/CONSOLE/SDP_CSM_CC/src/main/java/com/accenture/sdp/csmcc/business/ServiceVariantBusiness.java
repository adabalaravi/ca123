package com.accenture.sdp.csmcc.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.accenture.sdp.csmcc.beans.ServiceVariantBean;
import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.constants.MessageConstants;
import com.accenture.sdp.csmcc.common.constants.PathConstants;
import com.accenture.sdp.csmcc.common.constants.StatusConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmcc.common.utils.ValidationUtilities;
import com.accenture.sdp.csmcc.controllers.SessionController;
import com.accenture.sdp.csmcc.converters.ServiceVariantConverter;
import com.accenture.sdp.csmcc.managers.ServiceVariantManager;
import com.accenture.sdp.csmcc.popups.PopupBean;
import com.accenture.sdp.csmcc.services.PlatformService;
import com.accenture.sdp.csmcc.services.ServiceVariantService;
import com.accenture.sdp.csmfe.webservices.clients.platform.SearchPlatformByServiceVariantResp;
import com.accenture.sdp.csmfe.webservices.clients.servicevariant.SearchAllServiceVariantsResp;
import com.accenture.sdp.csmfe.webservices.clients.servicevariant.SearchServiceVariantsByServiceTemplateResp;

public final class ServiceVariantBusiness {
	
	
	private ServiceVariantBusiness(){
		
	}
		
	public  static List<ServiceVariantBean> getBufferedData(long startRow, long numElementToFind) throws ServiceErrorException{
		
		ServiceVariantService service = Utilities.findBean(ApplicationConstants.SERVICEVARIANT_SERVICE_BEAN_NAME, ServiceVariantService.class);
		ServiceVariantManager tableBean = Utilities.findBean(ApplicationConstants.SERVICEVARIANT_TABLE_BEAN_NAME, ServiceVariantManager.class);
		List<ServiceVariantBean> tableResult= new ArrayList<ServiceVariantBean>();
		
		if(tableBean.isDetails()){
			PlatformService platformService = Utilities.findBean(ApplicationConstants.PLATFORM_SERVICE_BEAN_NAME, PlatformService.class);
			SearchPlatformByServiceVariantResp resp=
					platformService.searchPlatformByServiceVariant(tableBean.getSelectedBean().getServiceVariantName(), Utilities.getTenantName());
			if (resp.getResultCode().equals(ApplicationConstants.CODE_OK)) {
				tableResult = ServiceVariantConverter.buildServiceVariantTable(resp);
			}
		} else if (tableBean.getParentTemplate()!=null){
			SearchServiceVariantsByServiceTemplateResp resp=
					service.searchServiceVariantsByServiceTemplate(tableBean.getParentTemplate().getServiceTemplateName(),  startRow, numElementToFind, Utilities.getTenantName());
			if (resp.getResultCode().equals(ApplicationConstants.CODE_OK)) {
				tableResult = ServiceVariantConverter.buildServiceVariantTable(resp);
			}
		} else {
			SearchAllServiceVariantsResp resp=service.searchAllServiceVariants(startRow, numElementToFind, Utilities.getTenantName());
			if (resp.getResultCode().equals(ApplicationConstants.CODE_OK)) {
				tableResult = ServiceVariantConverter.buildServiceVariantTable(resp);
			}
		}
	
		return tableResult;
		
	}
	
	public static List<ServiceVariantBean> searchServiceVariantsByServiceTemplate(String serviceTemplateName) throws ServiceErrorException{
		ServiceVariantService service = Utilities.findBean(ApplicationConstants.SERVICEVARIANT_SERVICE_BEAN_NAME, ServiceVariantService.class);
		List<ServiceVariantBean> tableResult = new ArrayList<ServiceVariantBean>();
		SearchServiceVariantsByServiceTemplateResp resp=service.searchServiceVariantsByServiceTemplate(serviceTemplateName,  0L, 0L, Utilities.getTenantName());
		if (resp.getResultCode().equals(ApplicationConstants.CODE_OK)) {
			tableResult = ServiceVariantConverter.buildServiceVariantTable(resp);
		}
		return tableResult;
	}
	
	

	public static void addServiceVariant(ServiceVariantBean serviceVariantBean) {
		
		String serviceVariantName = serviceVariantBean.getServiceVariantName();
		String templateName = serviceVariantBean.getTemplateName();
		String serviceVariantDesc = serviceVariantBean.getServiceVariantDesc();
		String serviceVariantExtId = serviceVariantBean.getServiceVariantExtId();
		String serviceVariantProfile = serviceVariantBean.getServiceVariantProfile();
		
		LoggerWrapper log = new LoggerWrapper(ServiceVariantBusiness.class);
		HashMap<String,Object> validationMap = new HashMap<String,Object>();
		validationMap.put(ApplicationConstants.SERVICEVARIANT_VALIDATION_NAME_FIELD, serviceVariantName);
		validationMap.put(ApplicationConstants.SERVICEVARIANT_VALIDATION_TEMPLATE_FIELD, serviceVariantBean.getParentServiceTemplate().getServiceTemplateId());
		if (ValidationUtilities.validateMandatoryFields(validationMap)){
			// logging
			String serviceVariantNameLbl = 
					MessageConstants.SERVICEVARIANT_NAME_LBL;
			String serviceVariantDescLbl = 
					MessageConstants.SERVICEVARIANT_DESC_LBL;
			String templateNameLbl = 
					MessageConstants.SERVICEVARIANT_TEMPLATENAME_LBL;
			String serviceVariantExtIdLbl = 
					MessageConstants.SERVICEVARIANT_EXTID_LBL;
			String serviceVariantProfileLbl = 
					MessageConstants.SERVICEVARIANT_PROFILE_LBL;
			Map<String,Object> logMap = new HashMap<String,Object>();
			logMap.put(serviceVariantNameLbl, serviceVariantName);
			logMap.put(serviceVariantDescLbl, serviceVariantDesc);
			logMap.put(templateNameLbl, templateName);
			logMap.put(serviceVariantExtIdLbl, serviceVariantExtId);
			logMap.put(serviceVariantProfileLbl, serviceVariantProfile);

			log.logStartFeature(logMap);
			logMap.clear();

			ServiceVariantManager tableBean = Utilities.findBean(ApplicationConstants.SERVICEVARIANT_TABLE_BEAN_NAME, ServiceVariantManager.class);
			
			String mex;
			String code;
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			try {
				ServiceVariantService service = Utilities.findBean(ApplicationConstants.SERVICEVARIANT_SERVICE_BEAN_NAME, ServiceVariantService.class);
				service.createServiceVariant(serviceVariantName, serviceVariantDesc, serviceVariantExtId, 
						serviceVariantProfile, serviceVariantBean.getParentServiceTemplate().getServiceTemplateId(), Utilities.getTenantName());
				mex = String.format(
						Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE,MessageConstants.OK_MESSAGE),
						Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE,MessageConstants.ADD_SERVICEVARIANT_MESSAGE),
						serviceVariantName);
				code = ApplicationConstants.CODE_OK;
				tableBean.refreshTable();
				
				
				msgBean.setNextParam(PathConstants.SERVICEVARIANT);
				
			} catch (ServiceErrorException e) {
				code = e.getCode();
				mex = e.getMessage();
				
			}
			msgBean.openPopup(mex);

			// logging
			log.logEndFeature(code,mex);
		}
	}

	public static void updateServiceVariant(ServiceVariantBean serviceVariantBean) {
		
		Long serviceVariantId = serviceVariantBean.getServiceVariantId();
		String serviceVariantName = serviceVariantBean.getServiceVariantName();
		String serviceVariantDesc = serviceVariantBean.getServiceVariantDesc();
		String serviceVariantExtId = serviceVariantBean.getServiceVariantExtId();
		String serviceVariantProfile = serviceVariantBean.getServiceVariantProfile();
		
		LoggerWrapper log = new LoggerWrapper(ServiceVariantBusiness.class);
		HashMap<String,Object> validationMap = new HashMap<String,Object>();
		validationMap.put(ApplicationConstants.SERVICEVARIANT_VALIDATION_NAME_FIELD, serviceVariantName);
		if (ValidationUtilities.validateMandatoryFields(validationMap)){
			// logging
			String serviceVariantNameLbl = 
					MessageConstants.SERVICEVARIANT_NAME_LBL;
			String serviceVariantDescLbl = 
					MessageConstants.SERVICEVARIANT_DESC_LBL;
			String serviceVariantExtIdLbl = 
					MessageConstants.SERVICEVARIANT_EXTID_LBL;
			String serviceVariantProfileLbl = 
					MessageConstants.SERVICEVARIANT_PROFILE_LBL;
			Map<String,Object> logMap = new HashMap<String,Object>();
			logMap.put(serviceVariantNameLbl, serviceVariantName);
			logMap.put(serviceVariantDescLbl, serviceVariantDesc);
			logMap.put(serviceVariantExtIdLbl, serviceVariantExtId);
			logMap.put(serviceVariantProfileLbl, serviceVariantProfile);
			log.logStartFeature(logMap);
			logMap.clear();

			String mex;
			String code;
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			try {
				ServiceVariantService service = Utilities.findBean(ApplicationConstants.SERVICEVARIANT_SERVICE_BEAN_NAME, ServiceVariantService.class);
				service.modifyServiceVariant(serviceVariantId,serviceVariantName, serviceVariantDesc, serviceVariantExtId, 
						serviceVariantProfile, serviceVariantBean.getParentServiceTemplate().getServiceTemplateId(), Utilities.getTenantName());
				mex = String.format(
						Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE,MessageConstants.OK_MESSAGE),
						Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE,MessageConstants.UPDATE_SERVICEVARIANT_MESSAGE),
						serviceVariantName);
				code = ApplicationConstants.CODE_OK;
				msgBean.setNextParam(PathConstants.SERVICEVARIANT);
				
				msgBean.setUpdateFlag(true);
			} catch (ServiceErrorException e) {
				code = e.getCode();
				mex = e.getMessage();
				
			}
			msgBean.openPopup(mex);
			// logging
			log.logEndFeature(code,mex);
		}
	}
	
	public static void changeStatus(ServiceVariantBean serviceVariantBean, String changeStatusValue) {
		LoggerWrapper log = new LoggerWrapper(ServiceVariantBusiness.class);
		SessionController infoSessionBean = Utilities.findBean(ApplicationConstants.INFO_SESSION_BEAN_NAME, SessionController.class);
		String entityName = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.SERVICE_VARIANT);
		String operation = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, "statusName." + changeStatusValue);
		log.logStartFeature(infoSessionBean.getBreadCrumbAsString()+"->" + operation + " " + entityName, null);

		String mex;
		String code;
		PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
		try {
			ServiceVariantService service = Utilities.findBean(ApplicationConstants.SERVICEVARIANT_SERVICE_BEAN_NAME, ServiceVariantService.class);
			if (StatusConstants.DELETED.equals(changeStatusValue)){
				service.deleteServiceVariant(serviceVariantBean.getServiceVariantId(), Utilities.getTenantName());
			} else {
				service.changeStatus(serviceVariantBean.getServiceVariantId(), changeStatusValue, Utilities.getTenantName());	
			}
			String message = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.OK_MESSAGE);
			mex = String.format(message, operation + " " + entityName, serviceVariantBean.getServiceVariantName());
			code = ApplicationConstants.CODE_OK;
			serviceVariantBean.setServiceVariantStatus(changeStatusValue);
			
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
