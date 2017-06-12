package com.accenture.sdp.csm.helpers;

import java.util.Date;
import java.util.HashMap;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.commons.FlagValue.Flag;
import com.accenture.sdp.csm.database.NamedQueryHelper;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.model.jpa.RefCurrencyType;
import com.accenture.sdp.csm.model.jpa.RefFrequencyType;
import com.accenture.sdp.csm.model.jpa.SdpPackagePrice;
import com.accenture.sdp.csm.model.jpa.SdpPriceCatalog;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.validation.ValidationResult;
import com.accenture.sdp.csm.validation.ValidationUtils;

public final class SdpPackagePriceHelper extends SdpBaseHelper {

	private static SdpPackagePriceHelper instance;

	private SdpPackagePriceHelper() {
		super();
	}

	public static SdpPackagePriceHelper getInstance() {
		if (instance == null) {
			instance = new SdpPackagePriceHelper();
		}
		return instance;
	}

	public SdpPackagePrice createPackagePrice(SdpPriceCatalog refPriceCatalogRC, SdpPriceCatalog refPriceCatalogNRC, RefCurrencyType refCurrencyType,
			RefFrequencyType refFrequencyType, String rcFlagProrate, String rcInAdvance, String createdById, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpPackagePrice newPackagePrice = new SdpPackagePrice();
		// Model valorization
		newPackagePrice.setRefPriceCatalog1(refPriceCatalogRC);
		newPackagePrice.setRefPriceCatalog2(refPriceCatalogNRC);
		newPackagePrice.setRefCurrencyType(refCurrencyType);
		newPackagePrice.setRefFrequencyType(refFrequencyType);
		newPackagePrice.setRcInAdvance(rcInAdvance);
		newPackagePrice.setRcFlagProrate(rcFlagProrate);
		newPackagePrice.setCreatedById(createdById);
		newPackagePrice.setCreatedDate(new Date());
		em.persist(newPackagePrice);
		return newPackagePrice;
	}

	public void deletePackagePrice(SdpPackagePrice packagePriceToDelete, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		em.remove(packagePriceToDelete);
	}

	public void modifyPackagePrice(SdpPackagePrice packagePriceToUpdate, SdpPriceCatalog refPriceCatalogRC, SdpPriceCatalog refPriceCatalogNRC,
			RefCurrencyType refCurrencyType, RefFrequencyType refFrequencyType, String rcFlagProrate, String rcInAdvance, String modifiedBy, EntityManager em)
			throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		packagePriceToUpdate.setRefPriceCatalog1(refPriceCatalogRC);
		packagePriceToUpdate.setRefPriceCatalog2(refPriceCatalogNRC);
		packagePriceToUpdate.setRefCurrencyType(refCurrencyType);
		packagePriceToUpdate.setRefFrequencyType(refFrequencyType);
		packagePriceToUpdate.setRcInAdvance(rcInAdvance);
		packagePriceToUpdate.setRcFlagProrate(rcFlagProrate);
		packagePriceToUpdate.setUpdatedById(modifiedBy);
		packagePriceToUpdate.setUpdatedDate(new Date());
	}

	public SdpPackagePrice searchPackagePriceById(Long packagePriceId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (packagePriceId == null) {
			return null;
		}
		return em.find(SdpPackagePrice.class, packagePriceId);
	}

	public Long searchPackagePriceByFrequencyIdCount(Long frequencyTypeId, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (frequencyTypeId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put("frequencyTypeId", frequencyTypeId);
		return NamedQueryHelper.executeNamedQueryCount("selectPackagePriceByFrequencyIdCount", parameHashMap, em);
	}

	public Long searchPackagePriceByCurrencyTypeIdCount(Long currencyTypeId, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (currencyTypeId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put("currencyTypeId", currencyTypeId);
		return NamedQueryHelper.executeNamedQueryCount("selectPackagePriceByCurrencyTypeIdCount", parameHashMap, em);
	}

	public Long searchPackagePriceByPriceCatalogIdCount(Long priceCatalogId, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (priceCatalogId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put("priceCatalogId", priceCatalogId);
		return NamedQueryHelper.executeNamedQueryCount("selectPackagePriceByPriceCatalogeIdCount", parameHashMap, em);
	}

	public void validatePackagePrice(Long rcPriceCatalogId, Long nrcPriceCatalogId, Long currencyTypeId, Long rcFrequencyTypeId, String rcFlagProrate,
			String rcInAdvance) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("rcPriceCatalogId", rcPriceCatalogId);
		validationMap.put("nrcPriceCatalogId", nrcPriceCatalogId);
		validationMap.put("currencyTypeId", currencyTypeId);
		validationMap.put("rcFrequencyTypeId", rcFrequencyTypeId);
		validationMap.put("rcFlagProrate", rcFlagProrate);
		validationMap.put("rcInAdvance", rcInAdvance);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		if (rcPriceCatalogId.longValue() < 0) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, "rcPriceCatalogId", String.valueOf(rcPriceCatalogId));
		}
		if (nrcPriceCatalogId.longValue() < 0) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, "nrcPriceCatalogId", String.valueOf(nrcPriceCatalogId));
		}
		if (currencyTypeId.longValue() < 0) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, "CurrencyTypeId", String.valueOf(currencyTypeId));
		}
		if (rcFrequencyTypeId.longValue() < 0) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, "rcFrequencyTypeId", String.valueOf(rcFrequencyTypeId));
		}
		if (!Flag.checkAllowedValue(rcFlagProrate)) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, "rcFlagProrate", rcFlagProrate);
		}
		if (!Flag.checkAllowedValue(rcInAdvance)) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, "rcInAdvance", rcInAdvance);
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateSearchPackagePriceById(Long packagePriceId) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("packagePriceId", packagePriceId);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}
}
