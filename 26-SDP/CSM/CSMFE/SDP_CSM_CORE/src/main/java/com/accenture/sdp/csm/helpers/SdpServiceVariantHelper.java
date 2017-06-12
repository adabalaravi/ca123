package com.accenture.sdp.csm.helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.database.NamedQueryHelper;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.model.jpa.SdpServiceTemplate;
import com.accenture.sdp.csm.model.jpa.SdpServiceVariant;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.validation.ValidationResult;
import com.accenture.sdp.csm.validation.ValidationUtils;

/**
 * @author alberto.marimpietri
 * 
 */
public final class SdpServiceVariantHelper extends SdpBaseHelper {

	private static SdpServiceVariantHelper instance;

	private SdpServiceVariantHelper() {
		super();
	}

	public static SdpServiceVariantHelper getInstance() {
		if (instance == null) {
			instance = new SdpServiceVariantHelper();
		}
		return instance;
	}

	public SdpServiceVariant createServiceVariant(String serviceVariantName, String serviceVariantDescription, String serviceVariantProfile,
			SdpServiceTemplate template, String externalId, String createdBy, EntityManager em) throws ValidationException, PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpServiceVariant newVariant = new SdpServiceVariant();
		newVariant.setSdpServiceTemplate(template);
		newVariant.setServiceVariantName(serviceVariantName);
		newVariant.setServiceVariantDescription(serviceVariantDescription);
		newVariant.setServiceVariantProfile(serviceVariantProfile);
		newVariant.setStatusId(ConstantsHandler.getInstance().retrieveLongConstant(Constants.ACTIVE));
		newVariant.setExternalId(externalId);
		newVariant.setCreatedById(createdBy);
		newVariant.setCreatedDate(new Date());
		em.persist(newVariant);
		return newVariant;
	}

	public void modifyServiceVariant(SdpServiceVariant toModify, String serviceVariantName, String serviceVariantDescription, String serviceVariantProfile,
			String externalId, SdpServiceTemplate sdpServiceTemplate, String modifyBy) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		// Model valorization
		toModify.setServiceVariantName(serviceVariantName);
		toModify.setServiceVariantDescription(serviceVariantDescription);
		toModify.setServiceVariantProfile(serviceVariantProfile);
		toModify.setExternalId(externalId);
		toModify.setSdpServiceTemplate(sdpServiceTemplate);
		toModify.setUpdatedById(modifyBy);
		toModify.setUpdatedDate(new Date());
	}

	public void deleteServiceVariant(SdpServiceVariant toDelete, String deletedBy) throws ValidationException, PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		// Model valorization
		toDelete.setStatusId(ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED));
		toDelete.setDeletedById(deletedBy);
		toDelete.setDeletedDate(new Date());
	}

	public SdpServiceVariant searchServiceVariantByName(String serviceVariantName, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (serviceVariantName == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put("serviceVariantName", serviceVariantName);
		List<SdpServiceVariant> results = NamedQueryHelper.executeNamedQuery(SdpServiceVariant.class, "selectServiceVariantByName", parameHashMap, em);
		if (results == null || results.isEmpty()) {
			return null;
		}
		return results.get(0);
	}

	public void validateServiceVariant(String serviceVariantName, Long serviceTemplateId) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(SERVICE_VARIANT_NAME, serviceVariantName);
		validationMap.put(SERVICE_TEMPLATE_ID, serviceTemplateId);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public List<SdpServiceVariant> searchAllServiceVariants(Long startPosition, Long maxRecordsNumber, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		return NamedQueryHelper.executePaginatedNamedQuery(SdpServiceVariant.class, SdpServiceVariant.QUERY_RETRIEVE_ALL, parameHashMap, startPosition,
				maxRecordsNumber, em);
	}

	public Long countServiceVariant(EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		return NamedQueryHelper.executeNamedQueryCount(SdpServiceVariant.QUERY_COUNT_ALL, parameHashMap, em);
	}

	public void changeStatus(SdpServiceVariant serviceVariant, Long nextStatus, String changedBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		serviceVariant.setStatusId(nextStatus);
		serviceVariant.setChangeStatusById(changedBy);
		serviceVariant.setChangeStatusDate(new Date());
	}

	public void validateSearchServiceVariantByName(String serviceVariantName) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(SERVICE_VARIANT_NAME, serviceVariantName);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateSearchServiceVariantById(Long serviceVariantId) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(SERVICE_VARIANT_ID, serviceVariantId);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);

	}

	public SdpServiceVariant searchServiceVariantById(Long serviceVariantId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (serviceVariantId == null) {
			return null;
		}
		return em.find(SdpServiceVariant.class, serviceVariantId);
	}

	public List<SdpServiceVariant> searchServiceVariantsByServiceTemplateId(Long serviceTemplateId, Long startPosition, Long maxRecordsNumber, EntityManager em)
			throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (serviceTemplateId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpServiceVariant.PARAM_SERVICE_TEMPLATE_ID, serviceTemplateId);
		return NamedQueryHelper.executePaginatedNamedQuery(SdpServiceVariant.class, "selectServiceVariantsByServiceTemplateId", parameHashMap, startPosition,
				maxRecordsNumber, em);
	}

	public Long countServiceVariantByServiceTemplateId(Long serviceTemplateId, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (serviceTemplateId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpServiceVariant.PARAM_SERVICE_TEMPLATE_ID, serviceTemplateId);
		return NamedQueryHelper.executeNamedQueryCount("countServiceVariantsByServiceTemplateId", parameHashMap, em);
	}

	public Long countServiceVariantsNotDeletedByServiceTemplateId(Long serviceTemplateId, EntityManager em) throws ValidationException,
			PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (serviceTemplateId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpServiceVariant.PARAM_SERVICE_TEMPLATE_ID, serviceTemplateId);
		parameHashMap.put("statusId", ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED));
		return NamedQueryHelper.executeNamedQueryCount("countServiceVariantsByServiceTemplateIdAndStatusIdNotEqual", parameHashMap, em);
	}

	public SdpServiceVariant searchByExternalId(String externalId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return super.searchByExternalId(SdpServiceVariant.class, SdpServiceVariant.QUERY_RETRIEVE_BY_EXTERNALID, externalId, em);
	}
}
