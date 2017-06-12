/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.sdp.csmcc.services;

import java.io.Serializable;
import java.math.BigDecimal;
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
import com.accenture.sdp.csmfe.webservices.clients.price.BaseRequestPaginated;
import com.accenture.sdp.csmfe.webservices.clients.price.BaseResp;
import com.accenture.sdp.csmfe.webservices.clients.price.CCreatePrice;
import com.accenture.sdp.csmfe.webservices.clients.price.CCreatePriceResponse;
import com.accenture.sdp.csmfe.webservices.clients.price.CDeletePrice;
import com.accenture.sdp.csmfe.webservices.clients.price.CDeletePriceResponse;
import com.accenture.sdp.csmfe.webservices.clients.price.CreatePriceRequest;
import com.accenture.sdp.csmfe.webservices.clients.price.CreatePriceResp;
import com.accenture.sdp.csmfe.webservices.clients.price.DeletePriceRequest;
import com.accenture.sdp.csmfe.webservices.clients.price.ParameterInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.price.SdpPriceCatalogService;
import com.accenture.sdp.csmfe.webservices.clients.price.SdpPriceCatalogService_Service;
import com.accenture.sdp.csmfe.webservices.clients.price.SearchAllPricesResp;
import com.accenture.sdp.csmfe.webservices.clients.price.SearchAllprices;
import com.accenture.sdp.csmfe.webservices.clients.price.SearchAllpricesResponse;

@ManagedBean(name = ApplicationConstants.PRICE_SERVICE_BEAN_NAME)
@ApplicationScoped
public class PriceService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient SdpPriceCatalogService port;


	public Long createPrice(BigDecimal price, Holder<String> tenantName) throws ServiceErrorException {
		CreatePriceRequest r=new CreatePriceRequest();
		r.setPrice(price);
		CCreatePrice req = new CCreatePrice();
		req.setCreatePriceRequest(r);
		CCreatePriceResponse result = port.cCreatePrice(req, tenantName);
		CreatePriceResp resp = result.getCreatePriceResponse();
		parseResponse(resp);
		return resp.getPriceCatalogId();
	}


	public void deletePrice(Long priceId, Holder<String> tenantName) throws ServiceErrorException{
		DeletePriceRequest r=new DeletePriceRequest();
		r.setPriceCatalogId(priceId);
		CDeletePrice req = new CDeletePrice();
		req.setDeletePriceRequest(r);
		CDeletePriceResponse result = port.cDeletePrice(req, tenantName);
		BaseResp resp = result.getDeletePriceResponse();
		parseResponse(resp);
	}

	public SearchAllPricesResp searchAllPrice(Long startPosition, Long maxRecordsNumber, Holder<String> tenantName) throws ServiceErrorException{
		BaseRequestPaginated r=new BaseRequestPaginated();
		r.setMaxRecordsNumber(maxRecordsNumber);
		r.setStartPosition(startPosition);
		SearchAllprices req = new SearchAllprices();
		req.setSearchAllPricesRequest(r);
		SearchAllpricesResponse result = port.searchAllprices(req, tenantName);
		SearchAllPricesResp resp = result.getSearchAllPricesResponse();
		parseResponse(resp);
		return resp;
	}


	public PriceService() {
		URL url=null;
		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
		LoggerWrapper log = new LoggerWrapper(this.getClass());
		String urlString = propertyManager.getProperty(ApplicationConstants.ROOT_ENDPOINT_URL) + propertyManager.getProperty(ApplicationConstants.PRICE_WSDL_URL);
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			log.logException(e.getMessage(), e);
		}

		SdpPriceCatalogService_Service service=new SdpPriceCatalogService_Service(url);
		port=service.getSdpPriceCatalogServicePort();
	}


	private void parseResponse(BaseResp resp) throws ServiceErrorException{
		if (!ApplicationConstants.CODE_OK.equals(resp.getResultCode())){
			ArrayList<String[]> parameters = new ArrayList<String[]>();
			if (resp.getParameters()!=null){
				for (ParameterInfoResp param : resp.getParameters().getParameter()){
					parameters.add(new String[]{param.getName(), param.getValue()});
				}
			}
			throw new ServiceErrorException(resp.getResultCode(),
					Utilities.parseErrorParameter(resp.getResultCode(), parameters));
		}
	}
}
