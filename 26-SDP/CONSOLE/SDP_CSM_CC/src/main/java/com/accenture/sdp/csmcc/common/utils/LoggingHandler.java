package com.accenture.sdp.csmcc.common.utils;

import java.util.Set;

import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import com.accenture.sdp.csmcc.common.LoggerWrapper;


public class LoggingHandler implements SOAPHandler<SOAPMessageContext> {
	
	

	public LoggingHandler() {
		super();
	}



	public boolean handleMessage (SOAPMessageContext c) {
		LoggerWrapper log = new LoggerWrapper(LoggingHandler.class);
		SOAPMessage msg = c.getMessage();

	   boolean request = ((Boolean) c.get(SOAPMessageContext.MESSAGE_OUTBOUND_PROPERTY)).booleanValue();

	   try {
	      if (request) {
	         // Write the message to the output stream 
	         msg.writeTo (System.out);
	      } else { 
	    	  // This is the response message 
	         msg.writeTo(System.out);
	      }
	   }
	   catch (Exception e) {
		   log.logException(e.getMessage(), e);
	   }
	   return true;
	}

	public boolean handleFault (SOAPMessageContext c) {
		LoggerWrapper log = new LoggerWrapper(LoggingHandler.class);
		SOAPMessage msg = c.getMessage();
	   try {
	      msg.writeTo(System.out);
	   }
	   catch (Exception e) {
		   log.logException(e.getMessage(), e);
	   }
	   return true;
	}

	public void close (MessageContext c) {
	}

	public Set getHeaders() {
	   // Not required for logging
	   return null;
	}
	}