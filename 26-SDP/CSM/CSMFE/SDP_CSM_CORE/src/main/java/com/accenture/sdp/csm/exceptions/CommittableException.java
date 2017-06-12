package com.accenture.sdp.csm.exceptions;

import com.accenture.sdp.csm.dto.responses.ParameterDto;

/**
 * A validationException that requires a commit of the transaction
 *
 */
public class CommittableException extends ValidationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CommittableException(String description, ParameterDto... parameter) {
		super(description, parameter);
	}

	public CommittableException(String description, String parameterName) {
		super(description, parameterName);
	}

	public CommittableException(String description, String parameterName, Object parameterValue) {
		super(description, parameterName, parameterValue);
	}

}
