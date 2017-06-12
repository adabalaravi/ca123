package com.accenture.sdp.csmfe.webservices.services;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.apache.cxf.annotations.SchemaValidation;

import com.accenture.sdp.csm.dto.ComplexServiceResponse;
import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.ResetPwdServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.requests.SdpOperatorRoleRightLnkRequestDto;
import com.accenture.sdp.csm.dto.requests.SdpTenantOperatorLnkRequestDto;
import com.accenture.sdp.csm.dto.responses.SdpOperatorResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpOperatorRightResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpTenantResponseDto;
import com.accenture.sdp.csm.managers.SdpOperatorsManager;
import com.accenture.sdp.csmfe.webservices.request.BaseRequestPaginated;
import com.accenture.sdp.csmfe.webservices.request.operators.ChangeOperatorPasswordRequest;
import com.accenture.sdp.csmfe.webservices.request.operators.CreateOperatorRequest;
import com.accenture.sdp.csmfe.webservices.request.operators.LoginOperatorRequest;
import com.accenture.sdp.csmfe.webservices.request.operators.ModifyOperatorRequest;
import com.accenture.sdp.csmfe.webservices.request.operators.ModifyOperatorRightsRequest;
import com.accenture.sdp.csmfe.webservices.request.operators.ModifyOperatorRoleRightInfoRequest;
import com.accenture.sdp.csmfe.webservices.request.operators.ModifyOperatorTenantInfoRequest;
import com.accenture.sdp.csmfe.webservices.request.operators.ModifyOperatorTenantRequest;
import com.accenture.sdp.csmfe.webservices.request.operators.ResetOperatorPasswordRequest;
import com.accenture.sdp.csmfe.webservices.request.operators.SearchOperatorByNameRequest;
import com.accenture.sdp.csmfe.webservices.request.operators.SearchOperatorByTenantRequest;
import com.accenture.sdp.csmfe.webservices.request.operators.SearchOperatorRequest;
import com.accenture.sdp.csmfe.webservices.request.operators.TenantInfoRequest;
import com.accenture.sdp.csmfe.webservices.response.BaseResp;
import com.accenture.sdp.csmfe.webservices.response.credential.ResetPasswordResp;
import com.accenture.sdp.csmfe.webservices.response.operators.LoginOperatorResp;
import com.accenture.sdp.csmfe.webservices.response.operators.SearchOperatorComplexResp;
import com.accenture.sdp.csmfe.webservices.response.operators.SearchOperatorComplexRespPaginated;
import com.accenture.sdp.csmfe.webservices.response.operators.SearchOperatorRightResp;
import com.accenture.sdp.csmfe.webservices.response.operators.SearchTenantResp;
import com.accenture.sdp.csmfe.webservices.utils.OperatorBeanConverter;

@WebService(serviceName = "SdpOperatorsService", targetNamespace = "http://com.accenture.sdp.csmfe.webservices")
@SchemaValidation
// JWS annotation that specifies the mapping of the service onto the
// SOAP message protocol. In particular, it specifies that the SOAP messages
// are document literal.
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class SdpOperatorsService extends BaseWebService {

	@WebMethod(action = "createOperator")
	@WebResult(name = "CreateOperatorResponse")
	public BaseResp createOperator(@WebParam(name = "CreateOperatorRequest") CreateOperatorRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);
		BaseResp wsResp = new BaseResp();
		try {
			List<String> tenants = new ArrayList<String>();
			if (request.getTenants() != null && request.getTenants().getTenantList() != null) {
				for (TenantInfoRequest tenant : request.getTenants().getTenantList()) {
					tenants.add(trim(tenant.getTenantName()));
				}
			}

			CreateServiceResponse resp = SdpOperatorsManager.getInstance().createOperator(trim(request.getUsername()), trim(request.getFirstName()),
					trim(request.getLastName()), trim(request.getPassword()), trim(request.getEmail()), tenants);

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "modifyOperator")
	@WebResult(name = "ModifyOperatorResponse")
	public BaseResp modifyOperator(@WebParam(name = "ModifyOperatorRequest") ModifyOperatorRequest request) {
		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);
		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpOperatorsManager.getInstance().modifyOperator(trim(request.getUsername()), trim(request.getFirstName()),
					trim(request.getLastName()), trim(request.getEmail()), trim(request.getStatus()));
			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "modifyOperatorTenant")
	@WebResult(name = "ModifyOperatorTenantResponse")
	public BaseResp modifyOperatorTenant(@WebParam(name = "ModifyOperatorTenantRequest") ModifyOperatorTenantRequest request) {
		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);
		BaseResp wsResp = new BaseResp();
		try {
			List<SdpTenantOperatorLnkRequestDto> tenants = new ArrayList<SdpTenantOperatorLnkRequestDto>();
			if (request.getTenants() != null && request.getTenants().getTenantList() != null) {
				for (ModifyOperatorTenantInfoRequest tenant : request.getTenants().getTenantList()) {
					SdpTenantOperatorLnkRequestDto dto = new SdpTenantOperatorLnkRequestDto();
					dto.setOperation(trim(tenant.getOperation()));
					dto.setTenantName(trim(tenant.getTenantName()));
					tenants.add(dto);
				}
			}
			DataServiceResponse resp = SdpOperatorsManager.getInstance().modifyTenantOperator(trim(request.getUsername()), tenants);
			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "deleteOperator")
	@WebResult(name = "DeleteOperatorResponse")
	public BaseResp deleteOperator(@WebParam(name = "DeleteOperatorRequest") SearchOperatorRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpOperatorsManager.getInstance().deleteOperator(trim(request.getUsername()));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchAllOperators")
	@WebResult(name = "SearchAllOperatorsResponse")
	public SearchOperatorComplexRespPaginated searchAllOperators(@WebParam(name = "SearchAllOperatorsRequest") BaseRequestPaginated request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchOperatorComplexRespPaginated wsResp = new SearchOperatorComplexRespPaginated();
		try {
			SearchServiceResponse resp = SdpOperatorsManager.getInstance().searchAllOperators(request.getStartPosition(), request.getMaxRecordsNumber());
			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				wsResp.getOperators().setOperatorsList(OperatorBeanConverter.convertComplexOperators((List<SdpOperatorResponseDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchOperator")
	@WebResult(name = "SearchOperatorResponse")
	public SearchOperatorComplexResp searchOperator(@WebParam(name = "SearchOperatorRequest") SearchOperatorRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchOperatorComplexResp wsResp = new SearchOperatorComplexResp();
		try {
			SearchServiceResponse resp = SdpOperatorsManager.getInstance().searchOperator(trim(request.getUsername()));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.getOperators().setOperatorsList(OperatorBeanConverter.convertComplexOperators((List<SdpOperatorResponseDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchOperatorByName")
	@WebResult(name = "SearchOperatorByNameResponse")
	public SearchOperatorComplexRespPaginated searchOperatorByName(@WebParam(name = "SearchOperatorByNameRequest") SearchOperatorByNameRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchOperatorComplexRespPaginated wsResp = new SearchOperatorComplexRespPaginated();
		try {
			SearchServiceResponse resp = SdpOperatorsManager.getInstance().searchOperatorByName(trim(request.getLastName()), trim(request.getFirstName()),
					request.getStartPosition(), request.getMaxRecordsNumber());

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				wsResp.getOperators().setOperatorsList(OperatorBeanConverter.convertComplexOperators((List<SdpOperatorResponseDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchOperatorByTenant")
	@WebResult(name = "SearchOperatorByTenantResponse")
	public SearchOperatorComplexRespPaginated searchOperatorByTenant(@WebParam(name = "SearchOperatorByTenantRequest") SearchOperatorByTenantRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchOperatorComplexRespPaginated wsResp = new SearchOperatorComplexRespPaginated();
		try {
			SearchServiceResponse resp = SdpOperatorsManager.getInstance().searchOperatorByTenant(trim(request.getTenantName()), request.getStartPosition(),
					request.getMaxRecordsNumber());
			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				wsResp.getOperators().setOperatorsList(OperatorBeanConverter.convertComplexOperators((List<SdpOperatorResponseDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "resetOperatorPassword")
	@WebResult(name = "ResetOperatorPasswordResponse")
	public ResetPasswordResp resetOperatorPassword(@WebParam(name = "ResetOperatorPasswordRequest") ResetOperatorPasswordRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);
		ResetPasswordResp wsResp = new ResetPasswordResp();
		try {
			ResetPwdServiceResponse resp = SdpOperatorsManager.getInstance().resetOperatorPassword(trim(request.getUsername()));
			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.setNewPassword(resp.getResetPassword());
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "ChangeOperatorPassword")
	@WebResult(name = "ChangeOperatorPasswordResponse")
	public BaseResp changeOperatorPassword(@WebParam(name = "ChangeOperatorPasswordRequest") ChangeOperatorPasswordRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);
		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpOperatorsManager.getInstance().changeOperatorPassword(trim(request.getUsername()), trim(request.getOldPassword()),
					trim(request.getNewPassword()), trim(request.getConfirmNewPassword()));
			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "loginOperator")
	@WebResult(name = "LoginOperatorResponse")
	public LoginOperatorResp loginOperator(@WebParam(name = "LoginOperatorRequest") LoginOperatorRequest request) {
		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);
		LoginOperatorResp wsResp = new LoginOperatorResp();
		try {
			ComplexServiceResponse resp = SdpOperatorsManager.getInstance().loginOperator(trim(request.getUsername()), trim(request.getPassword()),
					trim(request.getTenantName()));
			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				if (resp.getComplexObject() != null) {
					SdpOperatorResponseDto result = (SdpOperatorResponseDto) resp.getComplexObject();
					wsResp.setFirstName(result.getFirstName());
					wsResp.setLastName(result.getLastName());
					wsResp.setStatusId(result.getStatusId());
					wsResp.setStatusName(result.getStatusName());
					wsResp.setUsername(result.getStatusName());
				}

			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchAllTenants")
	@WebResult(name = "SearchAllTenantsResponse")
	public SearchTenantResp searchAllTenants() {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchTenantResp wsResp = new SearchTenantResp();
		try {
			SearchServiceResponse resp = SdpOperatorsManager.getInstance().searchAllTenants();
			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.getTenants().setTenantList(OperatorBeanConverter.convertTenants((List<SdpTenantResponseDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchAllOperatorRights")
	@WebResult(name = "SearchAllOperatorRightsResponse")
	public SearchOperatorRightResp searchAllOperatorRights() {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchOperatorRightResp wsResp = new SearchOperatorRightResp();
		try {
			SearchServiceResponse resp = SdpOperatorsManager.getInstance().searchAllOperatorRights();
			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.getRights().setOperatorRightsList(
						OperatorBeanConverter.convertOperatorRights((List<SdpOperatorRightResponseDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "modifyOperatorRoleRights")
	@WebResult(name = "ModifyOperatorRoleRightsResponse")
	public BaseResp modifyOperatorRoleRights(@WebParam(name = "ModifyOperatorRoleRightsRequest") ModifyOperatorRightsRequest request) {
		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);
		BaseResp wsResp = new BaseResp();
		try {
			List<SdpOperatorRoleRightLnkRequestDto> rights = new ArrayList<SdpOperatorRoleRightLnkRequestDto>();
			if (request.getRights() != null && request.getRights().getRightList() != null) {
				for (ModifyOperatorRoleRightInfoRequest right : request.getRights().getRightList()) {
					SdpOperatorRoleRightLnkRequestDto dto = new SdpOperatorRoleRightLnkRequestDto();
					dto.setOperation(trim(right.getOperation()));
					dto.setRightId(right.getRightId());
					rights.add(dto);
				}
			}
			DataServiceResponse resp = SdpOperatorsManager.getInstance().modifyOperatorRights(request.getUsername(), rights);

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchOperatorRights")
	@WebResult(name = "SearchOperatorRightsResponse")
	public SearchOperatorRightResp searchOperatorRights(@WebParam(name = "SearchOperatorRightsRequest") SearchOperatorRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchOperatorRightResp wsResp = new SearchOperatorRightResp();
		try {
			SearchServiceResponse resp = SdpOperatorsManager.getInstance().searchOperatorRights(request.getUsername());

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.getRights().setOperatorRightsList(
						OperatorBeanConverter.convertOperatorRights((List<SdpOperatorRightResponseDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
}
