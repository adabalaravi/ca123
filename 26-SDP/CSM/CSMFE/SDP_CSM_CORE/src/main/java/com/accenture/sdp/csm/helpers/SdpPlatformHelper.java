package com.accenture.sdp.csm.helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.database.NamedQueryHelper;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.model.jpa.SdpPlatform;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.validation.ValidationResult;
import com.accenture.sdp.csm.validation.ValidationUtils;

public final class SdpPlatformHelper extends SdpBaseHelper {

	private static SdpPlatformHelper instance;

	private SdpPlatformHelper() {
		super();
	}

	public static SdpPlatformHelper getInstance() {
		if (instance == null) {
			instance = new SdpPlatformHelper();
		}
		return instance;
	}

	public SdpPlatform createPlatform(String platformName, String platformDescription, String externalId, String platformProfile, String createdBy,
			EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpPlatform newPlatform = new SdpPlatform();
		newPlatform.setPlatformName(platformName);
		newPlatform.setPlatformDescription(platformDescription);
		newPlatform.setStatusId(ConstantsHandler.getInstance().retrieveLongConstant(Constants.ACTIVE));
		newPlatform.setExternalId(externalId);
		newPlatform.setPlatformProfile(platformProfile);
		newPlatform.setCreatedById(createdBy);
		newPlatform.setCreatedDate(new Date());
		log.logDebug("Persisting new Platform");
		em.persist(newPlatform);
		return newPlatform;
	}

	public void modifyPlatform(SdpPlatform toModify, String platformName, String platformDescription, String externalId, String platformProfile, String modifyBy)
			throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		// Model valorization
		toModify.setPlatformName(platformName);
		toModify.setPlatformDescription(platformDescription);
		toModify.setExternalId(externalId);
		toModify.setPlatformProfile(platformProfile);
		toModify.setUpdatedById(modifyBy);
		toModify.setUpdatedDate(new Date());
	}

	public void deletePlatform(SdpPlatform toDelete, String deletedBy) throws ValidationException, PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		// Model valorization
		toDelete.setStatusId(ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED));
		toDelete.setDeletedById(deletedBy);
		toDelete.setDeletedDate(new Date());
	}

	public SdpPlatform searchPlatformByName(String platformName, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(platformName)) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put("platformName", platformName);
		List<SdpPlatform> result = NamedQueryHelper.executeNamedQuery(SdpPlatform.class, "selectPlatformByName", parameHashMap, em);
		if (result == null || result.isEmpty()) {
			return null;
		}
		return result.get(0);
	}

	public List<SdpPlatform> searchAllPlatforms(EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		return NamedQueryHelper.executeNamedQuery(SdpPlatform.class, "selectAllPlatforms", parameHashMap, em);
	}

	public List<SdpPlatform> searchAllPlatforms(Long startPosition, Long maxRecordsNumber, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		return NamedQueryHelper.executePaginatedNamedQuery(SdpPlatform.class, "selectAllPlatforms", parameHashMap, startPosition, maxRecordsNumber, em);
	}

	public void validatePlatform(String platformName) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(PLATFORM_NAME, platformName);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug("End parameters validation");

	}

	public void changeStatus(SdpPlatform platform, Long nextStatus, String changedBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		platform.setChangeStatusDate(new Date());
		platform.setChangeStatusById(changedBy);
		platform.setStatusId(nextStatus);
	}

	public SdpPlatform searchPlatformById(Long platformId, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (platformId == null) {
			return null;
		}
		return em.find(SdpPlatform.class, platformId);
	}

	public void validateSearchPlatformById(Long platformId) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("platformId", platformId);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);

	}

	public Long countPlatform(EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		return NamedQueryHelper.executeNamedQueryCount("selectAllPlatformsCount", parameHashMap, em);
	}

	public SdpPlatform searchByExternalId(String externalId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return super.searchByExternalId(SdpPlatform.class, "selectPlatformByExternalId", externalId, em);
	}

}
