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
import com.accenture.sdp.csmfe.webservices.clients.offer.BaseResp;
import com.accenture.sdp.csmfe.webservices.clients.offer.CSearchOffer;
import com.accenture.sdp.csmfe.webservices.clients.offer.ParameterInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.offer.SdpOfferService;
import com.accenture.sdp.csmfe.webservices.clients.offer.SdpOfferService_Service;
import com.accenture.sdp.csmfe.webservices.clients.offer.SearchOfferRequest;
import com.accenture.sdp.csmfe.webservices.clients.offer.SearchOfferResp;

@ManagedBean(name = ApplicationConstants.OFFER_SERVICE_BEAN)
@ApplicationScoped
public class OfferService {

	private static LoggerWrapper log = new LoggerWrapper(OfferService.class);

	private SdpOfferService port;

	public OfferService() {
		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
		String urlString = propertyManager.getProperty(ApplicationConstants.ROOT_ENDPOINT_URL)
				+ propertyManager.getProperty(ApplicationConstants.OFFER_WSDL_URL);
		try {
			URL url = new URL(urlString);
			SdpOfferService_Service service = new SdpOfferService_Service(url);
			port = service.getSdpOfferServicePort();
			log.logDebug("Offer Service instantiated.Endpoint Url: %s", url);
		} catch (MalformedURLException e) {
			log.logError(e);
		}
	}

	public SearchOfferResp searchOffer(Long offerId, Holder<String> tenantName) throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		CSearchOffer wrapper = new CSearchOffer();
		SearchOfferRequest req = new SearchOfferRequest();
		req.setOfferId(offerId);
		wrapper.setSearchOfferRequest(req);
		SearchOfferResp resp = port.cSearchOffer(wrapper, tenantName).getSearchOfferResponse();
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
