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
import com.accenture.sdp.csmfe.webservices.clients.platform.BaseRequestPaginated;
import com.accenture.sdp.csmfe.webservices.clients.platform.BaseResp;
import com.accenture.sdp.csmfe.webservices.clients.platform.CCreatePlatform;
import com.accenture.sdp.csmfe.webservices.clients.platform.CCreatePlatformResponse;
import com.accenture.sdp.csmfe.webservices.clients.platform.CDeletePlatform;
import com.accenture.sdp.csmfe.webservices.clients.platform.CDeletePlatformResponse;
import com.accenture.sdp.csmfe.webservices.clients.platform.CModifyPlatform;
import com.accenture.sdp.csmfe.webservices.clients.platform.CModifyPlatformResponse;
import com.accenture.sdp.csmfe.webservices.clients.platform.CreatePlatformRequest;
import com.accenture.sdp.csmfe.webservices.clients.platform.CreatePlatformResp;
import com.accenture.sdp.csmfe.webservices.clients.platform.DeletePlatformRequest;
import com.accenture.sdp.csmfe.webservices.clients.platform.ModifyPlatformRequest;
import com.accenture.sdp.csmfe.webservices.clients.platform.ParameterInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.platform.PlatformChangeStatus;
import com.accenture.sdp.csmfe.webservices.clients.platform.PlatformChangeStatusRequest;
import com.accenture.sdp.csmfe.webservices.clients.platform.PlatformChangeStatusResponse;
import com.accenture.sdp.csmfe.webservices.clients.platform.SdpPlatformService;
import com.accenture.sdp.csmfe.webservices.clients.platform.SdpPlatformService_Service;
import com.accenture.sdp.csmfe.webservices.clients.platform.SearchAllPlatform;
import com.accenture.sdp.csmfe.webservices.clients.platform.SearchAllPlatformResponse;
import com.accenture.sdp.csmfe.webservices.clients.platform.SearchPlatformByServiceVariantRequest;
import com.accenture.sdp.csmfe.webservices.clients.platform.SearchPlatformByServiceVariantResp;
import com.accenture.sdp.csmfe.webservices.clients.platform.SearchPlatformCompleteInformationByServiceVariant;
import com.accenture.sdp.csmfe.webservices.clients.platform.SearchPlatformCompleteInformationByServiceVariantResponse;
import com.accenture.sdp.csmfe.webservices.clients.platform.SearchPlatformComplexRespPaginated;

@ManagedBean(name = ApplicationConstants.PLATFORM_SERVICE_BEAN_NAME)
@ApplicationScoped
public class PlatformService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient SdpPlatformService port;


	public Long createPlatform(String platformName,
			String platformDescription, String externalId,
			String platformProfile, Holder<String> tenantName) throws ServiceErrorException{

		CreatePlatformRequest r=new CreatePlatformRequest();
		r.setPlatformName(platformName);
		r.setPlatformDescription(platformDescription);
		r.setExternalId(externalId);
		r.setPlatformProfile(platformProfile);
		CCreatePlatform req = new CCreatePlatform();
		req.setCreatePlatformRequest(r);
		CCreatePlatformResponse result = port.cCreatePlatform(req, tenantName);
		CreatePlatformResp resp = result.getCreatePlatformResponse();
		parseResponse(resp);
		return resp.getPlatformId();
	}

	public void modifyPlatform(String platformName,
			String platformDescription, String externalId,
			String platformProfile,Long platformId, Holder<String> tenantName) throws ServiceErrorException{

		//Request
		ModifyPlatformRequest r=new ModifyPlatformRequest();
		r.setPlatformName(platformName);
		r.setPlatformDescription(platformDescription);
		r.setExternalId(externalId);
		r.setPlatformProfile(platformProfile);
		r.setPlatformId(platformId);

		//webmethod
		CModifyPlatform req = new CModifyPlatform();
		req.setModifyPlatformRequest(r);
		CModifyPlatformResponse result = port.cModifyPlatform(req, tenantName);
		BaseResp resp = result.getModifyPlatformResponse();
		parseResponse(resp);
	}

	public void deletePlatform(Long platformId, Holder<String> tenantName) throws ServiceErrorException{
		
		//Request
		DeletePlatformRequest r=new DeletePlatformRequest();
		r.setPlatformId(platformId);
		//webmethod
		CDeletePlatform req = new CDeletePlatform();
		req.setDeletePlatformRequest(r);
		CDeletePlatformResponse result = port.cDeletePlatform(req, tenantName);
		BaseResp resp = result.getDeletePlatformResponse();
		parseResponse(resp);
	}

	public SearchPlatformComplexRespPaginated searchAllPlatforms(Long startPosition,Long maxRecordsNumber, Holder<String> tenantName) throws ServiceErrorException{

		//Request
		BaseRequestPaginated r=new BaseRequestPaginated();
		r.setMaxRecordsNumber(maxRecordsNumber);
		r.setStartPosition(startPosition);

		//webmethod
		SearchAllPlatform req = new SearchAllPlatform();
		req.setSearchAllPlatformRequest(r);
		SearchAllPlatformResponse result=port.searchAllPlatform(req, tenantName);
		SearchPlatformComplexRespPaginated resp = result.getSearchAllPlatformResponse();
		parseResponse(resp);
		return resp;
	}
	
	public SearchPlatformByServiceVariantResp searchPlatformByServiceVariant(String serviceVariantName, Holder<String> tenantName) throws ServiceErrorException{

		//Request
		SearchPlatformByServiceVariantRequest r=new SearchPlatformByServiceVariantRequest();

		r.setServiceVariantName(serviceVariantName);
		//webmethod
		SearchPlatformCompleteInformationByServiceVariant req = new SearchPlatformCompleteInformationByServiceVariant();
		req.setSearchPlatformCompleteInformationByServiceVariantRequest(r);
		SearchPlatformCompleteInformationByServiceVariantResponse result = port.searchPlatformCompleteInformationByServiceVariant(req, tenantName);
		SearchPlatformByServiceVariantResp resp = result.getSearchPlatformCompleteInformationByServiceVariantResponse();
		parseResponse(resp);
		return resp;
	}
	
	
	public void changeStatus(long id, String status, Holder<String> tenantName) throws ServiceErrorException{
		PlatformChangeStatusRequest r=new PlatformChangeStatusRequest();
		r.setPlatformId(id);
		r.setStatus(status);
		PlatformChangeStatus req = new PlatformChangeStatus();
		req.setPlatformChangeStatusRequest(r);
		PlatformChangeStatusResponse result = port.platformChangeStatus(req, tenantName);
		BaseResp resp = result.getPlatformChangeStatusResponse();
		parseResponse(resp);
	}


	public PlatformService() {
		URL url=null;
		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
		LoggerWrapper log = new LoggerWrapper(this.getClass());
		String urlString = propertyManager.getProperty(ApplicationConstants.ROOT_ENDPOINT_URL) + propertyManager.getProperty(ApplicationConstants.PLATFORM_WSDL_URL);
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			log.logException(e.getMessage(), e);
		}
		SdpPlatformService_Service service=new SdpPlatformService_Service(url);
		port=service.getSdpPlatformServicePort();


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
