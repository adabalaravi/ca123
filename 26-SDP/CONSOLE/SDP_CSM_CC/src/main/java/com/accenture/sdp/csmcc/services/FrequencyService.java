/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.sdp.csmcc.services;

import java.io.Serializable;
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
import com.accenture.sdp.csmfe.webservices.clients.frequency.BaseRequestPaginated;
import com.accenture.sdp.csmfe.webservices.clients.frequency.BaseResp;
import com.accenture.sdp.csmfe.webservices.clients.frequency.CCreateFrequency;
import com.accenture.sdp.csmfe.webservices.clients.frequency.CCreateFrequencyResponse;
import com.accenture.sdp.csmfe.webservices.clients.frequency.CDeleteFrequency;
import com.accenture.sdp.csmfe.webservices.clients.frequency.CDeleteFrequencyResponse;
import com.accenture.sdp.csmfe.webservices.clients.frequency.CreateFrequencyRequest;
import com.accenture.sdp.csmfe.webservices.clients.frequency.CreateFrequencyResp;
import com.accenture.sdp.csmfe.webservices.clients.frequency.DeleteFrequencyRequest;
import com.accenture.sdp.csmfe.webservices.clients.frequency.ParameterInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.frequency.RefFrequencyTypeService;
import com.accenture.sdp.csmfe.webservices.clients.frequency.RefFrequencyTypeService_Service;
import com.accenture.sdp.csmfe.webservices.clients.frequency.SearchAllFrequencies;
import com.accenture.sdp.csmfe.webservices.clients.frequency.SearchAllFrequenciesResp;
import com.accenture.sdp.csmfe.webservices.clients.frequency.SearchAllFrequenciesResponse;
@ManagedBean(name="frequencyService")
@ApplicationScoped
public class FrequencyService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient RefFrequencyTypeService port;


	public Long createFrequency(String frequencyName, String frequencyDescription,Long frequencyDays, Holder<String> tenantName) throws ServiceErrorException {
		CreateFrequencyRequest r=new CreateFrequencyRequest();
		r.setFrequencyTypeName(frequencyName);
		r.setFrequencyTypeDescription(frequencyDescription);
		r.setFrequencyDays(frequencyDays);
		CCreateFrequency req = new CCreateFrequency();
		req.setCreateFrequencyRequest(r);
		CCreateFrequencyResponse result = port.cCreateFrequency(req, tenantName);
		CreateFrequencyResp resp = result.getCreateFrequencyResponse();
		parseResponse(resp);
		return resp.getFrequencyTypeId();
	}


	public void deleteFrequency(Long frequencyId, Holder<String> tenantName) throws ServiceErrorException {
		DeleteFrequencyRequest r=new DeleteFrequencyRequest();
		r.setFrequencyTypeId(frequencyId);
		CDeleteFrequency req = new CDeleteFrequency();
		req.setDeletePriceRequest(r);
		CDeleteFrequencyResponse result = port.cDeleteFrequency(req, tenantName);
		BaseResp resp = result.getDeleteFrequencyResponse();
		parseResponse(resp);
	}

	public SearchAllFrequenciesResp searchAllFrequencies(Long startPosition,Long maxRecordsNumber, Holder<String> tenantName) throws ServiceErrorException{
		BaseRequestPaginated r=new BaseRequestPaginated();
		r.setMaxRecordsNumber(maxRecordsNumber);
		r.setStartPosition(startPosition);
		SearchAllFrequencies req = new SearchAllFrequencies();
		req.setSearchAllFrequenciesRequest(r);
		SearchAllFrequenciesResponse result = port.searchAllFrequencies(req, tenantName);
		SearchAllFrequenciesResp resp = result.getSearchAllFrequenciesResponse();
		parseResponse(resp);
		return resp;
	}


	public FrequencyService() {
		URL url=null;
		LoggerWrapper log = new LoggerWrapper(this.getClass());
		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
		String urlString = propertyManager.getProperty(ApplicationConstants.ROOT_ENDPOINT_URL) + propertyManager.getProperty(ApplicationConstants.FREQUENCY_WSDL_URL);
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			log.logException(e.getMessage(), e);
		}
		RefFrequencyTypeService_Service service=new RefFrequencyTypeService_Service(url);
		port=service.getRefFrequencyTypeServicePort();

	}

	private void parseResponse(BaseResp resp) throws ServiceErrorException{
		if (!ApplicationConstants.CODE_OK.equals(resp.getResultCode())){
			ArrayList<String[]> parameters = new ArrayList<String[]>();
			if (resp.getParameters()!=null) {
				for (ParameterInfoResp param : resp.getParameters().getParameter()){
					parameters.add(new String[]{param.getName(), param.getValue()});
				}
			}
			throw new ServiceErrorException(resp.getResultCode(),
					Utilities.parseErrorParameter(resp.getResultCode(), parameters));
		}
		
	}

}
