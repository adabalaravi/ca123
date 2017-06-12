/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.sdp.csmcc.services;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.xml.ws.Holder;

import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.accenture.sdp.csmcc.common.PropertyManager;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmfe.webservices.clients.servicetype.BaseResp;
import com.accenture.sdp.csmfe.webservices.clients.servicetype.ParameterInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.servicetype.SdpServiceTypeService;
import com.accenture.sdp.csmfe.webservices.clients.servicetype.SdpServiceTypeService_Service;
import com.accenture.sdp.csmfe.webservices.clients.servicetype.SearchAllServiceTypes;
import com.accenture.sdp.csmfe.webservices.clients.servicetype.SearchAllServiceTypesResponse;
import com.accenture.sdp.csmfe.webservices.clients.servicetype.SearchServiceTypesResp;



@ManagedBean(name = ApplicationConstants.SERVICETYPE_SERVICE_BEAN_NAME)
@ApplicationScoped
public class ServiceTypeService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient SdpServiceTypeService port;
	 

	

	public SearchServiceTypesResp searchAllServiceTypes(Holder<String> tenantName) throws ServiceErrorException{

		//Request
		SearchAllServiceTypes req = new SearchAllServiceTypes();
		//webmethod
		SearchAllServiceTypesResponse result = port.searchAllServiceTypes(req, tenantName);
		SearchServiceTypesResp resp = result.getSearchAllServiceTypesResponse();
		parseResponse(resp);
		return resp;
	}


	public ServiceTypeService() {
		URL url=null;
		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);		
		LoggerWrapper log = new LoggerWrapper(this.getClass());
		String urlString = propertyManager.getProperty(ApplicationConstants.ROOT_ENDPOINT_URL) + propertyManager.getProperty(ApplicationConstants.SERVICETYPE_WSDL_URL);
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			log.logException(e.getMessage(), e);
		}
		SdpServiceTypeService_Service service = new SdpServiceTypeService_Service(url);
		port=service.getSdpServiceTypeServicePort();
	}


	private void parseResponse(BaseResp resp) throws ServiceErrorException{
		if (!ApplicationConstants.CODE_OK.equals(resp.getResultCode())){
			ArrayList<String[]> parameters = new ArrayList<String[]>();
			if (resp.getParameters()!=null) {
				for (ParameterInfoResp param : resp.getParameters().getParameter()){
					parameters.add(new String[]{param.getName(), param.getValue()});
				}
			}
			throw new ServiceErrorException(resp.getResultCode(),
					Utilities.parseErrorParameter(resp.getResultCode(), parameters));
		}
	}

}
