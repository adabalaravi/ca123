package com.accenture.sdp.csm.helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.database.NamedQueryHelper;
import com.accenture.sdp.csm.dto.requests.SdpOperatorRoleRightLnkRequestDto;
import com.accenture.sdp.csm.dto.requests.SdpTenantOperatorLnkRequestDto;
import com.accenture.sdp.csm.dto.responses.ParameterDto;
import com.accenture.sdp.csm.exceptions.EncryptionException;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.model.jpa.RefTenant;
import com.accenture.sdp.csm.model.jpa.SdpOperator;
import com.accenture.sdp.csm.model.jpa.SdpOperatorRight;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.CryptoUtils;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.validation.ValidationResult;
import com.accenture.sdp.csm.validation.ValidationUtils;

public final class SdpOperatorsHelper extends SdpBaseHelper {

	private static SdpOperatorsHelper instance;

	private SdpOperatorsHelper() {
		super();
	}

	public static SdpOperatorsHelper getInstance() {
		if (instance == null) {
			instance = new SdpOperatorsHelper();
		}
		return instance;
	}

	public SdpOperator createOperator(String username, String firstName, String lastName, String password, String email, List<RefTenant> tenants,
			String createdBy, EntityManager em) throws PropertyNotFoundException, EncryptionException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpOperator operator = new SdpOperator();
		operator.setUsername(username);
		operator.setFirstName(firstName);
		operator.setLastName(lastName);
		operator.setPassword(CryptoUtils.crypt(password));
		operator.setStatusId(ConstantsHandler.getInstance().retrieveLongConstant(Constants.ACTIVE));
		operator.setEmail(email);
		operator.setCreatedById(createdBy);
		operator.setCreatedDate(new Date());
		if (tenants != null) {
			operator.setTenants(tenants);
		}
		em.persist(operator);
		return operator;
	}

	public void modifyOperator(SdpOperator operator, String firstName, String lastName, String email, Long statusId, String updatedById, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		Date now = new Date();

		operator.setFirstName(firstName);
		operator.setLastName(lastName);
		if (!operator.getStatusId().equals(statusId)) {
			operator.setChangeStatusById(updatedById);
			operator.setChangeStatusDate(now);
		}
		operator.setStatusId(statusId);
		operator.setEmail(email);
		operator.setUpdatedById(updatedById);
		operator.setUpdatedDate(now);
	}

	public void updateOperatorPassword(SdpOperator operator, String password, String updatedById, EntityManager em) throws EncryptionException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		operator.setUpdatedById(updatedById);
		operator.setUpdatedDate(new Date());
		operator.setPassword(CryptoUtils.crypt(password));
	}

	public void deleteOperator(SdpOperator operator, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		for (RefTenant tenant : operator.getTenants()) {
			tenant.getOperators().remove(operator);
		}
		em.remove(operator);
	}

	public List<RefTenant> searchAllTenants(EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return NamedQueryHelper.executeNamedQuery(RefTenant.class, "selectAllTenants", null, em);
	}

	public List<SdpOperator> searchAllOperators(Long startPosition, Long maxRecordsNumber, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return NamedQueryHelper.executePaginatedNamedQuery(SdpOperator.class, SdpOperator.QUERY_RETRIEVE_ALL, null, startPosition, maxRecordsNumber, em);
	}

	public long countAllOperators(EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		return NamedQueryHelper.executeNamedQueryCount(SdpOperator.QUERY_COUNT_ALL, parameHashMap, em);
	}

	public SdpOperator searchOperatorByUsername(String username, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(username)) {
			return null;
		}
		return em.find(SdpOperator.class, username);
	}

	public SdpOperator searchOperatorByUsernameAndTenantName(String username, String tenantName, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(username) || Utilities.isNull(tenantName)) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpOperator.PARAM_USERNAME, username);
		parameHashMap.put(SdpOperator.PARAM_TENANT_NAME, tenantName);
		List<SdpOperator> temp = NamedQueryHelper
				.executeNamedQuery(SdpOperator.class, SdpOperator.QUERY_RETRIEVE_BY_USERNAME_AND_TENANTNAME, parameHashMap, em);
		if (temp == null || temp.isEmpty()) {
			return null;
		}
		return temp.get(0);
	}

	public void validateSearchOperatorByName(String lastName) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(LAST_NAME, lastName);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public Long countOperatorByName(String lastName, String firstName, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(lastName)) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpOperator.PARAM_LAST_NAME, lastName);
		if (!Utilities.isNull(firstName)) {
			parameHashMap.put(SdpOperator.PARAM_FIRST_NAME, firstName);
			return NamedQueryHelper.executeNamedQueryCount(SdpOperator.QUERY_COUNT_BY_LASTNAME_AND_FIRSTNAME, parameHashMap, em);
		}
		return NamedQueryHelper.executeNamedQueryCount(SdpOperator.QUERY_COUNT_BY_LASTNAME, parameHashMap, em);
	}

	public List<SdpOperator> searchOperatorByName(String lastName, String firstName, Long startPosition, Long maxRecordsNumber, EntityManager em)
			throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(lastName)) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpOperator.PARAM_LAST_NAME, lastName);
		if (!Utilities.isNull(firstName)) {
			parameHashMap.put(SdpOperator.PARAM_FIRST_NAME, firstName);
			return NamedQueryHelper.executePaginatedNamedQuery(SdpOperator.class, SdpOperator.QUERY_RETRIEVE_BY_LASTNAME_AND_FIRSTNAME, parameHashMap,
					startPosition, maxRecordsNumber, em);
		}
		return NamedQueryHelper.executePaginatedNamedQuery(SdpOperator.class, SdpOperator.QUERY_RETRIEVE_BY_LASTNAME, parameHashMap, startPosition,
				maxRecordsNumber, em);
	}

	public List<SdpOperator> searchOperatorsByTenantName(String tenantName, Long startPosition, Long maxRecordsNumber, EntityManager em)
			throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(tenantName)) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(SdpOperator.PARAM_TENANT_NAME, tenantName);
		return NamedQueryHelper.executePaginatedNamedQuery(SdpOperator.class, SdpOperator.QUERY_RETRIEVE_BY_TENANTNAME, parameHashMap, startPosition,
				maxRecordsNumber, em);
	}

	public String resetOperatorPassword(SdpOperator operator, EntityManager em) throws EncryptionException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		String password = Utilities.pwdGenerator();
		operator.setPassword(CryptoUtils.crypt(password));
		return password;
	}

	public boolean checkOperatorCredential(SdpOperator operator, String password) throws EncryptionException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return (!Utilities.isNull(password) && CryptoUtils.crypt(password).equals(operator.getPassword()));
	}

	public void validateCreateOperator(String username, String firstName, String lastName, String password) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(USERNAME, username);
		validationMap.put(FIRST_NAME, firstName);
		validationMap.put(LAST_NAME, lastName);
		validationMap.put(PASSWORD, password);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateModifyOperator(String username, String firstName, String lastName) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(USERNAME, username);
		validationMap.put(FIRST_NAME, firstName);
		validationMap.put(LAST_NAME, lastName);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateSearchOperatorByUsername(String username) throws ValidationException {
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

	public boolean checkOperatorStatus(String status) throws PropertyNotFoundException, ValidationException {
		return (Constants.ACTIVE.equalsIgnoreCase(status) || Constants.INACTIVE.equalsIgnoreCase(status));
	}

	public boolean checkPasswordFormat(String password) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return password.matches(Constants.PASSWORD_FORMAT_REGEX);
	}

	public void validateLoginOperator(String username, String password) throws ValidationException {
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

	public void validateSearchTenantByName(String tenantName) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(TENANT_NAME, tenantName);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public RefTenant searchTenantByName(String tenantName, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(tenantName)) {
			return null;
		}
		return em.find(RefTenant.class, tenantName);
	}

	public void validateModifyTenantOperatorLink(List<SdpTenantOperatorLnkRequestDto> tenants) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		if (tenants == null || tenants.isEmpty()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, new ParameterDto(OPERATION, null), new ParameterDto(TENANT_NAME, null));
		}
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		for (SdpTenantOperatorLnkRequestDto dto : tenants) {
			validationMap.put(OPERATION, dto.getOperation());
			validationMap.put(TENANT_NAME, dto.getTenantName());
			ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
			validationMap.clear();
			if (!res.isValid()) {
				throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
			}
			log.logDebug("Validating Operation");
			res = ValidationUtils.validateLinkUpdateOperation(dto.getOperation());
			if (!res.isValid()) {
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, res.getParams());
			}
		}
		log.logDebug(VALIDATION_END);
	}

	public void removeTenantOperatorLink(SdpOperator operator, RefTenant tenant, String updatedBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		operator.getTenants().remove(tenant);
		tenant.getOperators().remove(operator);
		operator.setUpdatedById(updatedBy);
		operator.setUpdatedDate(new Date());
	}

	public void createTenantOperatorLink(SdpOperator operator, RefTenant tenant, String updatedBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		operator.getTenants().add(tenant);
		tenant.getOperators().add(operator);
		operator.setUpdatedById(updatedBy);
		operator.setUpdatedDate(new Date());
	}

	public void validateSearchOperatorRight(Long rightId) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("rightId", rightId);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public SdpOperatorRight searchOperatorRightById(Long rightId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (rightId == null) {
			return null;
		}
		return em.find(SdpOperatorRight.class, rightId);
	}

	public void validateModifyOperatorRoleRightsLink(List<SdpOperatorRoleRightLnkRequestDto> rights) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		if (rights == null || rights.isEmpty()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, new ParameterDto(OPERATION, null), new ParameterDto("rightId", null));
		}
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		for (SdpOperatorRoleRightLnkRequestDto dto : rights) {
			validationMap.put(OPERATION, dto.getOperation());
			validationMap.put("rightId", dto.getRightId());
			ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
			validationMap.clear();
			if (!res.isValid()) {
				throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
			}
			log.logDebug("Validating Operation");
			res = ValidationUtils.validateLinkUpdateOperation(dto.getOperation());
			if (!res.isValid()) {
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, res.getParams());
			}
		}
		log.logDebug(VALIDATION_END);
	}

	public void removeOperatorOperatorRightLink(SdpOperator operator, SdpOperatorRight right, String updatedBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		operator.getOperatorRights().remove(right);
		right.getOperators().remove(operator);
		operator.setUpdatedById(updatedBy);
		operator.setUpdatedDate(new Date());
	}

	public void createOperatorOperatorRightLink(SdpOperator operator, SdpOperatorRight right, String updatedBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		operator.getOperatorRights().add(right);
		right.getOperators().add(operator);
		operator.setUpdatedById(updatedBy);
		operator.setUpdatedDate(new Date());
	}

	public List<SdpOperatorRight> searchAllOperatorRights(EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return NamedQueryHelper.executeNamedQuery(SdpOperatorRight.class, "selectAllOperatorRight", null, em);
	}
}
