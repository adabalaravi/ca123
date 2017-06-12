package com.accenture.sdp.csm.helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.database.NamedQueryHelper;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.model.jpa.SdpServiceVariant;
import com.accenture.sdp.csm.model.jpa.SdpServiceVariantOperation;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.validation.ValidationResult;
import com.accenture.sdp.csm.validation.ValidationUtils;

public final class SdpServiceVariantOperationsHelper extends SdpBaseHelper {

	private static SdpServiceVariantOperationsHelper instance;

	private SdpServiceVariantOperationsHelper() {
		super();
	}

	public static SdpServiceVariantOperationsHelper getInstance() {
		if (instance == null) {
			instance = new SdpServiceVariantOperationsHelper();
		}
		return instance;
	}

	public SdpServiceVariantOperation createSdpServiceVariantOperation(SdpServiceVariant serviceVariant, String methodName, String inputParameters,
			String inputXslt, String outputXslt, String uddiKey, String operationType, String createdById, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpServiceVariantOperation sdpServiceVariantOperation = new SdpServiceVariantOperation();
		sdpServiceVariantOperation.setServiceVariantId(serviceVariant.getServiceVariantId());
		sdpServiceVariantOperation.setSdpServiceVariant(serviceVariant);
		sdpServiceVariantOperation.setMethodName(methodName);
		sdpServiceVariantOperation.setInputParameters(inputParameters);
		sdpServiceVariantOperation.setInputXslt(inputXslt);
		sdpServiceVariantOperation.setOutputXslt(outputXslt);
		sdpServiceVariantOperation.setUddiKey(uddiKey);
		sdpServiceVariantOperation.setOperationType(operationType);
		sdpServiceVariantOperation.setCreatedById(createdById);
		sdpServiceVariantOperation.setCreatedDate(new Date());
		em.persist(sdpServiceVariantOperation);

		return sdpServiceVariantOperation;
	}

	public void modifySdpServiceVariantOperation(SdpServiceVariantOperation sdpServiceVariantOperation, String inputParameters, String inputXslt,
			String outputXslt, String uddiKey, String operationType, String updatedById, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		sdpServiceVariantOperation.setInputParameters(inputParameters);
		sdpServiceVariantOperation.setInputXslt(inputXslt);
		sdpServiceVariantOperation.setOutputXslt(outputXslt);
		sdpServiceVariantOperation.setUddiKey(uddiKey);
		sdpServiceVariantOperation.setOperationType(operationType);
		sdpServiceVariantOperation.setUpdatedById(updatedById);
		sdpServiceVariantOperation.setUpdatedDate(new Date());
	}

	public void deleteSdpServiceVariantOperation(SdpServiceVariantOperation sdpServiceVariantOperation, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		em.remove(sdpServiceVariantOperation);
	}

	public SdpServiceVariantOperation searchSdpServiceVariantOperationById(Long serviceVariantId, String methodName, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (serviceVariantId == null || methodName == null) {
			return null;
		}
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("serviceVariantId", serviceVariantId);
		params.put(SdpServiceVariantOperation.PARAM_METHOD_NAME, methodName);
		List<SdpServiceVariantOperation> results = NamedQueryHelper.executeNamedQuery(SdpServiceVariantOperation.class, "selectServiceVariantOperationById",
				params, em);
		if (results == null || results.isEmpty()) {
			return null;
		}
		return results.get(0);
	}

	public SdpServiceVariantOperation searchSdpServiceVariantOperationByName(String serviceVariantName, String methodName, EntityManager em)
			throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (serviceVariantName == null || methodName == null) {
			return null;
		}
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpServiceVariantOperation.PARAM_METHOD_NAME, methodName);
		parameHashMap.put("serviceVariantName", serviceVariantName);
		List<SdpServiceVariantOperation> results = NamedQueryHelper.executeNamedQuery(SdpServiceVariantOperation.class, "selectServiceVariantOperationByName",
				parameHashMap, em);
		if (results == null || results.isEmpty()) {
			return null;
		}
		return results.get(0);
	}

	public void validateSearchSdpServiceVariantOperationsById(Long serviceVariantId, String methodName) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(SERVICE_VARIANT_ID, serviceVariantId);
		validationMap.put("methodName", methodName);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateSearchSdpServiceVariantOperationsByName(String serviceVariantName, String methodName) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("serviceVariantName", serviceVariantName);
		validationMap.put("methodName", methodName);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public List<SdpServiceVariantOperation> searchSdpServiceVariantOperationByServiceVariantId(Long serviceVariantId, EntityManager em)
			throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (serviceVariantId == null) {
			return null;
		}
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put("serviceVariantId", serviceVariantId);
		return NamedQueryHelper.executeNamedQuery(SdpServiceVariantOperation.class, "selectServiceVariantOperationByServiceVariantId", parameHashMap, em);
	}

	public List<SdpServiceVariantOperation> searchSdpServiceVariantOperationByOperationType(Long serviceVariantId, String operationType, EntityManager em)
			throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (serviceVariantId == null || operationType == null) {
			return null;
		}
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put("serviceVariantId", serviceVariantId);
		parameHashMap.put("operationType", operationType);
		return NamedQueryHelper.executeNamedQuery(SdpServiceVariantOperation.class, "selectServiceVariantOperationByOperationType", parameHashMap, em);
	}
}
