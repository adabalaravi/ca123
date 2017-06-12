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
import com.accenture.sdp.csm.dto.responses.SdpPlatformCompleteDto;
import com.accenture.sdp.csm.dto.responses.SdpPlatformDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.helpers.SdpPlatformHelper;
import com.accenture.sdp.csm.helpers.SdpServiceTemplateHelper;
import com.accenture.sdp.csm.helpers.SdpServiceVariantHelper;
import com.accenture.sdp.csm.model.jpa.SdpPlatform;
import com.accenture.sdp.csm.model.jpa.SdpServiceTemplate;
import com.accenture.sdp.csm.model.jpa.SdpServiceVariant;
import com.accenture.sdp.csm.utilities.BeanConverter;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LogMap;

public final class SdpPlatformManager extends SdpBaseManager {

	private SdpPlatformManager() {
		super();
	}

	private static SdpPlatformManager instance;

	public static SdpPlatformManager getInstance() {
		if (instance == null) {
			instance = new SdpPlatformManager();
		}
		return instance;
	}

	/**
	 * <p>
	 * This method allows to insert into CSM database a new platform. The new platform is created by default with ACTIVE Status
	 * </p>
	 * 
	 * @param platformName
	 *            Name of the new platform to create
	 * @param platformDescription
	 *            Description of the new platform to create
	 * @param externalId
	 *            External id of new platform to create
	 * @param platformProfile
	 *            profile of new platform to create
	 * @return Returns a CreateServiceResponse containing all the informations related to SdpPlatform and operation result
	 * @exception PropertyNotFoundException
	 */
	public CreateServiceResponse createPlatform(String platformName, String platformDescription, String externalId, String platformProfile, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(PLATFORM_NAME, platformName);
		logMap.put("platformDescription", platformDescription);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put("platformProfile", platformProfile);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		CreateServiceResponse resp;
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);

			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			SdpPlatform platform = createPlatform(platformName, platformDescription, externalId, platformProfile, Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildCreateResponse(Constants.CODE_OK);
			resp.setEntityId(platform.getPlatformId());
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

	SdpPlatform createPlatform(String platformName, String platformDescription, String externalId, String platformProfile, String createdBy, EntityManager em)
			throws ValidationException, PropertyNotFoundException {
		SdpPlatformHelper handler = SdpPlatformHelper.getInstance();
		// formal validation
		handler.validatePlatform(platformName);

		SdpPlatform checkName = handler.searchPlatformByName(platformName, em);
		if (checkName != null) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, PLATFORM_NAME, platformName);
		}

		if (externalId != null) {
			SdpPlatform checkExternalId = handler.searchByExternalId(externalId, em);
			if (checkExternalId != null) {
				throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, EXTERNAL_ID, externalId);
			}
		}

		return handler.createPlatform(platformName, platformDescription, externalId, platformProfile, createdBy, em);
	}

	/**
	 * <p>
	 * This method allows to update the information associated to a platform (but not the Status) already present into CSM database with the following
	 * constraints:
	 * 
	 * <li>The platform status is ACTIVE</li>
	 * </p>
	 * 
	 * @param platformId
	 *            Id of the platform to modify
	 * @param platformName
	 *            Name of the platform to modify
	 * @param platformDescription
	 *            Description of the platform to modify
	 * @param externalId
	 *            External id of the platform to modify
	 * @param platformProfile
	 *            profile of new platform to create
	 * @return Returns a DataServiceResponse containing operation result code and description
	 * @exception PropertyNotFoundException
	 */
	public DataServiceResponse modifyPlatform(Long platformId, String platformName, String platformDescription, String externalId, String platformProfile,
			String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(PLATFORM_ID, platformId);
		logMap.put(PLATFORM_NAME, platformName);
		logMap.put("platformDescription", platformDescription);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put("platformProfile", platformProfile);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		DataServiceResponse resp;
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			SdpPlatform toModify = modifyPlatform(platformId, platformName, platformDescription, externalId, platformProfile,
					Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildUpdateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
			log.logDebug("SdpPlatform with id:%s updated successfully", toModify.getPlatformId());
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

	SdpPlatform modifyPlatform(Long platformId, String platformName, String platformDescription, String externalId, String platformProfile, String updatedBy,
			EntityManager em) throws ValidationException, PropertyNotFoundException {
		SdpPlatformHelper handler = SdpPlatformHelper.getInstance();
		// formal validation
		handler.validateSearchPlatformById(platformId);
		handler.validatePlatform(platformName);

		// workflow validation
		SdpPlatform toModify = handler.searchPlatformById(platformId, em);
		if (toModify == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PLATFORM_ID, platformId);
		}

		SdpPlatform checkName = handler.searchPlatformByName(platformName, em);
		if (checkName != null && !checkName.getPlatformId().equals(platformId)) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, PLATFORM_NAME, platformName);
		}

		if (externalId != null) {
			SdpPlatform checkExternalId = handler.searchByExternalId(externalId, em);
			if (checkExternalId != null && !checkExternalId.getPlatformId().equals(platformId)) {
				throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, EXTERNAL_ID, externalId);
			}
		}

		if (!isStatusActive(toModify.getStatusId())) {
			throw new ValidationException(Constants.CODE_UPDATE_NOT_ALLOWED_FOR_STATUS, PLATFORM_ID, platformId);
		}

		handler.modifyPlatform(toModify, platformName, platformDescription, externalId, platformProfile, updatedBy);
		return toModify;
	}

	/**
	 * <p>
	 * This method allows to delete a platform, only in logically mode, setting the status of the platform to DELETED.
	 * 
	 * Pre-Requisites:
	 * 
	 * It's necessary to do the following check:
	 * <li>The platform to be deleted is in status Inactive</li>
	 * <li>There aren't service templates associated to the platform with status different from Deleted</li>
	 * </p>
	 * 
	 * @param platformId
	 *            Id of the platform to delete
	 * @return Returns a DataServiceResponse containing operation result code and description
	 * @exception PropertyNotFoundException
	 */
	public DataServiceResponse deletePlatform(Long platformId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(PLATFORM_ID, platformId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		DataServiceResponse resp;
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			deletePlatform(platformId, Utilities.getCurrentClassAndMethod(), em);

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
			resp = buildCreateResponse(Constants.CODE_UPDATE_FAILED);
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

	void deletePlatform(Long platformId, String deletedBy, EntityManager em) throws ValidationException, PropertyNotFoundException {
		SdpPlatformHelper handler = SdpPlatformHelper.getInstance();
		// formal validation
		handler.validateSearchPlatformById(platformId);

		// workflow validation
		SdpPlatform toDelete = handler.searchPlatformById(platformId, em);
		if (toDelete == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PLATFORM_ID, platformId);
		}
		handler.checkAllowedChangeStatus(Constants.ENTITY_TYPE_OTHER, toDelete.getStatusId(), ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED), em);

		SdpServiceTemplateHelper tHelper = SdpServiceTemplateHelper.getInstance();
		Long ntemplates = tHelper.countServiceTemplatesNotDeletedByPlatformId(platformId, em);
		if (ntemplates != null && ntemplates > 0L) {
			log.logDebug(ntemplates + " service templates not deleted found");
			throw new ValidationException(Constants.CODE_ELEMENT_ALREADY_ASSOCIATED, PLATFORM_ID, platformId);
		}

		handler.deletePlatform(toDelete, deletedBy);
	}

	/**
	 * <p>
	 * This method allows to retrieve the information related to a platform. NOTE: The input parameter is mandatory
	 * 
	 * @param platformId
	 *            Id of the platform to search
	 * @return Returns a SearchServiceResponse containing all the informations related to SdpPlatform and operation result
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchPlatform(Long platformId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(PLATFORM_ID, platformId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		SearchServiceResponse resp;
		EntityManager em = null;
		try {
			SdpPlatformHelper handler = SdpPlatformHelper.getInstance();
			// formal validation
			handler.validateSearchPlatformById(platformId);

			em = PersistenceManager.getEntityManager(tenantName);
			// helper
			SdpPlatform platform = handler.searchPlatformById(platformId, em);
			if (platform == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PLATFORM_ID, platformId);
			}
			List<SdpPlatform> platforms = new ArrayList<SdpPlatform>();
			platforms.add(platform);
			List<SdpPlatformDto> platformResponse = BeanConverter.convertPlatforms(platforms);
			resp = buildSearchResponse(Constants.CODE_OK);
			resp.setSearchResult(platformResponse);
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
	 * This method allows to retrieve the information related to all the platforms present into CSM database.
	 * 
	 * @param startPosition
	 *            Index of the first result
	 * @param maxRecordsNumber
	 *            Total number of rows to return
	 * @return Returns a SearchServiceResponse containing all the informations related to SdpPlatform and operation result
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchAllPlatforms(Long startPosition, Long maxRecordsNumber, String tenantName) throws PropertyNotFoundException {
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
			SdpPlatformHelper handler = SdpPlatformHelper.getInstance();
			handler.validateSearchAll(startPosition, maxRecordsNumber);

			em = PersistenceManager.getEntityManager(tenantName);

			Long totalResult = handler.countPlatform(em);
			if (totalResult == null || totalResult == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}

			List<SdpPlatform> platforms = handler.searchAllPlatforms(startPosition, maxRecordsNumber, em);

			if (platforms == null || platforms.isEmpty()) {
				resp = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				resp = buildSearchResponse(Constants.CODE_OK);
			}
			resp.setSearchResult(BeanConverter.convertPlatforms(platforms));
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
	 * This method allows to retrieve the information related to a platform given a service template name. NOTE: The input parameter is mandatory
	 * 
	 * @param serviceTemplateName
	 *            Name of the serviceTemplate of the platform to search
	 * @return Returns a SearchServiceResponse containing all the informations related to SdpPlatform and operation result
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchPlatformByServiceTemplate(String serviceTemplateName, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put("serviceTemplateName", serviceTemplateName);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		SearchServiceResponse resp;
		EntityManager em = null;
		try {
			SdpServiceTemplateHelper handler = SdpServiceTemplateHelper.getInstance();

			// formal validation
			handler.validateSearchServiceTemplateByName(serviceTemplateName);

			em = PersistenceManager.getEntityManager(tenantName);

			List<SdpPlatform> platforms = new ArrayList<SdpPlatform>();
			SdpServiceTemplate template = handler.searchServiceTemplateByName(serviceTemplateName, em);
			if (template == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, "serviceTemplateName", serviceTemplateName);
			}
			SdpPlatform platform = template.getSdpPlatform();
			if (platform == null) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, "serviceTemplateName", serviceTemplateName);
			}
			platforms.add(platform);
			List<SdpPlatformDto> platformResponse = BeanConverter.convertPlatforms(platforms);
			resp = buildSearchResponse(Constants.CODE_OK);
			resp.setSearchResult(platformResponse);
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
	 * This method allows to retrieve the information related to a platform given a service variant name. NOTE: The input parameter is mandatory
	 * 
	 * @param serviceVariantName
	 *            Name of the serviceVariant of the platform to search
	 * @return Returns a SearchServiceResponse containing all the informations related to SdpPlatform and operation result
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchPlatformByServiceVariant(String serviceVariantName, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
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

			SdpServiceVariant variant = handler.searchServiceVariantByName(serviceVariantName, em);
			if (variant == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, "serviceVariantName", serviceVariantName);
			}
			SdpServiceTemplate template = variant.getSdpServiceTemplate();
			if (template == null || template.getSdpPlatform() == null) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, "serviceVariantName", serviceVariantName);
			}
			// costruzione risposta
			SdpPlatformCompleteDto dto = new SdpPlatformCompleteDto();
			dto.setSdpPlatformDto(BeanConverter.convertPlatform(template.getSdpPlatform()));
			dto.setSdpServiceTemplateDto(BeanConverter.convertServiceTemplate(template));
			dto.setSdpServiceVariantDto(BeanConverter.convertServiceVariant(variant));
			List<SdpPlatformCompleteDto> result = new ArrayList<SdpPlatformCompleteDto>();
			result.add(dto);
			// fatto
			resp = buildSearchResponse(Constants.CODE_OK);
			resp.setSearchResult(result);
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
	 * This method allows to change the status of a platform.
	 * 
	 * @param platformId
	 *            Id of the the platform to update
	 * @param status
	 *            value of the status to set on the platform
	 * @return Returns a DataServiceResponse containing operation result code and description
	 * @exception PropertyNotFoundException
	 */
	public DataServiceResponse platformChangeStatus(Long platformId, String status, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(PLATFORM_ID, platformId);
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

			SdpPlatform platform = platformChangeStatus(platformId, status, Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildUpdateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
			log.logDebug("SdpPlatform with id:%s changed status successfully", platform.getPlatformId());
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

	SdpPlatform platformChangeStatus(Long platformId, String status, String changedStatusBy, EntityManager em) throws ValidationException,
			PropertyNotFoundException {
		SdpPlatformHelper helper = SdpPlatformHelper.getInstance();

		// formal validation
		helper.validateSearchPlatformById(platformId);
		helper.validateChangeStatus(status);

		SdpPlatform platform = helper.searchPlatformById(platformId, em);
		if (platform == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PLATFORM_ID, platformId);
		}
		helper.checkAllowedChangeStatus(Constants.ENTITY_TYPE_OTHER, platform.getStatusId(), StatusIdConverter.getStatusValue(status), em);

		helper.changeStatus(platform, StatusIdConverter.getStatusValue(status), changedStatusBy);

		return platform;
	}

}
