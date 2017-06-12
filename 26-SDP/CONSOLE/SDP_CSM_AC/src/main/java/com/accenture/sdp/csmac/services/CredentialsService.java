package com.accenture.sdp.csmac.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.xml.ws.Holder;

import com.accenture.sdp.csmac.common.LoggerWrapper;
import com.accenture.sdp.csmac.common.PropertyManager;
import com.accenture.sdp.csmac.common.constants.ApplicationConstants;
import com.accenture.sdp.csmac.common.exception.ServiceErrorException;
import com.accenture.sdp.csmac.common.utils.Utilities;
import com.accenture.sdp.csmfe.webservices.clients.credential.BaseResp;
import com.accenture.sdp.csmfe.webservices.clients.credential.CheckCredential;
import com.accenture.sdp.csmfe.webservices.clients.credential.CheckCredentialsRequest;
import com.accenture.sdp.csmfe.webservices.clients.credential.ParameterInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.credential.ResetPassword;
import com.accenture.sdp.csmfe.webservices.clients.credential.ResetPasswordRequest;
import com.accenture.sdp.csmfe.webservices.clients.credential.ResetPasswordResp;
import com.accenture.sdp.csmfe.webservices.clients.credential.SdpCredentialService;
import com.accenture.sdp.csmfe.webservices.clients.credential.SdpCredentialService_Service;
import com.accenture.sdp.csmfe.webservices.clients.credential.SearchCredential;
import com.accenture.sdp.csmfe.webservices.clients.credential.SearchCredentialsByParty;
import com.accenture.sdp.csmfe.webservices.clients.credential.SearchCredentialsByPartyRequest;
import com.accenture.sdp.csmfe.webservices.clients.credential.SearchCredentialsRequest;
import com.accenture.sdp.csmfe.webservices.clients.credential.SearchCredentialsResp;
import com.accenture.sdp.csmfe.webservices.clients.credential.SearchCredentialsRespPaginated;

@ManagedBean(name = ApplicationConstants.CREDENTIALS_SERVICE_BEAN)
@ApplicationScoped
public class CredentialsService {

	private static LoggerWrapper log = new LoggerWrapper(CredentialsService.class);
	private SdpCredentialService port;

	public CredentialsService() {
		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
		String urlString = propertyManager.getProperty(ApplicationConstants.ROOT_ENDPOINT_URL)
				+ propertyManager.getProperty(ApplicationConstants.CREDENTIALS_WSDL_URL);
		try {
			URL url = new URL(urlString);
			SdpCredentialService_Service service = new SdpCredentialService_Service(url);
			port = service.getSdpCredentialServicePort();
			log.logDebug("SdpCredentialService_Service instantiated");
		} catch (MalformedURLException e) {
			log.logError(e);
		}
	}

	public BaseResp checkCredentials(String username, String password, Holder<String> tenantName) throws ServiceErrorException {
		CheckCredentialsRequest credentialsRequest = new CheckCredentialsRequest();
		credentialsRequest.setUsername(username);
		credentialsRequest.setPassword(password);

		CheckCredential checkCredentials = new CheckCredential();
		checkCredentials.setCheckCredentialRequest(credentialsRequest);

		BaseResp resp = port.checkCredential(checkCredentials, tenantName).getCheckCredentialResponse();
		parseResponse(resp);
		return resp;
	}

	public SearchCredentialsRespPaginated searchCredentialsByParty(Long partyId, Long startPosition, Long maxRecordsNumber, Holder<String> tenantName)
			throws ServiceErrorException {
		SearchCredentialsByPartyRequest r = new SearchCredentialsByPartyRequest();
		r.setMaxRecordsNumber(maxRecordsNumber);
		r.setStartPosition(startPosition);
		r.setPartyId(partyId);

		SearchCredentialsByParty searchCredentialsByParty = new SearchCredentialsByParty();
		searchCredentialsByParty.setSearchCredentialsByPartyRequest(r);

		SearchCredentialsRespPaginated resp = port.searchCredentialsByParty(searchCredentialsByParty, tenantName).getSearchCredentialsByPartyResponse();
		parseResponse(resp);
		return resp;
	}

	public SearchCredentialsRespPaginated selectedbytheuser(Long partyId, Long startPosition, Long maxRecordsNumber, Holder<String> tenantName)
			throws ServiceErrorException {
		SearchCredentialsByPartyRequest r = new SearchCredentialsByPartyRequest();
		r.setMaxRecordsNumber(maxRecordsNumber);
		r.setStartPosition(startPosition);
		r.setPartyId(partyId);

		SearchCredentialsByParty searchCredentialsByParty = new SearchCredentialsByParty();
		searchCredentialsByParty.setSearchCredentialsByPartyRequest(r);

		SearchCredentialsRespPaginated resp = port.searchCredentialsByParty(searchCredentialsByParty, tenantName).getSearchCredentialsByPartyResponse();
		parseResponse(resp);
		return resp;
	}

	public ResetPasswordResp resetPassword(String username, Holder<String> tenantName) throws ServiceErrorException {
		ResetPasswordRequest r = new ResetPasswordRequest();
		r.setUsername(username);

		ResetPassword resetPassword = new ResetPassword();
		resetPassword.setResetPasswordRequest(r);

		ResetPasswordResp resp = port.resetPassword(resetPassword, tenantName).getResetPasswordResponse();
		parseResponse(resp);
		return resp;
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

	public SearchCredentialsResp searchCredential(String username, Holder<String> tenantName) throws ServiceErrorException {
		SearchCredentialsRequest r = new SearchCredentialsRequest();
		r.setUsername(username);

		SearchCredential wrapper = new SearchCredential();
		wrapper.setSearchCredentialRequest(r);

		SearchCredentialsResp resp = port.searchCredential(wrapper, tenantName).getSearchCredentialResponse();
		parseResponse(resp);
		return resp;
	}

}
