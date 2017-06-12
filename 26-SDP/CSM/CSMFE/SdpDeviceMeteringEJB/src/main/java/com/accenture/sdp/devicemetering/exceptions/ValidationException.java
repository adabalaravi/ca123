package com.accenture.sdp.devicemetering.exceptions;


public class ValidationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String description;


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
	public ValidationException(String description) {
		super(description);
		this.description = description;
	}

	public ValidationException(String description, String parameterName) {
		super(description);
		this.description = description;
	}

	public ValidationException(String description, String parameterName, Object parameterValue) {
		super(description);
		this.description = description;
	}

}
