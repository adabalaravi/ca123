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

import com.accenture.sdp.csm.dto.ComplexServiceResponse;
import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.requests.SdpDiscountModifyRequestDto;
import com.accenture.sdp.csm.dto.responses.SdpSolutionOfferAndDiscountDto;
import com.accenture.sdp.csm.dto.responses.SdpSolutionOfferDto;
import com.accenture.sdp.csm.dto.responses.SdpSolutionOfferPackageDto;
import com.accenture.sdp.csm.dto.responses.SdpSolutionSolutionOfferDto;
import com.accenture.sdp.csm.managers.SdpSolutionOfferManager;
import com.accenture.sdp.csm.utilities.FieldConstants;
import com.accenture.sdp.csmfe.webservices.request.BaseRequestPaginated;
import com.accenture.sdp.csmfe.webservices.request.partygroup.SearchPartyGroupRequest;
import com.accenture.sdp.csmfe.webservices.request.solutionoffer.CreateSolutionOfferAndDiscountRequest;
import com.accenture.sdp.csmfe.webservices.request.solutionoffer.CreateSolutionOfferAndPackageRequest;
import com.accenture.sdp.csmfe.webservices.request.solutionoffer.CreateSolutionOfferRequest;
import com.accenture.sdp.csmfe.webservices.request.solutionoffer.DeleteSolutionOfferRequest;
import com.accenture.sdp.csmfe.webservices.request.solutionoffer.ModifyDiscountsRequest;
import com.accenture.sdp.csmfe.webservices.request.solutionoffer.ModifySolutionOfferPartyGroupRequest;
import com.accenture.sdp.csmfe.webservices.request.solutionoffer.ModifySolutionOfferRequest;
import com.accenture.sdp.csmfe.webservices.request.solutionoffer.SearchDiscountedSolutionOffersBySolutionOfferRequest;
import com.accenture.sdp.csmfe.webservices.request.solutionoffer.SearchSolutionOfferRequest;
import com.accenture.sdp.csmfe.webservices.request.solutionoffer.SearchSolutionOffersBySolutionRequest;
import com.accenture.sdp.csmfe.webservices.request.solutionoffer.SolutionOfferChangeStatusRequest;
import com.accenture.sdp.csmfe.webservices.response.BaseResp;
import com.accenture.sdp.csmfe.webservices.response.solutionoffer.CreateSolutionOfferAndDiscountResp;
import com.accenture.sdp.csmfe.webservices.response.solutionoffer.CreateSolutionOfferAndPackageResp;
import com.accenture.sdp.csmfe.webservices.response.solutionoffer.CreateSolutionOfferResp;
import com.accenture.sdp.csmfe.webservices.response.solutionoffer.SearchAllSolutionOfferTypesResp;
import com.accenture.sdp.csmfe.webservices.response.solutionoffer.SearchSolutionOfferAndSolutionRespPaginated;
import com.accenture.sdp.csmfe.webservices.response.solutionoffer.SearchSolutionOfferComplexRespPaginated;
import com.accenture.sdp.csmfe.webservices.response.solutionoffer.SearchSolutionOfferResp;
import com.accenture.sdp.csmfe.webservices.utils.PartyGroupBeanConverter;
import com.accenture.sdp.csmfe.webservices.utils.SolutionBeanConverter;
import com.accenture.sdp.csmfe.webservices.utils.SolutionOfferBeanConverter;

/**
 * @author elia.furiozzi
 * 
 */
@WebService(serviceName = "SdpSolutionOfferService", targetNamespace = "http://com.accenture.sdp.csmfe.webservices")
@SchemaValidation
// JWS annotation that specifies the mapping of the service onto the
// SOAP message protocol. In particular, it specifies that the SOAP messages
// are document literal.
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class SdpSolutionOfferService extends BaseWebService {

	@WebMethod(action = "createSolutionOffer")
	@WebResult(name = "CreateSolutionOfferResponse")
	public CreateSolutionOfferResp createSolutionOffer(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "CreateSolutionOfferRequest") CreateSolutionOfferRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		CreateSolutionOfferResp wsResp = new CreateSolutionOfferResp();
		try {
			CreateServiceResponse resp = SdpSolutionOfferManager.getInstance().createSolutionOffer(request.getSolutionId(),
					trim(request.getSolutionOfferName()), trim(request.getSolutionOfferDescription()), request.getStartDate(), request.getEndDate(),
					SolutionOfferBeanConverter.convertExternalIdRequests(request.getExternalIds().getExternalIdList()), trim(request.getProfile()),
					SolutionBeanConverter.convertPartyGroupNames(request.getPartyGroups().getPartyGroupList()), trim(request.getSolutionOfferType()),
					request.getDuration(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.setSolutionOfferId(resp.getEntityId());
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "createSolutionOfferAndPackage")
	@WebResult(name = "CreateSolutionOfferAndPackageResponse")
	public CreateSolutionOfferAndPackageResp createSolutionOfferAndPackage(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "CreateSolutionOfferRequest") CreateSolutionOfferAndPackageRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		CreateSolutionOfferAndPackageResp wsResp = new CreateSolutionOfferAndPackageResp();
		try {
			ComplexServiceResponse resp = SdpSolutionOfferManager.getInstance().createSolutionOfferAndPackage(trim(request.getSolutionName()),
					trim(request.getSolutionOfferName()), trim(request.getSolutionOfferDescription()), request.getSolutionOfferStartDate(),
					request.getSolutionOfferEndDate(), SolutionOfferBeanConverter.convertExternalIdRequests(request.getExternalIds().getExternalIdList()),
					trim(request.getSolutionOfferProfile()),
					SolutionOfferBeanConverter.convertSolutionOfferDetailReq(request.getSolutionOfferDetails().getSolutionOfferDetails()),
					SolutionBeanConverter.convertPartyGroupNames(request.getPartyGroups().getPartyGroupList()), trim(request.getSolutionOfferType()),
					request.getDuration(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				SdpSolutionOfferPackageDto respdto = (SdpSolutionOfferPackageDto) resp.getComplexObject();
				if (respdto != null) {
					wsResp.setSolutionOfferId(respdto.getSolutionOfferDto().getSolutionOfferId());
					wsResp.getSolutionOfferDetails().setSolutionOfferDetailList(
							SolutionOfferBeanConverter.convertSolutionOfferDetailResp(respdto.getSolutionOfferDetailDto()));
				}
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "createSolutionOfferAndDiscount")
	@WebResult(name = "CreateSolutionOfferAndDiscountResponse")
	public CreateSolutionOfferAndDiscountResp createSolutionOfferAndDiscount(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "CreateSolutionOfferRequest") CreateSolutionOfferAndDiscountRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		CreateSolutionOfferAndDiscountResp wsResp = new CreateSolutionOfferAndDiscountResp();
		try {
			ComplexServiceResponse resp = SdpSolutionOfferManager.getInstance().createSolutionOfferAndDiscount(request.getParentSolutionOfferId(),
					trim(request.getSolutionOfferName()), trim(request.getSolutionOfferDescription()), request.getStartDate(), request.getEndDate(),
					SolutionOfferBeanConverter.convertExternalIdRequests(request.getExternalIds().getExternalIdList()), trim(request.getProfile()),
					SolutionOfferBeanConverter.convertCreateDiscountsReq(request.getDiscounts().getDiscountList()),
					SolutionBeanConverter.convertPartyGroupNames(request.getPartyGroups().getPartyGroupList()), trim(request.getSolutionOfferType()),
					request.getDuration(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				SdpSolutionOfferAndDiscountDto respdto = (SdpSolutionOfferAndDiscountDto) resp.getComplexObject();
				if (respdto != null) {
					wsResp.setSolutionOfferId(respdto.getSolutionOfferId());
					wsResp.getDiscounts().setDiscountIdList(respdto.getDiscountIdList());
				}
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "modifySolutionOffer")
	@WebResult(name = "ModifySolutionOfferResponse")
	public BaseResp modifySolutionOffer(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "ModifySolutionOfferRequest") ModifySolutionOfferRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpSolutionOfferManager.getInstance().modifySolutionOffer(request.getSolutionOfferId(),
					trim(request.getSolutionOfferName()), trim(request.getSolutionOfferDescription()), request.getStartDate(), request.getEndDate(),
					SolutionOfferBeanConverter.convertExternalIdRequests(request.getExternalIds().getExternalIdList()), request.getSolutionId(),
					trim(request.getProfile()), trim(request.getSolutionOfferType()), request.getDuration(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "modifyDiscounts")
	@WebResult(name = "ModifyDiscountsResponse")
	public BaseResp modifyDiscounts(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "ModifyDiscountRequest") ModifyDiscountsRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			List<SdpDiscountModifyRequestDto> discounts = SolutionOfferBeanConverter.convertModifyDiscountsReq(request.getDiscounts());

			DataServiceResponse resp = SdpSolutionOfferManager.getInstance().modifyDiscounts(discounts, trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "cDeleteSolutionOffer")
	@WebResult(name = "DeleteSolutionOfferResponse")
	public BaseResp cDeleteSolutionOffer(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "DeleteSolutionOfferRequest") DeleteSolutionOfferRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpSolutionOfferManager.getInstance().deleteSolutionOffer(request.getSolutionOfferId(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "cSearchSolutionOffer")
	@WebResult(name = "SearchSolutionOfferResponse")
	public SearchSolutionOfferResp cSearchSolutionOffer(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchCredentialsRequest") SearchSolutionOfferRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchSolutionOfferResp wsResp = new SearchSolutionOfferResp();
		try {
			SearchServiceResponse resp = SdpSolutionOfferManager.getInstance().searchSolutionOffer(request.getSolutionOfferId(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.getSolutionOffers().setSolutionOfferList(
						SolutionOfferBeanConverter.convertSolutionOffers((List<SdpSolutionOfferDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchSolutionOffersBySolution")
	@WebResult(name = "SearchSolutionOffersBySolutionResponse")
	public SearchSolutionOfferAndSolutionRespPaginated searchSolutionOffersBySolution(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchSolutionOffersBySolutionRequest") SearchSolutionOffersBySolutionRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchSolutionOfferAndSolutionRespPaginated wsResp = new SearchSolutionOfferAndSolutionRespPaginated();
		try {
			SearchServiceResponse resp = SdpSolutionOfferManager.getInstance().searchSolutionOffersBySolution(trim(request.getSolutionName()),
					request.getStartPosition(), request.getMaxRecordsNumber(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				if (resp.getSearchResult() != null && resp.getSearchResult().size() > 0) {
					List<SdpSolutionSolutionOfferDto> results = (List<SdpSolutionSolutionOfferDto>) resp.getSearchResult();
					SdpSolutionSolutionOfferDto result = results.get(0);
					wsResp.getSolutionOffers().setSolutionOfferList(
							SolutionOfferBeanConverter.convertSolutionOffersWithoutSolutionName(result.getSolutionOfferDto()));
					wsResp.setSolution(SolutionBeanConverter.convertSolution(result.getSolutionDto()));
				}
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchAllSolutionOffers")
	@WebResult(name = "SearchAllSolutionOffersResponse")
	public SearchSolutionOfferComplexRespPaginated searchAllSolutionOffers(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchAllSolutionOffersRequest") BaseRequestPaginated request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchSolutionOfferComplexRespPaginated wsResp = new SearchSolutionOfferComplexRespPaginated();
		try {
			SearchServiceResponse resp = SdpSolutionOfferManager.getInstance().searchAllSolutionOffers(request.getStartPosition(),
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

	@WebMethod(action = "searchDiscountedSolutionOfferBySolutionOffer")
	@WebResult(name = "SearchDiscountedSolutionOfferBySolutionOfferResponse")
	public SearchSolutionOfferComplexRespPaginated searchDiscountedSolutionOfferBySolutionOffer(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchDiscountedSolutionOfferBySolutionOfferRequest") SearchDiscountedSolutionOffersBySolutionOfferRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchSolutionOfferComplexRespPaginated wsResp = new SearchSolutionOfferComplexRespPaginated();
		try {
			SearchServiceResponse resp = SdpSolutionOfferManager.getInstance().searchDiscountedSolutionOfferBySolutionOffer(request.getSolutionOfferName(),
					request.getStartPosition(), request.getMaxRecordsNumber(), trim(tenantName.value));

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

	@WebMethod(action = "solutionOfferChangeStatus")
	@WebResult(name = "SolutionOfferChangeStatusResponse")
	public BaseResp solutionOfferChangeStatus(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SolutionOfferChangeStatusRequest") SolutionOfferChangeStatusRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpSolutionOfferManager.getInstance().solutionOfferChangeStatus(request.getSolutionOfferId(), trim(request.getStatus()),
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

	@WebMethod(action = "searchSolutionOffersByPartyGroup")
	@WebResult(name = "SearchSolutionOffersByPartyGroupResponse")
	public SearchSolutionOfferComplexRespPaginated searchSolutionOffersByPartyGroup(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchSolutionOffersByPartyGroupRequest") SearchPartyGroupRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchSolutionOfferComplexRespPaginated wsResp = new SearchSolutionOfferComplexRespPaginated();
		try {
			SearchServiceResponse resp = SdpSolutionOfferManager.getInstance().searchSolutionOffersByPartyGroup(request.getPartyGroupId(),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.getSolutionOffers().setSolutionOfferList(
						SolutionOfferBeanConverter.convertSolutionOffersWithStatus((List<SdpSolutionOfferDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "modifySolutionOfferPartyGroup")
	@WebResult(name = "ModifySolutionOfferResponse")
	public BaseResp modifySolutionOfferPartyGroup(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "ModifySolutionOfferPartyGroupRequest") ModifySolutionOfferPartyGroupRequest request) {
		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);
		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpSolutionOfferManager.getInstance().modifySolutionOfferPartyGroups(request.getSolutionOfferId(),
					PartyGroupBeanConverter.convertPartyGroupsOperations(request.getPartyGroups().getPartyGroupList()), trim(tenantName.value));
			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "isSolutionOfferSubscribed")
	@WebResult(name = "IsSolutionOfferSubscribedResponse")
	public BaseResp isSolutionOfferSubscribed(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "IsSolutionOfferSubscribedRequest") SearchSolutionOfferRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpSolutionOfferManager.getInstance().isSolutionOfferSubscribed(request.getSolutionOfferId(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchAllSolutionOfferTypes")
	@WebResult(name = "SearchAllSolutionOfferTypesResponse")
	public SearchAllSolutionOfferTypesResp searchAllSolutionOfferTypes(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchAllSolutionOfferTypesResp wsResp = new SearchAllSolutionOfferTypesResp();
		try {
			SearchServiceResponse resp = SdpSolutionOfferManager.getInstance().searchAllSolutionOfferTypes(trim(tenantName));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				if (resp.getSearchResult() != null) {
					wsResp.setSolutionOfferTypeList((List<String>) resp.getSearchResult());
				}
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
}
