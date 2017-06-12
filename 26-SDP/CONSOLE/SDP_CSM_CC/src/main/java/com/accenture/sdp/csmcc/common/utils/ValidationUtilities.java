package com.accenture.sdp.csmcc.common.utils;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.constants.MessageConstants;

public final class ValidationUtilities {
	
	private ValidationUtilities(){
		
	}

	public static boolean validateMandatoryFields(Map<String, Object> parameterMap) {
		String mex = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.MANDATORY_MESSAGE);
		FacesContext context = FacesContext.getCurrentInstance();
		boolean result = true;
		for (Entry<String, Object> entry : parameterMap.entrySet()) {
			Object value = entry.getValue();
			boolean isValid = true;
			if (value == null) {
				isValid = false;
			} else if ((value instanceof String)
					&& isEmptyString((String) value)) {
				isValid = false;
			}
			if (!isValid) {
				FacesMessage message = new FacesMessage();
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				message.setSummary(mex);
				message.setDetail(mex);
				context.addMessage(entry.getKey(), message);
				result = false;
			}
		}

		return result;
	}
	
	
	
	public static boolean validateExistFields(Map<String, Object> parameterMap) {
		String mex = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.NOT_EXIST_MESSAGE);
		FacesContext context = FacesContext.getCurrentInstance();
		boolean result = true;
		for (Entry<String, Object> entry : parameterMap.entrySet()) {
			Object value = entry.getValue();
			boolean isValid = true;
			if (value == null) {
				isValid = false;
			} else if ((value instanceof String)
					&& isEmptyString((String) value)) {
				isValid = false;
			}
			if (!isValid) {
				FacesMessage message = new FacesMessage();
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				message.setSummary(mex);
				message.setDetail(mex);
				context.addMessage(entry.getKey(), message);
				result = false;
			}
		}

		return result;
	}


	public static boolean validatePriceFormat(String value) {
		boolean res = false;

		try {
			
			new BigDecimal(value);
			res = true;

		} catch (Exception e) {
			String mex = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.NUMBER_FORMAT_VALIDATOR);
			FacesMessage message = new FacesMessage();
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			message.setSummary(mex);
			message.setDetail(mex);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(ApplicationConstants.PRICE_VALIDATION_NAME_FIELD, message);
			res = false;
		}
		return res;

	}
	
	public static boolean validateLong(String value, String field) {
		boolean res = false;

		try {
			new Long(value);
			res = true;

		} catch (Exception e) {
			String mex = Utilities.getMessage(ApplicationConstants.MESSAGE_BUNDLE, MessageConstants.NUMBER_FORMAT_VALIDATOR);
			FacesMessage message = new FacesMessage();
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			message.setSummary(mex);
			message.setDetail(mex);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(field, message);
			res = false;
		}
		return res;

	}
	
	public static boolean isEmptyString(String value){
		return (value == null || value.trim().equals(""));
		
	}

}
