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
import com.accenture.sdp.csm.dto.responses.SdpChildPartyDto;
import com.accenture.sdp.csm.dto.responses.SdpParentPartyDto;
import com.accenture.sdp.csm.dto.responses.SdpPartyResponseDto;
import com.accenture.sdp.csm.managers.SdpPartyManager;
import com.accenture.sdp.csm.utilities.FieldConstants;
import com.accenture.sdp.csmfe.webservices.request.party.CreateChildPartyAndCredentialRequest;
import com.accenture.sdp.csmfe.webservices.request.party.CreateChildPartyAndParentDummyRequest;
import com.accenture.sdp.csmfe.webservices.request.party.CreateChildPartyRequest;
import com.accenture.sdp.csmfe.webservices.request.party.CreateParentPartyCompleteRequest;
import com.accenture.sdp.csmfe.webservices.request.party.CreateParentPartyRequest;
import com.accenture.sdp.csmfe.webservices.request.party.DeletePartyRequest;
import com.accenture.sdp.csmfe.webservices.request.party.ModifyChildPartyAndParentDummyRequest;
import com.accenture.sdp.csmfe.webservices.request.party.ModifyChildPartyRequest;
import com.accenture.sdp.csmfe.webservices.request.party.ModifyParentPartyRequest;
import com.accenture.sdp.csmfe.webservices.request.party.ModifyPartyClusterRequest;
import com.accenture.sdp.csmfe.webservices.request.party.PartyChangeStatusRequest;
import com.accenture.sdp.csmfe.webservices.request.party.SearchChildPartyByNameRequest;
import com.accenture.sdp.csmfe.webservices.request.party.SearchChildPartyRequest;
import com.accenture.sdp.csmfe.webservices.request.party.SearchParentPartyRequest;
import com.accenture.sdp.csmfe.webservices.request.party.SearchPartiesByAccountRequest;
import com.accenture.sdp.csmfe.webservices.request.party.SearchPartiesByCredentialRequest;
import com.accenture.sdp.csmfe.webservices.request.party.SearchPartiesBySiteRequest;
import com.accenture.sdp.csmfe.webservices.request.party.SearchPartiesBySubscriptionRequest;
import com.accenture.sdp.csmfe.webservices.request.party.SearchPartyRequest;
import com.accenture.sdp.csmfe.webservices.response.BaseResp;
import com.accenture.sdp.csmfe.webservices.response.party.CreateChildPartyAndParentResp;
import com.accenture.sdp.csmfe.webservices.response.party.CreatePartyResp;
import com.accenture.sdp.csmfe.webservices.response.party.SearchChildPartyResp;
import com.accenture.sdp.csmfe.webservices.response.party.SearchChildPartyRespPaginated;
import com.accenture.sdp.csmfe.webservices.response.party.SearchParentPartyRespPaginated;
import com.accenture.sdp.csmfe.webservices.response.party.SearchPartyResp;
import com.accenture.sdp.csmfe.webservices.utils.CredentialBeanConverter;
import com.accenture.sdp.csmfe.webservices.utils.PartyBeanConverter;
import com.accenture.sdp.csmfe.webservices.utils.PartyGroupBeanConverter;

/**
 * @author elia.furiozzi
 * 
 */
@WebService(serviceName = "SdpPartyService", targetNamespace = "http://com.accenture.sdp.csmfe.webservices")
@SchemaValidation
// JWS annotation that specifies the mapping of the service onto the
// SOAP message protocol. In particular, it specifies that the SOAP messages
// are document literal.
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class SdpPartyService extends BaseWebService {

	@WebMethod(action = "createParentParty")
	@WebResult(name = "CreateParentPartyResponse")
	public CreatePartyResp createParentParty(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "CreateParentPartyRequest") CreateParentPartyRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		CreatePartyResp wsResp = new CreatePartyResp();
		try {
			CreateServiceResponse resp = SdpPartyManager.getInstance().createParentParty(
					trim(request.getPartyName()), trim(request.getPartyDescription()),
					trim(request.getExternalId()), trim(request.getPartyProfile()),
					request.getPartyGroups().getPartyGroupIdList(),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setPartyId(resp.getEntityId());
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "createChildParty")
	@WebResult(name = "CreateChildPartyResponse")
	public CreatePartyResp createChildParty(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "CreateChildPartyRequest") CreateChildPartyRequest request) {

		CreatePartyResp wsResp = new CreatePartyResp();
		try {
			CreateServiceResponse resp = SdpPartyManager.getInstance().createChildParty(
					trim(request.getPartyName()), trim(request.getPartyDescription()),
					request.getParentPartyId(), request.getPartyGroups().getPartyGroupIdList(),
					trim(request.getExternalId()), trim(request.getPartyProfile()),
					trim(request.getFirstName()), trim(request.getLastName()),
					request.getUserDefaultSiteId(), trim(request.getStreetAddress()),
					trim(request.getCity()), trim(request.getZipCode()),
					trim(request.getProvince()), trim(request.getCountry()),
					trim(request.getGender()), request.getBirthDate(),
					trim(request.getBirthProvince()), trim(request.getBirthCountry()),
					trim(request.getBirthCity()), trim(request.getPhoneNumber()),
					trim(request.getFaxNumber()), trim(request.getEmail()),
					trim(request.getNote()),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setPartyId(resp.getEntityId());
			}
		} catch (Exception e) {
			log.logError(e);
		}
		return wsResp;
	}

	@WebMethod(action = "createChildPartyAndCredential")
	@WebResult(name = "CreateChildPartyAndCredentialResponse")
	public CreatePartyResp createChildPartyAndCredential(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "CreateChildPartyAndCredentialRequest") CreateChildPartyAndCredentialRequest request) {

		CreatePartyResp wsResp = new CreatePartyResp();
		try {
			CreateServiceResponse resp = SdpPartyManager.getInstance().createChildPartyAndCredential(
					trim(request.getPartyName()), trim(request.getPartyDescription()),
					request.getParentPartyId(), request.getPartyGroups().getPartyGroupIdList(),
					trim(request.getExternalId()), trim(request.getPartyProfile()),
					trim(request.getFirstName()), trim(request.getLastName()),
					request.getUserDefaultSiteId(), trim(request.getStreetAddress()),
					trim(request.getCity()), trim(request.getZipCode()),
					trim(request.getProvince()), trim(request.getCountry()),
					trim(request.getGender()), request.getBirthDate(),
					trim(request.getBirthProvince()), trim(request.getBirthCountry()),
					trim(request.getBirthCity()), trim(request.getPhoneNumber()),
					trim(request.getFaxNumber()), trim(request.getEmail()),
					trim(request.getNote()), trim(request.getCredential().getUsername()),
					trim(request.getCredential().getPassword()),
					trim(request.getCredential().getUsernameExternalId()),
					CredentialBeanConverter.convertSecretQuestions(request.getCredential().getSecretQuestions().getSecretQuestionList()),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setPartyId(resp.getEntityId());
			}
		} catch (Exception e) {
			log.logError(e);
		}
		return wsResp;
	}

	@WebMethod(action = "createChildPartyAndParentDummy")
	@WebResult(name = "CreateChildPartyAndParentDummyResponse")
	public CreateChildPartyAndParentResp createChildPartyAndParentDummy(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "CreateChildPartyAndParentDummyRequest") CreateChildPartyAndParentDummyRequest request) {

		CreateChildPartyAndParentResp wsResp = new CreateChildPartyAndParentResp();
		try {
			CreateServicesResponse resp = SdpPartyManager.getInstance()
					.createChildPartyAndParentDummy(
							trim(request.getPartyName()),
							trim(request.getPartyDescription()),
							request.getPartyGroups().getPartyGroupIdList(),
							trim(request.getExternalId()),
							trim(request.getPartyProfile()),
							trim(request.getFirstName()),
							trim(request.getLastName()),
							trim(request.getUserDefaultSiteName()),
							trim(request.getStreetAddress()),
							trim(request.getCity()),
							trim(request.getZipCode()),
							trim(request.getProvince()),
							trim(request.getCountry()),
							trim(request.getGender()),
							request.getBirthDate(),
							trim(request.getBirthProvince()),
							trim(request.getBirthCountry()),
							trim(request.getBirthCity()),
							trim(request.getPhoneNumber()),
							trim(request.getFaxNumber()),
							trim(request.getEmail()),
							trim(request.getNote()),
							trim(request.getCredential().getUsername()),
							trim(request.getCredential().getPassword()),
							trim(request.getCredential().getUsernameExternalId()),
							CredentialBeanConverter.convertSecretQuestions(request.getCredential().getSecretQuestions().getSecretQuestionList()),
							PartyBeanConverter.convertSites(request.getSites().getSiteList()),
							trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setPartyChildId(resp.getEntityId().get("partyChildId"));
				wsResp.setPartyParentId(resp.getEntityId().get("partyParentId"));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		return wsResp;
	}

	@WebMethod(action = "createParentPartyComplete")
	@WebResult(name = "CreateParentPartyCompleteResponse")
	public CreatePartyResp createParentPartyComplete(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "CreateParentPartyCompleteRequest") CreateParentPartyCompleteRequest request) {

		CreatePartyResp wsResp = new CreatePartyResp();
		try {
			CreateServiceResponse resp = SdpPartyManager.getInstance().createParentPartyComplete(
					trim(request.getPartyName()), trim(request.getPartyDescription()),
					request.getPartyGroups().getPartyGroupIdList(), trim(request.getExternalId()),
					trim(request.getPartyProfile()),
					trim(request.getCredential().getUsername()),
					trim(request.getCredential().getPassword()),
					trim(request.getCredential().getUsernameExternalId()),
					CredentialBeanConverter.convertSecretQuestions(request.getCredential().getSecretQuestions().getSecretQuestionList()),
					PartyBeanConverter.convertSites(request.getSites().getSiteList()),
					PartyBeanConverter.convertAccounts(request.getAccounts().getAccountList()),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setPartyId(resp.getEntityId());
			}
		} catch (Exception e) {
			log.logError(e);
		}
		return wsResp;
	}

	@WebMethod(action = "cModifyParentParty")
	@WebResult(name = "ModifyParentPartyResponse")
	public BaseResp cModifyParentParty(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "ModifyParentPartyRequest") ModifyParentPartyRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpPartyManager.getInstance().modifyParentParty(
					request.getPartyId(), trim(request.getPartyName()),
					trim(request.getPartyDescription()), trim(request.getExternalId()),
					trim(request.getPartyProfile()),
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


	@WebMethod(action = "modifyChildParty")
	@WebResult(name = "ModifyChildPartyResponse")
	public BaseResp modifyChildParty(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "ModifyChildPartyRequest") ModifyChildPartyRequest request) {

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpPartyManager.getInstance().modifyChildParty(
					request.getPartyId(), trim(request.getPartyName()),
					trim(request.getPartyDescription()), trim(request.getExternalId()),
					trim(request.getPartyProfile()),
					request.getUserDefaultSiteId(), trim(request.getFirstName()),
					trim(request.getLastName()), trim(request.getStreetAddress()),
					trim(request.getCity()), trim(request.getZipCode()),
					trim(request.getProvince()), trim(request.getCountry()),
					trim(request.getGender()), request.getBirthDate(),
					trim(request.getBirthProvince()), trim(request.getBirthCountry()),
					trim(request.getBirthCity()), trim(request.getPhoneNumber()),
					trim(request.getFaxNumber()), trim(request.getEmail()),
					trim(request.getNote()),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		return wsResp;
	}



	@WebMethod(action = "modifyChildPartyAndParentDummy")
	@WebResult(name = "ModifyChildPartyAndParentResponse")
	public BaseResp modifyChildPartyAndParentDummy(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "ModifyChildPartyAndParentDummyRequest") ModifyChildPartyAndParentDummyRequest request) {

		CreateChildPartyAndParentResp wsResp = new CreateChildPartyAndParentResp();
		try {
			DataServiceResponse resp = SdpPartyManager.getInstance().modifyChildPartyAndParentDummy(
					request.getParentPartyId(),
					request.getPartyId(),
					trim(request.getPartyName()),
					trim(request.getPartyDescription()),
					trim(request.getExternalId()),
					trim(request.getPartyProfile()),
					request.getUserDefaultSiteId(),
					trim(request.getFirstName()),
					trim(request.getLastName()),
					trim(request.getStreetAddress()),
					trim(request.getCity()),
					trim(request.getZipCode()),
					trim(request.getProvince()),
					trim(request.getCountry()),
					trim(request.getGender()),
					request.getBirthDate(),
					trim(request.getBirthProvince()),
					trim(request.getBirthCountry()),
					trim(request.getBirthCity()),
					trim(request.getPhoneNumber()),
					trim(request.getFaxNumber()),
					trim(request.getEmail()),
					trim(request.getNote()),
					trim(tenantName.value)
					);

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		return wsResp;
	}
	
	
	@WebMethod(action="modifyPartyCluster")
	@WebResult(name="ModifyPartyClusterResponse")
	public BaseResp modifyPartyCluster(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "ModifyPartyClusterRequest") ModifyPartyClusterRequest request) {
		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);
		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpPartyManager.getInstance().modifyPartyCluster(
					request.getPartyId(),
					PartyGroupBeanConverter.convertPartyGroupsOperations(request.getPartyGroups().getPartyGroupList()),
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


	@WebMethod(action = "cDeleteParty")
	@WebResult(name = "DeletePartyResponse")
	public BaseResp cDeleteParty(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "DeletePartyRequest") DeletePartyRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpPartyManager.getInstance().deleteParty(
					request.getPartyId(),
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

	@WebMethod(action = "searchParentParty")
	@WebResult(name = "SearchParentPartyResponse")
	public SearchParentPartyRespPaginated searchParentParty(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchParentPartyRequest") SearchParentPartyRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchParentPartyRespPaginated wsResp = new SearchParentPartyRespPaginated();
		try {
			SearchServiceResponse resp = SdpPartyManager.getInstance().searchParentParty(
					trim(request.getPartyName()), request.getPartyGroupId(), request.getStartPosition(),
					request.getMaxRecordsNumber(),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.getParties().setPartyList(PartyBeanConverter
						.convertParentParties((List<SdpParentPartyDto>) resp.getSearchResult()));
				wsResp.setTotalResult(resp.getTotalResult());
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchChildParty")
	@WebResult(name = "SearchChildPartyResponse")
	public SearchChildPartyRespPaginated searchChildParty(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchChildPartyRequest") SearchChildPartyRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchChildPartyRespPaginated wsResp = new SearchChildPartyRespPaginated();
		try {
			SearchServiceResponse resp = SdpPartyManager.getInstance().searchChildParty(
					request.getParentPartyId(), trim(request.getPartyName()),
					request.getStartPosition(), request.getMaxRecordsNumber(),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.getParties().setPartyList(PartyBeanConverter
						.convertChildParties((List<SdpChildPartyDto>) resp.getSearchResult()));
				wsResp.setTotalResult(resp.getTotalResult());
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchChildPartyByName")
	@WebResult(name = "SearchChildPartyByNameResponse")
	public SearchChildPartyRespPaginated searchChildPartyByName(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchChildPartyByNameRequest") SearchChildPartyByNameRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchChildPartyRespPaginated wsResp = new SearchChildPartyRespPaginated();
		try {
			SearchServiceResponse resp = SdpPartyManager.getInstance()
					.searchChildPartyByName(
							trim(request.getFirstName()), trim(request.getLastName()),
							trim(request.getPartyName()),
							trim(request.getParentPartyName()),
							request.getStartPosition(), request.getMaxRecordsNumber(),
							trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.getParties().setPartyList(PartyBeanConverter
						.convertChildParties((List<SdpChildPartyDto>) resp.getSearchResult()));
				wsResp.setTotalResult(resp.getTotalResult());
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}


	@WebMethod(action = "searchPartiesByCredential")
	@WebResult(name = "SearchPartiesByCredentialResponse")
	public SearchChildPartyResp searchPartiesByCredential(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchPartiesByCredentialRequest") SearchPartiesByCredentialRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchChildPartyResp wsResp = new SearchChildPartyResp();
		try {
			SearchServiceResponse resp = SdpPartyManager.getInstance().searchPartyByCredential(
					trim(request.getUsername()),
					request.getPartyGroupId(),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.getParties().setPartyList(PartyBeanConverter
						.convertChildParties((List<SdpChildPartyDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}


	@WebMethod(action = "searchPartiesByAccount")
	@WebResult(name = "SearchPartiesByAccountResponse")
	public SearchChildPartyResp searchPartiesByAccount(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchPartiesByAccountRequest") SearchPartiesByAccountRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchChildPartyResp wsResp = new SearchChildPartyResp();
		try {
			SearchServiceResponse resp = SdpPartyManager.getInstance().searchPartyByAccount(
					trim(request.getAccountName()),
					request.getPartyGroupId(),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.getParties().setPartyList(PartyBeanConverter
						.convertChildParties((List<SdpChildPartyDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchPartiesBySite")
	@WebResult(name = "SearchPartiesBySiteResponse")
	public SearchChildPartyRespPaginated searchPartiesBySite(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchPartiesBySiteRequest") SearchPartiesBySiteRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchChildPartyRespPaginated wsResp = new SearchChildPartyRespPaginated();
		try {
			SearchServiceResponse resp = SdpPartyManager.getInstance().searchPartiesBySite(
					request.getSiteId(), request.getPartyGroupId(), request.getStartPosition(),
					request.getMaxRecordsNumber(),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.getParties().setPartyList(PartyBeanConverter
						.convertChildParties((List<SdpChildPartyDto>) resp.getSearchResult()));
				wsResp.setTotalResult(resp.getTotalResult());
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchPartiesBySubscription")
	@WebResult(name = "SearchPartiesBySubscriptionResponse")
	public SearchChildPartyResp searchPartiesBySubscription(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchPartiesBySubscriptionRequest") SearchPartiesBySubscriptionRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchChildPartyResp wsResp = new SearchChildPartyResp();
		try {
			SearchServiceResponse resp = SdpPartyManager.getInstance().searchPartyBySubscription(
					request.getSubscriptionId(),
					request.getPartyGroupId(),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.getParties().setPartyList(PartyBeanConverter
						.convertChildParties((List<SdpChildPartyDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "partyChangeStatus")
	@WebResult(name = "PartyChangeStatusResponse")
	public BaseResp partyChangeStatus(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "PartyChangeStatusRequest") PartyChangeStatusRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpPartyManager.getInstance().partyChangeStatus(
					request.getPartyId(), trim(request.getStatus()),
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

	@WebMethod(action = "searchParty")
	@WebResult(name = "SearchPartyResponse")
	public SearchPartyResp searchParty(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchPartyRequest") SearchPartyRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchPartyResp wsResp = new SearchPartyResp();
		try {
			SearchServiceResponse resp = SdpPartyManager.getInstance().searchParty(
					request.getPartyId(),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				if (resp.getSearchResult()!=null && resp.getSearchResult().size()>0) {
					List<SdpPartyResponseDto> temp = (List<SdpPartyResponseDto>) resp.getSearchResult();
					wsResp.setParty(PartyBeanConverter.convertParty(temp.get(0)));
				}
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
}
