package com.accenture.sdp.csm.managers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import com.accenture.sdp.csm.commons.StatusIdConverter;
import com.accenture.sdp.csm.database.PersistenceManager;
import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.responses.SdpServiceVariantDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.helpers.SdpOfferHelper;
import com.accenture.sdp.csm.helpers.SdpServiceTemplateHelper;
import com.accenture.sdp.csm.helpers.SdpServiceVariantHelper;
import com.accenture.sdp.csm.model.jpa.SdpOffer;
import com.accenture.sdp.csm.model.jpa.SdpServiceTemplate;
import com.accenture.sdp.csm.model.jpa.SdpServiceVariant;
import com.accenture.sdp.csm.utilities.BeanConverter;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LogMap;

public final class SdpServiceVariantManager extends SdpBaseManager {

	private SdpServiceVariantManager() {
		super();
	}

	private static SdpServiceVariantManager instance;

	public static SdpServiceVariantManager getInstance() {
		if (instance == null) {
			instance = new SdpServiceVariantManager();
		}
		return instance;
	}

	/**
	 * <p>
	 * This method allows to insert into CSM database a new service variant. The new service variant is created by default with ACTIVE Status
	 * </p>
	 * 
	 * @param serviceVariantName
	 *            Name of the new service variant to create
	 * @param serviceVariantDescription
	 *            Description of the new service variant to create
	 * @param serviceVariantProfile
	 *            Profile of the new service variant to create
	 * @param serviceTemplateId
	 *            Id of service template to associate with the new service variant to create
	 * @param externalId
	 *            External id of new service variant to create
	 * @return Returns a CreateServiceResponse containing all the informations related to SdpServiceVariant and operation result
	 * @exception PropertyNotFoundException
	 */
	public CreateServiceResponse createServiceVariant(String serviceVariantName, String serviceVariantDescription, String serviceVariantProfile,
			Long serviceTemplateId, String externalId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(SERVICE_VARIANT_NAME, serviceVariantName);
		logMap.put("serviceVariantDescription", serviceVariantDescription);
		logMap.put("serviceVariantProfile", serviceVariantProfile);
		logMap.put(SERVICE_TEMPLATE_ID, serviceTemplateId);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		CreateServiceResponse resp;
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			SdpServiceVariant variant = createServiceVariant(serviceVariantName, serviceVariantDescription, serviceVariantProfile, serviceTemplateId,
					externalId, Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);

			resp = buildCreateResponse(Constants.CODE_OK);
			resp.setEntityId(variant.getServiceVariantId());
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

	SdpServiceVariant createServiceVariant(String serviceVariantName, String serviceVariantDescription, String serviceVariantProfile, Long serviceTemplateId,
			String externalId, String createdBy, EntityManager em) throws ValidationException, PropertyNotFoundException {

		SdpServiceVariantHelper handler = SdpServiceVariantHelper.getInstance();
		SdpServiceTemplateHelper templHelper = SdpServiceTemplateHelper.getInstance();

		// formal validation
		handler.validateServiceVariant(serviceVariantName, serviceTemplateId);

		// helper
		SdpServiceTemplate template = templHelper.searchServiceTemplateById(serviceTemplateId, em);
		if (template == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SERVICE_TEMPLATE_ID, serviceTemplateId);
		}

		SdpServiceVariant checkName = handler.searchServiceVariantByName(serviceVariantName, em);
		if (checkName != null) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, SERVICE_VARIANT_NAME, serviceVariantName);
		}
		SdpServiceVariant checkExternalId = handler.searchByExternalId(externalId, em);
		if (checkExternalId != null) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, EXTERNAL_ID, externalId);
		}

		return handler.createServiceVariant(serviceVariantName, serviceVariantDescription, serviceVariantProfile, template, externalId, createdBy, em);
	}

	/**
	 * <p>
	 * This method allows to update the information associated to a service variant (but not the Status) already present into CSM database with the following
	 * constraints:
	 * <li>The service variant status is ACTIVE</li>
	 * </p>
	 * 
	 * @param serviceVariantId
	 *            Id of the service variant to modify
	 * @param serviceVariantName
	 *            Name of the service variant to modify
	 * @param serviceVariantDescription
	 *            Description of the service variant to modify
	 * @param serviceVariantProfile
	 *            Profile of the service variant to modify
	 * @param externalId
	 *            External id of the service variant to modify
	 * @return Returns a DataServiceResponse containing operation result code and description
	 * @exception PropertyNotFoundException
	 */
	public DataServiceResponse modifyServiceVariant(Long serviceVariantId, String serviceVariantName, String serviceVariantDescription, String externalId,
			Long serviceTemplateId, String serviceVariantProfile, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(SERVICE_VARIANT_ID, serviceVariantId);
		logMap.put(SERVICE_VARIANT_NAME, serviceVariantName);
		logMap.put("serviceVariantDescription", serviceVariantDescription);
		logMap.put("serviceVariantProfile", serviceVariantProfile);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put(SERVICE_TEMPLATE_ID, serviceTemplateId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		DataServiceResponse resp;
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			modifyServiceVariant(serviceVariantId, serviceVariantName, serviceVariantDescription, externalId, serviceTemplateId, serviceVariantProfile,
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

	SdpServiceVariant modifyServiceVariant(Long serviceVariantId, String serviceVariantName, String serviceVariantDescription, String externalId,
			Long serviceTemplateId, String serviceVariantProfile, String updatedBy, EntityManager em) throws PropertyNotFoundException, ValidationException {

		SdpServiceVariantHelper handler = SdpServiceVariantHelper.getInstance();
		SdpServiceTemplateHelper templHelper = SdpServiceTemplateHelper.getInstance();

		// formal validation
		handler.validateSearchServiceVariantById(serviceVariantId);
		handler.validateServiceVariant(serviceVariantName, serviceTemplateId);

		// workflow validation
		SdpServiceVariant toModify = handler.searchServiceVariantById(serviceVariantId, em);
		if (toModify == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SERVICE_VARIANT_ID, serviceVariantId);
		}
		SdpServiceTemplate template = templHelper.searchServiceTemplateById(serviceTemplateId, em);
		if (template == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SERVICE_TEMPLATE_ID, serviceTemplateId);
		}
		SdpServiceVariant checkName = handler.searchServiceVariantByName(serviceVariantName, em);
		if (checkName != null && !checkName.getServiceVariantId().equals(serviceVariantId)) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, SERVICE_VARIANT_NAME, serviceVariantName);
		}

		if (externalId != null) {
			SdpServiceVariant checkExternalId = handler.searchByExternalId(externalId, em);
			if (checkExternalId != null && !checkExternalId.getServiceVariantId().equals(serviceVariantId)) {
				throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, EXTERNAL_ID, externalId);
			}
		}

		if (!isStatusActive(toModify.getStatusId())) {
			throw new ValidationException(Constants.CODE_UPDATE_NOT_ALLOWED_FOR_STATUS, SERVICE_VARIANT_ID, serviceVariantId);
		}
		handler.modifyServiceVariant(toModify, serviceVariantName, serviceVariantDescription, serviceVariantProfile, externalId, template, updatedBy);
		return toModify;
	}

	/**
	 * <p>
	 * This method allows to delete a service variant, only in logically mode, setting the status of the service variant to DELETED.
	 * 
	 * Pre-Requisites:
	 * 
	 * It's necessary to do the following check:
	 * <li>The service variant to be deleted is in status Inactive</li>
	 * <li>There aren't offers associated to the service variant with status different from Deleted</li>
	 * </p>
	 * 
	 * @param serviceVariantId
	 *            Id of the service variant to delete
	 * @return Returns a DataServiceResponse containing operation result code and description
	 * @exception PropertyNotFoundException
	 */
	public DataServiceResponse deleteServiceVariant(Long serviceVariantId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(SERVICE_VARIANT_ID, serviceVariantId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		DataServiceResponse resp;
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			deleteServiceVariant(serviceVariantId, Utilities.getCurrentClassAndMethod(), em);

			// commit
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

	void deleteServiceVariant(Long serviceVariantId, String deletedBy, EntityManager em) throws PropertyNotFoundException, ValidationException {
		SdpServiceVariantHelper handler = SdpServiceVariantHelper.getInstance();

		// formal validation
		handler.validateSearchServiceVariantById(serviceVariantId);

		// workflow validation
		SdpServiceVariant toDelete = handler.searchServiceVariantById(serviceVariantId, em);
		if (toDelete == null) {
			log.logDebug("Service Variant not Found!");
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SERVICE_VARIANT_ID, serviceVariantId);
		}
		handler.checkAllowedChangeStatus(Constants.ENTITY_TYPE_OTHER, toDelete.getStatusId(), ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED), em);

		List<SdpOffer> offers = toDelete.getSdpOffers();
		for (SdpOffer offer : offers) {
			if (!isStatusDeleted(offer.getStatusId())) {
				log.logDebug("Service Variant has not-deleted offer associated!");
				throw new ValidationException(Constants.CODE_ELEMENT_ALREADY_ASSOCIATED, SERVICE_VARIANT_ID, serviceVariantId);
			}
		}
		handler.deleteServiceVariant(toDelete, deletedBy);
	}

	/**
	 * <p>
	 * This method allows to retrieve the information related to a service variant. NOTE: The input parameter is mandatory
	 * 
	 * @param serviceVariantId
	 *            Id of the service variant to search
	 * @return Returns a SearchServiceResponse containing all the informations related to SdpServiceVariant and operation result
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchServiceVariant(Long serviceVariantId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(SERVICE_VARIANT_ID, serviceVariantId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		SearchServiceResponse resp;
		EntityManager em = null;
		try {
			SdpServiceVariantHelper handler = SdpServiceVariantHelper.getInstance();

			// formal validation
			handler.validateSearchServiceVariantById(serviceVariantId);

			em = PersistenceManager.getEntityManager(tenantName);

			// helper
			SdpServiceVariant sv = handler.searchServiceVariantById(serviceVariantId, em);
			if (sv == null) {
				log.logDebug("ServiceVariant not Found!");
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SERVICE_VARIANT_ID, serviceVariantId);
			}
			List<SdpServiceVariant> variants = new ArrayList<SdpServiceVariant>();
			variants.add(sv);
			List<SdpServiceVariantDto> variantResponse = BeanConverter.convertServiceVariant(variants);

			resp = buildSearchResponse(Constants.CODE_OK);
			resp.setSearchResult(variantResponse);
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

	/**
	 * <p>
	 * This method allows to retrieve all service variants information. NOTE:
	 * 
	 * @return Returns a SearchServiceResponse containing all the informations related to SdpServiceTemplate and operation result
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchAllServiceVariants(Long startPosition, Long maxRecordsNumber, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		SearchServiceResponse resp;
		EntityManager em = null;
		try {
			SdpServiceVariantHelper handler = SdpServiceVariantHelper.getInstance();
			handler.validateSearchAll(startPosition, maxRecordsNumber);

			em = PersistenceManager.getEntityManager(tenantName);

			Long totalResult = handler.countServiceVariant(em);
			if (totalResult == null || totalResult == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}

			List<SdpServiceVariant> variants = handler.searchAllServiceVariants(startPosition, maxRecordsNumber, em);

			if (variants == null || variants.isEmpty()) {
				resp = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				resp = buildSearchResponse(Constants.CODE_OK);
			}
			resp.setSearchResult(BeanConverter.convertServiceVariant(variants));
			resp.setTotalResult(totalResult);
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

	/**
	 * <p>
	 * This method allows to retrieve the information related to a service variant given an offer name. NOTE: The input parameter is mandatory
	 * 
	 * @param offerName
	 *            Name of the offer related to the service variant to search
	 * @return Returns a SearchServiceResponse containing all the informations related to SdpServiceVariant and operation result
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchServiceVariantByOffer(String offerName, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put("offerName", offerName);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		SearchServiceResponse resp;
		EntityManager em = null;
		try {
			SdpOfferHelper handler = SdpOfferHelper.getInstance();

			// formal validation
			handler.validateSearchOfferByName(offerName);

			em = PersistenceManager.getEntityManager(tenantName);

			List<SdpServiceVariant> variants = new ArrayList<SdpServiceVariant>();
			SdpOffer offer = handler.searchOfferByName(offerName, em);
			if (offer == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, "offerName", offerName);
			}
			variants.add(offer.getSdpServiceVariant());
			List<SdpServiceVariantDto> variantResponse = BeanConverter.convertServiceVariant(variants);
			resp = buildSearchResponse(Constants.CODE_OK);
			resp.setSearchResult(variantResponse);
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

	/**
	 * <p>
	 * This method allows to retrieve the information related to a service variant given a service template name. NOTE: The input parameter is mandatory
	 * 
	 * @param serviceTemplateName
	 *            Name of the service template related to the service variant to search
	 * @param startPosition
	 *            Index of the first result
	 * @param maxRecordsNumber
	 *            Total number of rows to return
	 * @return Returns a SearchServiceResponse containing all the informations related to SdpServiceVariant and operation result
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchServiceVariantsByServiceTemplate(String serviceTemplateName, Long startPosition, Long maxRecordsNumber, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put("serviceTemplateName", serviceTemplateName);
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		SearchServiceResponse resp;
		EntityManager em = null;
		try {
			SdpServiceTemplateHelper handler = SdpServiceTemplateHelper.getInstance();
			SdpServiceVariantHelper variantHandler = SdpServiceVariantHelper.getInstance();

			// formal validation
			handler.validateSearchServiceTemplateByName(serviceTemplateName);
			handler.validateSearchAll(startPosition, maxRecordsNumber);

			em = PersistenceManager.getEntityManager(tenantName);

			SdpServiceTemplate template = handler.searchServiceTemplateByName(serviceTemplateName, em);
			if (template == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, "serviceTemplateName", serviceTemplateName);
			}
			Long totalResult = variantHandler.countServiceVariantByServiceTemplateId(template.getServiceTemplateId(), em);
			if (totalResult == null || totalResult == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, "serviceTemplateName", serviceTemplateName);
			}

			List<SdpServiceVariant> variants = variantHandler.searchServiceVariantsByServiceTemplateId(template.getServiceTemplateId(), startPosition,
					maxRecordsNumber, em);

			if (variants == null || variants.isEmpty()) {
				resp = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				resp = buildSearchResponse(Constants.CODE_OK);
			}
			resp.setSearchResult(BeanConverter.convertServiceVariant(variants));
			resp.setTotalResult(totalResult);
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

	/**
	 * <p>
	 * This method allows to change the status of a service variant.
	 * 
	 * @param serviceVariantId
	 *            Id of service variant to update
	 * @param status
	 *            value of the status to set on the service variant
	 * @return Returns a DataServiceResponse containing operation result code and description
	 * @exception PropertyNotFoundException
	 */
	public DataServiceResponse serviceVariantChangeStatus(Long serviceVariantId, String status, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(SERVICE_VARIANT_ID, serviceVariantId);
		logMap.put(STATUS, status);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityManager em = null;
		DataServiceResponse resp = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			serviceVariantChangeStatus(serviceVariantId, status, Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);

			resp = buildUpdateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildCreateResponse(validationException.getDescription(), validationException.getParameters());
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_UPDATE_FAILED);
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

	SdpServiceVariant serviceVariantChangeStatus(Long serviceVariantId, String status, String changedStatusBy, EntityManager em)
			throws PropertyNotFoundException, ValidationException {
		SdpServiceVariantHelper helper = SdpServiceVariantHelper.getInstance();
		// formal validation
		helper.validateSearchServiceVariantById(serviceVariantId);
		helper.validateChangeStatus(status);

		SdpServiceVariant variant = helper.searchServiceVariantById(serviceVariantId, em);
		if (variant == null) {
			log.logDebug("Variant not Found!");
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SERVICE_VARIANT_ID, serviceVariantId);
		}
		helper.checkAllowedChangeStatus(Constants.ENTITY_TYPE_OTHER, variant.getStatusId(), StatusIdConverter.getStatusValue(status), em);

		helper.changeStatus(variant, StatusIdConverter.getStatusValue(status), changedStatusBy);
		return variant;
	}

}
