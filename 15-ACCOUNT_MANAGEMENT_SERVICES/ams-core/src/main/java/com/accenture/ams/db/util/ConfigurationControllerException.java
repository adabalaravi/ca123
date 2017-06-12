package com.accenture.ams.db.util;

public class ConfigurationControllerException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public ConfigurationControllerException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ConfigurationControllerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public ConfigurationControllerException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ConfigurationControllerException(Throwable cause) {
		super(cause);
	}
}
