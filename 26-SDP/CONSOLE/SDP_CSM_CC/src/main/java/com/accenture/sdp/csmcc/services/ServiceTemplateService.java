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
import com.accenture.sdp.csmfe.webservices.clients.servicetemplate.BaseRequestPaginated;
import com.accenture.sdp.csmfe.webservices.clients.servicetemplate.BaseResp;
import com.accenture.sdp.csmfe.webservices.clients.servicetemplate.CCreateServiceTemplate;
import com.accenture.sdp.csmfe.webservices.clients.servicetemplate.CCreateServiceTemplateResponse;
import com.accenture.sdp.csmfe.webservices.clients.servicetemplate.CDeleteServiceTemplate;
import com.accenture.sdp.csmfe.webservices.clients.servicetemplate.CDeleteServiceTemplateResponse;
import com.accenture.sdp.csmfe.webservices.clients.servicetemplate.CModifyServiceTemplate;
import com.accenture.sdp.csmfe.webservices.clients.servicetemplate.CModifyServiceTemplateResponse;
import com.accenture.sdp.csmfe.webservices.clients.servicetemplate.CreateServiceTemplateRequest;
import com.accenture.sdp.csmfe.webservices.clients.servicetemplate.CreateServiceTemplateResp;
import com.accenture.sdp.csmfe.webservices.clients.servicetemplate.DeleteServiceTemplateRequest;
import com.accenture.sdp.csmfe.webservices.clients.servicetemplate.ModifyServiceTemplateRequest;
import com.accenture.sdp.csmfe.webservices.clients.servicetemplate.ParameterInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.servicetemplate.SdpServiceTemplateService;
import com.accenture.sdp.csmfe.webservices.clients.servicetemplate.SdpServiceTemplateService_Service;
import com.accenture.sdp.csmfe.webservices.clients.servicetemplate.SearchAllServiceTemplates;
import com.accenture.sdp.csmfe.webservices.clients.servicetemplate.SearchAllServiceTemplatesResp;
import com.accenture.sdp.csmfe.webservices.clients.servicetemplate.SearchAllServiceTemplatesResponse;
import com.accenture.sdp.csmfe.webservices.clients.servicetemplate.SearchServiceTemplatesByPlatform;
import com.accenture.sdp.csmfe.webservices.clients.servicetemplate.SearchServiceTemplatesByPlatformRequest;
import com.accenture.sdp.csmfe.webservices.clients.servicetemplate.SearchServiceTemplatesByPlatformResp;
import com.accenture.sdp.csmfe.webservices.clients.servicetemplate.SearchServiceTemplatesByPlatformResponse;
import com.accenture.sdp.csmfe.webservices.clients.servicetemplate.ServiceTemplateChangeStatus;
import com.accenture.sdp.csmfe.webservices.clients.servicetemplate.ServiceTemplateChangeStatusRequest;
import com.accenture.sdp.csmfe.webservices.clients.servicetemplate.ServiceTemplateChangeStatusResponse;
@ManagedBean(name=ApplicationConstants.SERVICETEMPLATE_SERVICE_BEAN_NAME)
@ApplicationScoped
public class ServiceTemplateService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 private transient SdpServiceTemplateService port;


		public Long createServiceTemplate(String serviceTemplateName,
				String serviceTemplateDescription, String externalId,
				String serviceTemplateProfile,Long platformId, Long serviceTypeId, Holder<String> tenantName) throws ServiceErrorException{
			
			CreateServiceTemplateRequest r=new CreateServiceTemplateRequest();
			r.setServiceTemplateName(serviceTemplateName);
			r.setServiceTemplateDescription(serviceTemplateDescription);
			r.setExternalId(externalId);
			r.setServiceTemplateProfile(serviceTemplateProfile);
			r.setServiceTypeId(serviceTypeId);
			r.setPlatformId(platformId);	
			CCreateServiceTemplate req = new CCreateServiceTemplate();
			req.setCreateServiceTemplateRequest(r);
			CCreateServiceTemplateResponse result = port.cCreateServiceTemplate(req, tenantName);
			CreateServiceTemplateResp resp = result.getCreateServiceTemplateResponse();
			parseResponse(resp);
			return resp.getServiceTemplateId();
		}
	
		public void modifyServiceTemplate(Long serviceTemplateId,String serviceTemplateName,
				String serviceTemplateDescription, String externalId,
				String serviceTemplateProfile,Long platformId, Long serviceTypeId, Holder<String> tenantName) throws ServiceErrorException{
			
			ModifyServiceTemplateRequest r=new ModifyServiceTemplateRequest();
			r.setServiceTemplateName(serviceTemplateName);
			
			r.setServiceTemplateDescription(serviceTemplateDescription);
			r.setExternalId(externalId);
			r.setServiceTemplateProfile(serviceTemplateProfile);
			r.setServiceTypeId(serviceTypeId);
			r.setPlatformId(platformId);
			r.setServiceTemplateId(serviceTemplateId);
			CModifyServiceTemplate req = new CModifyServiceTemplate();
			req.setModifyServiceTemplateRequest(r);
			CModifyServiceTemplateResponse result = port.cModifyServiceTemplate(req, tenantName);
			BaseResp resp = result.getModifyServiceTemplateResponse();
			parseResponse(resp);
		}
		
		public void deleteServiceTemplate(Long serviceTemplateId, Holder<String> tenantName) throws ServiceErrorException{
		
			DeleteServiceTemplateRequest r=new DeleteServiceTemplateRequest();
			r.setServiceTemplateId(serviceTemplateId);
			CDeleteServiceTemplate req = new CDeleteServiceTemplate();
			req.setDeleteServiceTemplateRequest(r);
			CDeleteServiceTemplateResponse result = port.cDeleteServiceTemplate(req, tenantName);
			BaseResp resp = result.getDeleteServiceTemplateResponse();
			parseResponse(resp);
		}

	public SearchAllServiceTemplatesResp searchAllServiceTemplates(Long startPosition,Long maxRecordsNumber, Holder<String> tenantName) throws ServiceErrorException{
		
		BaseRequestPaginated r=new BaseRequestPaginated();
		r.setMaxRecordsNumber(maxRecordsNumber);
		r.setStartPosition(startPosition);
		SearchAllServiceTemplates req = new SearchAllServiceTemplates();
		req.setSearchAllServiceTemplatesRequest(r);
		SearchAllServiceTemplatesResponse result = port.searchAllServiceTemplates(req, tenantName);
		SearchAllServiceTemplatesResp resp = result.getSearchAllServiceTemplatesResponse();
		parseResponse(resp);
		return resp;
	}

	public SearchServiceTemplatesByPlatformResp searchServiceTemplatesByPlatform(String platfornName,Long startPosition,Long maxRecordsNumber, Holder<String> tenantName) throws ServiceErrorException{
		
		SearchServiceTemplatesByPlatformRequest r=new SearchServiceTemplatesByPlatformRequest();
		r.setPlatformName(platfornName);
		r.setStartPosition(startPosition);
		r.setMaxRecordsNumber(maxRecordsNumber);
		SearchServiceTemplatesByPlatform req = new SearchServiceTemplatesByPlatform();
		req.setSearchServiceTemplateByPlatformRequest(r);
		SearchServiceTemplatesByPlatformResponse result = port.searchServiceTemplatesByPlatform(req, tenantName);
		SearchServiceTemplatesByPlatformResp resp = result.getSearchServiceTemplatesByPlatformResponse();
		parseResponse(resp);
		return resp;
	}
	
	public void changeStatus(long id, String status, Holder<String> tenantName) throws ServiceErrorException{
		ServiceTemplateChangeStatusRequest r=new ServiceTemplateChangeStatusRequest();
		r.setServiceTemplateId(id);
		r.setStatus(status);
		ServiceTemplateChangeStatus req = new ServiceTemplateChangeStatus();
		req.setServiceTemplateChangeStatusRequest(r);
		ServiceTemplateChangeStatusResponse result = port.serviceTemplateChangeStatus(req, tenantName);
		BaseResp resp = result.getServiceTemplateChangeStatusResponse();
		parseResponse(resp);
	}



	public ServiceTemplateService() {
		URL url=null;
		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
		LoggerWrapper log = new LoggerWrapper(this.getClass());
		String urlString = propertyManager.getProperty(ApplicationConstants.ROOT_ENDPOINT_URL) + propertyManager.getProperty(ApplicationConstants.SERVICETEMPLATE_WSDL_URL);
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			log.logException(e.getMessage(), e);
		}

		SdpServiceTemplateService_Service service=new SdpServiceTemplateService_Service(url);
		port=service.getSdpServiceTemplateServicePort();


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
