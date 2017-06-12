package com.accenture.sdp.csm.helpers;

import java.util.HashMap;

import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.validation.ValidationResult;
import com.accenture.sdp.csm.validation.ValidationUtils;

public final class SdpAsynchronousFlowsHelper extends SdpBaseHelper {

	private static SdpAsynchronousFlowsHelper instance;

	private SdpAsynchronousFlowsHelper() {
		super();
	}

	public static SdpAsynchronousFlowsHelper getInstance() {
		if (instance == null) {
			instance = new SdpAsynchronousFlowsHelper();
		}
		return instance;
	}

	public void validateGetEndpointsInfo(Long solutionOfferId, String operationType) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("solutionOfferId", solutionOfferId);
		validationMap.put("operationType", operationType);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}
}
