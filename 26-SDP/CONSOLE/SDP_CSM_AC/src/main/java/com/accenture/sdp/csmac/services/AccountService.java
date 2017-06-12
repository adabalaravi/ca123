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
import com.accenture.sdp.csmfe.webservices.clients.account.BaseResp;
import com.accenture.sdp.csmfe.webservices.clients.account.ParameterInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.account.SdpAccountService;
import com.accenture.sdp.csmfe.webservices.clients.account.SdpAccountService_Service;
import com.accenture.sdp.csmfe.webservices.clients.account.SearchAccountsByParty;
import com.accenture.sdp.csmfe.webservices.clients.account.SearchAccountsByPartyRequest;
import com.accenture.sdp.csmfe.webservices.clients.account.SearchAccountsRespPaginated;

@ManagedBean(name = ApplicationConstants.ACCOUNT_SERVICE_BEAN_NAME)
@ApplicationScoped
public class AccountService {

	private static LoggerWrapper log = new LoggerWrapper(AccountService.class);

	private SdpAccountService port;

	public AccountService() {
		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
		String urlString = propertyManager.getProperty(ApplicationConstants.ROOT_ENDPOINT_URL)
				+ propertyManager.getProperty(ApplicationConstants.ACCOUNT_WSDL_URL);
		try {
			URL url = new URL(urlString);
			SdpAccountService_Service service = new SdpAccountService_Service(url);
			port = service.getSdpAccountServicePort();
			log.logDebug("AccountService instantiated. Endpoint Url: %s", url);
		} catch (MalformedURLException e) {
			log.logError(e);
		}
	}

	public SearchAccountsRespPaginated searchBillingsByParty(Long partyId, Long startPosition, Long maxRecordsNumber, Holder<String> tenantName)
			throws ServiceErrorException {
		// Request
		SearchAccountsByPartyRequest r = new SearchAccountsByPartyRequest();
		r.setPartyId(partyId);
		r.setMaxRecordsNumber(maxRecordsNumber);
		r.setStartPosition(startPosition);

		SearchAccountsByParty wrapper = new SearchAccountsByParty();
		wrapper.setSearchAccountsByPartyRequest(r);

		// webmethod
		SearchAccountsRespPaginated resp = port.searchAccountsByParty(wrapper, tenantName).getSearchAccountsByParty();

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

}
