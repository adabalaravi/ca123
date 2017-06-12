package com.accenture.sdp.csm.managers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import com.accenture.sdp.csm.database.PersistenceManager;
import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.responses.ParameterDto;
import com.accenture.sdp.csm.dto.responses.SdpServiceVariantOperationDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.helpers.SdpServiceVariantHelper;
import com.accenture.sdp.csm.helpers.SdpServiceVariantOperationsHelper;
import com.accenture.sdp.csm.model.jpa.SdpServiceVariant;
import com.accenture.sdp.csm.model.jpa.SdpServiceVariantOperation;
import com.accenture.sdp.csm.utilities.BeanConverter;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LogMap;

public final class SdpServiceVariantOperationsManager extends SdpBaseManager {

	private static final String METHOD_NAME = "methodName";

	private SdpServiceVariantOperationsManager() {
		super();
	}

	private static SdpServiceVariantOperationsManager instance;

	public static SdpServiceVariantOperationsManager getInstance() {
		if (instance == null) {
			instance = new SdpServiceVariantOperationsManager();
		}
		return instance;
	}

	public CreateServiceResponse createSdpServiceVariantOperation(Long serviceVariantId, String methodName, String inputParameters, String inputXslt,
			String outputXslt, String uddiKey, String operationType, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		CreateServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(SERVICE_VARIANT_ID, serviceVariantId);
		logMap.put(METHOD_NAME, methodName);
		logMap.put("inputParameters", inputParameters);
		logMap.put("inputXslt", inputXslt);
		logMap.put("outputXslt", outputXslt);
		logMap.put("uddiKey", uddiKey);
		logMap.put("operationType", operationType);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();
			createSdpServiceVariantOperation(serviceVariantId, methodName, inputParameters, inputXslt, outputXslt, uddiKey, operationType,
					Utilities.getCurrentClassAndMethod(), em);
			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildCreateResponse(Constants.CODE_OK);

			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildCreateResponse(validationException.getDescription(), validationException.getParameters());
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_INSERT_FAILED);
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_GENERIC_ERROR);
		} finally {
			if (tx != null && tx.isActive()) {
				log.logDebug(TRANSACTION_ROLLBACK);
				tx.rollback();
				log.logDebug(TRANSACTION_ROLLBACKED);
			}
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		return resp;
	}

	SdpServiceVariantOperation createSdpServiceVariantOperation(Long serviceVariantId, String methodName, String inputParameters, String inputXslt,
			String outputXslt, String uddiKey, String operationType, String createdById, EntityManager em) throws PropertyNotFoundException,
			ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpServiceVariantHelper serviceVariantHelper = SdpServiceVariantHelper.getInstance();
		SdpServiceVariantOperationsHelper sdpServiceVariantOperationsHelper = SdpServiceVariantOperationsHelper.getInstance();
		// Validazione Formale
		sdpServiceVariantOperationsHelper.validateSearchSdpServiceVariantOperationsById(serviceVariantId, methodName);

		SdpServiceVariant serviceVariant = serviceVariantHelper.searchServiceVariantById(serviceVariantId, em);
		if (serviceVariant == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SERVICE_VARIANT_ID, serviceVariantId);
		}
		// verifica duplicati
		if (sdpServiceVariantOperationsHelper.searchSdpServiceVariantOperationById(serviceVariantId, methodName, em) != null) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, new ParameterDto(SERVICE_VARIANT_ID, serviceVariantId), new ParameterDto(METHOD_NAME,
					methodName));
		}
		// creazione SdpServiceVariantOperation
		return sdpServiceVariantOperationsHelper.createSdpServiceVariantOperation(serviceVariant, methodName, inputParameters, inputXslt, outputXslt, uddiKey,
				operationType, createdById, em);
	}

	public DataServiceResponse modifySdpServiceVariantOperation(Long serviceVariantId, String methodName, String inputParameters, String inputXslt,
			String outputXslt, String uddiKey, String operationType, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(SERVICE_VARIANT_ID, serviceVariantId);
		logMap.put(METHOD_NAME, methodName);
		logMap.put("inputParameters", inputParameters);
		logMap.put("inputXslt", inputXslt);
		logMap.put("outputXslt", outputXslt);
		logMap.put("uddiKey", uddiKey);
		logMap.put("operationType", operationType);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();
			modifySdpServiceVariantOperation(serviceVariantId, methodName, inputParameters, inputXslt, outputXslt, uddiKey, operationType,
					Utilities.getCurrentClassAndMethod(), em);
			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildUpdateResponse(Constants.CODE_OK);

			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildUpdateResponse(validationException.getDescription(), validationException.getParameters());
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildUpdateResponse(Constants.CODE_UPDATE_FAILED);
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildUpdateResponse(Constants.CODE_GENERIC_ERROR);
		} finally {
			if (tx != null && tx.isActive()) {
				log.logDebug(TRANSACTION_ROLLBACK);
				tx.rollback();
				log.logDebug(TRANSACTION_ROLLBACKED);
			}
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		return resp;
	}

	void modifySdpServiceVariantOperation(Long serviceVariantId, String methodName, String inputParameters, String inputXslt, String outputXslt,
			String uddiKey, String operationType, String updatedById, EntityManager em) throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpServiceVariantHelper serviceVariantHelper = SdpServiceVariantHelper.getInstance();
		SdpServiceVariantOperationsHelper sdpServiceVariantOperationsHelper = SdpServiceVariantOperationsHelper.getInstance();
		// Validazione Formale

		sdpServiceVariantOperationsHelper.validateSearchSdpServiceVariantOperationsById(serviceVariantId, methodName);

		SdpServiceVariantOperation sdpServiceVariantOperation = sdpServiceVariantOperationsHelper.searchSdpServiceVariantOperationById(serviceVariantId,
				methodName, em);

		if (sdpServiceVariantOperation == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, new ParameterDto(SERVICE_VARIANT_ID, serviceVariantId), new ParameterDto(
					METHOD_NAME, methodName));
		}
		SdpServiceVariant serviceVariant = serviceVariantHelper.searchServiceVariantById(serviceVariantId, em);
		if (serviceVariant == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SERVICE_VARIANT_ID, serviceVariantId);
		}

		// SdpServiceVariantOperation update
		sdpServiceVariantOperationsHelper.modifySdpServiceVariantOperation(sdpServiceVariantOperation, inputParameters, inputXslt, outputXslt, uddiKey,
				operationType, updatedById, em);
	}

	public DataServiceResponse deleteSdpServiceVariantOperation(Long serviceVariantId, String methodName, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(SERVICE_VARIANT_ID, serviceVariantId);
		logMap.put(METHOD_NAME, methodName);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();
			deleteSdpServiceVariantOperation(serviceVariantId, methodName, em);
			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildUpdateResponse(Constants.CODE_OK);

			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildUpdateResponse(validationException.getDescription(), validationException.getParameters());
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildUpdateResponse(Constants.CODE_DELETE_FAILED, new ParameterDto(SERVICE_VARIANT_ID, serviceVariantId), new ParameterDto(METHOD_NAME,
					methodName));
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildUpdateResponse(Constants.CODE_GENERIC_ERROR);
		} finally {
			if (tx != null && tx.isActive()) {
				log.logDebug(TRANSACTION_ROLLBACK);
				tx.rollback();
				log.logDebug(TRANSACTION_ROLLBACKED);
			}
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		return resp;
	}

	void deleteSdpServiceVariantOperation(Long serviceVariantId, String methodName, EntityManager em) throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpServiceVariantOperationsHelper sdpServiceVariantOperationsHelper = SdpServiceVariantOperationsHelper.getInstance();
		// Validazione Formale
		sdpServiceVariantOperationsHelper.validateSearchSdpServiceVariantOperationsById(serviceVariantId, methodName);

		SdpServiceVariantOperation sdpServiceVariantOperation = sdpServiceVariantOperationsHelper.searchSdpServiceVariantOperationById(serviceVariantId,
				methodName, em);
		if (sdpServiceVariantOperation == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, new ParameterDto(SERVICE_VARIANT_ID, serviceVariantId), new ParameterDto(
					METHOD_NAME, methodName));
		}

		// SdpServiceVariantOperation delete
		sdpServiceVariantOperationsHelper.deleteSdpServiceVariantOperation(sdpServiceVariantOperation, em);
	}

	public SearchServiceResponse searchSdpServiceVariantOperationById(Long serviceVariantId, String methodName, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(SERVICE_VARIANT_ID, serviceVariantId);
		logMap.put(METHOD_NAME, methodName);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			SdpServiceVariantOperationsHelper sdpServiceVariantOperationsHelper = SdpServiceVariantOperationsHelper.getInstance();
			sdpServiceVariantOperationsHelper.validateSearchSdpServiceVariantOperationsById(serviceVariantId, Utilities.getCurrentClassAndMethod());
			SdpServiceVariantOperation serviceVariantOperation = sdpServiceVariantOperationsHelper.searchSdpServiceVariantOperationById(serviceVariantId,
					methodName, em);
			if (serviceVariantOperation == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, new ParameterDto(SERVICE_VARIANT_ID, serviceVariantId), new ParameterDto(
						METHOD_NAME, methodName));
			}
			resp = buildSearchResponse(Constants.CODE_OK);
			List<SdpServiceVariantOperationDto> variantOperations = new ArrayList<SdpServiceVariantOperationDto>();
			variantOperations.add(BeanConverter.convertSdpServiceVariantOperation(serviceVariantOperation));
			resp.setSearchResult(variantOperations);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildSearchResponse(validationException.getDescription(), validationException.getParameters());
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildSearchResponse(Constants.CODE_GENERIC_ERROR);
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		return resp;

	}

	public SearchServiceResponse searchSdpServiceVariantOperationByName(String serviceVariantName, String methodName, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(SERVICE_VARIANT_NAME, serviceVariantName);
		logMap.put(METHOD_NAME, methodName);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			em = PersistenceManager.getEntityManager(tenantName);

			SdpServiceVariantOperationsHelper sdpServiceVariantOperationsHelper = SdpServiceVariantOperationsHelper.getInstance();
			sdpServiceVariantOperationsHelper.validateSearchSdpServiceVariantOperationsByName(serviceVariantName, Utilities.getCurrentClassAndMethod());
			SdpServiceVariantOperation serviceVariantOperation = sdpServiceVariantOperationsHelper.searchSdpServiceVariantOperationByName(serviceVariantName,
					methodName, em);
			if (serviceVariantOperation == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, new ParameterDto(SERVICE_VARIANT_NAME, serviceVariantName), new ParameterDto(
						METHOD_NAME, methodName));
			}
			resp = buildSearchResponse(Constants.CODE_OK);
			List<SdpServiceVariantOperationDto> variantOperations = new ArrayList<SdpServiceVariantOperationDto>();
			variantOperations.add(BeanConverter.convertSdpServiceVariantOperation(serviceVariantOperation));
			resp.setSearchResult(variantOperations);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildSearchResponse(validationException.getDescription(), validationException.getParameters());
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildSearchResponse(Constants.CODE_GENERIC_ERROR);
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		return resp;

	}

	public SearchServiceResponse searchSdpServiceVariantOperationByServiceVariantId(Long serviceVariantId, String tenantName) throws PropertyNotFoundException {

		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(SERVICE_VARIANT_ID, serviceVariantId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			SdpServiceVariantHelper serviceVariantHelper = SdpServiceVariantHelper.getInstance();
			serviceVariantHelper.validateSearchServiceVariantById(serviceVariantId);

			em = PersistenceManager.getEntityManager(tenantName);

			SdpServiceVariant serviceVariant = serviceVariantHelper.searchServiceVariantById(serviceVariantId, em);

			if (serviceVariant == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SERVICE_VARIANT_ID, serviceVariantId);
			}

			SdpServiceVariantOperationsHelper sdpServiceVariantOperationsHelper = SdpServiceVariantOperationsHelper.getInstance();
			List<SdpServiceVariantOperation> serviceVariantOperationList = sdpServiceVariantOperationsHelper
					.searchSdpServiceVariantOperationByServiceVariantId(serviceVariantId, em);

			if (serviceVariantOperationList == null || serviceVariantOperationList.isEmpty()) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}
			resp = buildSearchResponse(Constants.CODE_OK);
			List<SdpServiceVariantOperationDto> variantOperations = BeanConverter.convertListOfSdpServiceVariantOperationDto(serviceVariantOperationList);
			resp.setSearchResult(variantOperations);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildSearchResponse(validationException.getDescription(), validationException.getParameters());
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildSearchResponse(Constants.CODE_GENERIC_ERROR);
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		return resp;

	}
}
