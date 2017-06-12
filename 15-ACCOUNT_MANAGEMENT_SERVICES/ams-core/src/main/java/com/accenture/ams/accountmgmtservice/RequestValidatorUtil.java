package com.accenture.ams.accountmgmtservice;

import java.util.ArrayList;

import org.xml.sax.SAXParseException;

public class RequestValidatorUtil {

	private static RequestValidatorUtil instance = null;
	private ArrayList<SAXParseException> errorList = null;
	
	private RequestValidatorUtil(){
		errorList = new ArrayList<SAXParseException>();
	}
	public static RequestValidatorUtil getInstance(){
		if (instance == null)
			instance = new RequestValidatorUtil();
		return instance;	
	}
	
	
	public void notifyError(SAXParseException error){
		errorList.add(error);
	}
	
	public boolean isErrorPresent(){
		return (errorList==null || errorList.size()==0) ? false : true;
	}
	
	public SAXParseException getLastError(){
		if (!isErrorPresent())
			return null;
		return errorList.get(0);
	}
	
	public void clean(){
		
	}
}
