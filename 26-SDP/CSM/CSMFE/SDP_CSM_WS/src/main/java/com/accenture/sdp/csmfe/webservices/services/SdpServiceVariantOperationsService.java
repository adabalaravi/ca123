package com.accenture.sdp.csmfe.webservices.services;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebParam.Mode;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Holder;

import org.apache.cxf.annotations.SchemaValidation;

import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.responses.SdpServiceVariantOperationDto;
import com.accenture.sdp.csm.managers.SdpServiceVariantOperationsManager;
import com.accenture.sdp.csm.utilities.FieldConstants;
import com.accenture.sdp.csmfe.webservices.request.servicevariantoperations.CreateSdpServiceVariantOperationRequest;
import com.accenture.sdp.csmfe.webservices.request.servicevariantoperations.DeleteSdpServiceVariantOperationRequest;
import com.accenture.sdp.csmfe.webservices.request.servicevariantoperations.ModifySdpServiceVariantOperationRequest;
import com.accenture.sdp.csmfe.webservices.request.servicevariantoperations.SearchSdpServiceVariantOperationByIdRequest;
import com.accenture.sdp.csmfe.webservices.request.servicevariantoperations.SearchSdpServiceVariantOperationByNameRequest;
import com.accenture.sdp.csmfe.webservices.request.servicevariantoperations.SearchSdpServiceVariantOperationByServiceVariantRequest;
import com.accenture.sdp.csmfe.webservices.response.BaseResp;
import com.accenture.sdp.csmfe.webservices.response.servicevariantoperations.SearchSdpServiceVariantOperationResp;
import com.accenture.sdp.csmfe.webservices.response.servicevariantoperations.ServiceVariantOperationListResp;
import com.accenture.sdp.csmfe.webservices.utils.ServiceVariantOperationsConverter;

@WebService(serviceName = "SdpServiceVariantOperationsService", targetNamespace = "http://com.accenture.sdp.csmfe.webservices")
@SchemaValidation
// JWS annotation that specifies the mapping of the service onto the
// SOAP message protocol. In particular, it specifies that the SOAP messages
// are document literal.
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class SdpServiceVariantOperationsService extends BaseWebService {

	@WebMethod(action = "cCreateSdpServiceVariantOperation")
	@WebResult(name = "CreateSdpServiceVariantOperationResponse")
	public BaseResp cCreateSdpServiceVariantOperation(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "CreateSdpServiceVariantOperationRequest") CreateSdpServiceVariantOperationRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);
		BaseResp wsResp = new BaseResp();

		try {
			CreateServiceResponse resp = SdpServiceVariantOperationsManager.getInstance().createSdpServiceVariantOperation(request.getServiceVariantId(),
					trim(request.getMethodName()), trim(request.getInputParameters()), trim(request.getInputXslt()), trim(request.getOutputXslt()),
					trim(request.getUddiKey()), trim(request.getOperationType()), trim(tenantName.value));
			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "cModifySdpServiceVariantOperation")
	@WebResult(name = "ModifySdpServiceVariantOperationResponse")
	public BaseResp cModifySdpServiceVariantOperation(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "ModifySdpServiceVariantOperationRequest") ModifySdpServiceVariantOperationRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);
		BaseResp wsResp = new BaseResp();

		try {
			DataServiceResponse resp = SdpServiceVariantOperationsManager.getInstance().modifySdpServiceVariantOperation(request.getServiceVariantId(),
					trim(request.getMethodName()), trim(request.getInputParameters()), trim(request.getInputXslt()), trim(request.getOutputXslt()),
					trim(request.getUddiKey()), trim(request.getOperationType()), trim(tenantName.value));
			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "cDeleteSdpServiceVariantOperation")
	@WebResult(name = "DeleteSdpServiceVariantOperationResponse")
	public BaseResp cDeleteSdpServiceVariantOperation(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "DeleteSdpServiceVariantOperationRequest") DeleteSdpServiceVariantOperationRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);
		BaseResp wsResp = new BaseResp();

		try {
			DataServiceResponse resp = SdpServiceVariantOperationsManager.getInstance().deleteSdpServiceVariantOperation(request.getServiceVariantId(),
					trim(request.getMethodName()), trim(tenantName.value));
			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchSdpServiceVariantOperationById")
	@WebResult(name = "SearchSdpServiceVariantOperationResponse")
	public SearchSdpServiceVariantOperationResp searchSdpServiceVariantOperationById(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchSdpServiceVariantOperationByIdRequest") SearchSdpServiceVariantOperationByIdRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);
		SearchSdpServiceVariantOperationResp wsResp = new SearchSdpServiceVariantOperationResp();

		try {
			SearchServiceResponse resp = SdpServiceVariantOperationsManager.getInstance().searchSdpServiceVariantOperationById(request.getServiceVariantId(),
					trim(request.getMethodName()), trim(tenantName.value));
			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				if (resp.getSearchResult() != null && resp.getSearchResult().size() > 0) {
					ServiceVariantOperationListResp tmp = new ServiceVariantOperationListResp();
					tmp.setServiceVariantOperations(ServiceVariantOperationsConverter
							.convertServiceVariantOperations((List<SdpServiceVariantOperationDto>) resp.getSearchResult()));
					wsResp.setServiceVariantOperations(tmp);
				}
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchSdpServiceVariantOperationByName")
	@WebResult(name = "SearchSdpServiceVariantOperationResponse")
	public SearchSdpServiceVariantOperationResp searchSdpServiceVariantOperationByName(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchSdpServiceVariantOperationByNameRequest") SearchSdpServiceVariantOperationByNameRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);
		SearchSdpServiceVariantOperationResp wsResp = new SearchSdpServiceVariantOperationResp();

		try {
			SearchServiceResponse resp = SdpServiceVariantOperationsManager.getInstance().searchSdpServiceVariantOperationByName(
					trim(request.getServiceVariantName()), trim(request.getMethodName()), trim(tenantName.value));
			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				if (resp.getSearchResult() != null && resp.getSearchResult().size() > 0) {
					ServiceVariantOperationListResp tmp = new ServiceVariantOperationListResp();
					tmp.setServiceVariantOperations(ServiceVariantOperationsConverter
							.convertServiceVariantOperations((List<SdpServiceVariantOperationDto>) resp.getSearchResult()));
					wsResp.setServiceVariantOperations(tmp);

				}

			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchSdpServiceVariantOperationsByServiceVariant")
	@WebResult(name = "SearchSdpServiceVariantOperationResponse")
	public SearchSdpServiceVariantOperationResp searchSdpServiceVariantOperationsByServiceVariantRequest(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchSdpServiceVariantOperationByServiceVariantRequest") SearchSdpServiceVariantOperationByServiceVariantRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);
		SearchSdpServiceVariantOperationResp wsResp = new SearchSdpServiceVariantOperationResp();

		try {
			SearchServiceResponse resp = SdpServiceVariantOperationsManager.getInstance().searchSdpServiceVariantOperationByServiceVariantId(
					request.getServiceVariantId(), trim(tenantName.value));
			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				if (resp.getSearchResult() != null && resp.getSearchResult().size() > 0) {
					ServiceVariantOperationListResp tmp = new ServiceVariantOperationListResp();
					tmp.setServiceVariantOperations(ServiceVariantOperationsConverter
							.convertServiceVariantOperations((List<SdpServiceVariantOperationDto>) resp.getSearchResult()));
					wsResp.setServiceVariantOperations(tmp);

				}

			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

}
