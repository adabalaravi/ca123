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
import com.accenture.sdp.csmfe.webservices.clients.solutiontype.BaseResp;
import com.accenture.sdp.csmfe.webservices.clients.solutiontype.ParameterInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.solutiontype.SdpSolutionTypeService;
import com.accenture.sdp.csmfe.webservices.clients.solutiontype.SdpSolutionTypeService_Service;
import com.accenture.sdp.csmfe.webservices.clients.solutiontype.SearchAllSolutionTypes;
import com.accenture.sdp.csmfe.webservices.clients.solutiontype.SearchAllSolutionTypesResponse;
import com.accenture.sdp.csmfe.webservices.clients.solutiontype.SearchSolutionTypesResp;

@ManagedBean(name = ApplicationConstants.SOLUTIONTYPE_SERVICE_BEAN_NAME)
@ApplicationScoped
public class SolutionTypeService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient SdpSolutionTypeService port;


	

	public SearchSolutionTypesResp searchAllSolutionTypes(Holder<String> tenantName) throws ServiceErrorException{
		
		SearchAllSolutionTypes req = new SearchAllSolutionTypes();
		SearchAllSolutionTypesResponse resp=port.searchAllSolutionTypes(req, tenantName);
		parseResponse(resp.getSearchAllSolutionTypesResponse());
		return resp.getSearchAllSolutionTypesResponse();
	}


	public SolutionTypeService() {
		URL url=null;
		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
		LoggerWrapper log = new LoggerWrapper(this.getClass());
		String urlString = propertyManager.getProperty(ApplicationConstants.ROOT_ENDPOINT_URL) + propertyManager.getProperty(ApplicationConstants.SOLUTIONTYPE_WSDL_URL);
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			log.logException(e.getMessage(), e);
		}
		SdpSolutionTypeService_Service service = new SdpSolutionTypeService_Service(url);
		port = service.getSdpSolutionTypeServicePort();
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
