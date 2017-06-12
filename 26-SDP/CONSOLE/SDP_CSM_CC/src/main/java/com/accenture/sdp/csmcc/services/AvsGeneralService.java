package com.accenture.sdp.csmcc.services;

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
import com.accenture.sdp.csmfe.webservices.clients.avs.AvsService;
import com.accenture.sdp.csmfe.webservices.clients.avs.AvsService_Service;
import com.accenture.sdp.csmfe.webservices.clients.avs.BaseRequestPaginated;
import com.accenture.sdp.csmfe.webservices.clients.avs.BaseResp;
import com.accenture.sdp.csmfe.webservices.clients.avs.DeleteOffer;
import com.accenture.sdp.csmfe.webservices.clients.avs.DeleteOfferRequest;
import com.accenture.sdp.csmfe.webservices.clients.avs.DeleteOfferResponse;
import com.accenture.sdp.csmfe.webservices.clients.avs.ParameterInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.avs.SearchAllDiscountSolutionOffers;
import com.accenture.sdp.csmfe.webservices.clients.avs.SearchAllDiscountSolutionOffersResponse;
import com.accenture.sdp.csmfe.webservices.clients.avs.SearchAvsDigitalGoodResp;
import com.accenture.sdp.csmfe.webservices.clients.avs.SearchAvsDigitalGoodsByOfferId;
import com.accenture.sdp.csmfe.webservices.clients.avs.SearchAvsDigitalGoodsByOfferIdResponse;
import com.accenture.sdp.csmfe.webservices.clients.avs.SearchDiscountSolutionOfferRespPaginated;
import com.accenture.sdp.csmfe.webservices.clients.avs.SearchOfferRequest;

@ManagedBean(name = ApplicationConstants.AVS_GENERAL_SERVICE_BEAN_NAME)
@ApplicationScoped
public class AvsGeneralService {

	private AvsService port;

	public BaseResp deleteOffer(long offerId, Holder<String> tenantName) throws ServiceErrorException {
		DeleteOfferRequest r = new DeleteOfferRequest();
		r.setOfferId(offerId);
		DeleteOffer req = new DeleteOffer();
		req.setDeleteOfferRequest(r);
		DeleteOfferResponse result = port.deleteOffer(req, tenantName);
		BaseResp resp = result.getDeleteOfferResponse();
		parseResponse(resp);
		return resp;
	}

	public BaseResp deleteSolutionOffer(long solutionOfferId, Holder<String> tenantName) throws ServiceErrorException {
		return null;
	}

	public AvsGeneralService() {
		URL url = null;

		LoggerWrapper log = new LoggerWrapper(AvsGeneralService.class);
		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
		String urlString = propertyManager.getProperty(ApplicationConstants.ROOT_ENDPOINT_URL)
				+ propertyManager.getProperty(ApplicationConstants.AVS_SERVICE_WSDL_URL);
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			log.logException(e.getMessage(), e);
		}
		AvsService_Service service = new AvsService_Service(url);
		port = service.getAvsServicePort();
	}

	public SearchDiscountSolutionOfferRespPaginated searchAllDiscountSolutionOffers(Long startPosition, Long maxRecordsNumber, Holder<String> tenantName)
			throws ServiceErrorException {

		BaseRequestPaginated r = new BaseRequestPaginated();
		r.setMaxRecordsNumber(maxRecordsNumber);
		r.setStartPosition(startPosition);
		SearchAllDiscountSolutionOffers req = new SearchAllDiscountSolutionOffers();
		req.setSearchAllDiscountSolutionOffersRequest(r);
		SearchAllDiscountSolutionOffersResponse result = port.searchAllDiscountSolutionOffers(req, tenantName);
		SearchDiscountSolutionOfferRespPaginated resp = result.getSearchAllDiscountSolutionOffersResponse();
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

	public SearchAvsDigitalGoodResp searchDigitalGoodsByOffer(long offerId, Holder<String> tenantName) throws ServiceErrorException {
		SearchOfferRequest r = new SearchOfferRequest();
		SearchAvsDigitalGoodsByOfferId wrapper = new SearchAvsDigitalGoodsByOfferId();
		wrapper.setSearchAvsDigitalGoodsByOfferIdRequest(r);
		r.setOfferId(offerId);
		SearchAvsDigitalGoodsByOfferIdResponse result = port.searchAvsDigitalGoodsByOfferId(wrapper, tenantName);
		SearchAvsDigitalGoodResp resp = result.getSearchAvsDigitalGoodsByOfferIdResponse();
		parseResponse(resp);
		return resp;
	}

}
