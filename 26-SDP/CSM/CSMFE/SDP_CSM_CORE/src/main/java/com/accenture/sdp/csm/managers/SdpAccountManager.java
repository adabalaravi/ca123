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
import com.accenture.sdp.csm.dto.responses.SdpAccountResponseDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.helpers.SdpAccountHelper;
import com.accenture.sdp.csm.helpers.SdpPartyHelper;
import com.accenture.sdp.csm.helpers.SdpSiteHelper;
import com.accenture.sdp.csm.helpers.SdpSubscriptionHelper;
import com.accenture.sdp.csm.model.jpa.SdpAccount;
import com.accenture.sdp.csm.model.jpa.SdpParty;
import com.accenture.sdp.csm.model.jpa.SdpSite;
import com.accenture.sdp.csm.model.jpa.SdpSubscription;
import com.accenture.sdp.csm.utilities.BeanConverter;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LogMap;

public final class SdpAccountManager extends SdpBaseManager {

	private SdpAccountManager() {
		super();
	}

	private static SdpAccountManager instance;

	public static SdpAccountManager getInstance() {
		if (instance == null) {
			instance = new SdpAccountManager();
		}
		return instance;
	}

	public CreateServiceResponse createAccount(String accountName, String accountDescription, boolean isDefaultAccount, Long partyId, Long siteId,
			String externalId, String accountProfile, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(ACCOUNT_NAME, accountName);
		logMap.put("accountDescription", accountDescription);
		logMap.put("isDefaultAccount", isDefaultAccount);
		logMap.put(PARTY_ID, partyId);
		logMap.put(SITE_ID, siteId);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put("accountProfile", accountProfile);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityManager em = null;
		CreateServiceResponse resp = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();
			SdpAccount account = createAccount(accountName, accountDescription, isDefaultAccount, partyId, siteId, externalId, accountProfile,
					Utilities.getCurrentClassAndMethod(), em);
			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			log.logDebug("SdpAccount with id:%s and accountName: %s inserted successfully", account.getAccountId(), accountName);
			resp = buildCreateResponse(Constants.CODE_OK);
			resp.setEntityId(account.getAccountId());
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

	SdpAccount createAccount(String accountName, String accountDescription, boolean isDefaultAccount, Long partyId, Long siteId, String externalId,
			String accountProfile, String createdBy, EntityManager em) throws ValidationException, PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpAccountHelper accountHelper = SdpAccountHelper.getInstance();
		SdpPartyHelper partyHelper = SdpPartyHelper.getInstance();
		SdpSiteHelper partySiteHelper = SdpSiteHelper.getInstance();
		// Validazione Formale
		accountHelper.validateAccount(accountName);
		partyHelper.validateSearchPartyById(partyId);
		SdpParty party = partyHelper.searchPartyById(partyId, em);
		if (party == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PARTY_ID, partyId);
		}
		SdpSite site = partySiteHelper.searchSiteById(siteId, em);
		if (siteId != null) {
			if (site == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SITE_ID, siteId);
			}
			// verifico site sia associato al parent
			SdpParty organization = party;
			if (!organization.getRefPartyType().equals(ConstantsHandler.getInstance().retrieveLongConstant(Constants.PARTY_TYPE_ORGANIZATION))) {
				organization = party.getParentParty();
			}
			if (!site.getSdpParty().equals(organization)) {
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, SITE_ID, siteId);
			}
		}

		// verifica duplicati
		if (accountHelper.searchAccountByName(accountName, em) != null) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, ACCOUNT_NAME, accountName);
		}
		if (accountHelper.searchByExternalId(externalId, em) != null) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, EXTERNAL_ID, externalId);
		}

		SdpAccount account = accountHelper.createAccount(accountName, accountDescription, isDefaultAccount, party, site, externalId, accountProfile, createdBy,
				em);
		return account;
	}

	public DataServiceResponse modifyAccount(Long accountId, String accountName, String accountDescription, boolean isDefaultAccount, Long partyId,
			Long siteId, String externalId, String accountProfile, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(ACCOUNT_ID, accountId);
		logMap.put(ACCOUNT_NAME, accountName);
		logMap.put("accountDescription", accountDescription);
		logMap.put(PARTY_ID, partyId);
		logMap.put(EXTERNAL_ID, externalId);
		logMap.put("accountProfile", accountProfile);
		logMap.put("isDefaultAccount", isDefaultAccount);
		logMap.put(SITE_ID, siteId);
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
			modifyAccount(accountId, accountName, accountDescription, isDefaultAccount, partyId, siteId, externalId, accountProfile,
					Utilities.getCurrentClassAndMethod(), em);
			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
			resp = buildUpdateResponse(Constants.CODE_OK);
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

	SdpAccount modifyAccount(Long accountId, String accountName, String accountDescription, boolean isDefaultAccount, Long partyId, Long siteId,
			String externalId, String accountProfile, String updatedBy, EntityManager em) throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		// Validazione Formale
		SdpAccountHelper helper = SdpAccountHelper.getInstance();
		SdpPartyHelper partyHelp = SdpPartyHelper.getInstance();
		helper.validateSearchAccountById(accountId);
		helper.validateAccount(accountName);
		partyHelp.validateSearchPartyById(partyId);
		// Workflow validation
		// validate account
		SdpAccount account = helper.searchAccountById(accountId, em);
		if (account == null) {
			log.logDebug("Account not Found!");
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, ACCOUNT_ID, accountId);
		}
		if (!isStatusActive(account.getStatusId())) {
			log.logDebug("Account not active!");
			throw new ValidationException(Constants.CODE_UPDATE_NOT_ALLOWED_FOR_STATUS, ACCOUNT_ID, accountId);
		}

		// validate party
		SdpParty party = partyHelp.searchPartyById(partyId, em);
		if (party == null) {
			log.logDebug("Party not Found!");
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PARTY_ID, partyId);
		}

		// retrieve organization which the account appertains
		SdpParty organization = account.getSdpParty();
		if (!organization.getRefPartyType().equals(ConstantsHandler.getInstance().retrieveLongConstant(Constants.PARTY_TYPE_ORGANIZATION))) {
			organization = account.getSdpParty().getParentParty();

			// check new party is of the same organization
			if (party.getRefPartyType().equals(ConstantsHandler.getInstance().retrieveLongConstant(Constants.PARTY_TYPE_USER)) && !party.getParentParty().equals(organization)) {
				log.logDebug("Party not allowed! Appertains to another organization");
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, PARTY_ID, partyId);
			}
		}
		// if party is an organization, it has to be the organization itself
		else if (!party.equals(organization)) {
			log.logDebug("Party not allowed! Is another organization");
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, PARTY_ID, partyId);
		}

		// validate site
		SdpSiteHelper siteHelp = SdpSiteHelper.getInstance();
		SdpSite site = null;
		if (siteId != null) {
			site = siteHelp.searchSiteById(siteId, em);
			if (site == null) {
				log.logDebug("Site not Found!");
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SITE_ID, siteId);
			}
			if (!site.getSdpParty().equals(organization)) {
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, SITE_ID, siteId);
			}
		}

		// verifica duplicati
		SdpAccount temp = helper.searchAccountByName(accountName, em);
		if (temp != null && !temp.getAccountId().equals(accountId)) {
			throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, ACCOUNT_NAME, accountName);
		}
		if (!Utilities.isNull(externalId)) {
			temp = helper.searchByExternalId(externalId, em);
			if (temp != null && !temp.getAccountId().equals(accountId)) {
				throw new ValidationException(Constants.CODE_DUPLICATE_ENTRY, EXTERNAL_ID, externalId);
			}
		}

		helper.modifyAccount(account, accountName, accountDescription, isDefaultAccount, party, site, externalId, accountProfile, updatedBy);
		return account;
	}

	public DataServiceResponse deleteAccount(Long accountId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(ACCOUNT_ID, accountId);
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
			deleteAccount(accountId, Utilities.getCurrentClassAndMethod(), em);
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

	void deleteAccount(Long accountId, String deletedBy, EntityManager em) throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpAccountHelper helper = SdpAccountHelper.getInstance();
		helper.validateSearchAccountById(accountId);
		// Workflow validation
		SdpAccount account = helper.searchAccountById(accountId, em);
		if (account == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, ACCOUNT_ID, accountId);
		}
		helper.checkAllowedChangeStatus(Constants.ENTITY_TYPE_OTHER, account.getStatusId(), ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED), em);

		SdpSubscriptionHelper subHelp = SdpSubscriptionHelper.getInstance();
		List<SdpSubscription> subs = subHelp.searchSubscriptionsNotDeletedByAccountId(accountId, em);
		if (subs != null && !subs.isEmpty()) {
			log.logDebug("Account has not-deleted subscription associated!");
			throw new ValidationException(Constants.CODE_ELEMENT_ALREADY_ASSOCIATED, ACCOUNT_ID, accountId);
		}

		helper.deleteAccount(account, deletedBy);
	}

	public SearchServiceResponse searchAccount(String accountName, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(ACCOUNT_NAME, accountName);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		EntityManager em = null;
		SearchServiceResponse resp = null;
		try {
			// Validazione Formale
			SdpAccountHelper helper = SdpAccountHelper.getInstance();
			helper.validateSearchAccountByName(accountName);
			em = PersistenceManager.getEntityManager(tenantName);
			SdpAccount account = helper.searchAccountByName(accountName, em);
			if (account == null) {
				log.logDebug("no result found");
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, ACCOUNT_NAME, accountName);
			}
			resp = buildSearchResponse(Constants.CODE_OK);
			ArrayList<SdpAccount> accounts = new ArrayList<SdpAccount>();
			accounts.add(account);
			resp.setSearchResult(BeanConverter.convertSdpAccounts(accounts));
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

	public SearchServiceResponse searchAccountsByParty(Long partyId, Long startPosition, Long maxRecordsNumber, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(PARTY_ID, partyId);
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		EntityManager em = null;
		SearchServiceResponse resp = null;
		try {
			// Validazione Formale
			SdpPartyHelper partyHelp = SdpPartyHelper.getInstance();
			SdpAccountHelper accountsHelp = SdpAccountHelper.getInstance();
			partyHelp.validateSearchPartyById(partyId);
			accountsHelp.validateSearchAll(startPosition, maxRecordsNumber);

			em = PersistenceManager.getEntityManager(tenantName);

			SdpParty party = partyHelp.searchPartyById(partyId, em);
			if (party == null) {
				log.logDebug("Party not Found!");
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PARTY_ID, partyId);
			}

			Long totalRes = accountsHelp.searchAccountsByPartyIdCount(partyId, em);
			if (totalRes == null || totalRes == 0L) {
				log.logDebug("Accounts not Found");
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, PARTY_ID, partyId);
			}

			List<SdpAccount> accounts = accountsHelp.searchAccountsByPartyId(partyId, startPosition, maxRecordsNumber, em);
			if (accounts == null || accounts.isEmpty()) {
				resp = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				resp = buildSearchResponse(Constants.CODE_OK);
			}
			resp.setSearchResult(BeanConverter.convertSdpAccounts(accounts));
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
			log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		}
		return resp;
	}

	public SearchServiceResponse searchAccountsBySite(Long siteId, Long startPosition, Long maxRecordsNumber, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(SITE_ID, siteId);
		logMap.put(START_POSITION, startPosition);
		logMap.put(MAX_RECORDS, maxRecordsNumber);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();

		EntityManager em = null;
		SearchServiceResponse resp = null;
		try {
			// Validazione Formale
			SdpSiteHelper siteHelp = SdpSiteHelper.getInstance();
			SdpAccountHelper helper = SdpAccountHelper.getInstance();
			siteHelp.validateSearchSiteById(siteId);
			siteHelp.validateSearchAll(startPosition, maxRecordsNumber);

			em = PersistenceManager.getEntityManager(tenantName);

			SdpSite site = siteHelp.searchSiteById(siteId, em);

			if (site == null) {
				log.logDebug("Site not Found!");
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SITE_ID, siteId);
			}

			Long totalRes = helper.searchAccountsBySiteIdCount(siteId, em);
			if (totalRes == null || totalRes == 0L) {
				log.logDebug("Accounts not Found");
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, SITE_ID, siteId);
			}

			List<SdpAccount> accounts = helper.searchAccountsBySiteId(siteId, startPosition, maxRecordsNumber, em);
			if (accounts == null || accounts.isEmpty()) {
				resp = buildSearchResponse(Constants.CODE_OK_OUT_OF_RANGE);
			} else {
				resp = buildSearchResponse(Constants.CODE_OK);
			}

			resp.setSearchResult(BeanConverter.convertSdpAccounts(accounts));
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
			log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		}
		return resp;
	}

	public SearchServiceResponse searchAccountsBySubscription(Long subscriptionId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put("subscriptionId", subscriptionId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		// ***Gestione LOG: inizializzazione e log start***
		EntityManager em = null;
		SearchServiceResponse resp = null;
		try {
			// Validazione Formale
			SdpSubscriptionHelper subHelp = SdpSubscriptionHelper.getInstance();
			subHelp.validateSearchSubscriptionById(subscriptionId);

			em = PersistenceManager.getEntityManager(tenantName);

			SdpSubscription sub = subHelp.searchSubscriptionById(subscriptionId, em);

			if (sub == null) {
				log.logDebug("Site not Found!");
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, "subscriptionId", subscriptionId);
			}

			List<SdpAccountResponseDto> accounts = new ArrayList<SdpAccountResponseDto>();

			if (sub.getSdpAccountOwner() != null) {
				accounts.add(BeanConverter.convertSdpAccount(sub.getSdpAccountOwner(), "Owner"));
			}
			if (sub.getSdpAccountPayee() != null) {
				accounts.add(BeanConverter.convertSdpAccount(sub.getSdpAccountPayee(), "Payee"));
			}

			if (accounts.isEmpty()) {
				log.logDebug("Accounts not Found!");
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND, "subscriptionId", subscriptionId);
			}

			resp = buildSearchResponse(Constants.CODE_OK);
			resp.setSearchResult(accounts);
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

	public DataServiceResponse accountChangeStatus(Long accountId, String nextStatus, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(ACCOUNT_ID, accountId);
		logMap.put(STATUS, nextStatus);
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
			accountChangeStatus(accountId, nextStatus, Utilities.getCurrentClassAndMethod(), em);
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

	SdpAccount accountChangeStatus(Long accountId, String nextStatus, String changedBy, EntityManager em) throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpAccountHelper helper = SdpAccountHelper.getInstance();
		helper.validateSearchAccountById(accountId);
		helper.validateChangeStatus(nextStatus);
		SdpAccount account = helper.searchAccountById(accountId, em);
		if (account == null) {
			log.logDebug("Account not Found!");
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, ACCOUNT_ID, accountId);
		}
		helper.checkAllowedChangeStatus(Constants.ENTITY_TYPE_OTHER, account.getStatusId(), StatusIdConverter.getStatusValue(nextStatus), em);
		helper.changeStatus(account, StatusIdConverter.getStatusValue(nextStatus), changedBy);

		return account;
	}

}
