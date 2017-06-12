/*
 * To change this template, choose Tools | Variants
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
import com.accenture.sdp.csmfe.webservices.clients.servicevariant.BaseRequestPaginated;
import com.accenture.sdp.csmfe.webservices.clients.servicevariant.BaseResp;
import com.accenture.sdp.csmfe.webservices.clients.servicevariant.CCreateServiceVariant;
import com.accenture.sdp.csmfe.webservices.clients.servicevariant.CCreateServiceVariantResponse;
import com.accenture.sdp.csmfe.webservices.clients.servicevariant.CDeleteServiceVariant;
import com.accenture.sdp.csmfe.webservices.clients.servicevariant.CDeleteServiceVariantResponse;
import com.accenture.sdp.csmfe.webservices.clients.servicevariant.CModifyServiceVariant;
import com.accenture.sdp.csmfe.webservices.clients.servicevariant.CModifyServiceVariantResponse;
import com.accenture.sdp.csmfe.webservices.clients.servicevariant.CreateServiceVariantRequest;
import com.accenture.sdp.csmfe.webservices.clients.servicevariant.CreateServiceVariantResp;
import com.accenture.sdp.csmfe.webservices.clients.servicevariant.DeleteServiceVariantRequest;
import com.accenture.sdp.csmfe.webservices.clients.servicevariant.ModifyServiceVariantRequest;
import com.accenture.sdp.csmfe.webservices.clients.servicevariant.ParameterInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.servicevariant.SdpServiceVariantService;
import com.accenture.sdp.csmfe.webservices.clients.servicevariant.SdpServiceVariantService_Service;
import com.accenture.sdp.csmfe.webservices.clients.servicevariant.SearchAllServiceVariants;
import com.accenture.sdp.csmfe.webservices.clients.servicevariant.SearchAllServiceVariantsResp;
import com.accenture.sdp.csmfe.webservices.clients.servicevariant.SearchAllServiceVariantsResponse;
import com.accenture.sdp.csmfe.webservices.clients.servicevariant.SearchServiceVariantsByServiceTemplate;
import com.accenture.sdp.csmfe.webservices.clients.servicevariant.SearchServiceVariantsByServiceTemplateRequest;
import com.accenture.sdp.csmfe.webservices.clients.servicevariant.SearchServiceVariantsByServiceTemplateResp;
import com.accenture.sdp.csmfe.webservices.clients.servicevariant.SearchServiceVariantsByServiceTemplateResponse;
import com.accenture.sdp.csmfe.webservices.clients.servicevariant.ServiceVariantChangeStatus;
import com.accenture.sdp.csmfe.webservices.clients.servicevariant.ServiceVariantChangeStatusRequest;
import com.accenture.sdp.csmfe.webservices.clients.servicevariant.ServiceVariantChangeStatusResponse;
@ManagedBean(name=ApplicationConstants.SERVICEVARIANT_SERVICE_BEAN_NAME)
@ApplicationScoped
public class ServiceVariantService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient SdpServiceVariantService port;


	public Long createServiceVariant(String serviceVariantName,
			String serviceVariantDescription, String externalId,
			String serviceVariantProfile,Long serviceTemplateId, Holder<String> tenantName) throws ServiceErrorException{

		CreateServiceVariantRequest r=new CreateServiceVariantRequest();
		r.setServiceVariantName(serviceVariantName);
		r.setServiceVariantDescription(serviceVariantDescription);
		r.setExternalId(externalId);
		r.setServiceVariantProfile(serviceVariantProfile);
		r.setServiceTemplateId(serviceTemplateId);			
		CCreateServiceVariant req = new CCreateServiceVariant();
		req.setCreateServiceVariantRequest(r);
		CCreateServiceVariantResponse result = port.cCreateServiceVariant(req, tenantName);
		CreateServiceVariantResp resp = result.getCreateServiceVariantResponse();
		parseResponse(resp);
		return resp.getServiceVariantId();
	}

	public void modifyServiceVariant(Long serviceVariantId,String serviceVariantName,
			String serviceVariantDescription, String externalId,
			String serviceVariantProfile,Long serviceTemplateId, Holder<String> tenantName) throws ServiceErrorException{

		ModifyServiceVariantRequest r=new ModifyServiceVariantRequest();
		r.setServiceVariantName(serviceVariantName);

		r.setServiceVariantName(serviceVariantName);
		r.setServiceVariantDescription(serviceVariantDescription);
		r.setExternalId(externalId);
		r.setServiceVariantProfile(serviceVariantProfile);
		r.setServiceTemplateId(serviceTemplateId);	
		r.setServiceVariantId(serviceVariantId);

		CModifyServiceVariant req = new CModifyServiceVariant();
		req.setModifyServiceVariantRequest(r);
		CModifyServiceVariantResponse result = port.cModifyServiceVariant(req, tenantName);
		BaseResp resp = result.getModifyServiceVariantResponse();
		parseResponse(resp);
	}

	public void deleteServiceVariant(Long serviceVariantId, Holder<String> tenantName) throws ServiceErrorException{

		DeleteServiceVariantRequest r=new DeleteServiceVariantRequest();
		r.setServiceVariantId(serviceVariantId);
		CDeleteServiceVariant req = new CDeleteServiceVariant();
		req.setDeleteServiceVariantRequest(r);
		CDeleteServiceVariantResponse result = port.cDeleteServiceVariant(req, tenantName);
		BaseResp resp = result.getDeleteserviceVariantResponse();
		parseResponse(resp);
	}

	public SearchAllServiceVariantsResp searchAllServiceVariants(Long startPosition,Long maxRecordsNumber, Holder<String> tenantName) throws ServiceErrorException{

		BaseRequestPaginated r=new BaseRequestPaginated();
		r.setMaxRecordsNumber(maxRecordsNumber);
		r.setStartPosition(startPosition);
		SearchAllServiceVariants req = new SearchAllServiceVariants();
		req.setBaseRequestPaginated(r);
		SearchAllServiceVariantsResponse result = port.searchAllServiceVariants(req, tenantName);
		SearchAllServiceVariantsResp resp = result.getSearchServiceVariantResponse();
		parseResponse(resp);
		return resp;
	}

	public SearchServiceVariantsByServiceTemplateResp searchServiceVariantsByServiceTemplate(String serviceTemplateName, 
			Long startPosition,Long maxRecordsNumber, Holder<String> tenantName) throws ServiceErrorException{

		SearchServiceVariantsByServiceTemplateRequest r=new SearchServiceVariantsByServiceTemplateRequest();
		r.setServiceTemplateName(serviceTemplateName);
		r.setStartPosition(startPosition);
		r.setMaxRecordsNumber(maxRecordsNumber);
		SearchServiceVariantsByServiceTemplate req = new SearchServiceVariantsByServiceTemplate();
		req.setSearchServiceVariantsByServiceTemplateRequest(r);
		SearchServiceVariantsByServiceTemplateResponse result = port.searchServiceVariantsByServiceTemplate(req, tenantName);
		SearchServiceVariantsByServiceTemplateResp resp = result.getSearchServiceVariantsByServiceTemplateResponse();
		parseResponse(resp);
		return resp;
	}


	public void changeStatus(long id, String status, Holder<String> tenantName) throws ServiceErrorException{
		ServiceVariantChangeStatusRequest r = new ServiceVariantChangeStatusRequest();
		r.setServiceVariantId(id);
		r.setStatus(status);
		ServiceVariantChangeStatus req = new ServiceVariantChangeStatus();
		req.setServiceVariantChangeStatusRequest(r);
		ServiceVariantChangeStatusResponse result = port.serviceVariantChangeStatus(req, tenantName);
		BaseResp resp = result.getServiceVariantChangeStatusResponse();
		parseResponse(resp);
	}


	public ServiceVariantService() {
		URL url=null;
		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
		LoggerWrapper log = new LoggerWrapper(this.getClass());
		String urlString = propertyManager.getProperty(ApplicationConstants.ROOT_ENDPOINT_URL) + propertyManager.getProperty(ApplicationConstants.SERVICEVARIANT_WSDL_URL);
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			log.logException(e.getMessage(), e);
		}


		SdpServiceVariantService_Service service=new SdpServiceVariantService_Service(url);
		port=service.getSdpServiceVariantServicePort();

 
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
