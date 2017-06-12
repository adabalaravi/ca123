package com.accenture.sdp.csm.exceptions;

public class InitException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 */
	public InitException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param ex , original exception
	 */
	public InitException(String message, Throwable ex) {
		super(message, ex);
	}

}
