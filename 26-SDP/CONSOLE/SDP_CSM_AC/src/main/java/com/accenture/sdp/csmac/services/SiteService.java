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
import com.accenture.sdp.csmfe.webservices.clients.site.BaseResp;
import com.accenture.sdp.csmfe.webservices.clients.site.ParameterInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.site.SdpSiteService;
import com.accenture.sdp.csmfe.webservices.clients.site.SdpSiteService_Service;
import com.accenture.sdp.csmfe.webservices.clients.site.SearchSitesByParty;
import com.accenture.sdp.csmfe.webservices.clients.site.SearchSitesByPartyRequest;
import com.accenture.sdp.csmfe.webservices.clients.site.SearchSitesRespPaginated;

@ManagedBean(name = ApplicationConstants.SITE_SERVICE_BEAN_NAME)
@ApplicationScoped
public class SiteService {

	private static LoggerWrapper log = new LoggerWrapper(SolutionOfferService.class);

	private SdpSiteService port;

	public SiteService() {
		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
		String urlString = propertyManager.getProperty(ApplicationConstants.ROOT_ENDPOINT_URL)
				+ propertyManager.getProperty(ApplicationConstants.SITE_WSDL_URL);
		try {
			URL url = new URL(urlString);
			SdpSiteService_Service service = new SdpSiteService_Service(url);
			port = service.getSdpSiteServicePort();
			log.logDebug("SdpSiteService instantiated. Endpoint Url: %s", url);
		} catch (MalformedURLException e) {
			log.logError(e);
		}

	}

	public SearchSitesRespPaginated searchSitesByParty(Long partyId, Long startPosition, Long maxRecordsNumber, Holder<String> tenantName)
			throws ServiceErrorException {
		// Request
		SearchSitesByPartyRequest r = new SearchSitesByPartyRequest();
		r.setParentPartyId(partyId);
		r.setMaxRecordsNumber(maxRecordsNumber);
		r.setStartPosition(startPosition);

		SearchSitesByParty searchSitesByParty = new SearchSitesByParty();
		searchSitesByParty.setSearchSitesByPartyRequest(r);

		// webmethod
		SearchSitesRespPaginated resp = port.searchSitesByParty(searchSitesByParty, tenantName).getSearchSitesByPartyResponse();
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
