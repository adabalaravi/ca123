package com.accenture.ams.accountmgmtservice;

import java.util.ArrayList;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class AmsErrorHandler implements ErrorHandler {
	
	private ArrayList<SAXParseException> error = null;
	private ArrayList<SAXParseException> fatal = null;
	private ArrayList<SAXParseException> warning = null;
	
	public void error(SAXParseException exception) throws SAXException {
		manageError(exception, error);
	}

	public void fatalError(SAXParseException exception) throws SAXException {
		manageError(exception, fatal);
	}

	public void warning(SAXParseException exception) throws SAXException {
		manageError(exception, warning);
	}
	
	private void manageError(SAXParseException exception, ArrayList<SAXParseException> repository){
		RequestValidatorUtil validator = RequestValidatorUtil.getInstance();
		if (repository==null)
			repository = new ArrayList<SAXParseException>();
		repository.add(exception);
		validator.notifyError(exception);
	}
	public ArrayList<SAXParseException> getErrorList(){
		return error;
	}
	
	public ArrayList<SAXParseException> getFatalErrorList(){
		return fatal;
	}
	
	public ArrayList<SAXParseException> getWarningList(){
		return warning;
	}	
	
	public int getErrorNumber(){
		if ( error==null )
			return 0;
		return error.size();
	}
	
	public int getFatalErrorNumber(){
		if (fatal==null)
			return 0;
		return fatal.size();
	}

	public int getWarningNumber(){
		if (warning==null)
			return 0;
		return warning.size();
	}
	
	public void cleanAll(){
		error=null;
		fatal=null;
		warning=null;
	}
}
