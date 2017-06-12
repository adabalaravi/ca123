package com.accenture.sdp.csm.helpers;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.database.NamedQueryHelper;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.model.jpa.RefServiceType;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.validation.ValidationResult;
import com.accenture.sdp.csm.validation.ValidationUtils;

public final class RefServiceTypeHelper extends SdpBaseHelper {

	private static RefServiceTypeHelper instance;

	private RefServiceTypeHelper() {
		super();
	}

	public static RefServiceTypeHelper getInstance() {
		if (instance == null) {
			instance = new RefServiceTypeHelper();
		}
		return instance;
	}

	public RefServiceType searchServiceTypeById(Long serviceTypeId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (serviceTypeId == null) {
			return null;
		}
		return em.find(RefServiceType.class, serviceTypeId);
	}

	public void validateSearchServiceTypeById(Long serviceTypeId) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("serviceTypeId", serviceTypeId);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public List<RefServiceType> searchAllServiceTypes(EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return NamedQueryHelper.executeNamedQuery(RefServiceType.class, RefServiceType.SERVICE_TYPE_RETRIEVE_ALL, null, em);
	}

}
