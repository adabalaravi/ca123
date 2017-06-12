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
import com.accenture.sdp.csmfe.webservices.clients.currency.BaseRequestPaginated;
import com.accenture.sdp.csmfe.webservices.clients.currency.BaseResp;
import com.accenture.sdp.csmfe.webservices.clients.currency.ParameterInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.currency.RefCurrencyTypeService;
import com.accenture.sdp.csmfe.webservices.clients.currency.RefCurrencyTypeService_Service;
import com.accenture.sdp.csmfe.webservices.clients.currency.SearchAllCurrencies;
import com.accenture.sdp.csmfe.webservices.clients.currency.SearchAllCurrenciesResp;
import com.accenture.sdp.csmfe.webservices.clients.currency.SearchAllCurrenciesResponse;

@ManagedBean(name = ApplicationConstants.CURRENCY_SERVICE_BEAN_NAME)
@ApplicationScoped
public class CurrencyService {

	private RefCurrencyTypeService port;
	
	public SearchAllCurrenciesResp searchAllCurrencies(Long startPosition,Long maxRecordsNumber, Holder<String> tenantName) throws ServiceErrorException{
		BaseRequestPaginated r = new BaseRequestPaginated();
		r.setMaxRecordsNumber(maxRecordsNumber);
		r.setStartPosition(startPosition);
		SearchAllCurrencies search = new SearchAllCurrencies();
		search.setSearchAllCurrenciesRequest(r);
		SearchAllCurrenciesResponse result = port.searchAllCurrencies(search, tenantName);
		SearchAllCurrenciesResp resp = result.getSearchAllCurrenciesResponse();
		parseResponse(resp);
		return resp;
	}

	
	public CurrencyService() {
		URL url=null;
		LoggerWrapper log = new LoggerWrapper(this.getClass());
		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
		String urlString = propertyManager.getProperty(ApplicationConstants.ROOT_ENDPOINT_URL) + propertyManager.getProperty(ApplicationConstants.CURRENCY_WSDL_URL);
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			log.logException(e.getMessage(), e);
		}
		RefCurrencyTypeService_Service service=new RefCurrencyTypeService_Service(url);
		port=service.getRefCurrencyTypeServicePort();
		
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
