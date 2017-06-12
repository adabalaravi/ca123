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
import com.accenture.sdp.csm.dto.responses.SdpServiceTemplateDto;
import com.accenture.sdp.csm.managers.SdpServiceTemplateManager;
import com.accenture.sdp.csm.utilities.FieldConstants;
import com.accenture.sdp.csmfe.webservices.request.BaseRequestPaginated;
import com.accenture.sdp.csmfe.webservices.request.servicetemplate.CreateServiceTemplateRequest;
import com.accenture.sdp.csmfe.webservices.request.servicetemplate.DeleteServiceTemplateRequest;
import com.accenture.sdp.csmfe.webservices.request.servicetemplate.ModifyServiceTemplateRequest;
import com.accenture.sdp.csmfe.webservices.request.servicetemplate.SearchServiceTemplateByVariantRequest;
import com.accenture.sdp.csmfe.webservices.request.servicetemplate.SearchServiceTemplateRequest;
import com.accenture.sdp.csmfe.webservices.request.servicetemplate.SearchServiceTemplatesByPlatformRequest;
import com.accenture.sdp.csmfe.webservices.request.servicetemplate.ServiceTemplateChangeStatusRequest;
import com.accenture.sdp.csmfe.webservices.response.BaseResp;
import com.accenture.sdp.csmfe.webservices.response.servicetemplate.CreateServiceTemplateResp;
import com.accenture.sdp.csmfe.webservices.response.servicetemplate.SearchAllServiceTemplatesResp;
import com.accenture.sdp.csmfe.webservices.response.servicetemplate.SearchServiceTemplateByVariantResp;
import com.accenture.sdp.csmfe.webservices.response.servicetemplate.SearchServiceTemplateResp;
import com.accenture.sdp.csmfe.webservices.response.servicetemplate.SearchServiceTemplatesByPlatformResp;
import com.accenture.sdp.csmfe.webservices.utils.ServiceTemplateBeanConverter;
import org.apache.cxf.annotations.SchemaValidation;

@WebService(serviceName = "SdpServiceTemplateService", targetNamespace = "http://com.accenture.sdp.csmfe.webservices")
@SchemaValidation
// JWS annotation that specifies the mapping of the service onto the
// SOAP message protocol. In particular, it specifies that the SOAP messages
// are document literal.
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class SdpServiceTemplateService extends BaseWebService {

	@WebMethod(action="cCreateServiceTemplate")
	@WebResult(name = "CreateServiceTemplateResponse")
	public CreateServiceTemplateResp cCreateServiceTemplate(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "CreateServiceTemplateRequest") CreateServiceTemplateRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);
		CreateServiceTemplateResp wsResp = new CreateServiceTemplateResp();

		try {
			CreateServiceResponse resp = SdpServiceTemplateManager.getInstance().createServiceTemplate(
					trim(request.getServiceTemplateName()),
					trim(request.getServiceTemplateDescription()), 
					trim(request.getServiceTemplateProfile()), 
					request.getPlatformId(), 
					trim(request.getExternalId()), 
					request.getServiceTypeId(),
					trim(tenantName.value)
					);
			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setServiceTemplateId(resp.getEntityId());
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action="cModifyServiceTemplate")
	@WebResult(name="ModifyServiceTemplateResponse")
	public BaseResp cModifyServiceTemplate(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "ModifyServiceTemplateRequest") ModifyServiceTemplateRequest request) {
		
		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);
		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpServiceTemplateManager.getInstance().modifyServiceTemplate(
					request.getServiceTemplateId(),
					trim(request.getServiceTemplateName()),
					trim(request.getServiceTemplateDescription()), 
					trim(request.getExternalId()),
					trim(request.getServiceTemplateProfile()),  
					request.getServiceTypeId(),
					request.getPlatformId(),
					trim(tenantName.value)
					);
			if (resp != null) {
				convertBaseResponse(resp,wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action="cDeleteServiceTemplate")
	@WebResult(name="DeleteServiceTemplateResponse")
	public BaseResp cDeleteServiceTemplate(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "DeleteServiceTemplateRequest") DeleteServiceTemplateRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpServiceTemplateManager.getInstance().deleteServiceTemplate(
					request.getServiceTemplateId(),
					trim(tenantName.value)
					);

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action="cSearchServiceTemplate")
	@WebResult(name="SearchServiceTemplateResponse")
	public SearchServiceTemplateResp cSearchServiceTemplate(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchServiceTemplateRequest") SearchServiceTemplateRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchServiceTemplateResp wsResp = new SearchServiceTemplateResp();
		try {
			SearchServiceResponse resp = SdpServiceTemplateManager.getInstance().searchServiceTemplate(
					(request.getServiceTemplateId()),
					trim(tenantName.value)
					);
			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.getServiceTemplates().setServiceTemplateList(ServiceTemplateBeanConverter
						.convertServiceTemplate((List<SdpServiceTemplateDto>)resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action="searchAllServiceTemplates")
	@WebResult(name="SearchAllServiceTemplatesResponse")
	public SearchAllServiceTemplatesResp searchAllServiceTemplates(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchAllServiceTemplatesRequest") BaseRequestPaginated request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchAllServiceTemplatesResp wsResp = new SearchAllServiceTemplatesResp();
		try {
			SearchServiceResponse resp = SdpServiceTemplateManager.getInstance().searchAllServiceTemplates(
					request.getStartPosition(),
					request.getMaxRecordsNumber(),
					trim(tenantName.value)
					);

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.getServiceTemplates().setServiceTemplateList(ServiceTemplateBeanConverter
						.convertServiceTemplateAll((List<SdpServiceTemplateDto>)resp.getSearchResult()));
				wsResp.setTotalResult(resp.getTotalResult());
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action="searchServiceTemplateByServiceVariant")
	@WebResult(name="SearchServiceTemplateByVariantResponse")
	public SearchServiceTemplateByVariantResp searchServiceTemplateByServiceVariant (
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchServiceTemplateByVariantRequest") SearchServiceTemplateByVariantRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchServiceTemplateByVariantResp wsResp = new SearchServiceTemplateByVariantResp();
		try {
			SearchServiceResponse resp = SdpServiceTemplateManager.getInstance().searchServiceTemplateByServiceVariant(
					trim(request.getServiceVariantName()),
					trim(tenantName.value)
					);

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.getServiceTemplates().setServiceTemplateList(ServiceTemplateBeanConverter
						.convertServiceTemplateByVariant((List<SdpServiceTemplateDto>)resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action="searchServiceTemplatesByPlatform")
	@WebResult(name="SearchServiceTemplatesByPlatformResponse")
	public SearchServiceTemplatesByPlatformResp searchServiceTemplatesByPlatform (
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchServiceTemplateByPlatformRequest") SearchServiceTemplatesByPlatformRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchServiceTemplatesByPlatformResp wsResp = new SearchServiceTemplatesByPlatformResp();
		try {
			SearchServiceResponse resp = SdpServiceTemplateManager.getInstance().searchServiceTemplatesByPlatform(
					trim(request.getPlatformName()),
					request.getStartPosition(),
					request.getMaxRecordsNumber(),
					trim(tenantName.value)
					);

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				wsResp.getServiceTemplates().setServiceTemplateList(ServiceTemplateBeanConverter
						.convertServiceTemplateByPlatform((List<SdpServiceTemplateDto>)resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action="serviceTemplateChangeStatus")
	@WebResult(name="ServiceTemplateChangeStatusResponse")
	public BaseResp serviceTemplateChangeStatus(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "ServiceTemplateChangeStatusRequest") ServiceTemplateChangeStatusRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse  resp = SdpServiceTemplateManager.getInstance().serviceTemplateChangeStatus(
					request.getServiceTemplateId(),
					trim(request.getStatus()),
					trim(tenantName.value)
					);

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}


}