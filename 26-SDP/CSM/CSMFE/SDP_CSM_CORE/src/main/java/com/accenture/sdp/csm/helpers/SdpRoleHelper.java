/**
 * 
 */
package com.accenture.sdp.csm.helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.database.NamedQueryHelper;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.model.jpa.SdpRole;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.validation.ValidationResult;
import com.accenture.sdp.csm.validation.ValidationUtils;

/**
 * @author patrizio.pontecorvi
 * 
 */
public final class SdpRoleHelper extends SdpBaseHelper {

	private static SdpRoleHelper instance;

	private SdpRoleHelper() {
		super();
	}

	public static SdpRoleHelper getInstance() {
		if (instance == null) {
			instance = new SdpRoleHelper();
		}
		return instance;
	}

	public SdpRole createRole(String roleName, String roleDescription, String createdBy, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpRole newRole = new SdpRole();
		newRole.setRoleName(roleName);
		newRole.setRoleDescription(roleDescription);
		newRole.setCreatedById(createdBy);
		newRole.setCreatedDate(new Date());
		em.persist(newRole);
		return newRole;
	}

	public SdpRole modifyRole(SdpRole role, String roleName, String roleDescription, String updatedBy, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		role.setRoleName(roleName);
		role.setRoleDescription(roleDescription);
		role.setUpdatedById(updatedBy);
		role.setUpdatedDate(new Date());
		return role;
	}

	public void validateRole(String roleName, String roleDescription) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("roleName", roleName);
		validationMap.put("roleDescription", roleDescription);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug("End mandatory validation");
	}

	public void validateRoleByName(String roleName) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("roleName", roleName);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug("End mandatory validation");
	}

	public void validateCreateModifyRole(String roleName, String roleDescription) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("roleName", roleName);
		validationMap.put("roleDescription", roleDescription);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug("End mandatory validation");
	}

	public void deleteRole(SdpRole toDelete, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		em.remove(toDelete);
	}

	public SdpRole searchRoleByName(String roleName, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(roleName)) {
			return null;
		}
		return em.find(SdpRole.class, roleName);
	}

	public List<SdpRole> searchAllRole(Long startPosition, Long maxRecordsNumber, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return NamedQueryHelper.executePaginatedNamedQuery(SdpRole.class, "selectAllRole", null, startPosition, maxRecordsNumber, em);
	}

	public Long countAllRole(EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		return NamedQueryHelper.executeNamedQueryCount("selectAllRoleCount", parameHashMap, em);
	}

	public boolean isAdmin(SdpRole role) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (role == null) {
			return false;
		}
		return ConstantsHandler.getInstance().retrieveConstant(Constants.ROLE_NAME_ADMIN).equalsIgnoreCase(role.getRoleName());
	}

	public boolean isUser(SdpRole role) throws PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (role == null) {
			return false;
		}
		return ConstantsHandler.getInstance().retrieveConstant(Constants.ROLE_NAME_USER).equalsIgnoreCase(role.getRoleName());
	}
}
