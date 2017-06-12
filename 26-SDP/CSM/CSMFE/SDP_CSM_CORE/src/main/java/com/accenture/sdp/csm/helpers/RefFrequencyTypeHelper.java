package com.accenture.sdp.csm.helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.database.NamedQueryHelper;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.model.jpa.RefFrequencyType;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.validation.ValidationResult;
import com.accenture.sdp.csm.validation.ValidationUtils;

public final class RefFrequencyTypeHelper extends SdpBaseHelper {

	private static RefFrequencyTypeHelper instance;

	private RefFrequencyTypeHelper() {
		super();
	}

	public static RefFrequencyTypeHelper getInstance() {
		if (instance == null) {
			instance = new RefFrequencyTypeHelper();
		}
		return instance;
	}

	public RefFrequencyType createFrequency(String frequencyTypeName, String frequencyTypeDescription, Long frequencyDays, String createdById, EntityManager em)
			throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		RefFrequencyType newRefFrequencyType = new RefFrequencyType();
		// Model valorization
		newRefFrequencyType.setFrequencyTypeName(frequencyTypeName);
		newRefFrequencyType.setFrequencyTypeDescription(frequencyTypeDescription);
		newRefFrequencyType.setFrequencyDays(frequencyDays);
		newRefFrequencyType.setCreatedById(createdById);
		newRefFrequencyType.setCreatedDate(new Date());
		em.persist(newRefFrequencyType);
		return newRefFrequencyType;

	}

	public void deleteFrequency(RefFrequencyType rrequencyTypeToDelete, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		em.remove(rrequencyTypeToDelete);
	}

	public RefFrequencyType searchFrequencyTypeById(Long frequencyTypeId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (frequencyTypeId == null) {
			return null;
		}
		return em.find(RefFrequencyType.class, frequencyTypeId);
	}

	public List<RefFrequencyType> searchAllFrequencies(Long startPosition, Long maxRecordsNumber, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return NamedQueryHelper.executePaginatedNamedQuery(RefFrequencyType.class, RefFrequencyType.FREQUENCY_RETRIEVE_ALL, null, startPosition,
				maxRecordsNumber, em);
	}

	public Long searchAllFrequenciesCount(EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return NamedQueryHelper.executeNamedQueryCount(RefFrequencyType.FREQUENCY_COUNT_ALL, null, em);
	}

	public List<RefFrequencyType> searchAllFrequencies(EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return NamedQueryHelper.executeNamedQuery(RefFrequencyType.class, RefFrequencyType.FREQUENCY_RETRIEVE_ALL, null, em);
	}

	public void validateFrequencyType(String frequencyTypeName, Long frequencyDays) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(FREQUENCY_TYPE_NAME, frequencyTypeName);
		validationMap.put("frequencyDays", frequencyDays);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateSearchFrequencyTypeById(Long frequencyTypeId) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(FREQUENCY_TYPE_ID, frequencyTypeId);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public RefFrequencyType searchFrequencyTypeByName(String frequencyTypeName, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(frequencyTypeName)) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(RefFrequencyType.PARAM_FREQUENCY_TYPE_NAME, frequencyTypeName);
		List<RefFrequencyType> results = NamedQueryHelper.executeNamedQuery(RefFrequencyType.class, RefFrequencyType.FREQUENCY_RETRIEVE_BY_NAME, parameHashMap,
				em);
		if (results == null || results.isEmpty()) {
			return null;
		}
		return results.get(0);
	}

	public RefFrequencyType searchFrequencyTypeByDays(Long frequencyDays, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (frequencyDays == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(RefFrequencyType.PARAM_FREQUENCY_DAYS, frequencyDays);
		List<RefFrequencyType> results = NamedQueryHelper.executeNamedQuery(RefFrequencyType.class, RefFrequencyType.FREQUENCY_RETRIEVE_BY_DAYS, parameHashMap,
				em);
		if (results == null || results.isEmpty()) {
			return null;
		}
		return results.get(0);
	}

}
