package com.accenture.avs.console.common.exception;

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

	public String getCode() {
		return code;
	}
	
}
