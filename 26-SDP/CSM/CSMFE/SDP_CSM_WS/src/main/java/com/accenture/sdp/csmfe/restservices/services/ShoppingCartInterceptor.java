package com.accenture.sdp.csmfe.restservices.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.logging.Logger;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;

@Provider
@ServerInterceptor
public class ShoppingCartInterceptor implements PreProcessInterceptor {

	Logger logger = Logger.getLogger(ShoppingCartInterceptor.class);

	private static int counter =0;
	@Context
	HttpServletRequest servletRequest;

	public ServerResponse preProcess(HttpRequest request, ResourceMethod resourceMethod) throws Failure, WebApplicationException {
		logger.info("*********************** " + (counter++) + "********************************************");
		
		logger.info("Receiving request from: " + request);
		logger.info("Receiving request from: " + servletRequest.getRemoteAddr());
		logger.info("getHttpMethod : " + request.getHttpMethod() + "");
		logger.info("URI 1: " + " \"" + servletRequest.getRequestURI() + "\"");
		logger.info("URI 2: " + " \"" + servletRequest.getRequestURL() + "\"");
		logger.info("Attempt to invoke path : " + " \"" + request.getPreprocessedPath() + "\"");
		logger.info("getMediaType : " + request.getHttpHeaders().getMediaType());
		for (MediaType media : request.getHttpHeaders().getAcceptableMediaTypes()){
			logger.info("getAcceptableMediaType: " + media.getType());
		}
		
		for (Map.Entry<String,List<String>> entry : request.getHttpHeaders().getRequestHeaders().entrySet()){
			logger.info("Header Entry: " + entry.getKey());
			String values = "";
			for (String value:  entry.getValue()){
				values += value + ", ";

			}
			logger.info("Header Entry Value: " + values);	
		}
		


//		try {
//			printBody(request);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return null;
	}

	private void printBody(HttpRequest request) throws IOException{
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(
						inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (IOException ex) {
			throw ex;
		} finally {
//			if (bufferedReader != null) {
//				try {
//					bufferedReader.close();
//				} catch (IOException ex) {
//					throw ex;
//				}
//			}
		}
		String body = stringBuilder.toString();
		System.out.println(body);
	}
}
