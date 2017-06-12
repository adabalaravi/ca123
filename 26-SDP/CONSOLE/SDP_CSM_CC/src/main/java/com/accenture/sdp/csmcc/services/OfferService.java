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
import com.accenture.sdp.csmfe.webservices.clients.offer.BaseRequestPaginated;
import com.accenture.sdp.csmfe.webservices.clients.offer.BaseResp;
import com.accenture.sdp.csmfe.webservices.clients.offer.CCreateOffer;
import com.accenture.sdp.csmfe.webservices.clients.offer.CCreateOfferResponse;
import com.accenture.sdp.csmfe.webservices.clients.offer.CModifyOffer;
import com.accenture.sdp.csmfe.webservices.clients.offer.CModifyOfferResponse;
import com.accenture.sdp.csmfe.webservices.clients.offer.CSearchOffer;
import com.accenture.sdp.csmfe.webservices.clients.offer.CSearchOfferResponse;
import com.accenture.sdp.csmfe.webservices.clients.offer.CreateOfferRequest;
import com.accenture.sdp.csmfe.webservices.clients.offer.CreateOfferResp;
import com.accenture.sdp.csmfe.webservices.clients.offer.DeleteOfferAndPackages;
import com.accenture.sdp.csmfe.webservices.clients.offer.DeleteOfferAndPackagesResponse;
import com.accenture.sdp.csmfe.webservices.clients.offer.DeleteOfferRequest;
import com.accenture.sdp.csmfe.webservices.clients.offer.IsOfferSubscribed;
import com.accenture.sdp.csmfe.webservices.clients.offer.IsOfferSubscribedResponse;
import com.accenture.sdp.csmfe.webservices.clients.offer.ModifyOfferRequest;
import com.accenture.sdp.csmfe.webservices.clients.offer.OfferAndPackagesChangeStatus;
import com.accenture.sdp.csmfe.webservices.clients.offer.OfferAndPackagesChangeStatusResponse;
import com.accenture.sdp.csmfe.webservices.clients.offer.OfferChangeStatusRequest;
import com.accenture.sdp.csmfe.webservices.clients.offer.ParameterInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.offer.SdpOfferService;
import com.accenture.sdp.csmfe.webservices.clients.offer.SdpOfferService_Service;
import com.accenture.sdp.csmfe.webservices.clients.offer.SearchAllOffers;
import com.accenture.sdp.csmfe.webservices.clients.offer.SearchAllOffersResponse;
import com.accenture.sdp.csmfe.webservices.clients.offer.SearchOfferComplexRespPaginated;
import com.accenture.sdp.csmfe.webservices.clients.offer.SearchOfferRequest;
import com.accenture.sdp.csmfe.webservices.clients.offer.SearchOfferResp;

@ManagedBean(name=ApplicationConstants.OFFER_SERVICE_BEAN_NAME)
@ApplicationScoped
public class OfferService {

	private SdpOfferService port;


	public Long createOffer(String offerName,
			String offerDescription, String externalId,String offerProfile,
			Long serviceVariantId, Holder<String> tenantName) throws ServiceErrorException{

		CreateOfferRequest r = new  CreateOfferRequest();
		r.setOfferName(offerName);
		r.setOfferDescription(offerDescription);
		r.setExternalId(externalId);
		r.setOfferProfile(offerProfile);
		r.setServiceVariantId(serviceVariantId);
		CCreateOffer req = new CCreateOffer();
		req.setCreateOfferRequest(r);
		CCreateOfferResponse result = port.cCreateOffer(req, tenantName);
		CreateOfferResp resp = result.getCreateOfferResponse();
		parseResponse(resp);
		return resp.getOfferId();
	}

	public void modifyOffer(String offerName,
			String offerDescription, String externalId,String offerProfile,
			Long serviceVariantId,Long offerId, Holder<String> tenantName) throws ServiceErrorException{
		ModifyOfferRequest r= new ModifyOfferRequest();
		r.setOfferName(offerName);
		r.setOfferDescription(offerDescription);
		r.setExternalId(externalId);
		r.setOfferProfile(offerProfile);
		r.setServiceVariantId(serviceVariantId);
		r.setOfferId(offerId);
		CModifyOffer req = new CModifyOffer();
		req.setModifyOfferRequest(r);
		CModifyOfferResponse result = port.cModifyOffer(req, tenantName);
		BaseResp resp = result.getModifyOfferResponse();
		parseResponse(resp);
	}

	public void deleteOfferAndPackages(Long offerId, Holder<String> tenantName) throws ServiceErrorException{
		DeleteOfferRequest r=new DeleteOfferRequest();
		r.setOfferId(offerId);
		DeleteOfferAndPackages req = new DeleteOfferAndPackages();
		req.setDeleteOfferRequest(r);
		DeleteOfferAndPackagesResponse result = port.deleteOfferAndPackages(req, tenantName);
		BaseResp resp = result.getDeleteOfferResponse();
		parseResponse(resp);
	}

	public  SearchOfferComplexRespPaginated searchAllOfferPaginated(Long startPosition,Long maxRecordsNumber, Holder<String> tenantName) throws ServiceErrorException{
		BaseRequestPaginated r=new BaseRequestPaginated();
		r.setMaxRecordsNumber(maxRecordsNumber);
		r.setStartPosition(startPosition);
		SearchAllOffers req = new SearchAllOffers();
		req.setSearchOffersByRequest(r);
		SearchAllOffersResponse result = port.searchAllOffers(req, tenantName);
		SearchOfferComplexRespPaginated resp = result.getSearchAllOffersResponse();
		parseResponse(resp);
		return resp;
	}
	
	public  SearchOfferResp searchOfferById(Long offerId, Holder<String> tenantName) throws ServiceErrorException{
		SearchOfferRequest r = new SearchOfferRequest();
		r.setOfferId(offerId);
		CSearchOffer req = new CSearchOffer();
		req.setSearchOfferRequest(r);
		CSearchOfferResponse  result = port.cSearchOffer(req, tenantName);
		SearchOfferResp resp = result.getSearchOfferResponse();
		parseResponse(resp);
		return resp;
	}

	public void offerAndPackagesChangeStatus(Long id, String status, Holder<String> tenantName) throws ServiceErrorException{
		OfferChangeStatusRequest r=new OfferChangeStatusRequest();
		r.setOfferId(id);
		r.setStatus(status);
		OfferAndPackagesChangeStatus req = new OfferAndPackagesChangeStatus();
		req.setOfferAndPackagesChangeStatusRequest(r);
		OfferAndPackagesChangeStatusResponse result = port.offerAndPackagesChangeStatus(req, tenantName);
		BaseResp resp = result.getOfferAndPackagesChangeStatusResponse();
		parseResponse(resp);
	}
	
	public BaseResp isOfferSubscribed(Long offerId, Holder<String> tenantName) {
		SearchOfferRequest r = new SearchOfferRequest();
		r.setOfferId(offerId);
		IsOfferSubscribed req = new IsOfferSubscribed();
		req.setIsOfferSubscribedRequest(r);
		IsOfferSubscribedResponse result = port.isOfferSubscribed(req, tenantName);
		return result.getIsOfferSubscribedResponse();
	}
	
	

	public OfferService() {
		URL url=null;
		LoggerWrapper log = new LoggerWrapper(this.getClass());
		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
		String urlString = propertyManager.getProperty(ApplicationConstants.ROOT_ENDPOINT_URL) + propertyManager.getProperty(ApplicationConstants.OFFER_WSDL_URL);
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			log.logException(e.getMessage(), e);
		}
		SdpOfferService_Service service=new SdpOfferService_Service(url);
		port=service.getSdpOfferServicePort();


	}


	private void parseResponse(BaseResp resp) throws ServiceErrorException{
		if (!ApplicationConstants.CODE_OK.equals(resp.getResultCode())){
			ArrayList<String[]> parameters = new ArrayList<String[]>();
			if (resp.getParameters()!=null) {
				for (ParameterInfoResp param : resp.getParameters().getParameter()){
					parameters.add(new String[]{param.getName(), param.getValue()});
				}
			}
			throw new ServiceErrorException(resp.getResultCode(), Utilities.parseErrorParameter(resp.getResultCode(), parameters));
		}

	}




}
