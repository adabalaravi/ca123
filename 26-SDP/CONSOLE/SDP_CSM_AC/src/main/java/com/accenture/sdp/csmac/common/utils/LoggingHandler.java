package com.accenture.sdp.csmac.common.utils;

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import com.accenture.sdp.csmac.common.LoggerWrapper;

public class LoggingHandler implements SOAPHandler<SOAPMessageContext> {

	private static transient LoggerWrapper log = new LoggerWrapper(LoggingHandler.class);

	public boolean handleMessage(SOAPMessageContext c) {
		SOAPMessage msg = c.getMessage();

		boolean request = ((Boolean) c.get(SOAPMessageContext.MESSAGE_OUTBOUND_PROPERTY)).booleanValue();

		try {
			if (request) {
				// This is a request message.
				// Write the message to the output stream
				msg.writeTo(System.out);
			} else {
				// This is the response message
				msg.writeTo(System.out);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		return true;
	}

	public boolean handleFault(SOAPMessageContext c) {
		SOAPMessage msg = c.getMessage();
		try {
			msg.writeTo(System.out);
		} catch (Exception e) {
			log.logError(e);
		}
		return true;
	}

	public void close(MessageContext c) {
	}

	public Set<QName> getHeaders() {
		// Not required for logging
		return null;
	}
}