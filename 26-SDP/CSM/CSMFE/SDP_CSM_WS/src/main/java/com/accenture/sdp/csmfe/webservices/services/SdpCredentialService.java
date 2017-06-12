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
import com.accenture.sdp.csm.dto.ResetPwdServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.responses.SdpCredentialDto;
import com.accenture.sdp.csm.managers.SdpCredentialManager;
import com.accenture.sdp.csm.utilities.FieldConstants;
import com.accenture.sdp.csmfe.webservices.request.credential.CheckCredentialsRequest;
import com.accenture.sdp.csmfe.webservices.request.credential.CreateCredentialsRequest;
import com.accenture.sdp.csmfe.webservices.request.credential.CredentialChangeStatusRequest;
import com.accenture.sdp.csmfe.webservices.request.credential.DeleteCredentialsRequest;
import com.accenture.sdp.csmfe.webservices.request.credential.ModifyCredentialsRequest;
import com.accenture.sdp.csmfe.webservices.request.credential.ReserveUsernameRequest;
import com.accenture.sdp.csmfe.webservices.request.credential.ResetPasswordRequest;
import com.accenture.sdp.csmfe.webservices.request.credential.SearchCredentialBySubscriptionRequest;
import com.accenture.sdp.csmfe.webservices.request.credential.SearchCredentialsByPartyRequest;
import com.accenture.sdp.csmfe.webservices.request.credential.SearchCredentialsRequest;
import com.accenture.sdp.csmfe.webservices.response.BaseResp;
import com.accenture.sdp.csmfe.webservices.response.credential.ReserveUsernameResp;
import com.accenture.sdp.csmfe.webservices.response.credential.ResetPasswordResp;
import com.accenture.sdp.csmfe.webservices.response.credential.SearchCredentialsResp;
import com.accenture.sdp.csmfe.webservices.response.credential.SearchCredentialsRespPaginated;
import com.accenture.sdp.csmfe.webservices.utils.CredentialBeanConverter;

/**
 * @author elia.furiozzi
 *
 */

@WebService(serviceName = "SdpCredentialService", targetNamespace = "http://com.accenture.sdp.csmfe.webservices")
@SchemaValidation
//JWS annotation that specifies the mapping of the service onto the
//SOAP message protocol. In particular, it specifies that the SOAP messages
//are document literal.
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class SdpCredentialService extends BaseWebService {
	
	@WebMethod(action = "createCredential")
	@WebResult(name="CreateCredentialResponse")
	public BaseResp createCredential(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "CreateCredentialRequest") CreateCredentialsRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			CreateServiceResponse resp = SdpCredentialManager.getInstance().createCredential(
					trim(request.getUsername()),
					trim(request.getPassword()),
					request.getPartyId(),
					trim(request.getExternalId()),
					trim(request.getRoleName()),
					CredentialBeanConverter.convertSecretQuestions(request.getSecretQuestions().getSecretQuestionList()),
					trim(tenantName.value)
					);

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				// id non tornato nella response perche' e' lo username di input
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	
	@WebMethod(action = "modifyCredential")
	@WebResult(name="ModifyCredentialResponse")
	public BaseResp modifyCredential(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "ModifyCredentialRequest") ModifyCredentialsRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpCredentialManager.getInstance().modifyCredential(
					trim(request.getUsername()),
					trim(request.getOldPassword()),
					trim(request.getNewPassword()),
					trim(request.getConfirmNewPassword()),
					trim(request.getExternalId()),
					CredentialBeanConverter.convertSecretQuestionsModify(request.getSecretQuestions().getSecretQuestionList()),
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
	
	
	@WebMethod(action = "cDeleteCredential")
	@WebResult(name="DeleteCredentialResponse")
	public BaseResp cDeleteCredential(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "DeleteCredentialRequest") DeleteCredentialsRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpCredentialManager.getInstance().deleteCredential(
					trim(request.getUsername()),
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
	
	
	@WebMethod(action = "searchCredential")
	@WebResult(name="SearchCredentialResponse")
	public SearchCredentialsResp searchCredential(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchCredentialRequest") SearchCredentialsRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchCredentialsResp wsResp = new SearchCredentialsResp();
		try {
			SearchServiceResponse resp = SdpCredentialManager.getInstance().searchCredential(
					trim(request.getUsername()),
					trim(tenantName.value)
					);

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.getCredentials().setCredentialList(CredentialBeanConverter.convertCredentials((List<SdpCredentialDto>)resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
	
	
	@WebMethod(action = "searchCredentialBySubscription")
	@WebResult(name="SearchCredentialBySubscriptionResponse")
	public SearchCredentialsResp searchCredentialBySubscription(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchCredentialBySubscriptionRequest") SearchCredentialBySubscriptionRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchCredentialsResp wsResp = new SearchCredentialsResp();
		try {
			SearchServiceResponse resp = SdpCredentialManager.getInstance().searchCredentialBySubscription(
					request.getSubscriptionId(),
					trim(tenantName.value)
					);

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.getCredentials().setCredentialList(CredentialBeanConverter.convertCredentials((List<SdpCredentialDto>)resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
	
	
	@WebMethod(action = "searchCredentialsByParty")
	@WebResult(name="SearchCredentialsByPartyResponse")
	public SearchCredentialsRespPaginated searchCredentialsByParty(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchCredentialsByPartyRequest") SearchCredentialsByPartyRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchCredentialsRespPaginated wsResp = new SearchCredentialsRespPaginated();
		try {
			SearchServiceResponse resp = SdpCredentialManager.getInstance().searchCredentialsByParty(
					request.getPartyId(),
					request.getStartPosition(), 
					request.getMaxRecordsNumber(),
					trim(tenantName.value)
					);

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				wsResp.getCredentials().setCredentialList(CredentialBeanConverter.convertCredentials((List<SdpCredentialDto>)resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
	
	
	@WebMethod(action = "credentialChangeStatus")
	@WebResult(name="CredentialChangeStatusResponse")
	public BaseResp credentialChangeStatus(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "CredentialChangeStatusRequest") CredentialChangeStatusRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpCredentialManager.getInstance().credentialChangeStatus(
					request.getUsername(),
					request.getStatus(),
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

 
	@WebMethod(action = "resetPassword")
	@WebResult(name="ResetPasswordResponse")
	public ResetPasswordResp resetPassword(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "ResetPasswordRequest") ResetPasswordRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		ResetPasswordResp wsResp = new ResetPasswordResp();
		try {
			ResetPwdServiceResponse resp = SdpCredentialManager.getInstance().resetPassword(
					request.getUsername(),
					trim(tenantName.value)
					);

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setNewPassword(resp.getResetPassword());
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
	
	@WebMethod(action = "checkCredential")
	@WebResult(name="CheckCredentialResponse")
	public BaseResp checkCredential(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "CheckCredentialRequest") CheckCredentialsRequest request) {
		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);
		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpCredentialManager.getInstance().checkCredential(
					trim(request.getUsername()),
					trim(request.getPassword()),
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
	

	
	@WebMethod(action = "reserveUsername")
	@WebResult(name="reserveUsernameResponse")
	public ReserveUsernameResp reserveUsername(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "ReserveUsernameRequest") ReserveUsernameRequest request) {
		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);
		ReserveUsernameResp wsResp = new ReserveUsernameResp();
		try {
			SearchServiceResponse resp = SdpCredentialManager.getInstance().reserveUsername(
					trim(request.getUsername()),
					request.getPartyId(),
					trim(tenantName.value)
					);

			if (resp != null) {
				wsResp.getAlternativeUsername().setUsernameList((List<String>) resp.getSearchResult());
				convertBaseResponse(resp,wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
}
