package com.accenture.sdp.csm.managers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import com.accenture.sdp.csm.commons.LinkUpdateOperation;
import com.accenture.sdp.csm.commons.StatusIdConverter;
import com.accenture.sdp.csm.database.PersistenceManager;
import com.accenture.sdp.csm.dto.ComplexServiceResponse;
import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.ResetPwdServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.requests.SdpOperatorRoleRightLnkRequestDto;
import com.accenture.sdp.csm.dto.requests.SdpTenantOperatorLnkRequestDto;
import com.accenture.sdp.csm.dto.responses.ParameterDto;
import com.accenture.sdp.csm.exceptions.EncryptionException;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.helpers.SdpCredentialHelper;
import com.accenture.sdp.csm.helpers.SdpOperatorsHelper;
import com.accenture.sdp.csm.model.jpa.RefTenant;
import com.accenture.sdp.csm.model.jpa.SdpOperator;
import com.accenture.sdp.csm.model.jpa.SdpOperatorRight;
import com.accenture.sdp.csm.utilities.BeanConverter;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LogMap;

public final class SdpOperatorsManager extends SdpBaseManager {

	private SdpOperatorsManager() {
		super();
	}

	private static SdpOperatorsManager instance;

	public static SdpOperatorsManager getInstance() {
		if (instance == null) {
			instance = new SdpOperatorsManager();
		}
		return instance;
	}

	/**
	 * <p>
	 * This method allows to enter a new operator into catalogue of all possible console operators
	 * </p>
	 * 
	 * @param username
	 *            Username of the new operator to create
	 * @param firstName
	 *            First name of the new operator to create
	 * @param lastName
	 *            Last name of the new operator to create
	 * @param password
	 *            Password of the new operator to create
	 * @param email
	 *            Email of the new operator to create
	 * @param tenantNames
	 *            tenant to link to the new operator to create
	 * 
	 * @return Returns a CreateServiceResponse containing operation result
	 * @exception PropertyNotFoundException
	 */
	public CreateServiceResponse createOperator(String username, String firstName, String lastName, String password, String email, List<String> tenantNames)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		CreateServiceResponse resp = null;
		LogMap logMap = new LogMap();
		logMap.put(USERNAME, username);
		logMap.put(FIRST_NAME, firstName);
		logMap.put(LAST_NAME, lastName);
		logMap.put(PASSWORD, Utilities.isNull(password) ? null : HIDDEN_VALUE);
		logMap.put("email", email);
		logMap.put(TENANT_NAME, tenantNames);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager();
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			createOperator(username, firstName, lastName, password, email, tenantNames, Utilities.getCurrentClassAndMethod(), em);

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

	SdpOperator createOperator(String username, String firstName, String lastName, String password, String email, List<String> tenantNames, String createdBy,
			EntityManager em) throws PropertyNotFoundException, ValidationException, EncryptionException {
		// validation
		SdpOperatorsHelper helper = SdpOperatorsHelper.getInstance();
		helper.validateCreateOperator(username, firstName, lastName, password);
		if (tenantNames == null || tenantNames.size() == 0) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, TENANT_NAME);
		}

		List<RefTenant> tenants = new ArrayList<RefTenant>();
		for (String tenantName : tenantNames) {
			helper.validateSearchTenantByName(tenantName);
			RefTenant tenant = helper.searchTenantByName(tenantName, em);
			if (tenant == null) {
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, TENANT_NAME, tenantName);
			}
			tenants.add(tenant);
		}

		if (!helper.checkPasswordFormat(password)) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, PASSWORD, password);
		}
		// verifica duplicati
		if (helper.searchOperatorByUsername(username, em) != null) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, USERNAME, username);
		}
		return helper.createOperator(username, firstName, lastName, password, email, tenants, createdBy, em);
	}

	/**
	 * <p>
	 * This method allows to modify all information related to an operator already present into catalogue of all possible console operators
	 * </p>
	 * 
	 * @param username
	 *            Username of the new operator to modify. It's a search key and it's not updatable
	 * @param firstName
	 *            First name of the new operator to create
	 * @param lastName
	 *            Last name of the new operator to create
	 * @param status
	 *            Status of the new operator to create. Possible values: Active - Inactive
	 * @param role
	 *            Role of the new operator to create
	 * @param operatorGroup
	 *            Operator group of the new operator to create
	 * 
	 * @return Returns a DataServiceResponse containing operation result
	 * @exception PropertyNotFoundException
	 */
	public DataServiceResponse modifyOperator(String username, String firstName, String lastName, String email, String status) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp = null;
		LogMap logMap = new LogMap();
		logMap.put(USERNAME, username);
		logMap.put(FIRST_NAME, firstName);
		logMap.put(LAST_NAME, lastName);
		logMap.put("email", email);
		logMap.put(STATUS, status);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager();
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			modifyOperator(username, firstName, lastName, email, status, Utilities.getCurrentClassAndMethod(), em);
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

	SdpOperator modifyOperator(String username, String firstName, String lastName, String email, String status, String updatedById, EntityManager em)
			throws PropertyNotFoundException, ValidationException {
		SdpOperatorsHelper helper = SdpOperatorsHelper.getInstance();
		helper.validateModifyOperator(username, firstName, lastName);
		helper.validateChangeStatus(status);
		if (!helper.checkOperatorStatus(status)) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, STATUS, status);
		}
		SdpOperator operator = helper.searchOperatorByUsername(username, em);
		if (operator == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, USERNAME, username);
		}

		Long statusId = StatusIdConverter.getStatusValue(status);

		helper.modifyOperator(operator, firstName, lastName, email, statusId, updatedById, em);

		return operator;
	}

	public DataServiceResponse modifyTenantOperator(String username, List<SdpTenantOperatorLnkRequestDto> tenants) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp = null;
		LogMap logMap = new LogMap();
		logMap.put(USERNAME, username);
		logMap.put("tenant", tenants);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager();
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			modifyTenantOperator(username, tenants, Utilities.getCurrentClassAndMethod(), em);
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

	SdpOperator modifyTenantOperator(String username, List<SdpTenantOperatorLnkRequestDto> tenants, String updatedBy, EntityManager em)
			throws PropertyNotFoundException, ValidationException {
		SdpOperatorsHelper helper = SdpOperatorsHelper.getInstance();
		// formal validation
		helper.validateSearchOperatorByUsername(username);
		helper.validateModifyTenantOperatorLink(tenants);

		SdpOperator operator = helper.searchOperatorByUsername(username, em);
		if (operator == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, USERNAME, username);
		}
		for (SdpTenantOperatorLnkRequestDto dto : tenants) {
			RefTenant tenant = helper.searchTenantByName(dto.getTenantName(), em);
			if (tenant == null) {
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, TENANT_NAME, dto.getTenantName());
			}
			if (dto.getOperation().equalsIgnoreCase(LinkUpdateOperation.Operation.DELETE.getValue())) {
				helper.removeTenantOperatorLink(operator, tenant, updatedBy);
			} else if (dto.getOperation().equalsIgnoreCase(LinkUpdateOperation.Operation.NEW.getValue())) {
				// verifica duplicati -> gestisco direttamente sulla lista
				if (operator.getTenants().contains(tenant)) {
					throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, new ParameterDto(USERNAME, username), new ParameterDto(TENANT_NAME,
							dto.getTenantName()));
				}
				helper.createTenantOperatorLink(operator, tenant, updatedBy);
			}
		}
		return operator;
	}

	/**
	 * <p>
	 * This method allows to physically delete an operator from the catalog *
	 * </p>
	 * 
	 * @param username
	 *            Username of the operator to delete
	 * @return Returns a DataServiceResponse containing operation result code and description
	 * @exception PropertyNotFoundException
	 */
	public DataServiceResponse deleteOperator(String username) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp = null;
		LogMap logMap = new LogMap();
		logMap.put(USERNAME, username);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager();
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();
			deleteOperator(username, em);
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
			resp = buildUpdateResponse(Constants.CODE_DELETE_FAILED, new ParameterDto(USERNAME, username));
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

	void deleteOperator(String username, EntityManager em) throws PropertyNotFoundException, ValidationException {
		// formal validation
		SdpOperatorsHelper handler = SdpOperatorsHelper.getInstance();
		handler.validateSearchOperatorByUsername(username);

		// workflow validation
		SdpOperator operatorToDelete = handler.searchOperatorByUsername(username, em);
		if (operatorToDelete == null) {
			log.logDebug("Operator with username: %s not found", username);
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, USERNAME, username);
		}
		if (!isStatusInactive(operatorToDelete.getStatusId())) {
			log.logDebug("operator not inactive!");
			throw new ValidationException(Constants.CODE_UPDATE_NOT_ALLOWED_FOR_STATUS, USERNAME, username);
		}

		handler.deleteOperator(operatorToDelete, em);
	}

	/**
	 * <p>
	 * This method allows to search for all operators present into catalogue of all possible console operators *
	 * </p>
	 * 
	 * @param startPosition
	 *            Index of the first result to return
	 * @param maxRecordsNumber
	 *            Total number of rows to return
	 * @return Returns a SearchServiceResponse containing operation result code and description and all operators informations
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchAllOperators(Long startPosition, Long maxRecordsNumber) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		EntityManager em = null;
		SearchServiceResponse resp = null;
		try {
			// Validazione Formale
			SdpOperatorsHelper helper = SdpOperatorsHelper.getInstance();
			helper.validateSearchAll(startPosition, maxRecordsNumber);
			em = PersistenceManager.getEntityManager();

			Long totalResult = helper.countAllOperators(em);
			if (totalResult == null || totalResult == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}

			List<SdpOperator> operators = helper.searchAllOperators(startPosition, maxRecordsNumber, em);

			if (operators == null || operators.isEmpty()) {
				resp = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				resp = buildSearchResponse(Constants.CODE_OK);
			}
			resp.setSearchResult(BeanConverter.convertOperators(operators));
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

	/**
	 * <p>
	 * This method allows to search for an operator present into catalogue of all possible console operators
	 * </p>
	 * 
	 * @param username
	 *            Username of the operator to search
	 * @return Returns a SearchServiceResponse containing operation result code and description and operator informations
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchOperator(String username) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(USERNAME, username);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		EntityManager em = null;
		SearchServiceResponse resp = null;
		try {
			// Validazione Formale
			SdpOperatorsHelper helper = SdpOperatorsHelper.getInstance();
			helper.validateSearchOperatorByUsername(username);
			em = PersistenceManager.getEntityManager();

			SdpOperator operator = helper.searchOperatorByUsername(username, em);
			if (operator == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, USERNAME, username);
			}

			List<SdpOperator> operators = new ArrayList<SdpOperator>();
			operators.add(operator);

			resp = buildSearchResponse(Constants.CODE_OK);
			resp.setSearchResult(BeanConverter.convertOperators(operators));

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

	public SearchServiceResponse searchOperatorByName(String lastName, String firstName, Long startPosition, Long maxRecordsNumber)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(LAST_NAME, lastName);
		logMap.put(FIRST_NAME, firstName);
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityManager em = null;
		SearchServiceResponse resp = null;
		try {
			// Validazione Formale
			SdpOperatorsHelper helper = SdpOperatorsHelper.getInstance();
			helper.validateSearchOperatorByName(lastName);
			helper.validateSearchAll(startPosition, maxRecordsNumber);
			em = PersistenceManager.getEntityManager();

			Long totalResult = helper.countOperatorByName(lastName, firstName, em);
			if (totalResult == null || totalResult == 0L) {
				if (!Utilities.isNull(firstName)) {
					throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, new ParameterDto(LAST_NAME, lastName), new ParameterDto(FIRST_NAME,
							firstName));
				} else {
					throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, LAST_NAME, lastName);
				}
			}

			List<SdpOperator> operators = helper.searchOperatorByName(lastName, firstName, startPosition, maxRecordsNumber, em);
			if (operators == null || operators.isEmpty()) {
				resp = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				resp = buildSearchResponse(Constants.CODE_OK);
			}

			resp.setSearchResult(BeanConverter.convertOperators(operators));
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

	public SearchServiceResponse searchOperatorByTenant(String tenantName, Long startPosition, Long maxRecordsNumber) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(TENANT_NAME, tenantName);
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityManager em = null;
		SearchServiceResponse resp = null;
		try {
			// Validazione Formale
			SdpOperatorsHelper helper = SdpOperatorsHelper.getInstance();
			helper.validateSearchTenantByName(tenantName);
			helper.validateSearchAll(startPosition, maxRecordsNumber);
			em = PersistenceManager.getEntityManager();

			RefTenant tenant = helper.searchTenantByName(tenantName, em);
			if (tenant == null) {
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, TENANT_NAME, tenantName);
			}
			Long totalResult = Long.valueOf(tenant.getOperators().size());
			if (totalResult == null || totalResult == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, TENANT_NAME, tenantName);
			}

			List<SdpOperator> operators = helper.searchOperatorsByTenantName(tenantName, startPosition, maxRecordsNumber, em);
			if (operators == null || operators.isEmpty()) {
				resp = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				resp = buildSearchResponse(Constants.CODE_OK);
			}
			resp.setSearchResult(BeanConverter.convertOperators(operators));
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

	public SearchServiceResponse searchAllTenants() throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logStartMethod(startTime, null);
		EntityManager em = null;
		SearchServiceResponse resp = null;
		try {
			em = PersistenceManager.getEntityManager();

			List<RefTenant> tenants = SdpOperatorsHelper.getInstance().searchAllTenants(em);
			if (tenants == null || tenants.isEmpty()) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}
			resp = buildSearchResponse(Constants.CODE_OK);
			resp.setSearchResult(BeanConverter.convertTenants(tenants));
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

	/**
	 * <p>
	 * This method allows to reset the password of an operator present into catalogue of all possible console operators
	 * </p>
	 * 
	 * @param username
	 *            Username of the operator
	 * @return Returns a ResetPwdServiceResponse containing operation result code and description and new generated password
	 * @exception PropertyNotFoundException
	 */

	public ResetPwdServiceResponse resetOperatorPassword(String username) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		ResetPwdServiceResponse resp = null;
		EntityManager em = null;
		SdpOperatorsHelper helper = SdpOperatorsHelper.getInstance();
		LogMap logMap = new LogMap();
		logMap.put(USERNAME, username);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		EntityTransaction tx = null;
		try {
			helper.validateSearchOperatorByUsername(username);
			em = PersistenceManager.getEntityManager();
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			SdpOperator operator = helper.searchOperatorByUsername(username, em);
			if (operator == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, USERNAME, username);
			}
			if (!isStatusActive(operator.getStatusId())) {
				throw new ValidationException(Constants.CODE_UPDATE_NOT_ALLOWED_FOR_STATUS, USERNAME, username);
			}
			String newPwd = helper.resetOperatorPassword(operator, em);
			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildResetPwdResponse(Constants.CODE_OK);
			resp.setResetPassword(newPwd);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildResetPwdResponse(validationException.getDescription(), validationException.getParameters());
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildResetPwdResponse(Constants.CODE_UPDATE_FAILED);
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildResetPwdResponse(Constants.CODE_GENERIC_ERROR);
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

	/**
	 * <p>
	 * This method allows to change the password of an operator present into catalogue of all possible console operators
	 * </p>
	 * 
	 * @param username
	 *            Username of the operator
	 * @param oldPassword
	 *            Old password of the operator
	 * @param newPassword
	 *            New password of the operator
	 * @param confirmNewPassword
	 *            Confirmation of the new password of the operator
	 * @return Returns a DataServiceResponse containing operation result code and description
	 * @exception PropertyNotFoundException
	 */
	public DataServiceResponse changeOperatorPassword(String username, String oldPassword, String newPassword, String confirmNewPassword)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp = null;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(USERNAME, username);
		logMap.put("oldPassword", Utilities.isNull(oldPassword) ? null : HIDDEN_VALUE);
		logMap.put("newPassword", Utilities.isNull(newPassword) ? null : HIDDEN_VALUE);
		logMap.put("confirmNewPassword", Utilities.isNull(confirmNewPassword) ? null : HIDDEN_VALUE);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager();
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			changeOperatorPassword(username, oldPassword, newPassword, confirmNewPassword, Utilities.getCurrentClassAndMethod(), em);
			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildUpdateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (EncryptionException encryptException) {
			log.logDebug(encryptException.getMessage());
			resp = buildUpdateResponse(Constants.CODE_GENERIC_ERROR);
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
			log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		}
		return resp;
	}

	SdpOperator changeOperatorPassword(String username, String oldPassword, String newPassword, String confirmNewPassword, String updatedById, EntityManager em)
			throws PropertyNotFoundException, ValidationException, EncryptionException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpOperatorsHelper operatorsHelper = SdpOperatorsHelper.getInstance();

		operatorsHelper.validateSearchOperatorByUsername(username);

		SdpOperator operator = operatorsHelper.searchOperatorByUsername(username, em);
		if (operator == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, USERNAME, String.valueOf(username));
		}
		if (!isStatusActive(operator.getStatusId())) {
			throw new ValidationException(Constants.CODE_UPDATE_NOT_ALLOWED_FOR_STATUS, USERNAME, username);
		}

		SdpCredentialHelper.getInstance().validateChangePassword(operator.getPassword(), oldPassword, newPassword, confirmNewPassword);

		if (!operatorsHelper.checkPasswordFormat(newPassword)) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, "newPassword", newPassword);
		}

		operatorsHelper.updateOperatorPassword(operator, newPassword, updatedById, em);

		return operator;
	}

	/**
	 * <p>
	 * This method allows to authenticate an operator.
	 * 
	 * @param username
	 *            Username of the operator
	 * @param password
	 *            password of the operator
	 * @return Returns a SearchServiceResponse containing all the informations related to an operator and operation result
	 * @exception PropertyNotFoundException
	 */

	public ComplexServiceResponse loginOperator(String username, String password, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		ComplexServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(USERNAME, username);
		logMap.put(PASSWORD, Utilities.isNull(password) ? null : HIDDEN_VALUE);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			SdpOperatorsHelper helper = SdpOperatorsHelper.getInstance();
			helper.validateLoginOperator(username, password);
			helper.validateSearchTenantByName(tenantName);
			em = PersistenceManager.getEntityManager();

			SdpOperator operator = helper.searchOperatorByUsernameAndTenantName(username, tenantName, em);
			if (operator == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, new ParameterDto(USERNAME, username), new ParameterDto(TENANT_NAME, tenantName));
			}
			if (!isStatusActive(operator.getStatusId())) {
				throw new ValidationException(Constants.CODE_UPDATE_NOT_ALLOWED_FOR_STATUS, USERNAME, username);
			}
			if (!helper.checkOperatorCredential(operator, password)) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND);
			}
			resp = buildComplexResponse(Constants.CODE_OK);
			resp.setComplexObject(BeanConverter.convertOperator(operator));
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildComplexResponse(validationException.getDescription(), validationException.getParameters());
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildComplexResponse(Constants.CODE_GENERIC_ERROR);
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		return resp;
	}

	public SearchServiceResponse searchAllOperatorRights() throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logStartMethod(startTime, null);
		EntityManager em = null;
		SearchServiceResponse resp = null;
		try {
			// Validazione Formale
			SdpOperatorsHelper helper = SdpOperatorsHelper.getInstance();
			em = PersistenceManager.getEntityManager();

			List<SdpOperatorRight> rights = helper.searchAllOperatorRights(em);

			if (rights == null || rights.isEmpty()) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}
			resp = buildSearchResponse(Constants.CODE_OK);

			resp.setSearchResult(BeanConverter.convertOperatorRights(rights));
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

	public DataServiceResponse modifyOperatorRights(String username, List<SdpOperatorRoleRightLnkRequestDto> rights) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp = null;
		LogMap logMap = new LogMap();
		logMap.put(USERNAME, username);
		logMap.put("right", rights);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager();
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			modifyOperatorRights(username, rights, Utilities.getCurrentClassAndMethod(), em);

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

	SdpOperator modifyOperatorRights(String username, List<SdpOperatorRoleRightLnkRequestDto> rights, String updatedBy, EntityManager em)
			throws PropertyNotFoundException, ValidationException {
		SdpOperatorsHelper helper = SdpOperatorsHelper.getInstance();
		// formal validation
		helper.validateSearchOperatorByUsername(username);
		helper.validateModifyOperatorRoleRightsLink(rights);

		SdpOperator operator = helper.searchOperatorByUsername(username, em);
		if (operator == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, USERNAME, username);
		}
		for (SdpOperatorRoleRightLnkRequestDto dto : rights) {
			SdpOperatorRight right = helper.searchOperatorRightById(dto.getRightId(), em);
			if (right == null) {
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, "rightId", String.valueOf(dto.getRightId()));
			}
			if (dto.getOperation().equalsIgnoreCase(LinkUpdateOperation.Operation.DELETE.getValue())) {
				helper.removeOperatorOperatorRightLink(operator, right, updatedBy);
			} else if (dto.getOperation().equalsIgnoreCase(LinkUpdateOperation.Operation.NEW.getValue())) {
				// verifica duplicati -> gestisco direttamente sulla lista
				if (operator.getOperatorRights().contains(right)) {
					throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, new ParameterDto(USERNAME, username), new ParameterDto("rightId",
							String.valueOf(dto.getRightId())));
				}
				helper.createOperatorOperatorRightLink(operator, right, updatedBy);
			}
		}
		return operator;
	}

	public SearchServiceResponse searchOperatorRights(String username) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(USERNAME, username);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		EntityManager em = null;
		SearchServiceResponse resp = null;
		try {
			// Validazione Formale
			SdpOperatorsHelper helper = SdpOperatorsHelper.getInstance();
			helper.validateSearchOperatorByUsername(username);
			em = PersistenceManager.getEntityManager();

			SdpOperator operator = helper.searchOperatorByUsername(username, em);
			if (operator == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, USERNAME, username);
			}

			List<SdpOperatorRight> rights = operator.getOperatorRights();

			if (rights == null || rights.isEmpty()) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}
			resp = buildSearchResponse(Constants.CODE_OK);
			resp.setSearchResult(BeanConverter.convertOperatorRights(rights));

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
