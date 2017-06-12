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
import com.accenture.sdp.csm.dto.responses.SdpServiceVariantDto;
import com.accenture.sdp.csm.managers.SdpServiceVariantManager;
import com.accenture.sdp.csm.utilities.FieldConstants;
import com.accenture.sdp.csmfe.webservices.request.BaseRequestPaginated;
import com.accenture.sdp.csmfe.webservices.request.servicevariant.CreateServiceVariantRequest;
import com.accenture.sdp.csmfe.webservices.request.servicevariant.DeleteServiceVariantRequest;
import com.accenture.sdp.csmfe.webservices.request.servicevariant.ModifyServiceVariantRequest;
import com.accenture.sdp.csmfe.webservices.request.servicevariant.SearchServiceVariantByOfferRequest;
import com.accenture.sdp.csmfe.webservices.request.servicevariant.SearchServiceVariantRequest;
import com.accenture.sdp.csmfe.webservices.request.servicevariant.SearchServiceVariantsByServiceTemplateRequest;
import com.accenture.sdp.csmfe.webservices.request.servicevariant.ServiceVariantChangeStatusRequest;
import com.accenture.sdp.csmfe.webservices.response.BaseResp;
import com.accenture.sdp.csmfe.webservices.response.servicevariant.CreateServiceVariantResp;
import com.accenture.sdp.csmfe.webservices.response.servicevariant.SearchAllServiceVariantsResp;
import com.accenture.sdp.csmfe.webservices.response.servicevariant.SearchServiceVariantByOfferResp;
import com.accenture.sdp.csmfe.webservices.response.servicevariant.SearchServiceVariantResp;
import com.accenture.sdp.csmfe.webservices.response.servicevariant.SearchServiceVariantsByServiceTemplateResp;
import com.accenture.sdp.csmfe.webservices.utils.ServiceVariantBeanConverter;

@WebService(serviceName = "SdpServiceVariantService", targetNamespace = "http://com.accenture.sdp.csmfe.webservices")
@SchemaValidation
// JWS annotation that specifies the mapping of the service onto the
// SOAP message protocol. In particular, it specifies that the SOAP messages
// are document literal.
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class SdpServiceVariantService extends BaseWebService {

	@WebMethod(action = "cCreateServiceVariant")
	@WebResult(name = "CreateServiceVariantResponse")
	public CreateServiceVariantResp cCreateServiceVariant(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "CreateServiceVariantRequest") CreateServiceVariantRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		CreateServiceVariantResp wsResp = new CreateServiceVariantResp();
		try {
			CreateServiceResponse resp = SdpServiceVariantManager.getInstance().createServiceVariant(trim(request.getServiceVariantName()),
					trim(request.getServiceVariantDescription()), trim(request.getServiceVariantProfile()), request.getServiceTemplateId(),
					trim(request.getExternalId()), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.setServiceVariantId(resp.getEntityId());
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "cModifyServiceVariant")
	@WebResult(name = "ModifyServiceVariantResponse")
	public BaseResp cModifyServiceVariant(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "ModifyServiceVariantRequest") ModifyServiceVariantRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpServiceVariantManager.getInstance().modifyServiceVariant(request.getServiceVariantId(),
					trim(request.getServiceVariantName()), trim(request.getServiceVariantDescription()), trim(request.getExternalId()),
					request.getServiceTemplateId(), trim(request.getServiceVariantProfile()), trim(tenantName.value));
			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "cDeleteserviceVariant")
	@WebResult(name = "DeleteserviceVariantResponse")
	public BaseResp cDeleteServiceVariant(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "DeleteServiceVariantRequest") DeleteServiceVariantRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpServiceVariantManager.getInstance().deleteServiceVariant(request.getServiceVariantId(), trim(tenantName.value));
			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "cSearchServiceVariant")
	@WebResult(name = "SearchServiceVariantResponse")
	public SearchServiceVariantResp cSearchServiceVariant(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchServiceVariantRequest") SearchServiceVariantRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchServiceVariantResp wsResp = new SearchServiceVariantResp();
		try {
			SearchServiceResponse resp = SdpServiceVariantManager.getInstance().searchServiceVariant(request.getServiceVariantId(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.getServicevariantList().setServicevariantList(
						ServiceVariantBeanConverter.convertServiceVariant((List<SdpServiceVariantDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchAllServiceVariants")
	@WebResult(name = "SearchServiceVariantResponse")
	public SearchAllServiceVariantsResp searchAllServiceVariants(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "BaseRequestPaginated") BaseRequestPaginated request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchAllServiceVariantsResp wsResp = new SearchAllServiceVariantsResp();
		try {
			SearchServiceResponse resp = SdpServiceVariantManager.getInstance().searchAllServiceVariants(request.getStartPosition(),
					request.getMaxRecordsNumber(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				wsResp.getServicevariantList().setServiceVariantList(
						ServiceVariantBeanConverter.convertSearchServiceVariant((List<SdpServiceVariantDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchServiceVariantsByServiceTemplate")
	@WebResult(name = "searchServiceVariantsByServiceTemplateResponse")
	public SearchServiceVariantsByServiceTemplateResp searchServiceVariantsByServiceTemplate(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchServiceVariantsByServiceTemplateRequest") SearchServiceVariantsByServiceTemplateRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchServiceVariantsByServiceTemplateResp wsResp = new SearchServiceVariantsByServiceTemplateResp();
		try {
			SearchServiceResponse resp = SdpServiceVariantManager.getInstance().searchServiceVariantsByServiceTemplate(trim(request.getServiceTemplateName()),
					request.getStartPosition(), request.getMaxRecordsNumber(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				wsResp.getServiceVariantList().setServiceVariantList(
						ServiceVariantBeanConverter.convertSearchServiceVariant((List<SdpServiceVariantDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchServiceVariantByOffer")
	@WebResult(name = "SearchServiceVariantByOfferResponse")
	public SearchServiceVariantByOfferResp searchServiceVariantByOffer(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchServiceVariantByOfferRequest") SearchServiceVariantByOfferRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchServiceVariantByOfferResp wsResp = new SearchServiceVariantByOfferResp();
		try {
			SearchServiceResponse resp = SdpServiceVariantManager.getInstance().searchServiceVariantByOffer(trim(request.getOfferName()),
					trim(tenantName.value));
			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.getServicevariantList().setServiceVariantList(
						ServiceVariantBeanConverter.convertSearchServiceVariant((List<SdpServiceVariantDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "serviceVariantChangeStatus")
	@WebResult(name = "ServiceVariantChangeStatusResponse")
	public BaseResp serviceVariantChangeStatus(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "ServiceVariantChangeStatusRequest") ServiceVariantChangeStatusRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpServiceVariantManager.getInstance().serviceVariantChangeStatus(request.getServiceVariantId(),
					trim(request.getStatus()), trim(tenantName.value));

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
