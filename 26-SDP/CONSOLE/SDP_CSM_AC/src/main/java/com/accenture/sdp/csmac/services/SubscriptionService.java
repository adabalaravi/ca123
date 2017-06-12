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
import com.accenture.sdp.csmfe.webservices.clients.subscription.BaseResp;
import com.accenture.sdp.csmfe.webservices.clients.subscription.ParameterInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.subscription.SdpSubscriptionService;
import com.accenture.sdp.csmfe.webservices.clients.subscription.SdpSubscriptionService_Service;
import com.accenture.sdp.csmfe.webservices.clients.subscription.SearchSubscription;
import com.accenture.sdp.csmfe.webservices.clients.subscription.SearchSubscriptionByPartyResp;
import com.accenture.sdp.csmfe.webservices.clients.subscription.SearchSubscriptionComplexResp;
import com.accenture.sdp.csmfe.webservices.clients.subscription.SearchSubscriptionComplexRespPaginated;
import com.accenture.sdp.csmfe.webservices.clients.subscription.SearchSubscriptionRequest;
import com.accenture.sdp.csmfe.webservices.clients.subscription.SearchSubscriptionsByParentSubscriptionLight;
import com.accenture.sdp.csmfe.webservices.clients.subscription.SearchSubscriptionsByParentSubscriptionRequest;
import com.accenture.sdp.csmfe.webservices.clients.subscription.SearchSubscriptionsByPartyLight;
import com.accenture.sdp.csmfe.webservices.clients.subscription.SearchSubscriptionsByPartyRequest;

@ManagedBean(name = ApplicationConstants.SUBSCRIPTION_SERVICE_BEAN)
@ApplicationScoped
public class SubscriptionService {
	private static LoggerWrapper log = new LoggerWrapper(SubscriptionService.class);
	private SdpSubscriptionService port;

	public SubscriptionService() {
		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
		String urlString = propertyManager.getProperty(ApplicationConstants.ROOT_ENDPOINT_URL)
				+ propertyManager.getProperty(ApplicationConstants.SUBSCRIPTION_WSDL_URL);
		try {
			URL url = new URL(urlString);
			SdpSubscriptionService_Service service = new SdpSubscriptionService_Service(url);
			port = service.getSdpSubscriptionServicePort();
			log.logDebug("SdpSubscriptionService instantiated. Endpoint Url: %s", url);
		} catch (MalformedURLException e) {
			log.logError(e);
		}
	}

	public SearchSubscriptionByPartyResp searchSubscriptionsByParty(long partyId, long startRow, long numElementToFind, Holder<String> tenantName)
			throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchSubscriptionsByPartyRequest req = new SearchSubscriptionsByPartyRequest();
		req.setPartyId(partyId);
		req.setStartPosition(startRow);
		req.setMaxRecordsNumber(numElementToFind);

		SearchSubscriptionsByPartyLight searchSubscriptionsByPartyLight = new SearchSubscriptionsByPartyLight();
		searchSubscriptionsByPartyLight.setSearchSubscriptionByPartyLightRequest(req);

		SearchSubscriptionByPartyResp resp = port.searchSubscriptionsByPartyLight(searchSubscriptionsByPartyLight, tenantName)
				.getSearchSubscriptionByPartyLightResponse();
		parseResponse(resp);
		log.logDebug("%s result: %s . Total results: %s ", Utilities.getCurrentClassAndMethod(), resp.getResultCode(), resp.getTotalResult());
		return resp;
	}

	public SearchSubscriptionComplexRespPaginated searchSubscriptionsByParentSubscription(long parentSubscriptionId, long startRow, long numElementToFind,
			Holder<String> tenantName) throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchSubscriptionsByParentSubscriptionRequest req = new SearchSubscriptionsByParentSubscriptionRequest();
		req.setParentSubscriptionId(parentSubscriptionId);
		req.setStartPosition(startRow);
		req.setMaxRecordsNumber(numElementToFind);

		SearchSubscriptionsByParentSubscriptionLight searchSubscriptionsByParentSubscriptionLight = new SearchSubscriptionsByParentSubscriptionLight();

		searchSubscriptionsByParentSubscriptionLight.setSearchSubscriptionsByParentSubscriptionLightRequest(req);
		SearchSubscriptionComplexRespPaginated resp = port.searchSubscriptionsByParentSubscriptionLight(searchSubscriptionsByParentSubscriptionLight,
				tenantName).getSearchSubscriptionsByParentSubscriptionLightResponse();
		parseResponse(resp);
		log.logDebug("%s result: %s . Total results: %s ", Utilities.getCurrentClassAndMethod(), resp.getResultCode(), resp.getTotalResult());
		return resp;
	}

	public SearchSubscriptionComplexResp searchSubscription(long subscriptionId, Holder<String> tenantName) throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchSubscriptionRequest req = new SearchSubscriptionRequest();
		req.setSubscriptionId(subscriptionId);

		SearchSubscription wrapper = new SearchSubscription();
		wrapper.setSearchSubscriptionRequest(req);

		SearchSubscriptionComplexResp resp = port.searchSubscription(wrapper, tenantName).getSearchSubscriptionResponse();
		parseResponse(resp);
		log.logDebug("%s result: %s", Utilities.getCurrentClassAndMethod(), resp.getResultCode());
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
