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
import com.accenture.sdp.csmfe.webservices.clients.servicevariantoperations.BaseResp;
import com.accenture.sdp.csmfe.webservices.clients.servicevariantoperations.CCreateSdpServiceVariantOperation;
import com.accenture.sdp.csmfe.webservices.clients.servicevariantoperations.CCreateSdpServiceVariantOperationResponse;
import com.accenture.sdp.csmfe.webservices.clients.servicevariantoperations.CDeleteSdpServiceVariantOperation;
import com.accenture.sdp.csmfe.webservices.clients.servicevariantoperations.CDeleteSdpServiceVariantOperationResponse;
import com.accenture.sdp.csmfe.webservices.clients.servicevariantoperations.CModifySdpServiceVariantOperation;
import com.accenture.sdp.csmfe.webservices.clients.servicevariantoperations.CModifySdpServiceVariantOperationResponse;
import com.accenture.sdp.csmfe.webservices.clients.servicevariantoperations.CreateSdpServiceVariantOperationRequest;
import com.accenture.sdp.csmfe.webservices.clients.servicevariantoperations.DeleteSdpServiceVariantOperationRequest;
import com.accenture.sdp.csmfe.webservices.clients.servicevariantoperations.ModifySdpServiceVariantOperationRequest;
import com.accenture.sdp.csmfe.webservices.clients.servicevariantoperations.ParameterInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.servicevariantoperations.SdpServiceVariantOperationsService;
import com.accenture.sdp.csmfe.webservices.clients.servicevariantoperations.SdpServiceVariantOperationsService_Service;
import com.accenture.sdp.csmfe.webservices.clients.servicevariantoperations.SearchSdpServiceVariantOperationByServiceVariantRequest;
import com.accenture.sdp.csmfe.webservices.clients.servicevariantoperations.SearchSdpServiceVariantOperationResp;
import com.accenture.sdp.csmfe.webservices.clients.servicevariantoperations.SearchSdpServiceVariantOperationsByServiceVariantRequest;
import com.accenture.sdp.csmfe.webservices.clients.servicevariantoperations.SearchSdpServiceVariantOperationsByServiceVariantRequestResponse;


@ManagedBean(name = ApplicationConstants.OPERATION_SERVICE_BEAN_NAME)
@ApplicationScoped
public class OperationService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient SdpServiceVariantOperationsService port;

	

	public OperationService() {
		URL url=null;
		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
		LoggerWrapper log = new LoggerWrapper(this.getClass());
		String urlString = propertyManager.getProperty(ApplicationConstants.ROOT_ENDPOINT_URL) + propertyManager.getProperty(ApplicationConstants.SERVICE_VARIANT_OPERATIONS_WSDL_URL);
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			log.logException(e.getMessage(), e);
		}
		SdpServiceVariantOperationsService_Service service=new SdpServiceVariantOperationsService_Service(url);
		port=service.getSdpServiceVariantOperationsServicePort();
	}
	
	public SearchSdpServiceVariantOperationResp searchOperationsByServiceVariantId(Long serviceVariantId, Holder<String> tenantName) throws ServiceErrorException{
		SearchSdpServiceVariantOperationByServiceVariantRequest r=new SearchSdpServiceVariantOperationByServiceVariantRequest();
		r.setServiceVariantId(serviceVariantId);
		SearchSdpServiceVariantOperationsByServiceVariantRequest req = new SearchSdpServiceVariantOperationsByServiceVariantRequest();
		req.setSearchSdpServiceVariantOperationByServiceVariantRequest(r);
		SearchSdpServiceVariantOperationsByServiceVariantRequestResponse result = port.searchSdpServiceVariantOperationsByServiceVariantRequest(req, tenantName);
		SearchSdpServiceVariantOperationResp resp = result.getSearchSdpServiceVariantOperationResponse();
		parseResponse(resp);
		return resp;
	}
	
	public void createOperation(String methodName, String inputXslt, String outputXslt, String uddiKey, 
			String inputParameters, String operationType, long serviceVariantId, Holder<String> tenantName) throws ServiceErrorException{
		CreateSdpServiceVariantOperationRequest req = new CreateSdpServiceVariantOperationRequest();
		req.setMethodName(methodName);
		req.setInputXslt(inputXslt);
		req.setOutputXslt(outputXslt);
		req.setUddiKey(uddiKey);
		req.setInputParameters(inputParameters);
		req.setOperationType(operationType);
		req.setServiceVariantId(serviceVariantId);
		CCreateSdpServiceVariantOperation request = new CCreateSdpServiceVariantOperation();
		request.setCreateSdpServiceVariantOperationRequest(req);
		CCreateSdpServiceVariantOperationResponse result = port.cCreateSdpServiceVariantOperation(request, tenantName);
		BaseResp resp = result.getCreateSdpServiceVariantOperationResponse();
		parseResponse(resp);
	}
	
	public void modifyOperation(String methodName, String inputXslt, String outputXslt, String uddiKey, 
			String inputParameters, String operationType, long serviceVariantId, Holder<String> tenantName) throws ServiceErrorException{
		ModifySdpServiceVariantOperationRequest req = new ModifySdpServiceVariantOperationRequest();
		req.setMethodName(methodName);
		req.setInputXslt(inputXslt);
		req.setOutputXslt(outputXslt);
		req.setUddiKey(uddiKey);
		req.setInputParameters(inputParameters);
		req.setOperationType(operationType);
		req.setServiceVariantId(serviceVariantId);
		CModifySdpServiceVariantOperation request = new CModifySdpServiceVariantOperation();
		request.setModifySdpServiceVariantOperationRequest(req);
		CModifySdpServiceVariantOperationResponse result = port.cModifySdpServiceVariantOperation(request, tenantName);
		BaseResp resp = result.getModifySdpServiceVariantOperationResponse();
		parseResponse(resp);
	}
	
	public void deleteOperation(String methodName,  long serviceVariantId, Holder<String> tenantName) throws ServiceErrorException{
		DeleteSdpServiceVariantOperationRequest req = new DeleteSdpServiceVariantOperationRequest();
		req.setMethodName(methodName);
		req.setServiceVariantId(serviceVariantId);
		CDeleteSdpServiceVariantOperation request = new CDeleteSdpServiceVariantOperation();
		request.setDeleteSdpServiceVariantOperationRequest(req);
		CDeleteSdpServiceVariantOperationResponse result = port.cDeleteSdpServiceVariantOperation(request, tenantName);
		BaseResp resp = result.getDeleteSdpServiceVariantOperationResponse();
		parseResponse(resp);
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
