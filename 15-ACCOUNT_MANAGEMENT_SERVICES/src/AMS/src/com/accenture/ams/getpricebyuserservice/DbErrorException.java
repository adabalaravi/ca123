package com.accenture.ams.getpricebyuserservice;

public class DbErrorException extends Exception {

	private static final long serialVersionUID = 1L;

	public DbErrorException(String exception){
		super(exception);
	}
}