package com.accenture.sdp.csm.exceptions;

import com.accenture.sdp.csm.dto.responses.ParameterDto;

public class ValidationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ParameterDto[] parameters;
	private String description;

	/**
	 * @return the code
	 */
	public ParameterDto[] getParameters() {
		return parameters.clone();
	}

	/**
	 * @param parameters
	 *            the list of parameters to set
	 */
	public void setParameters(ParameterDto[] parameters) {
		if (parameters != null) {
			this.parameters = parameters.clone();
		}
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param description
	 * @param parameter
	 */
	public ValidationException(String description, ParameterDto... parameter) {
		super(description);
		this.description = description;
		this.parameters = parameter;
	}

	public ValidationException(String description, String parameterName) {
		super(description);
		this.description = description;
		this.parameters = new ParameterDto[1];
		this.parameters[0] = new ParameterDto(parameterName, null);
	}

	public ValidationException(String description, String parameterName, Object parameterValue) {
		super(description);
		this.description = description;
		this.parameters = new ParameterDto[1];
		this.parameters[0] = new ParameterDto(parameterName, parameterValue);
	}

}
