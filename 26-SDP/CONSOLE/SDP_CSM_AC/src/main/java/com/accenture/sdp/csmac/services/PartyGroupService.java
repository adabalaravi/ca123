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
import com.accenture.sdp.csmfe.webservices.clients.partygroup.BaseRequestPaginated;
import com.accenture.sdp.csmfe.webservices.clients.partygroup.BaseResp;
import com.accenture.sdp.csmfe.webservices.clients.partygroup.ParameterInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.partygroup.SdpPartyGroupService;
import com.accenture.sdp.csmfe.webservices.clients.partygroup.SdpPartyGroupService_Service;
import com.accenture.sdp.csmfe.webservices.clients.partygroup.SearchAllPartyGroup;
import com.accenture.sdp.csmfe.webservices.clients.partygroup.SearchPartyGroupResp;

@ManagedBean(name = ApplicationConstants.PARTY_GROUP_SERVICE_BEAN)
@ApplicationScoped
public class PartyGroupService {
	private static LoggerWrapper log = new LoggerWrapper(PartyGroupService.class);
	private SdpPartyGroupService port;

	public PartyGroupService() {
		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
		String urlString = propertyManager.getProperty(ApplicationConstants.ROOT_ENDPOINT_URL)
				+ propertyManager.getProperty(ApplicationConstants.PARTY_GROUP_WSDL_URL);
		try {
			URL url = new URL(urlString);
			SdpPartyGroupService_Service service = new SdpPartyGroupService_Service(url);
			port = service.getSdpPartyGroupServicePort();
			log.logDebug("SdpPartyGroupService instantiated. Endpoint Url: %s", url);
		} catch (MalformedURLException e) {
			log.logError(e);
		}
	}

	public SearchPartyGroupResp searchAllPartyGroups(Holder<String> tenantName) throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		BaseRequestPaginated req = new BaseRequestPaginated();
		SearchAllPartyGroup wrapper = new SearchAllPartyGroup();
		wrapper.setSearchAllPartyGroupsRequest(req);
		req.setMaxRecordsNumber(0L);
		req.setStartPosition(0L);
		SearchPartyGroupResp resp = port.searchAllPartyGroup(wrapper, tenantName).getSearchAllPartyGroupsResponse();
		log.logDebug("searchAllPartyGroups " + resp.getResultCode() + ", " + resp.getDescription() + " - result:" + resp.getTotalResult());
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
