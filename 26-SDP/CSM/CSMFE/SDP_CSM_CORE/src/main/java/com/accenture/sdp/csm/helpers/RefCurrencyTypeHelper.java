package com.accenture.sdp.csm.helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.database.NamedQueryHelper;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.model.jpa.RefCurrencyType;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.validation.ValidationResult;
import com.accenture.sdp.csm.validation.ValidationUtils;

public final class RefCurrencyTypeHelper extends SdpBaseHelper {

	private static RefCurrencyTypeHelper instance;

	private RefCurrencyTypeHelper() {
		super();
	}

	public static RefCurrencyTypeHelper getInstance() {
		if (instance == null) {
			instance = new RefCurrencyTypeHelper();
		}
		return instance;
	}

	public RefCurrencyType searchCurrencyById(Long refCurrencyTypeId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (refCurrencyTypeId == null) {
			return null;
		}
		return em.find(RefCurrencyType.class, refCurrencyTypeId);
	}

	public List<RefCurrencyType> searchAllCurrencies(Long startPosition, Long maxRecordsNumber, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return NamedQueryHelper.executePaginatedNamedQuery(RefCurrencyType.class, RefCurrencyType.CURRENCY_RETRIEVE_ALL, null, startPosition, maxRecordsNumber,
				em);
	}

	public List<RefCurrencyType> searchAllCurrencies(EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return NamedQueryHelper.executeNamedQuery(RefCurrencyType.class, RefCurrencyType.CURRENCY_RETRIEVE_ALL, null, em);
	}

	public Long countAllCurrencies(EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return NamedQueryHelper.executeNamedQueryCount(RefCurrencyType.CURRENCY_COUNT_ALL, null, em);
	}

	public RefCurrencyType createCurrency(String currencyTypeName, String createdById, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		RefCurrencyType newRefCurrencyType = new RefCurrencyType();
		// Model valorization
		newRefCurrencyType.setCurrencyTypeName(currencyTypeName);
		newRefCurrencyType.setCreatedById(createdById);
		newRefCurrencyType.setCreatedDate(new Date());
		em.persist(newRefCurrencyType);
		return newRefCurrencyType;

	}

	public void deleteCurrency(RefCurrencyType currencyTypeToDelete, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		em.remove(currencyTypeToDelete);
	}

	public void validateCurrencyType(String currencyTypeName) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(CURRENCY_TYPE_NAME, currencyTypeName);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateSearchCurrencyTypeById(Long currencyTypeId) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(CURRENCY_TYPE_ID, currencyTypeId);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public RefCurrencyType searchCurrencyTypeByName(String currencyTypeName, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(currencyTypeName)) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(RefCurrencyType.PARAM_CURRENCY_TYPE_NAME, currencyTypeName);
		List<RefCurrencyType> results = NamedQueryHelper.executeNamedQuery(RefCurrencyType.class, RefCurrencyType.CURRENCY_RETRIEVE_BY_NAME, parameHashMap, em);
		if (results == null || results.isEmpty()) {
			return null;
		}
		return results.get(0);
	}

}
