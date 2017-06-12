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
import com.accenture.sdp.csm.dto.responses.SdpServiceTemplateDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.helpers.RefServiceTypeHelper;
import com.accenture.sdp.csm.helpers.SdpPlatformHelper;
import com.accenture.sdp.csm.helpers.SdpServiceTemplateHelper;
import com.accenture.sdp.csm.helpers.SdpServiceVariantHelper;
import com.accenture.sdp.csm.model.jpa.RefServiceType;
import com.accenture.sdp.csm.model.jpa.SdpPlatform;
import com.accenture.sdp.csm.model.jpa.SdpServiceTemplate;
import com.accenture.sdp.csm.model.jpa.SdpServiceVariant;
import com.accenture.sdp.csm.utilities.BeanConverter;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LogMap;

public final class SdpServiceTemplateManager extends SdpBaseManager {

	private SdpServiceTemplateManager() {
		super();
	}

	private static SdpServiceTemplateManager instance;

	public static SdpServiceTemplateManager getInstance() {
		if (instance == null) {
			instance = new SdpServiceTemplateManager();
		}
		return instance;
	}

	/**
	 * <p>
	 * This method allows to insert into CSM database a new service template. The new service template is created by default with ACTIVE Status
	 * </p>
	 * 
	 * @param serviceTemplateName
	 *            Name of the new service template to create
	 * @param serviceTemplateDescription
	 *            Description of the new service template to create
	 * @param serviceTemplateProfile
	 *            Profile of the new service template to create
	 * @param platformId
	 *            Id of platform to associate with the new service template to create
	 * @param externalId
	 *            External id of new service template to create
	 * @param serviceTypeId
	 *            Id of type of new service template to create
	 * @return Returns a CreateServiceResponse containing all the informations related to SdpServiceTemplate and operation result
	 * @exception PropertyNotFoundException
	 */
	public CreateServiceResponse createServiceTemplate(String serviceTemplateName, String serviceTemplateDescription, String serviceTemplateProfile,
			Long platformId, String externalId, Long serviceTypeId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(SERVICE_TEMPLATE_NAME, serviceTemplateName);
		logMap.put("serviceTemplateDescription", serviceTemplateDescription);
		logMap.put("serviceTemplateProfile", serviceTemplateProfile);
		logMap.put(PLATFORM_ID, platformId);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put(SERVICE_TYPE_ID, serviceTypeId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		CreateServiceResponse resp = null;
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			SdpServiceTemplate template = createServiceTemplate(serviceTemplateName, serviceTemplateDescription, serviceTemplateProfile, platformId,
					externalId, serviceTypeId, Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);

			resp = buildCreateResponse(Constants.CODE_OK);
			resp.setEntityId(template.getServiceTemplateId());
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
			log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		}
		return resp;
	}

	SdpServiceTemplate createServiceTemplate(String serviceTemplateName, String serviceTemplateDescription, String serviceTemplateProfile, Long platformId,
			String externalId, Long serviceTypeId, String createdBy, EntityManager em) throws ValidationException, PropertyNotFoundException {
		SdpServiceTemplateHelper handler = SdpServiceTemplateHelper.getInstance();
		SdpPlatformHelper platformHelper = SdpPlatformHelper.getInstance();
		RefServiceTypeHelper serviceHelper = RefServiceTypeHelper.getInstance();
		// formal validation
		handler.validateServiceTemplate(serviceTemplateName, platformId, serviceTypeId);

		// helper
		RefServiceType serviceType = serviceHelper.searchServiceTypeById(serviceTypeId, em);
		if (serviceType == null) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, SERVICE_TYPE_ID, String.valueOf(serviceTypeId));
		}
		SdpPlatform platform = platformHelper.searchPlatformById(platformId, em);
		if (platform == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PLATFORM_ID, platformId);
		}
		SdpServiceTemplate checkName = handler.searchServiceTemplateByName(serviceTemplateName, em);
		if (checkName != null) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, SERVICE_TEMPLATE_NAME, serviceTemplateName);
		}

		if (externalId != null) {
			SdpServiceTemplate checkExternalId = handler.searchByExternalId(externalId, em);
			if (checkExternalId != null) {
				throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, EXTERNAL_ID, externalId);
			}
		}

		return handler.createServiceTemplate(serviceTemplateName, serviceTemplateDescription, serviceTemplateProfile, platform, externalId, serviceType,
				createdBy, em);
	}

	/**
	 * <p>
	 * This method allows to update the information associated to a service template (but not the Status) already present into CSM database with the following
	 * constraints:
	 * <li>The service template status is ACTIVE</li>
	 * </p>
	 * 
	 * @param serviceTemplateId
	 *            Id of the service template to modify
	 * @param serviceTemplateName
	 *            Name of the service template to modify
	 * @param serviceTemplateDescription
	 *            Description of the service template to modify
	 * @param externalId
	 *            External id of the service template to modify
	 * @param serviceTemplateProfile
	 *            Profile of the service template to modify
	 * @param serviceTypeId
	 *            Id of type of new service template to modify
	 * @return Returns a DataServiceResponse containing operation result code and description
	 * @exception PropertyNotFoundException
	 */
	public DataServiceResponse modifyServiceTemplate(Long serviceTemplateId, String serviceTemplateName, String serviceTemplateDescription, String externalId,
			String serviceTemplateProfile, Long serviceTypeId, Long platformId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(SERVICE_TEMPLATE_ID, serviceTemplateId);
		logMap.put(SERVICE_TEMPLATE_NAME, serviceTemplateName);
		logMap.put("serviceTemplateDescription", serviceTemplateDescription);
		logMap.put("serviceTemplateProfile", serviceTemplateProfile);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put(SERVICE_TYPE_ID, serviceTypeId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		DataServiceResponse resp = null;
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			modifyServiceTemplate(serviceTemplateId, serviceTemplateName, serviceTemplateDescription, externalId, serviceTemplateProfile, serviceTypeId,
					platformId, Utilities.getCurrentClassAndMethod(), em);

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
			log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		}
		return resp;
	}

	SdpServiceTemplate modifyServiceTemplate(Long serviceTemplateId, String serviceTemplateName, String serviceTemplateDescription, String externalId,
			String serviceTemplateProfile, Long serviceTypeId, Long platformId, String updatedBy, EntityManager em) throws ValidationException,
			PropertyNotFoundException {

		SdpServiceTemplateHelper handler = SdpServiceTemplateHelper.getInstance();
		RefServiceTypeHelper serviceHelper = RefServiceTypeHelper.getInstance();
		SdpPlatformHelper platformHelper = SdpPlatformHelper.getInstance();
		// formal validation
		handler.validateSearchServiceTemplateById(serviceTemplateId);
		handler.validateServiceTemplate(serviceTemplateName, platformId, serviceTypeId);

		// workflow validation
		SdpServiceTemplate toModify = handler.searchServiceTemplateById(serviceTemplateId, em);
		if (toModify == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SERVICE_TEMPLATE_ID, serviceTemplateId);
		}
		RefServiceType serviceType = serviceHelper.searchServiceTypeById(serviceTypeId, em);
		if (serviceType == null) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, SERVICE_TYPE_ID, String.valueOf(serviceTypeId));
		}
		SdpPlatform platform = platformHelper.searchPlatformById(platformId, em);
		if (platform == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PLATFORM_ID, platformId);
		}
		if (!isStatusActive(toModify.getStatusId())) {
			throw new ValidationException(Constants.CODE_UPDATE_NOT_ALLOWED_FOR_STATUS, SERVICE_TEMPLATE_ID, serviceTemplateId);
		}
		SdpServiceTemplate checkName = handler.searchServiceTemplateByName(serviceTemplateName, em);
		if (checkName != null && !checkName.getServiceTemplateId().equals(serviceTemplateId)) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, SERVICE_TEMPLATE_NAME, serviceTemplateName);
		}
		if (externalId != null) {
			SdpServiceTemplate checkExternalId = handler.searchByExternalId(externalId, em);
			if (checkExternalId != null && !checkExternalId.getServiceTemplateId().equals(serviceTemplateId)) {
				throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, EXTERNAL_ID, externalId);
			}
		}

		handler.modifyServiceTemplate(toModify, serviceTemplateName, serviceTemplateDescription, serviceTemplateProfile, externalId, serviceType, platform,
				updatedBy);
		return toModify;
	}

	/**
	 * <p>
	 * This method allows to delete a service template, only in logically mode, setting the status of the service template to DELETED.
	 * 
	 * Pre-Requisites:
	 * 
	 * It's necessary to do the following check:
	 * <li>The service template to be deleted is in status Inactive</li>
	 * <li>There aren't service variants associated to the service template with status different from Deleted</li>
	 * </p>
	 * 
	 * @param serviceTemplateId
	 *            Id of the service template to delete
	 * @return Returns a DataServiceResponse containing operation result code and description
	 * @exception PropertyNotFoundException
	 */
	public DataServiceResponse deleteServiceTemplate(Long serviceTemplateId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(SERVICE_TEMPLATE_ID, serviceTemplateId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		DataServiceResponse resp = null;
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			deleteServiceTemplate(serviceTemplateId, Utilities.getCurrentClassAndMethod(), em);

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
			log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		}
		return resp;
	}

	void deleteServiceTemplate(Long serviceTemplateId, String deletedBy, EntityManager em) throws ValidationException, PropertyNotFoundException {
		SdpServiceTemplateHelper handler = SdpServiceTemplateHelper.getInstance();
		// formal validation
		handler.validateSearchServiceTemplateById(serviceTemplateId);

		// workflow validation
		SdpServiceTemplate toDelete = handler.searchServiceTemplateById(serviceTemplateId, em);
		if (toDelete == null) {
			log.logDebug("Service Template not Found!");
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SERVICE_TEMPLATE_ID, serviceTemplateId);
		}
		handler.checkAllowedChangeStatus(Constants.ENTITY_TYPE_OTHER, toDelete.getStatusId(), ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED), em);

		Long nVar = SdpServiceVariantHelper.getInstance().countServiceVariantsNotDeletedByServiceTemplateId(serviceTemplateId, em);
		if (nVar != null && nVar > 0L) {
			log.logDebug("Service Template has not-deleted service variant associated!");
			throw new ValidationException(Constants.CODE_ELEMENT_ALREADY_ASSOCIATED, SERVICE_TEMPLATE_ID, serviceTemplateId);
		}

		handler.deleteServiceTemplate(toDelete, deletedBy);
	}

	/**
	 * <p>
	 * This method allows to retrieve the information related to a service template. NOTE: The input parameter is mandatory
	 * 
	 * @param serviceTemplateId
	 *            Id of the service template to search
	 * @return Returns a SearchServiceResponse containing all the informations related to SdpServiceTemplate and operation result
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchServiceTemplate(Long serviceTemplateId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(SERVICE_TEMPLATE_ID, serviceTemplateId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		SearchServiceResponse resp;
		EntityManager em = null;
		try {
			SdpServiceTemplateHelper handler = SdpServiceTemplateHelper.getInstance();

			// formal validation
			handler.validateSearchServiceTemplateById(serviceTemplateId);

			em = PersistenceManager.getEntityManager(tenantName);

			// helper
			SdpServiceTemplate template = handler.searchServiceTemplateById(serviceTemplateId, em);
			if (template == null) {
				log.logDebug("Template not Found!");
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SERVICE_TEMPLATE_ID, serviceTemplateId);
			}
			List<SdpServiceTemplate> templates = new ArrayList<SdpServiceTemplate>();
			templates.add(template);
			List<SdpServiceTemplateDto> templateResponse = BeanConverter.convertServiceTemplate(templates);
			resp = buildSearchResponse(Constants.CODE_OK);
			resp.setSearchResult(templateResponse);
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
	 * This method allows to retrieve all service templates information. NOTE:
	 * 
	 * @param startPosition
	 *            Index of the first result
	 * @param maxRecordsNumber
	 *            Total number of rows to return
	 * @return Returns a SearchServiceResponse containing all the informations related to SdpServiceTemplate and operation result
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchAllServiceTemplates(Long startPosition, Long maxRecordsNumber, String tenantName) throws PropertyNotFoundException {
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
			SdpServiceTemplateHelper handler = SdpServiceTemplateHelper.getInstance();
			handler.validateSearchAll(startPosition, maxRecordsNumber);
			//
			em = PersistenceManager.getEntityManager(tenantName);

			Long totalRes = handler.countAllServiceTemplate(em);
			if (totalRes == null || totalRes == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}

			List<SdpServiceTemplate> templates = handler.searchAllServiceTemplates(startPosition, maxRecordsNumber, em);

			if (templates == null || templates.isEmpty()) {
				resp = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				resp = buildSearchResponse(Constants.CODE_OK);
			}

			resp.setSearchResult(BeanConverter.convertServiceTemplate(templates));
			resp.setTotalResult(totalRes);
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
	 * This method allows to retrieve the information related to a service template given a service variant name. NOTE: The input parameter is mandatory
	 * 
	 * @param serviceVariantName
	 *            Name of the service variant related to the service template to search
	 * @return Returns a SearchServiceResponse containing all the informations related to SdpServiceTemplate and operation result
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchServiceTemplateByServiceVariant(String serviceVariantName, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put("serviceVariantName", serviceVariantName);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		SearchServiceResponse resp;
		EntityManager em = null;
		try {
			SdpServiceVariantHelper handler = SdpServiceVariantHelper.getInstance();

			// formal validation
			handler.validateSearchServiceVariantByName(serviceVariantName);

			em = PersistenceManager.getEntityManager(tenantName);

			List<SdpServiceTemplate> templates = new ArrayList<SdpServiceTemplate>();
			SdpServiceVariant variant = handler.searchServiceVariantByName(serviceVariantName, em);
			if (variant == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, "serviceVariantName", serviceVariantName);
			}
			SdpServiceTemplate template = variant.getSdpServiceTemplate();
			if (template == null) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, "serviceVariantName", serviceVariantName);
			}
			templates.add(template);
			List<SdpServiceTemplateDto> templateResponse = BeanConverter.convertServiceTemplate(templates);
			resp = buildSearchResponse(Constants.CODE_OK);
			resp.setSearchResult(templateResponse);
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
	 * This method allows to retrieve the information related to a service template given a platform name. NOTE: The input parameter is mandatory
	 * 
	 * @param platformName
	 *            Name of the platform related to the service templates to search
	 * @param startPosition
	 *            Index of the first result
	 * @param maxRecordsNumber
	 *            Total number of rows to return
	 * @return Returns a SearchServiceResponse containing all the informations related to SdpServiceTemplate and operation result
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchServiceTemplatesByPlatform(String platformName, Long startPosition, Long maxRecordsNumber, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put("platformName", platformName);
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		SearchServiceResponse resp;
		EntityManager em = null;
		try {
			SdpPlatformHelper handler = SdpPlatformHelper.getInstance();
			SdpServiceTemplateHelper templateHelper = SdpServiceTemplateHelper.getInstance();
			// formal validation
			handler.validatePlatform(platformName);
			handler.validateSearchAll(startPosition, maxRecordsNumber);

			em = PersistenceManager.getEntityManager(tenantName);

			SdpPlatform platform = handler.searchPlatformByName(platformName, em);
			if (platform == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, "platformName", platformName);
			}
			Long totalResult = templateHelper.countServiceTemplateByPlatformId(platform.getPlatformId(), em);
			if (totalResult == null || totalResult == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, "platformName", platformName);
			}

			List<SdpServiceTemplate> templates = templateHelper.searchServiceTemplatesByPlatformId(platform.getPlatformId(), startPosition, maxRecordsNumber,
					em);

			if (templates == null || templates.isEmpty()) {
				resp = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				resp = buildSearchResponse(Constants.CODE_OK);
			}
			resp.setSearchResult(BeanConverter.convertServiceTemplate(templates));
			resp.setTotalResult(totalResult);
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
	 * This method allows to change the status of a service template.
	 * 
	 * @param serviceTemplateId
	 *            Id of service template to update
	 * @param status
	 *            value of the status to set on the service template
	 * @return Returns a DataServiceResponse containing operation result code and description
	 * @exception PropertyNotFoundException
	 */
	public DataServiceResponse serviceTemplateChangeStatus(Long serviceTemplateId, String status, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(SERVICE_TEMPLATE_ID, serviceTemplateId);
		logMap.put(STATUS, status);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		EntityManager em = null;
		DataServiceResponse resp = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			serviceTemplateChangeStatus(serviceTemplateId, status, Utilities.getCurrentClassAndMethod(), em);

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
			log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		}
		return resp;
	}

	SdpServiceTemplate serviceTemplateChangeStatus(Long serviceTemplateId, String status, String changedStatusBy, EntityManager em)
			throws PropertyNotFoundException, ValidationException {

		SdpServiceTemplateHelper helper = SdpServiceTemplateHelper.getInstance();
		// formal validation

		helper.validateSearchServiceTemplateById(serviceTemplateId);
		helper.validateChangeStatus(status);

		SdpServiceTemplate template = helper.searchServiceTemplateById(serviceTemplateId, em);
		if (template == null) {
			log.logDebug("Template not Found!");
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SERVICE_TEMPLATE_ID, serviceTemplateId);
		}
		helper.checkAllowedChangeStatus(Constants.ENTITY_TYPE_OTHER, template.getStatusId(), StatusIdConverter.getStatusValue(status), em);

		helper.changeStatus(template, StatusIdConverter.getStatusValue(status), changedStatusBy);
		return template;
	}
}
