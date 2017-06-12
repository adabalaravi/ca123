package com.accenture.ams.accountmgmtservice;

public class MissingParameterException extends Exception {

	private static final long serialVersionUID = 1L;

	public MissingParameterException(String exception){
		super(exception);
	}
}
