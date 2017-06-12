package com.accenture.sdp.csm.exceptions;

public class EncryptionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 */
	public EncryptionException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param ex , original exception
	 */
	public EncryptionException(String message, Throwable ex) {
		super(message, ex);
	}

}
