package com.accenture.sdp.csm.helpers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.database.NamedQueryHelper;
import com.accenture.sdp.csm.exceptions.EncryptionException;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.model.jpa.SdpCredential;
import com.accenture.sdp.csm.model.jpa.SdpParty;
import com.accenture.sdp.csm.model.jpa.SdpSecretQuestion;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.CryptoUtils;
import com.accenture.sdp.csm.utilities.MD5Utils;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.validation.ValidationResult;
import com.accenture.sdp.csm.validation.ValidationUtils;

public final class SdpCredentialHelper extends SdpBaseHelper {

	private static SdpCredentialHelper instance;

	private SdpCredentialHelper() {
		super();
	}

	public static SdpCredentialHelper getInstance() {
		if (instance == null) {
			instance = new SdpCredentialHelper();
		}
		return instance;
	}

	public SdpCredential createCredential(SdpParty party, String username, String password, String role, String externalId, String createdBy, EntityManager em)
			throws EncryptionException, PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpCredential newCredential = new SdpCredential();
		// Model valorization
		newCredential.setSdpParty(party);
		newCredential.setUsername(username);
		newCredential.setPassword(CryptoUtils.crypt(password));
		newCredential.setStatusId(ConstantsHandler.getInstance().retrieveLongConstant(Constants.ACTIVE));
		newCredential.setExternalId(externalId);
		newCredential.setSdpRole(role);
		newCredential.setCreatedById(createdBy);
		newCredential.setCreatedDate(new Date());
		if (party != null) {
			party.getSdpCredentials().add(newCredential);
		}
		em.persist(newCredential);
		return newCredential;
	}

	public void modifyCredential(SdpCredential credential, SdpParty party, String username, String password, String role, String externalId,
			List<SdpSecretQuestion> secretQuestions, String modifyBy) throws EncryptionException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		credential.setSdpParty(party);
		credential.setUsername(username);
		credential.setPassword(CryptoUtils.crypt(password));
		credential.setExternalId(externalId);
		credential.setSdpRole(role);
		credential.setSdpSecretQuestions(secretQuestions);
		credential.setUpdatedById(modifyBy);
		credential.setUpdatedDate(new Date());
	}

	public void deleteCredential(SdpCredential credential, String deletedBy) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		credential.setStatusId(ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED));
		credential.setDeletedById(deletedBy);
		credential.setDeletedDate(new Date());
	}

	public void changeStatus(SdpCredential credential, Long nextstatus, String madeBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		credential.setStatusId(nextstatus);
		credential.setUpdatedById(madeBy);
		credential.setUpdatedDate(new Date());
	}

	public SdpCredential searchCredentialByUsername(String username, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(username)) {
			return null;
		}
		return em.find(SdpCredential.class, username);
	}

	public List<SdpCredential> searchCredentialsNotDeletedByPartyId(Long partyId, EntityManager em) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (partyId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpCredential.PARAM_PARTY_ID, partyId);
		parameHashMap.put(SdpCredential.PARAM_STATUS_ID, ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED));
		return NamedQueryHelper.executeNamedQuery(SdpCredential.class, SdpCredential.QUERY_RETRIEVE_BY_PARTYID_AND_NOT_STATUS, parameHashMap, em);
	}

	public boolean checkCredential(SdpCredential credential, String password, EntityManager em) throws EncryptionException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (credential == null || password == null) {
			return false;
		}
		return CryptoUtils.crypt(password).equals(credential.getPassword());
	}

	public String resetPassword(SdpCredential credential, EntityManager em) throws EncryptionException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		credential.setPassword(CryptoUtils.crypt(Utilities.pwdGenerator()));
		return credential.getPassword();
	}

	public void validateCheckCredential(String username, String password) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(USERNAME, username);
		validationMap.put(PASSWORD, password);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateCreateCredential(String username, String password, String roleName) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(USERNAME, username);
		validationMap.put(PASSWORD, password);
		validationMap.put(ROLE_NAME, roleName);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		if (!Utilities.checkRightUsernameFormat(username)) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, USERNAME, username);
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateSearchCredentialByUsername(String username) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(USERNAME, username);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public List<SdpCredential> searchCredentialByPartyIdPaginated(Long parentPartyId, Long startPosition, Long maxRecordsNumber, EntityManager em)
			throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (parentPartyId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpCredential.PARAM_PARTY_ID, parentPartyId);
		return NamedQueryHelper.executePaginatedNamedQuery(SdpCredential.class, SdpCredential.QUERY_RETRIEVE_BY_PARTYID, parameHashMap, startPosition,
				maxRecordsNumber, em);
	}

	public Long searchCredentialByRoleNameCount(String roleName, EntityManager em) throws ValidationException, PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (roleName == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpCredential.PARAM_ROLE_NAME, roleName);
		parameHashMap.put(SdpCredential.PARAM_STATUS_ID, ConstantsHandler.getInstance().retrieveLongConstant(Constants.DELETED));
		return NamedQueryHelper.executeNamedQueryCount(SdpCredential.QUERY_COUNT_BY_ROLENAME, parameHashMap, em);
	}

	public Long searchCredentialByPartyIdPaginatedCount(Long partyId, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (partyId == null) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpCredential.PARAM_PARTY_ID, partyId);
		return NamedQueryHelper.executeNamedQueryCount(SdpCredential.QUERY_COUNT_BY_PARTYID, parameHashMap, em);
	}

	public String retriveCorrectPassword(String dbPassword, String oldPassword, String newPassword, String confirmNewPassword) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (oldPassword == null && newPassword == null && confirmNewPassword == null) {
			return null;
		}
		log.logDebug(VALIDATION_START);

		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("oldPassword", oldPassword);
		validationMap.put("newPassword", newPassword);
		validationMap.put("confirmNewPassword", confirmNewPassword);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		if (!dbPassword.equals(oldPassword) || !newPassword.equals(confirmNewPassword)) {
			throw new ValidationException(Constants.CODE_NOT_POSSIBLE_TO_MODIFY_PASSWORD_FOR_CREDENTIAL);
		}
		return newPassword;
	}

	public SdpCredential searchByExternalId(String externalId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return super.searchByExternalId(SdpCredential.class, SdpCredential.QUERY_RETRIEVE_BY_EXTERNALID, externalId, em);
	}

	public void validateChangePassword(String dbPassword, String oldPassword, String newPassword, String confirmNewPassword) throws ValidationException,
			EncryptionException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("oldPassword", oldPassword);
		validationMap.put("newPassword", newPassword);
		validationMap.put("confirmNewPassword", confirmNewPassword);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		if (!dbPassword.equals(CryptoUtils.crypt(oldPassword)) || !newPassword.equals(confirmNewPassword)) {
			throw new ValidationException(Constants.CODE_NOT_POSSIBLE_TO_MODIFY_PASSWORD_FOR_CREDENTIAL);
		}
	}

	public List<String> createUnreservedUsernameRandom(String firstName, String lastName, String domain, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		List<String> alternatives = new ArrayList<String>();
		SdpCredentialHelper credentialHelper = SdpCredentialHelper.getInstance();
		for (int i = 1; i < (Constants.USERNAME_MAX_ALTERNATIVES + 1); i++) {
			String alternative = Utilities.usernameRuledGenerator(i, firstName, lastName, domain);
			if (alternative != null) {
				SdpCredential credential = credentialHelper.searchCredentialByUsername(alternative, em);
				if (credential == null) {
					alternatives.add(alternative);
				}
			}
		}
		return alternatives;
	}

	public boolean checkCredentialMd5(SdpCredential credential, String password, EntityManager em) throws EncryptionException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (credential == null || password == null) {
			return false;
		}
		return MD5Utils.hash(password).equals(credential.getPassword());
	}
}
