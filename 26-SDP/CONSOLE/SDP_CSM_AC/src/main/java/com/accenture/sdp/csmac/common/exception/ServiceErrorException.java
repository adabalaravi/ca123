package com.accenture.sdp.csmac.common.exception;

public class ServiceErrorException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String code;
	
	public ServiceErrorException(String code, String message){
		super(message);
		this.code = code;
	}

	public ServiceErrorException(String message) {
		super(message);
	}

	public ServiceErrorException(Throwable arg0) {
		super("GENERIC ERROR \n"+arg0.getMessage(), arg0);
	}

	public String getCode() {
		return code;
	}
	
}
