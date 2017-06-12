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
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.helpers.SdpCredentialHelper;
import com.accenture.sdp.csm.helpers.SdpRoleHelper;
import com.accenture.sdp.csm.helpers.SdpSubscriptionHelper;
import com.accenture.sdp.csm.model.jpa.SdpRole;
import com.accenture.sdp.csm.utilities.BeanConverter;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LogMap;

public final class SdpRoleManager extends SdpBaseManager {

	private SdpRoleManager() {
		super();
	}

	private static SdpRoleManager instance;

	public static SdpRoleManager getInstance() {
		if (instance == null) {
			instance = new SdpRoleManager();
		}
		return instance;
	}

	/**
	 * <p>
	 * This method allows to enter a new role into catalogue of all possible role for a party.
	 * </p>
	 * 
	 * @param roleName
	 *            Name of the new role to create
	 * @param roleDescription
	 *            Description of the new role to create
	 * @return Returns a CreateServiceResponse containing all the informations related to SdpRole and operation result
	 * @exception PropertyNotFoundException
	 */
	public CreateServiceResponse createRole(String roleName, String roleDescription, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		CreateServiceResponse resp = null;
		LogMap logMap = new LogMap();
		logMap.put(ROLE_NAME, roleName);
		logMap.put("roleDescription", roleDescription);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			createRole(roleName, roleDescription, Utilities.getCurrentClassAndMethod(), em);

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
			log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		}
		return resp;
	}

	SdpRole createRole(String roleName, String roleDescription, String createdBy, EntityManager em) throws PropertyNotFoundException, ValidationException {
		// Validazione e chiamata helper
		SdpRoleHelper handler = SdpRoleHelper.getInstance();
		handler.validateCreateModifyRole(roleName, roleDescription);

		// verifica duplicati
		if (handler.searchRoleByName(roleName, em) != null) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, ROLE_NAME, roleName);
		}
		return handler.createRole(roleName, roleDescription, createdBy, em);
	}

	/**
	 * <p>
	 * This method allows to update the information associated to a role already present into CSM database
	 * </p>
	 * 
	 * @param roleName
	 *            Name of the role to modify
	 * @param roleDescription
	 *            Description of the role to modify
	 * @return Returns a DataServiceResponse containing operation result code and description
	 * @exception PropertyNotFoundException
	 */
	public DataServiceResponse modifyRole(String roleName, String roleDescription, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp = null;
		LogMap logMap = new LogMap();
		logMap.put(ROLE_NAME, roleName);
		logMap.put("roleDescription", roleDescription);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();
			modifyRole(roleName, roleDescription, Utilities.getCurrentClassAndMethod(), em);
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
			log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		}
		return resp;
	}

	SdpRole modifyRole(String roleName, String roleDescription, String updatedBy, EntityManager em) throws PropertyNotFoundException, ValidationException {
		// formal validation
		SdpRoleHelper handler = SdpRoleHelper.getInstance();
		handler.validateRoleByName(roleName);

		// workflow validation
		SdpRole toModify = handler.searchRoleByName(roleName, em);
		if (toModify == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, ROLE_NAME, roleName);
		}
		handler.modifyRole(toModify, roleName, roleDescription, updatedBy, em);

		return toModify;
	}

	/**
	 * <p>
	 * This method allows to physically delete a price from the catalog
	 * 
	 * @param roleName
	 *            role name of the role to delete
	 * @return Returns a DataServiceResponse containing operation result code and description
	 * @exception PropertyNotFoundException
	 */
	public DataServiceResponse deleteRole(String roleName, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp = null;
		LogMap logMap = new LogMap();
		logMap.put(ROLE_NAME, roleName);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			// cancellazione fisica, non si passa il deletedBy
			deleteRole(roleName, em);
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
			resp = buildUpdateResponse(Constants.CODE_DELETE_FAILED, new ParameterDto(ROLE_NAME, roleName));
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

	void deleteRole(String roleName, EntityManager em) throws PropertyNotFoundException, ValidationException {
		// formal validation
		SdpRoleHelper handler = SdpRoleHelper.getInstance();
		handler.validateRoleByName(roleName);

		// workflow validation
		SdpRole toDelete = handler.searchRoleByName(roleName, em);
		if (toDelete == null) {
			log.logDebug("Role not Found!");
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, ROLE_NAME, roleName);
		}
		SdpCredentialHelper credentialHelper = SdpCredentialHelper.getInstance();
		Long countCredentials = credentialHelper.searchCredentialByRoleNameCount(roleName, em);
		if (countCredentials > 0) {
			log.logDebug("Role has not-deleted credential associated!");
			throw new ValidationException(Constants.CODE_ELEMENT_ALREADY_ASSOCIATED, ROLE_NAME, roleName);
		}
		SdpSubscriptionHelper subscriptionHelper = SdpSubscriptionHelper.getInstance();
		Long countSubscription = subscriptionHelper.searchSubscriptionByRoleNameCount(roleName, em);
		if (countSubscription > 0) {
			log.logDebug("Role has not-deleted subscription associated!");
			throw new ValidationException(Constants.CODE_ELEMENT_ALREADY_ASSOCIATED, ROLE_NAME, roleName);
		}
		handler.deleteRole(toDelete, em);
	}

	public SearchServiceResponse searchRole(String roleName, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(ROLE_NAME, roleName);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		EntityManager em = null;
		SearchServiceResponse resp = null;
		try {
			// Validazione Formale
			SdpRoleHelper helper = SdpRoleHelper.getInstance();
			helper.validateRoleByName(roleName);

			em = PersistenceManager.getEntityManager(tenantName);
			SdpRole role = helper.searchRoleByName(roleName, em);
			if (role == null) {
				log.logDebug(Utilities.getCurrentClassAndMethod() + ": no result found");
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, ROLE_NAME, roleName);
			}
			List<SdpRole> roles = new ArrayList<SdpRole>();
			roles.add(role);
			resp = buildSearchResponse(Constants.CODE_OK);
			resp.setSearchResult(BeanConverter.convertRole(roles));
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
			log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		}
		return resp;
	}

	public SearchServiceResponse searchAllRole(Long startPosition, Long maxRecordsNumber, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		EntityManager em = null;
		SearchServiceResponse resp = null;
		try {
			// Validazione Formale
			SdpRoleHelper helper = SdpRoleHelper.getInstance();
			helper.validateSearchAll(startPosition, maxRecordsNumber);
			em = PersistenceManager.getEntityManager(tenantName);

			Long totalResult = helper.countAllRole(em);
			if (totalResult == null || totalResult == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}

			List<SdpRole> roles = helper.searchAllRole(startPosition, maxRecordsNumber, em);

			if (roles == null || roles.isEmpty()) {
				resp = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				resp = buildSearchResponse(Constants.CODE_OK);
			}
			resp.setSearchResult(BeanConverter.convertRole(roles));
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
			log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		}
		return resp;
	}
}
