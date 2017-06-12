package com.accenture.sdp.csm.helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.database.NamedQueryHelper;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.model.jpa.RefServiceType;
import com.accenture.sdp.csm.model.jpa.SdpPlatform;
import com.accenture.sdp.csm.model.jpa.SdpServiceTemplate;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.validation.ValidationResult;
import com.accenture.sdp.csm.validation.ValidationUtils;

public final class SdpServiceTemplateHelper extends SdpBaseHelper {

	private static SdpServiceTemplateHelper instance;

	private SdpServiceTemplateHelper() {
		super();
	}

	public static SdpServiceTemplateHelper getInstance() {
		if (instance == null) {
			instance = new SdpServiceTemplateHelper();
		}
		return instance;
	}

	public SdpServiceTemplate createServiceTemplate(String serviceTemplateName, String serviceTemplateDescription, String serviceTemplateProfile,
			SdpPlatform platform, String externalId, RefServiceType serviceType, String createdBy, EntityManager em) throws ValidationException,
			PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpServiceTemplate newTemplate = new SdpServiceTemplate();
		newTemplate.setServiceTemplateName(serviceTemplateName);
		newTemplate.setServiceTemplateDescription(serviceTemplateDescription);
		newTemplate.setServiceTemplateProfile(serviceTemplateProfile);
		newTemplate.setSdpPlatform(platform);
		newTemplate.setExternalId(externalId);
		newTemplate.setRefServiceType(serviceType);
		newTemplate.setStatusId(ConstantsHandler.getInstance().retrieveLongConstant(Constants.ACTIVE));
		newTemplate.setCreatedById(createdBy);
		newTemplate.setCreatedDate(new Date());
		em.persist(newTemplate);
		return newTemplate;
	}

	public void modifyServiceTemplate(SdpServiceTemplate toModify, String serviceTemplateName, String serviceTemplateDescription,
			String serviceTemplateProfile, String externalId, RefServiceType serviceType, SdpPlatform platform, String modifyBy) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		toModify.setServiceTemplateName(serviceTemplateName);
		toModify.setServiceTemplateDescription(serviceTemplateDescription);
		toModify.setServiceTemplateProfile(serviceTemplateProfile);
		toModify.setExternalId(externalId);
		toModify.setRefServiceType(serviceType);
		toModify.setSdpPlatform(platform);
		toModify.setUpdatedById(modifyBy);
		toModify.setUpdatedDate(new Date());
	}

	public void deleteServiceTemplate(SdpServiceTemplate toDelete, String deletedBy) throws ValidationException, PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		toDelete.setStatusId(ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED));
		toDelete.setDeletedById(deletedBy);
		toDelete.setDeletedDate(new Date());
	}

	public void validateServiceTemplate(String serviceTemplateName, Long platformId, Long serviceTypeId) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(SERVICE_TEMPLATE_NAME, serviceTemplateName);
		validationMap.put(PLATFORM_ID, platformId);
		validationMap.put(SERVICE_TYPE_ID, serviceTypeId);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug("End parameters validation");

	}

	public void changeStatus(SdpServiceTemplate serviceTemplate, Long nextStatus, String changedBy) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		serviceTemplate.setStatusId(nextStatus);
		serviceTemplate.setChangeStatusById(changedBy);
		serviceTemplate.setChangeStatusDate(new Date());
	}

	public void validateSearchServiceTemplateByName(String serviceTemplateName) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(SERVICE_TEMPLATE_NAME, serviceTemplateName);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug("End parameters validation");

	}

	public void validateSearchServiceTemplateById(Long serviceTemplateId) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(SERVICE_TEMPLATE_ID, serviceTemplateId);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);

	}

	public SdpServiceTemplate searchServiceTemplateById(Long serviceTemplateId, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (serviceTemplateId == null) {
			return null;
		}
		return em.find(SdpServiceTemplate.class, serviceTemplateId);
	}

	public List<SdpServiceTemplate> searchAllServiceTemplates(Long startPosition, Long maxRecordsNumber, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		return NamedQueryHelper.executePaginatedNamedQuery(SdpServiceTemplate.class, SdpServiceTemplate.QUERY_RETRIEVE_ALL, parameHashMap, startPosition,
				maxRecordsNumber, em);
	}

	public Long countAllServiceTemplate(EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		return NamedQueryHelper.executeNamedQueryCount(SdpServiceTemplate.QUERY_COUNT_ALL, parameHashMap, em);
	}

	public SdpServiceTemplate searchServiceTemplateByName(String serviceTemplateName, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(serviceTemplateName)) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put("serviceTemplateName", serviceTemplateName);
		List<SdpServiceTemplate> results = NamedQueryHelper.executeNamedQuery(SdpServiceTemplate.class, "selectServiceTemplateByName", parameHashMap, em);
		if (results == null || results.isEmpty()) {
			return null;
		}
		return results.get(0);
	}

	public List<SdpServiceTemplate> searchServiceTemplatesByPlatformId(Long platformId, Long startPosition, Long maxRecordsNumber, EntityManager em)
			throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (platformId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpServiceTemplate.PARAM_PLATFORM_ID, platformId);
		return NamedQueryHelper.executePaginatedNamedQuery(SdpServiceTemplate.class, "selectServiceTemplatesByPlatformId", parameHashMap, startPosition,
				maxRecordsNumber, em);
	}

	public Long countServiceTemplateByPlatformId(Long platformId, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (platformId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpServiceTemplate.PARAM_PLATFORM_ID, platformId);
		return NamedQueryHelper.executeNamedQueryCount("countServiceTemplatesByPlatformId", parameHashMap, em);
	}

	public Long countServiceTemplatesNotDeletedByPlatformId(Long platformId, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (platformId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpServiceTemplate.PARAM_PLATFORM_ID, platformId);
		parameHashMap.put("statusId", ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED));
		return NamedQueryHelper.executeNamedQueryCount("selectServiceTemplatesByPlatformIdAndStatusIdNotEqualCount", parameHashMap, em);
	}

	public SdpServiceTemplate searchByExternalId(String externalId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return super.searchByExternalId(SdpServiceTemplate.class, SdpServiceTemplate.QUERY_RETRIEVE_BY_EXTERNALID, externalId, em);
	}

}
