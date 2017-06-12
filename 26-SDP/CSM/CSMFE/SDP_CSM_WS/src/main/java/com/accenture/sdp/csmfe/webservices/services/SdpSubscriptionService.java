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
import com.accenture.sdp.csm.dto.CreateServicesResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.responses.SdpSubscriptionDetailResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpSubscriptionResponseDto;
import com.accenture.sdp.csm.managers.SdpSubscriptionManager;
import com.accenture.sdp.csm.utilities.FieldConstants;
import com.accenture.sdp.csmfe.webservices.request.subscription.CreatePartyAndSubscriptionRequest;
import com.accenture.sdp.csmfe.webservices.request.subscription.CreateSubscriptionComplexRequest;
import com.accenture.sdp.csmfe.webservices.request.subscription.CreateSubscriptionDetailRequest;
import com.accenture.sdp.csmfe.webservices.request.subscription.CreateSubscriptionRequest;
import com.accenture.sdp.csmfe.webservices.request.subscription.DeleteOfferInSubscriptionRequest;
import com.accenture.sdp.csmfe.webservices.request.subscription.ModifySubscriptionComplexRequest;
import com.accenture.sdp.csmfe.webservices.request.subscription.ModifySubscriptionRequest;
import com.accenture.sdp.csmfe.webservices.request.subscription.OfferInSubscriptionChangeStatusRequest;
import com.accenture.sdp.csmfe.webservices.request.subscription.SearchSubscriptionDetailRequest;
import com.accenture.sdp.csmfe.webservices.request.subscription.SearchSubscriptionRequest;
import com.accenture.sdp.csmfe.webservices.request.subscription.SearchSubscriptionsByAccountRequest;
import com.accenture.sdp.csmfe.webservices.request.subscription.SearchSubscriptionsByCredentialRequest;
import com.accenture.sdp.csmfe.webservices.request.subscription.SearchSubscriptionsByParentSubscriptionRequest;
import com.accenture.sdp.csmfe.webservices.request.subscription.SearchSubscriptionsByPartyRequest;
import com.accenture.sdp.csmfe.webservices.request.subscription.SearchSubscriptionsBySiteRequest;
import com.accenture.sdp.csmfe.webservices.request.subscription.SubscriptionChangeStatusRequest;
import com.accenture.sdp.csmfe.webservices.response.BaseResp;
import com.accenture.sdp.csmfe.webservices.response.subscription.CreatePartyAndSubscriptionResp;
import com.accenture.sdp.csmfe.webservices.response.subscription.CreateSubscriptionResp;
import com.accenture.sdp.csmfe.webservices.response.subscription.SearchSubscriptionByPartyResp;
import com.accenture.sdp.csmfe.webservices.response.subscription.SearchSubscriptionComplexResp;
import com.accenture.sdp.csmfe.webservices.response.subscription.SearchSubscriptionComplexRespPaginated;
import com.accenture.sdp.csmfe.webservices.response.subscription.SearchSubscriptionDetailResp;
import com.accenture.sdp.csmfe.webservices.response.subscription.SearchSubscriptionResp;
import com.accenture.sdp.csmfe.webservices.utils.CredentialBeanConverter;
import com.accenture.sdp.csmfe.webservices.utils.PartyBeanConverter;
import com.accenture.sdp.csmfe.webservices.utils.SubscriptionBeanConverter;

/**
 * @author elia.furiozzi
 * 
 */
@WebService(serviceName = "SdpSubscriptionService", targetNamespace = "http://com.accenture.sdp.csmfe.webservices")
@SchemaValidation
//JWS annotation that specifies the mapping of the service onto the
//SOAP message protocol. In particular, it specifies that the SOAP messages
//are document literal.
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class SdpSubscriptionService extends BaseWebService {

	@WebMethod(action = "cCreateSubscription")
	@WebResult(name = "CreateSubscriptionResponse")
	public CreateSubscriptionResp cCreateSubscription(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "CreateSubscriptionRequest") CreateSubscriptionRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		CreateSubscriptionResp wsResp = new CreateSubscriptionResp();
		try {
			CreateServiceResponse resp = SdpSubscriptionManager.getInstance().createSubscription(
					request.getPartyId(),
					request.getSolutionOfferId(),
					request.getParentSubscriptionId(),
					trim(request.getUsername()),
					trim(request.getRoleName()),
					request.getOwnerId(),
					request.getPayeeId(),
					request.getSiteId(),
					trim(request.getExternalId()),
					request.getStartDate(),
					request.getEndDate(),
					trim(tenantName.value));
			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setSubscriptionId(resp.getEntityId());
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}


	@WebMethod(action = "cCreateSubscriptionDetail")
	@WebResult(name = "CreateSubscriptionDetailResponse")
	public BaseResp cCreateSubscriptionDetail(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "CreateSubscriptionDetailRequest") CreateSubscriptionDetailRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			CreateServiceResponse resp = SdpSubscriptionManager.getInstance().createSubscriptionDetail(
					request.getSubscriptionId(),
					request.getPackageId(),
					trim(request.getSubscriptionOfferProfile()),
					trim(request.getExternalId()),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}


	@WebMethod(action = "createSubscription")
	@WebResult(name = "CreateSubscriptionResponse")
	public CreateSubscriptionResp createSubscription(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "CreateSubscriptionRequest") CreateSubscriptionComplexRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		CreateSubscriptionResp wsResp = new CreateSubscriptionResp();
		try {
			CreateServiceResponse resp = SdpSubscriptionManager.getInstance().createSubscription(
					request.getPartyId(),
					request.getSolutionOfferId(),
					request.getParentSubscriptionId(),
					trim(request.getUsername()),
					trim(request.getRoleName()),
					request.getOwnerId(),
					request.getPayeeId(),
					request.getSiteId(),
					trim(request.getExternalId()),
					request.getStartDate(),
					request.getEndDate(),
					SubscriptionBeanConverter.convertSubscriptionDetailReq(request.getSubscriptionDetails().getSubscriptionDetailList()),
					trim(tenantName.value));
			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setSubscriptionId(resp.getEntityId());
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
	

	@WebMethod(action = "createPartyAndSubscription")
	@WebResult(name = "CreatePartyAndSubscriptionResponse")
	public CreatePartyAndSubscriptionResp createPartyAndSubscription(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "CreateSubscriptionRequest") CreatePartyAndSubscriptionRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		CreatePartyAndSubscriptionResp wsResp = new CreatePartyAndSubscriptionResp();
		try {
			CreateServicesResponse resp = SdpSubscriptionManager.getInstance().createPartyAndSubscription(
					trim(request.getPartyName()),
					trim(request.getPartyDescription()),
					request.getPartyGroups().getPartyGroupIdList(),
					trim(request.getExternalId()),
					trim(request.getPartyProfile()),
					trim(request.getCredential().getUsername()),
					trim(request.getCredential().getPassword()),
					trim(request.getCredential().getUsernameExternalId()),
					CredentialBeanConverter.convertSecretQuestions(request.getCredential().getSecretQuestions().getSecretQuestionList()),
					PartyBeanConverter.convertSites(request.getSites().getSiteList()),
					PartyBeanConverter.convertAccounts(request.getAccounts().getAccountList()),
					SubscriptionBeanConverter.convertSubscriptionReq(request.getSubscription()),
					SubscriptionBeanConverter.convertSubscriptionDetailReq(request.getSubscription().getSubscriptionDetails().getSubscriptionDetailList()),
					trim(tenantName.value));
			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setSubscriptionId(resp.getEntityId().get("subscriptionId"));
				wsResp.setPartyId(resp.getEntityId().get("partyId"));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
	
	
	@WebMethod(action = "cModifySubscription")
	@WebResult(name="ModifySubscriptionResponse")
	public BaseResp cModifySubscription(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "ModifySubscriptionRequest") ModifySubscriptionRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpSubscriptionManager.getInstance().modifySubscription(
					request.getSubscriptionId(),
					request.getParentSubscriptionId(),
					trim(request.getUsername()),
					trim(request.getRoleName()),
					request.getOwnerId(),
					request.getPayeeId(),
					request.getSiteId(),
					trim(request.getExternalId()),
					request.getStartDate(),
					request.getEndDate(),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}


	@WebMethod(action = "cModifySubscriptionDetail")
	@WebResult(name="ModifySubscriptionDetailResponse")
	public BaseResp cModifySubscriptionDetail(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "ModifySubscriptionDetailRequest") CreateSubscriptionDetailRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpSubscriptionManager.getInstance().modifySubscriptionDetail(
					request.getSubscriptionId(),
					request.getPackageId(),
					trim(request.getSubscriptionOfferProfile()),
					trim(request.getExternalId()),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	
	@WebMethod(action = "modifySubscription")
	@WebResult(name="ModifySubscriptionResponse")
	public BaseResp modifySubscription(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "ModifySubscriptionRequest") ModifySubscriptionComplexRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpSubscriptionManager.getInstance().modifySubscription(
					request.getSubscriptionId(), 
					request.getParentSubscriptionId(),
					trim(request.getUsername()),
					trim(request.getRoleName()),
					request.getOwnerId(),
					request.getPayeeId(),
					request.getSiteId(),
					trim(request.getExternalId()),
					request.getStartDate(),
					request.getEndDate(),
					SubscriptionBeanConverter.convertSubscriptionDetailReq(request.getSubscriptionDetails().getSubscriptionDetailList()),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
	

	@WebMethod(action = "deleteSubscription")
	@WebResult(name="DeleteSubscriptionResponse")
	public BaseResp deleteSubscription(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "DeleteSubscriptionRequest") SearchSubscriptionRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpSubscriptionManager.getInstance().deleteSubscription(
					request.getSubscriptionId(),
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


	@WebMethod(action = "deleteOfferInSubscription")
	@WebResult(name="DeleteOfferInSubscriptionResponse")
	public BaseResp deleteOfferInSubscription(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "DeleteOfferInSubscriptionRequest") DeleteOfferInSubscriptionRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpSubscriptionManager.getInstance().deleteOfferInSubscription(
					request.getSubscriptionId(),
					request.getPackages().getPackageIdList(),
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

	@WebMethod(action = "cSearchSubscription")
	@WebResult(name="SearchSubscriptionResponse")
	public SearchSubscriptionResp cSearchSubscription(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchSubscriptionRequest") SearchSubscriptionRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchSubscriptionResp wsResp = new SearchSubscriptionResp();
		try {
			SearchServiceResponse resp = SdpSubscriptionManager.getInstance().searchSubscription(request.getSubscriptionId(),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);

				wsResp.getSubscriptions().setSubscriptionList(SubscriptionBeanConverter.convertSubscriptions((List<SdpSubscriptionResponseDto>)resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
	
	

	@WebMethod(action = "cSearchSubscriptionDetail")
	@WebResult(name="SearchSubscriptionDetailResponse")
	public SearchSubscriptionDetailResp cSearchSubscriptionDetail(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchSubscriptionDetailRequest") SearchSubscriptionDetailRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchSubscriptionDetailResp wsResp = new SearchSubscriptionDetailResp();
		try {
			SearchServiceResponse resp = SdpSubscriptionManager.getInstance().searchSubscriptionDetail(request.getSubscriptionId(),
					request.getPackageId(),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				
				wsResp.getSubscriptionsDetail().setSubscriptionDetailList(SubscriptionBeanConverter.convertSubscriptionDetailResp((List<SdpSubscriptionDetailResponseDto>)resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	
	@WebMethod(action = "searchSubscription")
	@WebResult(name="SearchSubscriptionResponse")
	public SearchSubscriptionComplexResp searchSubscription(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchSubscriptionRequest") SearchSubscriptionRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchSubscriptionComplexResp wsResp = new SearchSubscriptionComplexResp();
		try {
			SearchServiceResponse resp = SdpSubscriptionManager.getInstance().searchSubscriptionAndDetails(request.getSubscriptionId(),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				
				wsResp.getSubscriptions().setSubscriptionList(SubscriptionBeanConverter.convertSubscriptionsComplex((List<SdpSubscriptionResponseDto>)resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchSubscriptionsByParty")
	@WebResult(name="SearchSubscriptionByPartyResponse")
	public SearchSubscriptionByPartyResp searchSubscriptionsByParty(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchSubscriptionByPartyRequest") SearchSubscriptionsByPartyRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchSubscriptionByPartyResp wsResp = new SearchSubscriptionByPartyResp();
		try {
			SearchServiceResponse resp = SdpSubscriptionManager.getInstance().searchSubscriptionsByParty(
					request.getPartyId(), request.getStartPosition(), request.getMaxRecordsNumber(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				wsResp.getSubscriptions().setSubscriptionList(SubscriptionBeanConverter.convertSubscriptionsByParty((List<SdpSubscriptionResponseDto>)resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
	
	@WebMethod(action = "searchSubscriptionsByPartyLight")
	@WebResult(name="SearchSubscriptionByPartyLightResponse")
	public SearchSubscriptionByPartyResp searchSubscriptionsByPartyLight(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchSubscriptionByPartyLightRequest") SearchSubscriptionsByPartyRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchSubscriptionByPartyResp wsResp = new SearchSubscriptionByPartyResp();
		try {
			SearchServiceResponse resp = SdpSubscriptionManager.getInstance().searchSubscriptionsByPartyLight(
					request.getPartyId(), request.getStartPosition(), request.getMaxRecordsNumber(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				wsResp.getSubscriptions().setSubscriptionList(SubscriptionBeanConverter.convertSubscriptionsByParty((List<SdpSubscriptionResponseDto>)resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}


	@WebMethod(action = "searchSubscriptionsByParentSubscription")
	@WebResult(name="SearchSubscriptionsByParentSubscriptionResponse")
	public SearchSubscriptionComplexRespPaginated searchSubscriptionsByParentSubscription(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchSubscriptionsByParentSubscriptionRequest") SearchSubscriptionsByParentSubscriptionRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchSubscriptionComplexRespPaginated wsResp = new SearchSubscriptionComplexRespPaginated();
		try {
			SearchServiceResponse resp = SdpSubscriptionManager.getInstance().searchSubscriptionsByParentSubscription(
					request.getParentSubscriptionId(), request.getStartPosition(), request.getMaxRecordsNumber(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				wsResp.getSubscriptions().setSubscriptionList(SubscriptionBeanConverter.convertSubscriptionsComplex((List<SdpSubscriptionResponseDto>)resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchSubscriptionsByParentSubscriptionLight")
	@WebResult(name="SearchSubscriptionsByParentSubscriptionLightResponse")
	public SearchSubscriptionComplexRespPaginated searchSubscriptionsByParentSubscriptionLight(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchSubscriptionsByParentSubscriptionLightRequest") SearchSubscriptionsByParentSubscriptionRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchSubscriptionComplexRespPaginated wsResp = new SearchSubscriptionComplexRespPaginated();
		try {
			SearchServiceResponse resp = SdpSubscriptionManager.getInstance().searchSubscriptionsByParentSubscriptionLight(
					request.getParentSubscriptionId(), request.getStartPosition(), request.getMaxRecordsNumber(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				wsResp.getSubscriptions().setSubscriptionList(SubscriptionBeanConverter.convertSubscriptionsComplex((List<SdpSubscriptionResponseDto>)resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}


	@WebMethod(action = "searchSubscriptionsByCredential")
	@WebResult(name="SearchSubscriptionsByCredentialResponse")
	public SearchSubscriptionComplexRespPaginated searchSubscriptionsByCredential(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchSubscriptionsByCredentialRequest") SearchSubscriptionsByCredentialRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchSubscriptionComplexRespPaginated wsResp = new SearchSubscriptionComplexRespPaginated();
		try {
			SearchServiceResponse resp = SdpSubscriptionManager.getInstance().searchSubscriptionsByCredential(
					trim(request.getUsername()), request.getStartPosition(), request.getMaxRecordsNumber(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				wsResp.getSubscriptions().setSubscriptionList(SubscriptionBeanConverter.convertSubscriptionsComplex((List<SdpSubscriptionResponseDto>)resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
	
	@WebMethod(action = "searchSubscriptionsByCredentialLight")
	@WebResult(name="SearchSubscriptionsByCredentialLightResponse")
	public SearchSubscriptionComplexRespPaginated searchSubscriptionsByCredentialLight(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchSubscriptionsByCredentialLightRequest") SearchSubscriptionsByCredentialRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchSubscriptionComplexRespPaginated wsResp = new SearchSubscriptionComplexRespPaginated();
		try {
			SearchServiceResponse resp = SdpSubscriptionManager.getInstance().searchSubscriptionsByCredentialLight(
					trim(request.getUsername()), request.getStartPosition(), request.getMaxRecordsNumber(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				wsResp.getSubscriptions().setSubscriptionList(SubscriptionBeanConverter.convertSubscriptionsComplex((List<SdpSubscriptionResponseDto>)resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
	
	
	@WebMethod(action = "searchSubscriptionsByAccount")
	@WebResult(name="SearchSubscriptionsByAccountResponse")
	public SearchSubscriptionComplexRespPaginated searchSubscriptionsByAccount(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchSubscriptionsByAccountRequest") SearchSubscriptionsByAccountRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchSubscriptionComplexRespPaginated wsResp = new SearchSubscriptionComplexRespPaginated();
		try {
			SearchServiceResponse resp = SdpSubscriptionManager.getInstance().searchSubscriptionsByAccount(
					trim(request.getOwnerAccountName()),
					trim(request.getPayeeAccountName()),
					request.getStartPosition(),
					request.getMaxRecordsNumber(),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				wsResp.getSubscriptions().setSubscriptionList(SubscriptionBeanConverter.convertSubscriptionsComplex((List<SdpSubscriptionResponseDto>)resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
	
	@WebMethod(action = "searchSubscriptionsByAccountLight")
	@WebResult(name="SearchSubscriptionsByAccountLightResponse")
	public SearchSubscriptionComplexRespPaginated searchSubscriptionsByAccountLight(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchSubscriptionsByAccountLightRequest") SearchSubscriptionsByAccountRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchSubscriptionComplexRespPaginated wsResp = new SearchSubscriptionComplexRespPaginated();
		try {
			SearchServiceResponse resp = SdpSubscriptionManager.getInstance().searchSubscriptionsByAccountLight(
					trim(request.getOwnerAccountName()),
					trim(request.getPayeeAccountName()),
					request.getStartPosition(),
					request.getMaxRecordsNumber(),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				wsResp.getSubscriptions().setSubscriptionList(SubscriptionBeanConverter.convertSubscriptionsComplex((List<SdpSubscriptionResponseDto>)resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchSubscriptionsBySite")
	@WebResult(name="SearchSubscriptionsBySiteResponse")
	public SearchSubscriptionComplexRespPaginated searchSubscriptionsBySite(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchSubscriptionsBySiteRequest") SearchSubscriptionsBySiteRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchSubscriptionComplexRespPaginated wsResp = new SearchSubscriptionComplexRespPaginated();
		try {
			SearchServiceResponse resp = SdpSubscriptionManager.getInstance().searchSubscriptionsBySite(
					trim(request.getSiteName()), request.getStartPosition(), request.getMaxRecordsNumber(),
					trim(tenantName.value));		

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				wsResp.getSubscriptions().setSubscriptionList(SubscriptionBeanConverter.convertSubscriptionsComplex((List<SdpSubscriptionResponseDto>)resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
	
	@WebMethod(action = "searchSubscriptionsBySiteLight")
	@WebResult(name="SearchSubscriptionsBySiteLightResponse")
	public SearchSubscriptionComplexRespPaginated searchSubscriptionsBySiteLight(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchSubscriptionsBySiteLightRequest") SearchSubscriptionsBySiteRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchSubscriptionComplexRespPaginated wsResp = new SearchSubscriptionComplexRespPaginated();
		try {
			SearchServiceResponse resp = SdpSubscriptionManager.getInstance().searchSubscriptionsBySiteLight(
					trim(request.getSiteName()), request.getStartPosition(), request.getMaxRecordsNumber(),
					trim(tenantName.value));		

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				wsResp.getSubscriptions().setSubscriptionList(SubscriptionBeanConverter.convertSubscriptionsComplex((List<SdpSubscriptionResponseDto>)resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
	
	@WebMethod(action = "subscriptionChangeStatus")
	@WebResult(name="SubscriptionChangeStatusResponse")
	public BaseResp subscriptionChangeStatus(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SubscriptionChangeStatusRequest") SubscriptionChangeStatusRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpSubscriptionManager.getInstance().subscriptionChangeStatus(
					request.getSubscriptionId(),
					trim(request.getStatusName()),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	
	@WebMethod(action = "offerInSubscriptionChangeStatus")
	@WebResult(name="OfferInSubscriptionChangeStatusResponse")
	public BaseResp offerInSubscriptionChangeStatus(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "OfferInSubscriptionChangeStatusRequest") OfferInSubscriptionChangeStatusRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpSubscriptionManager.getInstance().offerInSubscriptionChangeStatus(
					trim(request.getStatusName()),
					request.getSubscriptionId(),
					request.getPackages().getPackageIdList(),
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
