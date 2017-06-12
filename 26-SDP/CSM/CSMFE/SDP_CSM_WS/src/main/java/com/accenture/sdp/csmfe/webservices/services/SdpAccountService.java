package com.accenture.sdp.csmfe.webservices.services;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebParam.Mode;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Holder;

import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.responses.SdpAccountResponseDto;
import com.accenture.sdp.csm.managers.SdpAccountManager;
import com.accenture.sdp.csm.utilities.FieldConstants;
import com.accenture.sdp.csmfe.webservices.request.account.AccountChangeStatusRequest;
import com.accenture.sdp.csmfe.webservices.request.account.CreateAccountRequest;
import com.accenture.sdp.csmfe.webservices.request.account.DeleteAccountRequest;
import com.accenture.sdp.csmfe.webservices.request.account.ModifyAccountRequest;
import com.accenture.sdp.csmfe.webservices.request.account.SearchAccountRequest;
import com.accenture.sdp.csmfe.webservices.request.account.SearchAccountsByPartyRequest;
import com.accenture.sdp.csmfe.webservices.request.account.SearchAccountsBySiteRequest;
import com.accenture.sdp.csmfe.webservices.request.account.SearchAccountsBySubscriptionRequest;
import com.accenture.sdp.csmfe.webservices.response.BaseResp;
import com.accenture.sdp.csmfe.webservices.response.account.CreateAccountResp;
import com.accenture.sdp.csmfe.webservices.response.account.SearchAccountsBySubscriptionResp;
import com.accenture.sdp.csmfe.webservices.response.account.SearchAccountsResp;
import com.accenture.sdp.csmfe.webservices.response.account.SearchAccountsRespPaginated;
import com.accenture.sdp.csmfe.webservices.utils.AccountBeanConverter;
import org.apache.cxf.annotations.SchemaValidation;

@WebService(serviceName = "SdpAccountService", targetNamespace = "http://com.accenture.sdp.csmfe.webservices")
@SchemaValidation
// JWS annotation that specifies the mapping of the service onto the
// SOAP message protocol. In particular, it specifies that the SOAP messages
// are document literal.
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class SdpAccountService extends BaseWebService {

	@WebMethod(action="cCreateAccount")
	@WebResult(name = "CreateAccountResponse")
	public CreateAccountResp cCreateAccount(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "CreateAccountRequest") CreateAccountRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);
		CreateAccountResp wsResp = new CreateAccountResp();

		try {
			CreateServiceResponse resp = SdpAccountManager.getInstance().createAccount(
					trim(request.getAccountName()),
					trim(request.getAccountDescription()), 
					request.isDefaultAccount(),
					request.getPartyId(), 
					request.getSiteId(), 
					trim(request.getExternalId()),
					trim(request.getAccountProfile()),
					trim(tenantName.value)
			);
			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setAccountId(resp.getEntityId());
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action="cModifyAccount")
	@WebResult(name="ModifyAccountResponse")
	public BaseResp cModifyAccount(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "ModifyAccountRequest") ModifyAccountRequest request) {
		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);
		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpAccountManager.getInstance().modifyAccount(
					request.getAccountId(), 
					trim(request.getAccountName()), 
					trim(request.getAccountDescription()), 
					request.isDefaultAccount(), 
					request.getPartyId(), 
					request.getSiteId(), 
					trim(request.getExternalId()), 
					trim(request.getAccountProfile()),
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
	
	@WebMethod(action="cDeleteAccount")
	@WebResult(name="DeleteAccountResponse")
	public BaseResp cDeleteAccount(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "DeleteAccountRequest") DeleteAccountRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpAccountManager.getInstance().deleteAccount(
					request.getAccountId(),
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
	
	@WebMethod(action="searchAccount")
	@WebResult(name="SearchAccountResponse")
	public SearchAccountsResp searchAccount(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchAccountRequest") SearchAccountRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchAccountsResp wsResp = new SearchAccountsResp();
		try {
			SearchServiceResponse resp = SdpAccountManager.getInstance().searchAccount(
					trim(request.getAccountName()),
					trim(tenantName.value)
					);
			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.getAccounts().setAccountList(AccountBeanConverter.convertAccounts((List<SdpAccountResponseDto>)resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
	
	@WebMethod(action="searchAccountsBySubscription")
	@WebResult(name="SearchAccountsBySubscriptionResponse")
	public SearchAccountsBySubscriptionResp searchAccountsBySubscription(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchAccountsBySubscriptionRequest") SearchAccountsBySubscriptionRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchAccountsBySubscriptionResp wsResp = new SearchAccountsBySubscriptionResp();
		try {
			SearchServiceResponse resp = SdpAccountManager.getInstance().searchAccountsBySubscription(
					request.getSubscriptionId(),
					trim(tenantName.value)
					);

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.getAccounts().setAccountList(AccountBeanConverter.convertAccountsWhithSupbscription((List<SdpAccountResponseDto>)resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
	
	@WebMethod(action="searchAccountsByParty")
	@WebResult(name="SearchAccountsByParty")
	public SearchAccountsRespPaginated searchAccountsByParty(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchAccountsByPartyRequest") SearchAccountsByPartyRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchAccountsRespPaginated wsResp = new SearchAccountsRespPaginated();
		try {
			SearchServiceResponse resp = SdpAccountManager.getInstance().searchAccountsByParty(
					request.getPartyId(),
					request.getStartPosition(),
					request.getMaxRecordsNumber(),
					trim(tenantName.value)
					);

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				wsResp.getAccounts().setAccountList(AccountBeanConverter.convertAccountsProper((List<SdpAccountResponseDto>)resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
	
	@WebMethod(action="searchAccountsBySite")
	@WebResult(name="SearchAccountsBySite")
	public SearchAccountsRespPaginated searchAccountsBySite(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchAccountsBySiteRequest") SearchAccountsBySiteRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchAccountsRespPaginated wsResp = new SearchAccountsRespPaginated();
		try {
			SearchServiceResponse resp = SdpAccountManager.getInstance().searchAccountsBySite(
					request.getSiteId(),
					request.getStartPosition(),
					request.getMaxRecordsNumber(),
					trim(tenantName.value)
					);

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				wsResp.getAccounts().setAccountList(AccountBeanConverter.convertAccounts((List<SdpAccountResponseDto>)resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
	
	@WebMethod(action="accountChangeStatus")
	@WebResult(name="AccountChangeStatusResponse")
	public BaseResp accountChangeStatus(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "AccountChangeStatusRequest") AccountChangeStatusRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse  resp = SdpAccountManager.getInstance().accountChangeStatus(
					request.getAccountId(),
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