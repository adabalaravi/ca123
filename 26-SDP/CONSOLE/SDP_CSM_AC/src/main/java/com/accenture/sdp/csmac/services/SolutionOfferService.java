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
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.BaseRequestPaginated;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.BaseResp;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.CSearchSolutionOffer;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.ParameterInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SdpSolutionOfferService;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SdpSolutionOfferService_Service;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SearchAllSolutionOffers;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SearchAllSolutionOffersResponse;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SearchPartyGroupRequest;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SearchSolutionOfferAndSolutionRespPaginated;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SearchSolutionOfferComplexRespPaginated;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SearchSolutionOfferRequest;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SearchSolutionOfferResp;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SearchSolutionOffersByPartyGroup;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SearchSolutionOffersByPartyGroupResponse;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SearchSolutionOffersBySolution;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SearchSolutionOffersBySolutionRequest;
import com.accenture.sdp.csmfe.webservices.clients.solutionoffer.SearchSolutionOffersBySolutionResponse;

@ManagedBean(name = ApplicationConstants.SOLUTION_OFFER_SERVICE_BEAN)
@ApplicationScoped
public class SolutionOfferService {

	private static LoggerWrapper log = new LoggerWrapper(SolutionOfferService.class);

	private SdpSolutionOfferService port;

	public SolutionOfferService() {
		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
		String urlString = propertyManager.getProperty(ApplicationConstants.ROOT_ENDPOINT_URL)
				+ propertyManager.getProperty(ApplicationConstants.SOLUTION_OFFER_WSDL_URL);
		try {
			URL url = new URL(urlString);
			SdpSolutionOfferService_Service service = new SdpSolutionOfferService_Service(url);
			port = service.getSdpSolutionOfferServicePort();
			log.logDebug("Solution Offer Service instantiated.Endpoint Url: %s", url);
		} catch (MalformedURLException e) {
			log.logError(e);
		}
	}

	public SearchSolutionOfferComplexRespPaginated searchAllSolutionOffers(Holder<String> tenantName) throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		BaseRequestPaginated req = new BaseRequestPaginated();
		req.setMaxRecordsNumber(0L);
		req.setStartPosition(0L);
		SearchAllSolutionOffers wrapper = new SearchAllSolutionOffers();
		wrapper.setSearchAllSolutionOffersRequest(req);
		SearchAllSolutionOffersResponse result = port.searchAllSolutionOffers(wrapper, tenantName);
		SearchSolutionOfferComplexRespPaginated resp = result.getSearchAllSolutionOffersResponse();
		parseResponse(resp);
		return resp;
	}

	@Deprecated
	public SearchSolutionOfferAndSolutionRespPaginated searchSolutionOffersBySolution(String solutionName, Holder<String> tenantName) throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchSolutionOffersBySolutionRequest req = new SearchSolutionOffersBySolutionRequest();
		req.setSolutionName(solutionName);
		req.setStartPosition(0L);
		req.setMaxRecordsNumber(0L);
		SearchSolutionOffersBySolution wrapper = new SearchSolutionOffersBySolution();
		wrapper.setSearchSolutionOffersBySolutionRequest(req);
		SearchSolutionOffersBySolutionResponse result = port.searchSolutionOffersBySolution(wrapper, tenantName);
		SearchSolutionOfferAndSolutionRespPaginated resp = result.getSearchSolutionOffersBySolutionResponse();
		parseResponse(resp);
		return resp;
	}

	public SearchSolutionOfferComplexRespPaginated searchSolutionOffersByPartyGroup(Long partyGroupId, Holder<String> tenantName) throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchPartyGroupRequest req = new SearchPartyGroupRequest();
		req.setPartyGroupId(partyGroupId);
		SearchSolutionOffersByPartyGroup wrapper = new SearchSolutionOffersByPartyGroup();
		wrapper.setSearchSolutionOffersByPartyGroupRequest(req);
		SearchSolutionOffersByPartyGroupResponse result = port.searchSolutionOffersByPartyGroup(wrapper, tenantName);
		SearchSolutionOfferComplexRespPaginated resp = result.getSearchSolutionOffersByPartyGroupResponse();
		parseResponse(resp);
		return resp;
	}

	public SearchSolutionOfferResp searchSolutionOfferById(Long solutionOfferId, Holder<String> tenantName) throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchSolutionOfferRequest req = new SearchSolutionOfferRequest();
		req.setSolutionOfferId(solutionOfferId);
		CSearchSolutionOffer wrapper = new CSearchSolutionOffer();
		wrapper.setSearchCredentialsRequest(req);
		SearchSolutionOfferResp resp = port.cSearchSolutionOffer(wrapper, tenantName).getSearchSolutionOfferResponse();
		parseResponse(resp);
		return resp;
	}

	private void parseResponse(BaseResp resp) throws ServiceErrorException {
		if (!ApplicationConstants.CODE_OK.equals(resp.getResultCode())) {
			ArrayList<String[]> parameters = new ArrayList<String[]>();
			if (resp.getParameters() != null){
				for (ParameterInfoResp param : resp.getParameters().getParameter()) {
					parameters.add(new String[] { param.getName(), param.getValue() });
				}
			}
			throw new ServiceErrorException(resp.getResultCode(), Utilities.parseErrorParameter(resp.getResultCode(), parameters));
		}
	}
}
