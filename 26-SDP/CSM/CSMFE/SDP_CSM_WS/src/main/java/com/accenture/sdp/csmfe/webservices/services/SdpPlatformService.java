package com.accenture.sdp.csmfe.webservices.services;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.WebParam.Mode;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Holder;

import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.responses.SdpPlatformCompleteDto;
import com.accenture.sdp.csm.dto.responses.SdpPlatformDto;
import com.accenture.sdp.csm.managers.SdpPlatformManager;
import com.accenture.sdp.csm.utilities.FieldConstants;
import com.accenture.sdp.csmfe.webservices.request.BaseRequestPaginated;
import com.accenture.sdp.csmfe.webservices.request.platform.CreatePlatformRequest;
import com.accenture.sdp.csmfe.webservices.request.platform.DeletePlatformRequest;
import com.accenture.sdp.csmfe.webservices.request.platform.ModifyPlatformRequest;
import com.accenture.sdp.csmfe.webservices.request.platform.PlatformChangeStatusRequest;
import com.accenture.sdp.csmfe.webservices.request.platform.SearchPlatformByServiceTemplateRequest;
import com.accenture.sdp.csmfe.webservices.request.platform.SearchPlatformByServiceVariantRequest;
import com.accenture.sdp.csmfe.webservices.request.platform.SearchPlatformRequest;
import com.accenture.sdp.csmfe.webservices.response.BaseResp;
import com.accenture.sdp.csmfe.webservices.response.platform.CreatePlatformResp;
import com.accenture.sdp.csmfe.webservices.response.platform.SearchPlatformByServiceVariantResp;
import com.accenture.sdp.csmfe.webservices.response.platform.SearchPlatformComplexResp;
import com.accenture.sdp.csmfe.webservices.response.platform.SearchPlatformComplexRespPaginated;
import com.accenture.sdp.csmfe.webservices.response.platform.SearchPlatformResp;
import com.accenture.sdp.csmfe.webservices.utils.PlatformBeanConverter;
import org.apache.cxf.annotations.SchemaValidation;

@WebService(serviceName = "SdpPlatformService", targetNamespace = "http://com.accenture.sdp.csmfe.webservices")
@SchemaValidation
// JWS annotation that specifies the mapping of the service onto the
// SOAP message protocol. In particular, it specifies that the SOAP messages
// are document literal.
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class SdpPlatformService extends BaseWebService {

	@WebMethod(action = "cCreatePlatform")
	@WebResult(name = "CreatePlatformResponse")
	public CreatePlatformResp cCreatePlatform(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "CreatePlatformRequest") CreatePlatformRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		CreatePlatformResp wsResp = new CreatePlatformResp();
		try {
			CreateServiceResponse resp = SdpPlatformManager.getInstance().createPlatform(trim(request.getPlatformName()),
					trim(request.getPlatformDescription()), trim(request.getExternalId()), trim(request.getPlatformProfile()), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.setPlatformId(resp.getEntityId());
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "cModifyPlatform")
	@WebResult(name = "ModifyPlatformResponse")
	public BaseResp cModifyPlatform(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "ModifyPlatformRequest") ModifyPlatformRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpPlatformManager.getInstance().modifyPlatform(request.getPlatformId(), trim(request.getPlatformName()),
					trim(request.getPlatformDescription()), trim(request.getExternalId()), trim(request.getPlatformProfile()), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "cDeletePlatform")
	@WebResult(name = "DeletePlatformResponse")
	public BaseResp cDeletePlatform(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "DeletePlatformRequest") DeletePlatformRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpPlatformManager.getInstance().deletePlatform(request.getPlatformId(), trim(tenantName.value));
			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "cSearchPlatform")
	@WebResult(name = "SearchPlatformResponse")
	public SearchPlatformResp cSearchPlatform(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchPlatformRequest") SearchPlatformRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchPlatformResp wsResp = new SearchPlatformResp();
		try {
			SearchServiceResponse resp = SdpPlatformManager.getInstance().searchPlatform(request.getPlatformId(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.getPlatforms().setPlatformList(PlatformBeanConverter.convertPlatform((List<SdpPlatformDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchAllPlatform")
	@WebResult(name = "SearchAllPlatformResponse")
	public SearchPlatformComplexRespPaginated searchAllPlatform(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchAllPlatformRequest") BaseRequestPaginated request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchPlatformComplexRespPaginated wsResp = new SearchPlatformComplexRespPaginated();
		try {
			SearchServiceResponse resp = SdpPlatformManager.getInstance().searchAllPlatforms(request.getStartPosition(), request.getMaxRecordsNumber(),
					trim(tenantName.value));
			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				wsResp.getPlatforms().setPlatformList(PlatformBeanConverter.convertComplexPlatform((List<SdpPlatformDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchPlatformByServiceTemplate")
	@WebResult(name = "SearchPlatformByServiceTemplateResponse")
	public SearchPlatformComplexResp searchPlatformByServiceTemplate(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchPlatformByServiceTemplateRequest") SearchPlatformByServiceTemplateRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchPlatformComplexResp wsResp = new SearchPlatformComplexResp();
		try {
			SearchServiceResponse resp = SdpPlatformManager.getInstance().searchPlatformByServiceTemplate(trim(request.getServiceTemplateName()),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.getPlatforms().setPlatformList(PlatformBeanConverter.convertComplexPlatform((List<SdpPlatformDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchPlatformCompleteInformationByServiceVariant")
	@WebResult(name = "SearchPlatformCompleteInformationByServiceVariantResponse")
	public SearchPlatformByServiceVariantResp searchPlatformCompleteInformationByServiceVariant(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchPlatformCompleteInformationByServiceVariantRequest") SearchPlatformByServiceVariantRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchPlatformByServiceVariantResp wsResp = new SearchPlatformByServiceVariantResp();
		try {
			SearchServiceResponse resp = SdpPlatformManager.getInstance().searchPlatformByServiceVariant(trim(request.getServiceVariantName()),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				if (resp.getSearchResult() != null && resp.getSearchResult().size() > 0) {
					List<SdpPlatformCompleteDto> dto = (List<SdpPlatformCompleteDto>) resp.getSearchResult();
					wsResp.getPlatforms().setPlatform(PlatformBeanConverter.convertPlatform(dto.get(0).getSdpPlatformDto()));
					wsResp.getPlatforms().setServiceTemplate(PlatformBeanConverter.convertServiceTemplate(dto.get(0).getSdpServiceTemplateDto()));
					wsResp.getPlatforms().setServiceVariant(PlatformBeanConverter.convertServiceVariant(dto.get(0).getSdpServiceVariantDto()));
				}
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "platformChangeStatus")
	@WebResult(name = "PlatformChangeStatusResponse")
	public BaseResp platformChangeStatus(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "PlatformChangeStatusRequest") PlatformChangeStatusRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpPlatformManager.getInstance().platformChangeStatus(request.getPlatformId(), trim(request.getStatus()),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
}
