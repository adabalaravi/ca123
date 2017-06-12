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
import com.accenture.sdp.csm.dto.responses.SdpRoleDto;
import com.accenture.sdp.csm.managers.SdpRoleManager;
import com.accenture.sdp.csm.utilities.FieldConstants;
import com.accenture.sdp.csmfe.webservices.request.BaseRequestPaginated;
import com.accenture.sdp.csmfe.webservices.request.role.CreateRoleRequest;
import com.accenture.sdp.csmfe.webservices.request.role.ModifyRoleRequest;
import com.accenture.sdp.csmfe.webservices.request.role.SearchRoleRequest;
import com.accenture.sdp.csmfe.webservices.response.BaseResp;
import com.accenture.sdp.csmfe.webservices.response.role.CreateRoleResp;
import com.accenture.sdp.csmfe.webservices.response.role.SearchAllRolesResp;
import com.accenture.sdp.csmfe.webservices.response.role.SearchRoleResp;
import com.accenture.sdp.csmfe.webservices.utils.RoleBeanConverter;
import org.apache.cxf.annotations.SchemaValidation;

@WebService(serviceName = "SdpRoleService", targetNamespace = "http://com.accenture.sdp.csmfe.webservices")
@SchemaValidation
// JWS annotation that specifies the mapping of the service onto the
// SOAP message protocol. In particular, it specifies that the SOAP messages
// are document literal.
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class SdpRoleService extends BaseWebService {

	@WebMethod(action = "cCreateRole")
	@WebResult(name = "CreateRoleResponse")
	public BaseResp cCreateRole(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "CreateRoleRequest") CreateRoleRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);
		CreateRoleResp wsResp = new CreateRoleResp();
		try {
			CreateServiceResponse resp = SdpRoleManager.getInstance().createRole(trim(request.getRoleName()), trim(request.getRoleDescription()),
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

	@WebMethod(action = "cModifyRole")
	@WebResult(name = "ModifyRoleResponse")
	public BaseResp cModifyRole(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "ModifyRoleRequest") ModifyRoleRequest request) {
		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);
		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpRoleManager.getInstance().modifyRole(trim(request.getRoleName()), trim(request.getRoleDescription()),
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

	@WebMethod(action = "cSearchRole")
	@WebResult(name = "SearchRoleResponse")
	public SearchRoleResp cSearchRole(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchRoleRequest") SearchRoleRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchRoleResp wsResp = new SearchRoleResp();
		try {
			SearchServiceResponse resp = SdpRoleManager.getInstance().searchRole(trim(request.getRoleName()), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.getRoles().setRoleList(RoleBeanConverter.convertRole((List<SdpRoleDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "cDeleteRole")
	@WebResult(name = "DeleteRoleResponse")
	public BaseResp cDeleteRole(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "DeleteRoleRequest") SearchRoleRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpRoleManager.getInstance().deleteRole(trim(request.getRoleName()), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchAllRoles")
	@WebResult(name = "SearchAllRolesResponse")
	public SearchAllRolesResp searchAllRoles(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchAllRolesRequest") BaseRequestPaginated request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchAllRolesResp wsResp = new SearchAllRolesResp();
		try {
			SearchServiceResponse resp = SdpRoleManager.getInstance().searchAllRole(request.getStartPosition(), request.getMaxRecordsNumber(),
					trim(tenantName.value));
			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				wsResp.getRoles().setRoleList(RoleBeanConverter.convertRole((List<SdpRoleDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
}
