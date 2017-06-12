package com.accenture.sdp.csmcc.services;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.accenture.sdp.csmcc.beans.OperatorInfo;
import com.accenture.sdp.csmcc.beans.OperatorRoleInfo;
import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.accenture.sdp.csmcc.common.PropertyManager;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmcc.converters.OperatorConverter;
import com.accenture.sdp.csmcc.converters.OperatorRightsConverter;
import com.accenture.sdp.csmfe.webservices.clients.operators.BaseResp;
import com.accenture.sdp.csmfe.webservices.clients.operators.LoginOperatorRequest;
import com.accenture.sdp.csmfe.webservices.clients.operators.LoginOperatorResp;
import com.accenture.sdp.csmfe.webservices.clients.operators.ParameterInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.operators.SdpOperatorsService;
import com.accenture.sdp.csmfe.webservices.clients.operators.SdpOperatorsService_Service;
import com.accenture.sdp.csmfe.webservices.clients.operators.SearchOperatorComplexResp;
import com.accenture.sdp.csmfe.webservices.clients.operators.SearchOperatorRequest;
import com.accenture.sdp.csmfe.webservices.clients.operators.SearchOperatorRightResp;
import com.accenture.sdp.csmfe.webservices.clients.operators.SearchTenantResp;

@ManagedBean(name = ApplicationConstants.OPERATOR_SERVICE_BEAN)
@ApplicationScoped
public class OperatorService implements Serializable {

	private static final long serialVersionUID = 1L;
	private transient SdpOperatorsService port;

	public OperatorService() {
		LoggerWrapper log = new LoggerWrapper(OperatorService.class);
		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
		String urlString = propertyManager.getProperty(ApplicationConstants.ROOT_ENDPOINT_URL)
				+ propertyManager.getProperty(ApplicationConstants.OPERATOR_WSDL_URL);
		try {
			URL url = new URL(urlString);
			SdpOperatorsService_Service service = new SdpOperatorsService_Service(url);
			port = service.getSdpOperatorsServicePort();
			log.logDebug("SdpOperatorsService_Service instantiated");
		} catch (MalformedURLException e) {
			log.logException(e.getMessage(), e);
		}
	}

	public LoginOperatorResp loginOperator(String username, String password, String tenantName) throws ServiceErrorException {
		LoggerWrapper log = new LoggerWrapper(OperatorService.class);
		Map<String, Object> logMap = new HashMap<String, Object>();
		logMap.put("username", username);
		log.logStartFeature(logMap);
		logMap.clear();
		LoginOperatorRequest loginOperatorReq = new LoginOperatorRequest();
		log.logDebug("Searching operator for username & password: %s %s", username, password);
		loginOperatorReq.setPassword(password);
		loginOperatorReq.setUsername(username);
		loginOperatorReq.setTenantName(tenantName);
		LoginOperatorResp resp = port.loginOperator(loginOperatorReq);
		parseResponse(resp);
		return resp;
	}

	public OperatorInfo searchOperatorByUsername(String username) throws ServiceErrorException {
		SearchOperatorRequest request = new SearchOperatorRequest();
		request.setUsername(username);

		SearchOperatorComplexResp resp = port.searchOperator(request);
		parseResponse(resp);
		return OperatorConverter.convertOperatorInfoToBean(resp.getOperators().getOperator().get(0));
	}

	public SearchTenantResp selectAllTenants() throws ServiceErrorException {
		SearchTenantResp resp = port.searchAllTenants();
		parseResponse(resp);
		return resp;
	}

	// ///////// RIGHTS HANDLING ////////////////
	public SearchOperatorRightResp searchAllRights() throws ServiceErrorException {
		SearchOperatorRightResp resp = port.searchAllOperatorRights();
		parseResponse(resp);
		return resp;
	}

	public OperatorRoleInfo searchOperatorRights(String username) throws ServiceErrorException {
		SearchOperatorRequest request = new SearchOperatorRequest();
		request.setUsername(username);

		SearchOperatorRightResp resp = port.searchOperatorRights(request);
		parseResponse(resp);
		return OperatorRightsConverter.convertRights(resp);
	}

	private void parseResponse(BaseResp resp) throws ServiceErrorException {
		if (!ApplicationConstants.CODE_OK.equals(resp.getResultCode())) {
			ArrayList<String[]> parameters = new ArrayList<String[]>();
			if (resp.getParameters() != null) {
				for (ParameterInfoResp param : resp.getParameters().getParameter()) {
					parameters.add(new String[] { param.getName(), param.getValue() });
				}
			}
			throw new ServiceErrorException(resp.getResultCode(), Utilities.parseErrorParameter(resp.getResultCode(), parameters));
		}
	}

}