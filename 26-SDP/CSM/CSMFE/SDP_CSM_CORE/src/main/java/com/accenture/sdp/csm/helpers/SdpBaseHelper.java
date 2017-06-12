package com.accenture.sdp.csm.helpers;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.commons.StatusIdConverter;
import com.accenture.sdp.csm.database.NamedQueryHelper;
import com.accenture.sdp.csm.dto.responses.ParameterDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.model.jpa.SdpStatusVariation;
import com.accenture.sdp.csm.model.jpa.SdpStatusVariationPK;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.FieldConstants;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LoggerWrapper;
import com.accenture.sdp.csm.validation.ValidationResult;
import com.accenture.sdp.csm.validation.ValidationUtils;

public abstract class SdpBaseHelper implements FieldConstants {

	protected LoggerWrapper log;

	protected static final String VALIDATION_START = "Starting parameters validation";
	protected static final String VALIDATION_END = "Validation Ended";

	protected SdpBaseHelper() {
		log = new LoggerWrapper(this.getClass());
	}

	public void validateChangeStatus(String status) throws ValidationException, PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(STATUS, status);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		res = ValidationUtils.validateStatus(status);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, res.getParams());
		}
		if (status.equals(StatusIdConverter.getStatusDescription(ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED)))) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, STATUS, status);
		}
		log.logDebug(VALIDATION_END);
	}

	public void checkAllowedChangeStatus(Long entityTypeId, Long previousStatusId, Long nextStatusId, EntityManager em) throws ValidationException,
			PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod() + " from " + previousStatusId + " to " + nextStatusId);
		SdpStatusVariationPK sdpStatusVariationPK = new SdpStatusVariationPK();
		sdpStatusVariationPK.setEntityId(entityTypeId);
		sdpStatusVariationPK.setPreviousStatusId(previousStatusId);
		sdpStatusVariationPK.setNextStatusId(nextStatusId);
		SdpStatusVariation variation = em.find(SdpStatusVariation.class, sdpStatusVariationPK);
		if (variation == null) {
			throw new ValidationException(Constants.CODE_STATUS_TRANSACTION_ERROR, new ParameterDto("fromStatus",
					StatusIdConverter.getStatusDescription(previousStatusId)), new ParameterDto("toStatus",
					StatusIdConverter.getStatusDescription(nextStatusId)));
		}
	}

	public ValidationResult validateSearchAll(Long startPosition, Long maxRecordsNumber) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod() + " from startPosition: %s for maxRecordsNumber:  %s", startPosition, maxRecordsNumber);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(START_POSITION, startPosition);
		validationMap.put(MAX_RECORDS, maxRecordsNumber);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		return res;
	}

	protected <T> T searchByExternalId(Class<T> clazz, String queryName, String externalId, EntityManager em) {
		if (Utilities.isNull(externalId)) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put("externalId", externalId);
		return NamedQueryHelper.executeNamedQuerySingle(clazz, queryName, parameHashMap, em);
	}
}
