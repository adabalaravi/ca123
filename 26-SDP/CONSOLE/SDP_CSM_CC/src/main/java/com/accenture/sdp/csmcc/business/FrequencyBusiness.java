package com.accenture.sdp.csmcc.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.accenture.sdp.csmcc.beans.FrequencyBean;
import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.constants.MessageConstants;
import com.accenture.sdp.csmcc.common.constants.PathConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmcc.common.utils.ValidationUtilities;
import com.accenture.sdp.csmcc.controllers.SessionController;
import com.accenture.sdp.csmcc.converters.FrequencyConverter;
import com.accenture.sdp.csmcc.managers.FrequencyManager;
import com.accenture.sdp.csmcc.popups.PopupBean;
import com.accenture.sdp.csmcc.services.FrequencyService;
import com.accenture.sdp.csmfe.webservices.clients.frequency.SearchAllFrequenciesResp;

public final class FrequencyBusiness {
	
	private FrequencyBusiness() {
		
	}

	
	
	public static List<FrequencyBean> searchAllFrequencies() throws ServiceErrorException{
		FrequencyService service = Utilities.findBean(ApplicationConstants.FREQUENCY_SERVICE_BEAN_NAME, FrequencyService.class);
		List<FrequencyBean> tableResult= new ArrayList<FrequencyBean>();
			SearchAllFrequenciesResp resp=service.searchAllFrequencies(0L, 0L, Utilities.getTenantName());
			if (resp.getResultCode().equals(ApplicationConstants.CODE_OK)) {
				tableResult = FrequencyConverter.buildFrequencyTable(resp);
			}
		return tableResult;
		
	}
	
	
	public static void addFrequency(FrequencyBean bean) {
		LoggerWrapper log = new LoggerWrapper(FrequencyBean.class);
		HashMap<String,Object> validationMap = new HashMap<String,Object>();
		validationMap.put(ApplicationConstants.FREQUENCY_VALIDATION_NAME_FIELD, bean.getFrequencyName());
		validationMap.put(ApplicationConstants.FREQUENCY_VALIDATION_DAYS_FIELD, bean.getFrequencyDaysString());
		if (ValidationUtilities.validateMandatoryFields(validationMap) && ValidationUtilities.validateLong(bean.getFrequencyDaysString(), ApplicationConstants.FREQUENCY_VALIDATION_DAYS_FIELD)){
			// logging
			Long frequencyDays = new Long(bean.getFrequencyDaysString());
			Map<String,Object> logMap = new HashMap<String,Object>();
			logMap.put(MessageConstants.FREQUENCY_NAME_LBL,  bean.getFrequencyName());
			logMap.put(MessageConstants.FREQUENCY_DESC_LBL, bean.getFrequencyDesc());
			logMap.put(MessageConstants.FREQUENCY_DAY_LBL,frequencyDays);
			log.logStartFeature(logMap);
			logMap.clear();
			FrequencyManager tableBean = Utilities.findBean(ApplicationConstants.FREQUENCY_MANAGER, FrequencyManager.class);
			FrequencyService service = Utilities.findBean(ApplicationConstants.FREQUENCY_SERVICE_BEAN_NAME, FrequencyService.class);

			String mex;
			String code;
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			try {
				service.createFrequency( bean.getFrequencyName(), bean.getFrequencyDesc(),frequencyDays, Utilities.getTenantName());
				code = ApplicationConstants.CODE_OK;
				mex = String.format(Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE,MessageConstants.OK_MESSAGE),
						Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE,MessageConstants.ADD_FREQUENCY_MESSAGE), bean.getFrequencyName());
				tableBean.refreshTable();
				
				msgBean.setNextParam(PathConstants.FREQUENCY_VIEW);
			} catch (ServiceErrorException e) {
				code = e.getCode();
				mex = e.getMessage();
			}			
			msgBean.openPopup(mex);
			log.logEndFeature(code,mex);
		}
	}
	
	public static void deleteFrequency(FrequencyBean bean) {
		// logging
		LoggerWrapper log = new LoggerWrapper(FrequencyManager.class);
		SessionController infoSessionBean = Utilities.findBean(ApplicationConstants.INFO_SESSION_BEAN_NAME, SessionController.class);
		FrequencyService service = Utilities.findBean(ApplicationConstants.FREQUENCY_SERVICE_BEAN_NAME, FrequencyService.class);

		String delete = Utilities.getDefaultMessage(ApplicationConstants.MESSAGE_BUNDLE,MessageConstants.DELETE_FREQUENCY);
		log.logStartFeature(infoSessionBean.getBreadCrumbAsString() + "->" + delete, null);
		String mex;
		String code;
		PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
		try {
			service.deleteFrequency(bean.getFrequencyId(), Utilities.getTenantName());
			code = ApplicationConstants.CODE_OK;
			mex = String.format(
					Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE,MessageConstants.OK_MESSAGE),
					Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE,MessageConstants.DELETE_FREQUENCY_MESSAGE),
					bean.getFrequencyName());
			
		} catch (ServiceErrorException e) {
			code = e.getCode();
			mex = e.getMessage();
			
		}
		msgBean.openPopup(mex);

		// logging
		log.logEndFeature(infoSessionBean.getBreadCrumbAsString() + "->"
				+ delete, code, mex);
	}

}
