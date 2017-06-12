package com.accenture.sdp.csm.helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.commons.IsDefaultAccount.DefaultAccount;
import com.accenture.sdp.csm.database.NamedQueryHelper;
import com.accenture.sdp.csm.dto.requests.SdpAccountRequestDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.model.jpa.SdpAccount;
import com.accenture.sdp.csm.model.jpa.SdpParty;
import com.accenture.sdp.csm.model.jpa.SdpSite;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.validation.ValidationResult;
import com.accenture.sdp.csm.validation.ValidationUtils;

public final class SdpAccountHelper extends SdpBaseHelper {

	private static SdpAccountHelper instance;

	private SdpAccountHelper() {
		super();
	}

	public static SdpAccountHelper getInstance() {
		if (instance == null) {
			instance = new SdpAccountHelper();
		}
		return instance;
	}

	public SdpAccount createAccount(String accountName, String accountDescription, boolean isDefaultAccount, SdpParty party, SdpSite site, String externalId,
			String accountProfile, String createdBy, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpAccount newAccount = new SdpAccount();
		newAccount.setAccountName(accountName);
		newAccount.setAccountDescription(accountDescription);
		newAccount.setStatusId(ConstantsHandler.getInstance().retrieveLongConstant(Constants.ACTIVE));
		newAccount.setSdpParty(party);
		newAccount.setIsDefaultPartyAccount(DefaultAccount.getIsDefaultAccountValue(isDefaultAccount));
		newAccount.setExternalId(externalId);
		newAccount.setAccountProfile(accountProfile);
		newAccount.setSdpPartySite(site);
		newAccount.setCreatedById(createdBy);
		newAccount.setCreatedDate(new Date());
		em.persist(newAccount);
		// aggiunto dopo modifica alla createPartyAndSubscription
		party.getSdpAccounts().add(newAccount);
		return newAccount;
	}

	public void modifyAccount(SdpAccount account, String accountName, String accountDescription, boolean isDefaultAccount, SdpParty party, SdpSite site,
			String externalId, String accountProfile, String updatedBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		account.setAccountName(accountName);
		account.setSdpParty(party);
		account.setAccountDescription(accountDescription);
		account.setExternalId(externalId);
		account.setAccountProfile(accountProfile);
		account.setIsDefaultPartyAccount(DefaultAccount.getIsDefaultAccountValue(isDefaultAccount));
		account.setSdpPartySite(site);
		account.setUpdatedById(updatedBy);
		account.setUpdatedDate(new Date());
	}

	public void deleteAccount(SdpAccount account, String madeBy) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		account.setStatusId(ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED));
		account.setDeletedById(madeBy);
		account.setDeletedDate(new Date());
	}

	public SdpAccount searchAccountByName(String accountName, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(accountName)) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpAccount.PARAM_ACCOUNT_NAME, accountName);
		List<SdpAccount> results = NamedQueryHelper.executeNamedQuery(SdpAccount.class, SdpAccount.QUERY_RETRIEVE_BY_NAME, parameHashMap, em);
		if (results == null || results.isEmpty()) {
			return null;
		}
		return results.get(0);
	}

	public List<SdpAccount> searchAccountsByPartyId(Long partyId, Long startPosition, Long maxRecordsNumber, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (partyId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpAccount.PARAM_PARTY_ID, partyId);
		return NamedQueryHelper.executePaginatedNamedQuery(SdpAccount.class, SdpAccount.QUERY_RETRIEVE_BY_PARTYID, parameHashMap, startPosition,
				maxRecordsNumber, em);
	}

	public Long searchAccountsByPartyIdCount(Long partyId, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (partyId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpAccount.PARAM_PARTY_ID, partyId);
		return NamedQueryHelper.executeNamedQueryCount(SdpAccount.QUERY_COUNT_BY_PARTYID, parameHashMap, em);
	}

	public List<SdpAccount> searchAccountsBySiteId(Long siteId, Long startPosition, Long maxRecordsNumber, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (siteId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpAccount.PARAM_SITE_ID, siteId);
		return NamedQueryHelper.executePaginatedNamedQuery(SdpAccount.class, SdpAccount.QUERY_RETRIEVE_BY_SITEID, parameHashMap, startPosition,
				maxRecordsNumber, em);
	}

	public Long searchAccountsBySiteIdCount(Long siteId, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (siteId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpAccount.PARAM_SITE_ID, siteId);
		return NamedQueryHelper.executeNamedQueryCount(SdpAccount.QUERY_COUNT_BY_SITEID, parameHashMap, em);
	}

	public SdpAccount searchAccountById(Long accountId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (accountId == null) {
			return null;
		}
		return em.find(SdpAccount.class, accountId);
	}

	public void validateSearchAccountById(Long accountId) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("accountId", accountId);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public List<SdpAccount> searchAccountsNotDeletedByPartyId(Long partyId, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (partyId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpAccount.PARAM_PARTY_ID, partyId);
		parameHashMap.put(SdpAccount.PARAM_STATUS_ID, ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED));
		return NamedQueryHelper.executeNamedQuery(SdpAccount.class, SdpAccount.QUERY_RETRIEVE_BY_PARTYID_AND_NOT_STATUS, parameHashMap, em);
	}

	public Long countAccountsNotDeletedBySiteId(Long siteId, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (siteId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpAccount.PARAM_SITE_ID, siteId);
		parameHashMap.put(SdpAccount.PARAM_STATUS_ID, ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED));
		return NamedQueryHelper.executeNamedQueryCount(SdpAccount.QUERY_COUNT_BY_SITEID_AND_NOT_STATUS, parameHashMap, em);
	}

	public void changeStatus(SdpAccount account, Long nextstatus, String madeBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		account.setStatusId(nextstatus);
		account.setChangeStatusById(madeBy);
		account.setChangeStatusDate(new Date());
	}

	public void validateAccount(String accountName) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("accountName", accountName);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateCreateAccounts(List<SdpAccountRequestDto> accounts) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug("Starting list parameters validation");
		if (accounts != null && !accounts.isEmpty()) {
			for (SdpAccountRequestDto a : accounts) {
				validateAccount(a.getAccountName());
			}
		}
		log.logDebug("Validation list Ended");
	}

	public void validateSearchAccountByName(String accountName) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("accountName", accountName);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public SdpAccount retrieveAccount(String accountName, List<SdpAccount> accounts) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(accountName) || accounts == null) {
			return null;
		}
		for (SdpAccount account : accounts) {
			if (accountName.equalsIgnoreCase(account.getAccountName())) {
				return account;
			}
		}
		return null;
	}

	public SdpAccount searchByExternalId(String externalId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return super.searchByExternalId(SdpAccount.class, SdpAccount.QUERY_RETRIEVE_BY_EXTERNALID, externalId, em);
	}

}
