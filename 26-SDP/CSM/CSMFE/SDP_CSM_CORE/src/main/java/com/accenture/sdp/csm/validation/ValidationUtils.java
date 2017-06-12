package com.accenture.sdp.csm.validation;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.accenture.sdp.csm.commons.DeviceEnums;
import com.accenture.sdp.csm.commons.LinkUpdateOperation;
import com.accenture.sdp.csm.commons.StatusIdConverter;
import com.accenture.sdp.csm.commons.GenderType.Gender;
import com.accenture.sdp.csm.commons.IsMandatory.Mandatory;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.Utilities;

public abstract class ValidationUtils {

	public static ValidationResult validateMandatoryFields(Map<String, Object> parameterMap) {
		ValidationResult res = new ValidationResult();
		for (Entry<String, Object> entry : parameterMap.entrySet()) {
			if (Utilities.isNull(entry.getValue())) {
				res.setValid(false);
				res.addParam(entry.getKey(), null);
			}
		}
		return res;
	}
	
	public static ValidationResult validateForbiddenFields(Map<String, Object> parameterMap) {
		ValidationResult res = new ValidationResult();
		for (Entry<String, Object> entry : parameterMap.entrySet()) {
			if (!Utilities.isNull(entry.getValue())) {
				res.setValid(false);
				res.addParam(entry.getKey(), null);
			}
		}
		return res;
	}

	public static ValidationResult validateSoftMandatoryFields(Map<String, Object> parameterMap) {
		ValidationResult res = new ValidationResult();
		res.setValid(false);
		// at least one have to be present : no need every one
		for (Entry<String, Object> entry : parameterMap.entrySet()) {
			if (!Utilities.isNull(entry.getValue())) {
				res.setValid(true);
				break;
			}
		}
		if (!res.isValid()) {
			for (String key : parameterMap.keySet()) {
				res.addParam(key, null);
			}
		}
		return res;
	}

	public static ValidationResult validateExclusiveFields(Map<String, Object> parameterMap) {
		ValidationResult res = new ValidationResult();
		res.setValid(true);
		boolean alreadyFound = false;
		// no more than one can be present
		for (Entry<String, Object> entry : parameterMap.entrySet()) {
			if (!Utilities.isNull(entry.getValue())) {
				if (alreadyFound) {
					res.setValid(false);
					break;
				} else {
					alreadyFound = true;
				}
			}
		}
		if (!res.isValid()) {
			for (Entry<String, Object> entry : parameterMap.entrySet()) {
				res.addParam(entry.getKey(), String.valueOf(entry.getValue()));
			}
		}
		return res;
	}

	public static ValidationResult validatePositiveDecimalFields(Map<String, BigDecimal> parameterMap) {
		ValidationResult res = new ValidationResult();
		for (Entry<String, BigDecimal> entry : parameterMap.entrySet()) {
			BigDecimal value = entry.getValue();
			if (value != null && value.compareTo(BigDecimal.ZERO) < 0) {
				res.setValid(false);
				res.addParam(entry.getKey(), value.toPlainString());
			}
		}
		return res;
	}

	public static ValidationResult validatePositiveLongFields(Map<String, Long> parameterMap) {
		ValidationResult res = new ValidationResult();
		for (Entry<String, Long> entry : parameterMap.entrySet()) {
			Long value = entry.getValue();
			if (value != null && value < 0L) {
				res.setValid(false);
				res.addParam(entry.getKey(), String.valueOf(value));
			}
		}
		return res;
	}

	public static ValidationResult validateGender(String gender) {
		ValidationResult res = new ValidationResult();
		if (gender != null) {
			try {
				Gender.getGenderValue(gender);
				res.setValid(true);
			} catch (Exception e) {
				res.addParam("gender", gender);
				res.setValid(false);
			}
		}
		return res;
	}

	public static ValidationResult validateIsMandatory(String mandatoryParam) {
		ValidationResult res = new ValidationResult();
		if (mandatoryParam != null) {
			try {
				Mandatory.getMandatoryValue(mandatoryParam);
				res.setValid(true);
			} catch (Exception e) {
				res.addParam("isMandatory", mandatoryParam);
				res.setValid(false);
			}
		}
		return res;
	}

	public static ValidationResult validateLinkUpdateOperation(String operation) {
		ValidationResult res = new ValidationResult();
		if (operation != null) {
			try {
				LinkUpdateOperation.Operation.getOperationValue(operation);
				res.setValid(true);
			} catch (Exception e) {
				res.addParam("operation", operation);
				res.setValid(false);
			}
		}
		return res;
	}

	public static ValidationResult validateDeviceAccess(String opType, String itemType) {
		ValidationResult res = new ValidationResult();
		if (opType != null) {
			try {
				DeviceEnums.getOperation(opType);
			} catch (Exception e) {
				res.addParam("opType", opType);
				res.setValid(false);
			}
		}
		if (itemType != null) {
			try {
				DeviceEnums.getItem(itemType);
			} catch (Exception e) {
				res.addParam("itemType", itemType);
				res.setValid(false);
			}
		}
		return res;
	}

	public static ValidationResult validateFilter(String filter) {
		ValidationResult res = new ValidationResult();
		if (filter != null) {
			try {
				DeviceEnums.getFilter(filter);
			} catch (Exception e) {
				res.addParam("filter", filter);
				res.setValid(false);
			}
		}
		return res;
	}
	
	public static ValidationResult validateAuthMode(String authMode) {
		ValidationResult res = new ValidationResult();
		if (authMode != null) {
			try {
				DeviceEnums.getAuthMode(authMode);
			} catch (Exception e) {
				res.addParam("authMode", authMode);
				res.setValid(false);
			}
		}
		return res;
	}

	public static ValidationResult validateStatus(String status) {
		ValidationResult res = new ValidationResult();
		try {
			StatusIdConverter.getStatusValue(status);
		} catch (Exception e) {
			res.addParam("status", status);
			res.setValid(false);
		}
		return res;
	}

	public static ValidationResult isAfterNow(String paramName, Date date) {
		ValidationResult res = new ValidationResult();
		if (date.after(new Date())) {
			res.setValid(false);
			res.addParam(paramName, Utilities.formatDate(date));
		}
		return res;
	}

	public static ValidationResult isStartAfterEnd(Date startDate, Date endDate) {
		ValidationResult res = new ValidationResult();
		if (startDate.after(endDate)) {
			res.setValid(false);
			res.addParam("startDate", Utilities.formatDate(startDate));
		}
		return res;
	}

	// SDP_02_11
	public static ValidationResult validateDates(Map<String, Date> parameterMap) {
		// pre-requisite:
		final int maxYear = 10000;
		GregorianCalendar dateNearTomorrow = new GregorianCalendar();
		dateNearTomorrow.clear();
		dateNearTomorrow.set(maxYear, 0, 1);
		// validation
		ValidationResult res = new ValidationResult();
		for (Entry<String, Date> entry : parameterMap.entrySet()) {
			Date date = entry.getValue();
			if (date != null && date.after(dateNearTomorrow.getTime())) {
				res.setValid(false);
				res.addParam(entry.getKey(), Utilities.formatDate(date));
			}
		}
		return res;
	}

	public static void validateDates(Date startDate, Date endDate) throws ValidationException {
		HashMap<String, Date> validationMap = new HashMap<String, Date>();
		validationMap.put("endDate", endDate);
		validationMap.put("startDate", startDate);
		ValidationResult res = ValidationUtils.validateDates(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, res.getParams());
		}
		if (endDate != null) {
			if (startDate == null) {
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, "endDate", Utilities.formatDate(endDate));
			} else {
				res = ValidationUtils.isStartAfterEnd(startDate, endDate);
				if (!res.isValid()) {
					throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, res.getParams());
				}
			}
		}
	}
}
