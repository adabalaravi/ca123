package com.accenture.sdp.csm.helpers;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.database.NamedQueryHelper;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.model.jpa.SdpPriceCatalog;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.validation.ValidationResult;
import com.accenture.sdp.csm.validation.ValidationUtils;

public final class SdpPriceCatalogHelper extends SdpBaseHelper {

	private static SdpPriceCatalogHelper instance;

	private SdpPriceCatalogHelper() {
		super();
	}

	public static SdpPriceCatalogHelper getInstance() {
		if (instance == null) {
			instance = new SdpPriceCatalogHelper();
		}
		return instance;
	}

	public SdpPriceCatalog createPriceCatalog(BigDecimal price, String createdById, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpPriceCatalog newSdpPriceCatalog = new SdpPriceCatalog();
		// Model valorization
		newSdpPriceCatalog.setPrice(price);
		newSdpPriceCatalog.setCreatedById(createdById);
		newSdpPriceCatalog.setCreatedDate(new Date());
		em.persist(newSdpPriceCatalog);
		return newSdpPriceCatalog;
	}

	public void deletePriceCatalog(SdpPriceCatalog priceCatalogToDelete, String deletedBy, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		em.remove(priceCatalogToDelete);
	}

	public SdpPriceCatalog searchPriceCatalogById(Long priceCatalogId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (priceCatalogId == null) {
			return null;
		}
		return em.find(SdpPriceCatalog.class, priceCatalogId);
	}

	public List<SdpPriceCatalog> searchAllPrices(EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> paramHashMap = new HashMap<String, Object>();
		return NamedQueryHelper.executeNamedQuery(SdpPriceCatalog.class, "searchAllPrices", paramHashMap, em);
	}

	public List<SdpPriceCatalog> searchAllPrices(Long startPosition, Long maxRecordsNumber, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> paramHashMap = new HashMap<String, Object>();
		return NamedQueryHelper.executePaginatedNamedQuery(SdpPriceCatalog.class, "searchAllPrices", paramHashMap, startPosition, maxRecordsNumber, em);
	}

	public Long searchAllPricesCount(EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> paramHashMap = new HashMap<String, Object>();
		return NamedQueryHelper.executeNamedQueryCount("searchAllPricesCount", paramHashMap, em);
	}

	public void validatePriceCatalog(BigDecimal price) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("price", price);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		// validate positive
		HashMap<String, BigDecimal> validationNumbers = new HashMap<String, BigDecimal>();
		validationNumbers.put("price", price);
		res = ValidationUtils.validatePositiveDecimalFields(validationNumbers);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateSearchPriceCatalogById(Long priceCatalogId) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("priceCatalogId", priceCatalogId);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public SdpPriceCatalog searchPriceCatalogByPrice(BigDecimal price, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (price == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put("price", price);
		List<SdpPriceCatalog> results = NamedQueryHelper.executeNamedQuery(SdpPriceCatalog.class, "searchByPrice", parameHashMap, em);
		if (results == null || results.isEmpty()) {
			return null;
		}
		return results.get(0);
	}

}
