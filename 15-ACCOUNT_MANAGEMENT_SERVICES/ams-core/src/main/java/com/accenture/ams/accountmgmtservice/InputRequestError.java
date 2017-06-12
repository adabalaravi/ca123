package com.accenture.ams.accountmgmtservice;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.sun.xml.ws.developer.ValidationErrorHandler;

public class InputRequestError extends ValidationErrorHandler{

	
//	@Override
	public void error(SAXParseException exception) throws SAXException {
		packet.invocationProperties.put(AccountMgmtServiceConstant.ERROR, exception);
	}

//	@Override
	public void fatalError(SAXParseException exception) throws SAXException {
		packet.invocationProperties.put(AccountMgmtServiceConstant.FATAL_ERROR, exception);
	}

//	@Override
	public void warning(SAXParseException exception) throws SAXException {
		packet.invocationProperties.put(AccountMgmtServiceConstant.WARNING, exception);
	}

}
