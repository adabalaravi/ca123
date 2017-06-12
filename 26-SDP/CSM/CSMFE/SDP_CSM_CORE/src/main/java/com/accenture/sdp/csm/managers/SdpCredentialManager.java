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
import com.accenture.sdp.csm.dto.ResetPwdServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.requests.SdpSecretQuestionRequestDto;
import com.accenture.sdp.csm.dto.responses.ParameterDto;
import com.accenture.sdp.csm.dto.responses.SdpCredentialDto;
import com.accenture.sdp.csm.exceptions.EncryptionException;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.helpers.SdpCredentialHelper;
import com.accenture.sdp.csm.helpers.SdpPartyHelper;
import com.accenture.sdp.csm.helpers.SdpRoleHelper;
import com.accenture.sdp.csm.helpers.SdpSecretQuestionHelper;
import com.accenture.sdp.csm.helpers.SdpSubscriptionHelper;
import com.accenture.sdp.csm.model.jpa.SdpCredential;
import com.accenture.sdp.csm.model.jpa.SdpParty;
import com.accenture.sdp.csm.model.jpa.SdpRole;
import com.accenture.sdp.csm.model.jpa.SdpSubscription;
import com.accenture.sdp.csm.utilities.BeanConverter;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.CryptoUtils;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LogMap;

public final class SdpCredentialManager extends SdpBaseManager {

	private SdpCredentialManager() {
		super();
	}

	private static SdpCredentialManager instance;

	public static SdpCredentialManager getInstance() {
		if (instance == null) {
			instance = new SdpCredentialManager();
		}
		return instance;
	}

	/**
	 * <p>
	 * This method allows to insert into CSM database a new credential and to relate it to the party. The new credential is created by default with Status =
	 * ACTIVE
	 * </p>
	 * 
	 * @param userName
	 *            userName of the new credential to create
	 * @param password
	 *            password of the new credential to create
	 * @param partyID
	 *            Id of party to associate to new credential
	 * @param externalId
	 *            External id of new credential to create
	 * @param roleName
	 *            Name of the role associated to the credential
	 * @param secretQuestions
	 *            list of three secret questions and answers used for retrieve the username or the password
	 * @return Returns a CreateServiceResponse containing all the informations related to SdpCredential and operation result
	 * @exception PropertyNotFoundException
	 */

	public CreateServiceResponse createCredential(String userName, String password, Long partyID, String externalId, String roleName,
			List<SdpSecretQuestionRequestDto> secretQuestions, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		CreateServiceResponse resp = null;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(USERNAME, userName);
		logMap.put(PASSWORD, Utilities.isNull(password) ? null : HIDDEN_VALUE);
		logMap.put(PARTY_ID, partyID);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put("roleName", roleName);
		logMap.put("secretQuestion", secretQuestions);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();
			createCredential(userName, password, partyID, externalId, roleName, secretQuestions, Utilities.getCurrentClassAndMethod(), em);
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

	SdpCredential createCredential(String userName, String password, Long partyId, String externalId, String roleName,
			List<SdpSecretQuestionRequestDto> secretQuestions, String createdBy, EntityManager em) throws PropertyNotFoundException, ValidationException,
			EncryptionException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpPartyHelper partyHelper = SdpPartyHelper.getInstance();
		SdpCredentialHelper credentialHelper = SdpCredentialHelper.getInstance();
		SdpSecretQuestionHelper secretQuestionHelper = SdpSecretQuestionHelper.getInstance();
		SdpRoleHelper roleHelper = SdpRoleHelper.getInstance();

		partyHelper.validateSearchPartyById(partyId);
		credentialHelper.validateCreateCredential(userName, password, roleName);
		secretQuestionHelper.validateSecretQuestions(secretQuestions);
		SdpParty party = partyHelper.searchPartyById(partyId, em);
		if (party == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PARTY_ID, partyId);
		}
		SdpRole role = roleHelper.searchRoleByName(roleName, em);
		if (role == null) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, "roleName", roleName);
		}
		checkPartyRoleConsistency(party, role, userName);

		// verifica duplicati
		if (credentialHelper.searchCredentialByUsername(userName, em) != null) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, USERNAME, userName);
		}
		if (credentialHelper.searchByExternalId(externalId, em) != null) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, EXTERNAL_ID, externalId);
		}

		SdpCredential newCredential = credentialHelper.createCredential(party, userName, password, role.getRoleName(), externalId, createdBy, em);
		secretQuestionHelper.createSecretQuestions(newCredential, secretQuestions, createdBy, em);
		return newCredential;
	}

	private void checkPartyRoleConsistency(SdpParty party, SdpRole role, String userName) throws ValidationException, PropertyNotFoundException {
		SdpPartyHelper partyHelper = SdpPartyHelper.getInstance();
		SdpRoleHelper roleHelper = SdpRoleHelper.getInstance();
		if (partyHelper.isAParent(party)) {
			if (!roleHelper.isAdmin(role)) {
				throw new ValidationException(Constants.CODE_NOT_POSSIBLE_TO_CREATE_THE_CREDENTIAL_FOR_THE_PARTY);
			}
			for (SdpCredential credential : party.getSdpCredentials()) {
				if (credential.getUsername().equalsIgnoreCase(userName)) {
					throw new ValidationException(Constants.CODE_NOT_POSSIBLE_TO_CREATE_THE_CREDENTIAL_FOR_THE_PARTY);
				}
			}
		} else {
			if (!roleHelper.isUser(role)) {
				throw new ValidationException(Constants.CODE_NOT_POSSIBLE_TO_CREATE_THE_CREDENTIAL_FOR_THE_PARTY);
			}
		}
	}

	/**
	 * <p>
	 * This method allows to update the information associated to a credential (but not the Status) already present into CSM database with the following
	 * constraints:
	 * 
	 * * Pre-Requisites:
	 * 
	 * It's necessary to do the following check:
	 * 
	 * <li>The credential status is ACTIVE</li>
	 * <li>if Old Password, New Password and ConfirmNewPassword are not valorized the method is used for update for update the externalID or the secret
	 * questions and answers.</li>
	 * <li>The field Old Password, New Password and ConfirmNewPassword are valorized</li>
	 * <li>OldPassword is equal with the Password to be modified</li>
	 * <li>New Password is equat to Confirm New Password</li>
	 * </p>
	 * 
	 * @param userName
	 *            userName of the new credential to modify
	 * @param oldPassword
	 *            Old password to be changed
	 * @param newPassword
	 *            New Password of the credential to modify
	 * @param confirmNewPassword
	 *            New Password of the credential to modify
	 * @param externalId
	 *            External id of the credential to modify
	 * @param secretQuestions
	 *            list that contains the secret questions and answers used for retrieve the username or the password
	 * @return Returns a DataServiceResponse containing all the informations related to SdpCredential and operation result
	 * @exception PropertyNotFoundException
	 */

	public DataServiceResponse modifyCredential(String userName, String oldPassword, String newPassword, String confirmNewPassword, String externalId,
			List<SdpSecretQuestionRequestDto> secretQuestions, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp = null;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(USERNAME, userName);
		logMap.put("oldPassword", Utilities.isNull(oldPassword) ? null : HIDDEN_VALUE);
		logMap.put("newPassword", Utilities.isNull(newPassword) ? null : HIDDEN_VALUE);
		logMap.put("confirmNewPassword", Utilities.isNull(confirmNewPassword) ? null : HIDDEN_VALUE);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put("secretQuestion", secretQuestions);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();
			modifyCredential(userName, oldPassword, newPassword, confirmNewPassword, externalId, secretQuestions, Utilities.getCurrentClassAndMethod(), em);
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

	SdpCredential modifyCredential(String userName, String oldPassword, String newPassword, String confirmNewPassword, String externalId,
			List<SdpSecretQuestionRequestDto> secretQuestions, String modifiedBy, EntityManager em) throws PropertyNotFoundException, ValidationException,
			EncryptionException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpCredentialHelper credentialHelper = SdpCredentialHelper.getInstance();
		SdpSecretQuestionHelper secretQuestionHelper = SdpSecretQuestionHelper.getInstance();
		credentialHelper.validateSearchCredentialByUsername(userName);
		if (secretQuestions != null && !secretQuestions.isEmpty()) {
			secretQuestionHelper.validateModifySecretQuestions(secretQuestions);
		}

		SdpCredential modifyCredential = credentialHelper.searchCredentialByUsername(userName, em);
		if (modifyCredential == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, USERNAME, userName);
		}
		if (!isStatusActive(modifyCredential.getStatusId())) {
			throw new ValidationException(Constants.CODE_UPDATE_NOT_ALLOWED_FOR_STATUS, USERNAME, userName);
		}

		String password = null;
		if (Utilities.isNull(oldPassword) && Utilities.isNull(newPassword) && Utilities.isNull(confirmNewPassword)) {
			password = modifyCredential.getPassword();

		} else {
			password = credentialHelper.retriveCorrectPassword(CryptoUtils.decrypt(modifyCredential.getPassword()), oldPassword, newPassword,
					confirmNewPassword);
		}

		// verifica duplicati
		// username non verificato perche' non modificabile
		if (!Utilities.isNull(externalId)) {
			SdpCredential temp = credentialHelper.searchByExternalId(externalId, em);
			if (temp != null && !temp.getUsername().equals(userName)) {
				throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, EXTERNAL_ID, externalId);
			}
		}

		credentialHelper.modifyCredential(modifyCredential, modifyCredential.getSdpParty(), userName, password, modifyCredential.getSdpRole(), externalId,
				modifyCredential.getSdpSecretQuestions(), modifiedBy);
		if (secretQuestions != null && !secretQuestions.isEmpty()) {
			secretQuestionHelper.modifySecretQuestions(modifyCredential.getSdpSecretQuestions(), secretQuestions, modifyCredential, modifiedBy);
		}
		return modifyCredential;

	}

	/**
	 * <p>
	 * This method allows to delete a credential, only in logically mode, setting the status of the credential to DELETED.
	 * 
	 * Pre-Requisites:
	 * 
	 * It's necessary to do the following check:
	 * <li>The credential to be deleted is in status Inactive</li>
	 * <li>There aren't subscriptions, in status differerent from Deleted, associated to the username</li>
	 * </p>
	 * 
	 * @param userName
	 *            userName of the Credential to delete
	 * @return Returns a DataServiceResponse containing all the informations related to SdpCredential and operation result
	 * @exception PropertyNotFoundException
	 */

	public DataServiceResponse deleteCredential(String userName, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp = null;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(USERNAME, userName);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		// ***Gestione LOG: inizializzazione e log start***
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();
			deleteCredential(userName, Utilities.getCurrentClassAndMethod(), em);
			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildCreateResponse(Constants.CODE_OK);
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
			log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		}
		return resp;
	}

	void deleteCredential(String userName, String deletedBy, EntityManager em) throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpCredentialHelper credentialHelper = SdpCredentialHelper.getInstance();

		credentialHelper.validateSearchCredentialByUsername(userName);
		SdpCredential credential = credentialHelper.searchCredentialByUsername(userName, em);
		if (credential == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, USERNAME, userName);
		}

		credentialHelper.checkAllowedChangeStatus(Constants.ENTITY_TYPE_OTHER, credential.getStatusId(), ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED),
				em);

		SdpSubscriptionHelper subHelp = SdpSubscriptionHelper.getInstance();
		List<SdpSubscription> subs = subHelp.searchSubscriptionsNotDeletedByUsername(credential.getUsername(), em);
		if (subs != null && !subs.isEmpty()) {
			log.logDebug("Credential has not-deleted subscription associated!");
			throw new ValidationException(Constants.CODE_ELEMENT_ALREADY_ASSOCIATED, USERNAME, userName);
		}
		credentialHelper.deleteCredential(credential, deletedBy);
	}

	public SearchServiceResponse searchCredential(String userName, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		SdpCredentialHelper credentialHelper = SdpCredentialHelper.getInstance();
		LogMap logMap = new LogMap();
		logMap.put(USERNAME, userName);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			credentialHelper.validateSearchCredentialByUsername(userName);
			SdpCredential credential = credentialHelper.searchCredentialByUsername(userName, em);
			if (credential == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, USERNAME, userName);
			}
			List<SdpCredential> sdpCredentialList = new ArrayList<SdpCredential>();
			sdpCredentialList.add(credential);
			List<SdpCredentialDto> credentialList = BeanConverter.convertCredentialDto(sdpCredentialList);
			resp = buildSearchResponse(Constants.CODE_OK);
			resp.setSearchResult(credentialList);
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
	 * This method allows to retrieve the information related to a Credential given a subscriptionId
	 * 
	 * @param subscriptionId
	 *            Subscription Id related to the Credential to search
	 * @return Returns a SearchServiceResponse containing all the informations related to SdpCredential and operation result
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchCredentialBySubscription(Long subscriptionId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		SdpSubscriptionHelper subscriptionHelper = SdpSubscriptionHelper.getInstance();
		LogMap logMap = new LogMap();
		logMap.put("subscriptionId", subscriptionId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			// Validazione Formale
			subscriptionHelper.validateSearchSubscriptionById(subscriptionId);
			em = PersistenceManager.getEntityManager(tenantName);
			SdpSubscription subscription = subscriptionHelper.searchSubscriptionById(subscriptionId, em);
			if (subscription == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, "subscriptionId", subscriptionId);
			}
			SdpCredential credential = subscription.getSdpCredential();
			if (credential == null) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, "subscriptionId", subscriptionId);
			}
			List<SdpCredentialDto> credentialResponse = new ArrayList<SdpCredentialDto>();
			credentialResponse.add(BeanConverter.convertCredentialDto(credential));
			resp = buildSearchResponse(Constants.CODE_OK);
			resp.setSearchResult(credentialResponse);
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
	 * This method allows to retrieve the information related to a Credential given a partyId
	 * 
	 * @param partyId
	 *            party Id related to the Credential to search
	 * @return Returns a SearchServiceResponse containing all the informations related to SdpCredential and operation result
	 * @exception PropertyNotFoundException
	 */
	public SearchServiceResponse searchCredentialsByParty(Long partyId, Long startPosition, Long maxRecordsNumber, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		SdpPartyHelper partyHelper = SdpPartyHelper.getInstance();
		SdpCredentialHelper credentialHelper = SdpCredentialHelper.getInstance();
		LogMap logMap = new LogMap();
		logMap.put(PARTY_ID, partyId);
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			// Validazione Formale
			partyHelper.validateSearchPartyById(partyId);
			credentialHelper.validateSearchAll(startPosition, maxRecordsNumber);
			em = PersistenceManager.getEntityManager(tenantName);
			SdpParty party = partyHelper.searchPartyById(partyId, em);
			if (party == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PARTY_ID, partyId);
			}
			Long totalRes = credentialHelper.searchCredentialByPartyIdPaginatedCount(partyId, em);
			if (totalRes == null || totalRes == 0L) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, PARTY_ID, partyId);
			}

			List<SdpCredential> credentials = credentialHelper.searchCredentialByPartyIdPaginated(partyId, startPosition, maxRecordsNumber, em);
			if (credentials == null || credentials.isEmpty()) {
				resp = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				resp = buildSearchResponse(Constants.CODE_OK);
			}
			List<SdpCredentialDto> credentialResponse = BeanConverter.convertCredentialDto(credentials);
			resp.setTotalResult(totalRes);
			resp.setSearchResult(credentialResponse);
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
	 * This method allows to change the status of a credential.
	 * 
	 * @param userName
	 *            username of the the Credential to update
	 * @param status
	 *            value of the status to set on the Credential
	 * @return Returns a DataServiceResponse containing all the informations related to SdpCredential and operation result
	 * @exception PropertyNotFoundException
	 */
	public DataServiceResponse credentialChangeStatus(String userName, String status, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp = null;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(USERNAME, userName);
		logMap.put(STATUS, status);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			credentialChangeStatus(userName, status, Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildCreateResponse(Constants.CODE_OK);
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
			log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		}
		return resp;
	}

	void credentialChangeStatus(String userName, String status, String changedBy, EntityManager em) throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpCredentialHelper credentialHelper = SdpCredentialHelper.getInstance();
		credentialHelper.validateSearchCredentialByUsername(userName);
		credentialHelper.validateChangeStatus(status);
		SdpCredential credential = credentialHelper.searchCredentialByUsername(userName, em);
		if (credential == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, USERNAME, userName);
		}
		credentialHelper.checkAllowedChangeStatus(Constants.ENTITY_TYPE_OTHER, credential.getStatusId(), StatusIdConverter.getStatusValue(status), em);
		credentialHelper.changeStatus(credential, StatusIdConverter.getStatusValue(status), changedBy);
	}

	/**
	 * <p>
	 * This method allows to authenticate the user.
	 * 
	 * @param username
	 *            userName of the Credential
	 * @param password
	 *            password of the Credential
	 * @return Returns a DataServiceResponse containing all the informations related to SdpCredential and operation result
	 * @exception PropertyNotFoundException
	 */

	public DataServiceResponse checkCredential(String username, String password, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		SdpCredentialHelper credentialHelper = SdpCredentialHelper.getInstance();
		LogMap logMap = new LogMap();
		logMap.put(USERNAME, username);
		logMap.put(PASSWORD, Utilities.isNull(password) ? null : HIDDEN_VALUE);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			// Validazione Formale
			credentialHelper.validateCheckCredential(username, password);
			em = PersistenceManager.getEntityManager(tenantName);
			// creazione party
			SdpCredential credential = credentialHelper.searchCredentialByUsername(username, em);
			if (!credentialHelper.checkCredential(credential, password, em)) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}
			ArrayList<SdpCredential> credentials = new ArrayList<SdpCredential>();
			credentials.add(credential);
			List<SdpCredentialDto> credentialResponse = BeanConverter.convertCredentialDto(credentials);
			resp = buildSearchResponse(Constants.CODE_OK);
			resp.setSearchResult(credentialResponse);
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

	public ResetPwdServiceResponse resetPassword(String userName, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		ResetPwdServiceResponse resp = null;
		EntityManager em = null;
		SdpCredentialHelper credentialHelper = SdpCredentialHelper.getInstance();
		LogMap logMap = new LogMap();
		logMap.put(USERNAME, userName);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityTransaction tx = null;
		try {
			// Validazione Formale
			credentialHelper.validateSearchCredentialByUsername(userName);
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			SdpCredential credential = credentialHelper.searchCredentialByUsername(userName, em);
			if (credential == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, USERNAME, userName);
			}
			if (!isStatusActive(credential.getStatusId())) {
				throw new ValidationException(Constants.CODE_UPDATE_NOT_ALLOWED_FOR_STATUS, USERNAME, userName);
			}
			// creazione party
			String newPwd = credentialHelper.resetPassword(credential, em);
			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildResetPwdResponse(Constants.CODE_OK);
			resp.setResetPassword(CryptoUtils.decrypt(newPwd));
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

	public SearchServiceResponse reserveUsername(String userName, Long partyId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		SdpCredentialHelper credentialHelper = SdpCredentialHelper.getInstance();
		SdpPartyHelper partyHelper = SdpPartyHelper.getInstance();
		LogMap logMap = new LogMap();
		logMap.put(PARTY_ID, partyId);
		logMap.put(USERNAME, userName);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			// Validazione Formale
			credentialHelper.validateSearchCredentialByUsername(userName);
			partyHelper.validateSearchPartyById(partyId);
			em = PersistenceManager.getEntityManager(tenantName);
			SdpParty party = partyHelper.searchPartyById(partyId, em);
			if (party == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PARTY_ID, partyId);
			}
			if (partyHelper.isAParent(party)) {
				throw new ValidationException(Constants.CODE_HIERARCHY_NOT_RESPECTED, PARTY_ID, partyId);
			}
			if (!Utilities.checkRightEmailFormat(userName)) {
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, USERNAME, userName);
			}
			String domain = Utilities.extractDomain(userName);
			SdpCredential credential = credentialHelper.searchCredentialByUsername(userName, em);
			resp = buildSearchResponse(Constants.CODE_OK);
			List<String> alternatives = new ArrayList<String>();
			if (credential != null) {
				// create alternative
				alternatives = credentialHelper.createUnreservedUsernameRandom(party.getSdpPartyData().getFirstName(), party.getSdpPartyData().getLastName(),
						domain, em);
				if (alternatives.size() == 0) {
					resp = buildSearchResponse(Constants.CODE_LIST_USERNAME_ALTERNATIVE_NOT_CREATED, new ParameterDto(USERNAME, userName));
					resp.setSearchResult(alternatives);
					log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_LIST_USERNAME_ALTERNATIVE_NOT_CREATED);
				} else if (alternatives.size() < Constants.USERNAME_MAX_ALTERNATIVES) {
					resp = buildSearchResponse(Constants.CODE_ERROR_GENERATION_USERNAME, new ParameterDto(USERNAME, userName));
					resp.setSearchResult(alternatives);
					log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_ERROR_GENERATION_USERNAME);
				} else {
					resp = buildSearchResponse(Constants.CODE_DUPLICATE_ENTRY, new ParameterDto(USERNAME, userName));
					resp.setSearchResult(alternatives);
					log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_DUPLICATE_ENTRY);
				}
			} else {
				resp = buildSearchResponse(Constants.CODE_OK);
				resp.setSearchResult(alternatives);
				log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
			}
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
