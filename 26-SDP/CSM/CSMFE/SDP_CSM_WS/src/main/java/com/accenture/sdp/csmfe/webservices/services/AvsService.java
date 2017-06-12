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

import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.responses.AvsCountryLangCodeDto;
import com.accenture.sdp.csm.dto.responses.AvsDeviceIdTypeDto;
import com.accenture.sdp.csm.dto.responses.AvsDigitalGoodDto;
import com.accenture.sdp.csm.dto.responses.AvsPaymentTypeDto;
import com.accenture.sdp.csm.dto.responses.AvsPcExtendedRatingDto;
import com.accenture.sdp.csm.dto.responses.AvsPcLevelDto;
import com.accenture.sdp.csm.dto.responses.AvsPlatformDto;
import com.accenture.sdp.csm.dto.responses.AvsRetailerDomainDto;
import com.accenture.sdp.csm.dto.responses.SdpSolutionOfferDto;
import com.accenture.sdp.csm.managers.AvsManager;
import com.accenture.sdp.csm.managers.SdpSolutionOfferManager;
import com.accenture.sdp.csm.utilities.FieldConstants;
import com.accenture.sdp.csmfe.webservices.request.BaseRequestPaginated;
import com.accenture.sdp.csmfe.webservices.request.offer.DeleteOfferRequest;
import com.accenture.sdp.csmfe.webservices.request.offer.SearchOfferRequest;
import com.accenture.sdp.csmfe.webservices.request.solution.DeleteSolutionRequest;
import com.accenture.sdp.csmfe.webservices.response.BaseResp;
import com.accenture.sdp.csmfe.webservices.response.avs.SearchAvsCountryLangCodeResp;
import com.accenture.sdp.csmfe.webservices.response.avs.SearchAvsDeviceIdTypeResp;
import com.accenture.sdp.csmfe.webservices.response.avs.SearchAvsDigitalGoodResp;
import com.accenture.sdp.csmfe.webservices.response.avs.SearchAvsPaymentTypeResp;
import com.accenture.sdp.csmfe.webservices.response.avs.SearchAvsPcExtendedRatingResp;
import com.accenture.sdp.csmfe.webservices.response.avs.SearchAvsPcLevelResp;
import com.accenture.sdp.csmfe.webservices.response.avs.SearchAvsPlatformResp;
import com.accenture.sdp.csmfe.webservices.response.avs.SearchAvsRetailerDomainResp;
import com.accenture.sdp.csmfe.webservices.response.avs.SearchDiscountSolutionOfferRespPaginated;
import com.accenture.sdp.csmfe.webservices.utils.AvsBeanConverter;
import com.accenture.sdp.csmfe.webservices.utils.SolutionOfferBeanConverter;

/**
 * @author elia.furiozzi
 * 
 */
@WebService(serviceName = "AvsService", targetNamespace = "http://com.accenture.sdp.csmfe.webservices")
@SchemaValidation
// JWS annotation that specifies the mapping of the service onto the
// SOAP message protocol. In particular, it specifies that the SOAP messages
// are document literal.
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class AvsService extends BaseWebService {

	@WebMethod(action = "searchAllAvsCountryLangCodes")
	@WebResult(name = "SearchAllAvsCountryLangCodesResponse")
	public SearchAvsCountryLangCodeResp searchAllAvsCountryLangCodes(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchAvsCountryLangCodeResp wsResp = new SearchAvsCountryLangCodeResp();
		try {
			SearchServiceResponse resp = AvsManager.getInstance().searchAllAvsCountryLangCodes(trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.getCountryLangCodes().setCountryLangCodeList(
						AvsBeanConverter.convertAvsCountryLangCodes((List<AvsCountryLangCodeDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchAllAvsRetailerDomains")
	@WebResult(name = "SearchAllAvsRetailerDomainsResponse")
	public SearchAvsRetailerDomainResp searchAllAvsRetailerDomains(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchAvsRetailerDomainResp wsResp = new SearchAvsRetailerDomainResp();
		try {
			SearchServiceResponse resp = AvsManager.getInstance().searchAllAvsRetailerDomains(trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.getRetailerDomains().setRetailerDomainList(
						AvsBeanConverter.convertAvsRetailerDomains((List<AvsRetailerDomainDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchAllAvsDeviceIdTypes")
	@WebResult(name = "SearchAllAvsDeviceIdTypesResponse")
	public SearchAvsDeviceIdTypeResp searchAllAvsDeviceIdTypes(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchAvsDeviceIdTypeResp wsResp = new SearchAvsDeviceIdTypeResp();
		try {
			SearchServiceResponse resp = AvsManager.getInstance().searchAllAvsDeviceIdTypes(trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.getDeviceIdTypes().setDeviceIdTypeList(AvsBeanConverter.convertAvsDeviceIdTypes((List<AvsDeviceIdTypeDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchAllAvsPaymentTypes")
	@WebResult(name = "SearchAllAvsPaymentTypesResponse")
	public SearchAvsPaymentTypeResp searchAllAvsPaymentTypes(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchAvsPaymentTypeResp wsResp = new SearchAvsPaymentTypeResp();
		try {
			SearchServiceResponse resp = AvsManager.getInstance().searchAllAvsPaymentTypes(trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.getPaymentTypes().setPaymentTypeList(AvsBeanConverter.convertAvsPaymentTypes((List<AvsPaymentTypeDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchAllAvsPcExtendedRatings")
	@WebResult(name = "SearchAllAvsPcExtendedRatingsResponse")
	public SearchAvsPcExtendedRatingResp searchAllAvsPcExtendedRatings(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchAvsPcExtendedRatingResp wsResp = new SearchAvsPcExtendedRatingResp();
		try {
			SearchServiceResponse resp = AvsManager.getInstance().searchAllAvsPcExtendedRatings(trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.getPcExtendedRatings().setPcExtendedRatingList(
						AvsBeanConverter.convertAvsPcExtendedRatings((List<AvsPcExtendedRatingDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchAllAvsPcLevels")
	@WebResult(name = "SearchAllAvsPcLevelsResponse")
	public SearchAvsPcLevelResp searchAllAvsPcLevels(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchAvsPcLevelResp wsResp = new SearchAvsPcLevelResp();
		try {
			SearchServiceResponse resp = AvsManager.getInstance().searchAllAvsPcLevels(trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.getPcLevels().setPcLevelList(AvsBeanConverter.convertAvsPcLevels((List<AvsPcLevelDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchAllAvsPlatforms")
	@WebResult(name = "SearchAllAvsPlatformsResponse")
	public SearchAvsPlatformResp searchAllAvsPlatforms(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchAvsPlatformResp wsResp = new SearchAvsPlatformResp();
		try {
			SearchServiceResponse resp = AvsManager.getInstance().searchAllAvsPlatforms(trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.getPlatforms().setPlatformList(AvsBeanConverter.convertAvsPlatforms((List<AvsPlatformDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchAvsDigitalGoodsByOfferId")
	@WebResult(name = "SearchAvsDigitalGoodsByOfferIdResponse")
	public SearchAvsDigitalGoodResp searchAvsDigitalGoodsByOfferId(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchAvsDigitalGoodsByOfferIdRequest") SearchOfferRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchAvsDigitalGoodResp wsResp = new SearchAvsDigitalGoodResp();
		try {
			SearchServiceResponse resp = AvsManager.getInstance().searchAvsDigitalGoodsByOfferId(request.getOfferId(), (trim(tenantName.value)));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.getDigitalGoods().setDigitalGoodList(AvsBeanConverter.convertAvsDigitalGoods((List<AvsDigitalGoodDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "deleteSolution")
	@WebResult(name = "DeleteSolutionResponse")
	public BaseResp deleteSolution(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "DeleteSolutionRequest") DeleteSolutionRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = AvsManager.getInstance().deleteSolution(request.getSolutionId(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "deleteOffer")
	@WebResult(name = "DeleteOfferResponse")
	public BaseResp deleteOffer(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "DeleteOfferRequest") DeleteOfferRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = AvsManager.getInstance().deleteOffer(request.getOfferId(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchAllDiscountSolutionOffers")
	@WebResult(name = "searchAllDiscountSolutionOffersResponse")
	public SearchDiscountSolutionOfferRespPaginated searchAllDiscountSolutionOffers(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "searchAllDiscountSolutionOffersRequest") BaseRequestPaginated request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchDiscountSolutionOfferRespPaginated wsResp = new SearchDiscountSolutionOfferRespPaginated();
		try {
			SearchServiceResponse resp = SdpSolutionOfferManager.getInstance().searchAllDiscountSolutionOffers(request.getStartPosition(),
					request.getMaxRecordsNumber(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				wsResp.getSolutionOffers().setSolutionOfferList(
						SolutionOfferBeanConverter.convertSolutionOffersWithStatus((List<SdpSolutionOfferDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

}
