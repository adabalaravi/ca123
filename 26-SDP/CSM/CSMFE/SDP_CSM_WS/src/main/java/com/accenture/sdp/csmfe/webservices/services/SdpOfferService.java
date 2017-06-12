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
import com.accenture.sdp.csm.dto.responses.SdpOfferDto;
import com.accenture.sdp.csm.managers.SdpOfferManager;
import com.accenture.sdp.csm.utilities.FieldConstants;
import com.accenture.sdp.csmfe.webservices.request.BaseRequestPaginated;
import com.accenture.sdp.csmfe.webservices.request.offer.CreateOfferRequest;
import com.accenture.sdp.csmfe.webservices.request.offer.DeleteOfferRequest;
import com.accenture.sdp.csmfe.webservices.request.offer.ModifyOfferRequest;
import com.accenture.sdp.csmfe.webservices.request.offer.OfferChangeStatusRequest;
import com.accenture.sdp.csmfe.webservices.request.offer.SearchOfferRequest;
import com.accenture.sdp.csmfe.webservices.request.offer.SearchOffersByServiceVariantRequest;
import com.accenture.sdp.csmfe.webservices.response.BaseResp;
import com.accenture.sdp.csmfe.webservices.response.offer.CreateOfferResp;
import com.accenture.sdp.csmfe.webservices.response.offer.SearchOfferComplexRespPaginated;
import com.accenture.sdp.csmfe.webservices.response.offer.SearchOfferComplexWithStatusNameRespPaginated;
import com.accenture.sdp.csmfe.webservices.response.offer.SearchOfferResp;
import com.accenture.sdp.csmfe.webservices.utils.OfferBeanConverter;

/**
 * @author elia.furiozzi
 * 
 */
@WebService(serviceName = "SdpOfferService", targetNamespace = "http://com.accenture.sdp.csmfe.webservices")
@SchemaValidation
// JWS annotation that specifies the mapping of the service onto the
// SOAP message protocol. In particular, it specifies that the SOAP messages
// are document literal.
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class SdpOfferService extends BaseWebService {

	@WebMethod(action = "cCreateOffer")
	@WebResult(name = "CreateOfferResponse")
	public CreateOfferResp cCreateOffer(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "CreateOfferRequest") CreateOfferRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		CreateOfferResp wsResp = new CreateOfferResp();
		try {
			CreateServiceResponse resp = SdpOfferManager.getInstance().createOffer(trim(request.getOfferName()), trim(request.getOfferDescription()),
					request.getServiceVariantId(), trim(request.getExternalId()), trim(request.getOfferProfile()), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.setOfferId(resp.getEntityId());
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "cModifyOffer")
	@WebResult(name = "ModifyOfferResponse")
	public BaseResp cModifyOffer(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "ModifyOfferRequest") ModifyOfferRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpOfferManager.getInstance().modifyOffer(request.getOfferId(), trim(request.getOfferName()),
					trim(request.getOfferDescription()), request.getServiceVariantId(), trim(request.getExternalId()), trim(request.getOfferProfile()),
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

	@WebMethod(action = "deleteOfferAndPackages")
	@WebResult(name = "DeleteOfferResponse")
	public BaseResp deleteOfferAndPackages(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "DeleteOfferRequest") DeleteOfferRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpOfferManager.getInstance().deleteOfferAndPackages(request.getOfferId(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "cSearchOffer")
	@WebResult(name = "SearchOfferResponse")
	public SearchOfferResp cSearchOffer(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchOfferRequest") SearchOfferRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchOfferResp wsResp = new SearchOfferResp();
		try {
			SearchServiceResponse resp = SdpOfferManager.getInstance().searchOffer(request.getOfferId(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.getOffers().setOfferList(OfferBeanConverter.convertOffers((List<SdpOfferDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchOffersByServiceVariant")
	@WebResult(name = "SearchOffersByServiceVariantResponse")
	public SearchOfferComplexWithStatusNameRespPaginated searchOffersByServiceVariant(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchOffersByRequest") SearchOffersByServiceVariantRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchOfferComplexWithStatusNameRespPaginated wsResp = new SearchOfferComplexWithStatusNameRespPaginated();
		try {
			SearchServiceResponse resp = SdpOfferManager.getInstance().searchOffersByServiceVariant(trim(request.getServiceVariantName()),
					request.getStartPosition(), request.getMaxRecordsNumber(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				wsResp.getOffers().setOfferList(OfferBeanConverter.convertOffersWithStatus((List<SdpOfferDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchAllOffers")
	@WebResult(name = "SearchAllOffersResponse")
	public SearchOfferComplexRespPaginated searchAllOffers(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchOffersByRequest") BaseRequestPaginated request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchOfferComplexRespPaginated wsResp = new SearchOfferComplexRespPaginated();
		try {
			SearchServiceResponse resp = SdpOfferManager.getInstance().searchAllOffers(request.getStartPosition(), request.getMaxRecordsNumber(),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				wsResp.getOffers().setOfferList(OfferBeanConverter.convertComplexOffers((List<SdpOfferDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "offerChangeStatus")
	@WebResult(name = "OfferChangeStatusResponse")
	public BaseResp offerChangeStatus(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "OfferChangeStatusRequest") OfferChangeStatusRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpOfferManager.getInstance().offerChangeStatus(request.getOfferId(), trim(request.getStatus()), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "offerAndPackagesChangeStatus")
	@WebResult(name = "OfferAndPackagesChangeStatusResponse")
	public BaseResp offerAndPackagesChangeStatus(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "OfferAndPackagesChangeStatusRequest") OfferChangeStatusRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpOfferManager.getInstance().offerAndPackagesChangeStatus(request.getOfferId(), trim(request.getStatus()),
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

	@WebMethod(action = "isOfferSubscribed")
	@WebResult(name = "IsOfferSubscribedResponse")
	public BaseResp isOfferSubscribed(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "IsOfferSubscribedRequest") SearchOfferRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpOfferManager.getInstance().isOfferSubscribed(request.getOfferId(), trim(tenantName.value));

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
