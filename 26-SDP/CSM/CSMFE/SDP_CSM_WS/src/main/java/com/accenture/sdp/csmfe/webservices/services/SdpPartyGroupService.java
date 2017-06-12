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
import com.accenture.sdp.csm.dto.responses.SdpPartyGroupResponseDto;
import com.accenture.sdp.csm.managers.SdpPartyGroupManager;
import com.accenture.sdp.csm.utilities.FieldConstants;
import com.accenture.sdp.csmfe.webservices.request.BaseRequestPaginated;
import com.accenture.sdp.csmfe.webservices.request.partygroup.CreatePartyGroupRequest;
import com.accenture.sdp.csmfe.webservices.request.partygroup.DeletePartyGroupRequest;
import com.accenture.sdp.csmfe.webservices.request.partygroup.ModifyPartyGroupRequest;
import com.accenture.sdp.csmfe.webservices.request.partygroup.SearchPartyGroupRequest;
import com.accenture.sdp.csmfe.webservices.response.BaseResp;
import com.accenture.sdp.csmfe.webservices.response.partygroup.CreatePartyGroupResp;
import com.accenture.sdp.csmfe.webservices.response.partygroup.SearchPartyGroupResp;
import com.accenture.sdp.csmfe.webservices.utils.PartyGroupBeanConverter;
import org.apache.cxf.annotations.SchemaValidation;

@WebService(serviceName = "SdpPartyGroupService", targetNamespace = "http://com.accenture.sdp.csmfe.webservices")
@SchemaValidation
// JWS annotation that specifies the mapping of the service onto the
// SOAP message protocol. In particular, it specifies that the SOAP messages
// are document literal.
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class SdpPartyGroupService extends BaseWebService {

	@WebMethod(action = "cCreatePartyGroup")
	@WebResult(name = "CreatePartyGroupResponse")
	public CreatePartyGroupResp cCreatePartyGroup(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "CreatePartyGroupRequest") CreatePartyGroupRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		CreatePartyGroupResp wsResp = new CreatePartyGroupResp();
		try {
			CreateServiceResponse resp = SdpPartyGroupManager.getInstance().createPartyGroup(trim(request.getPartyGroupName()),
					trim(request.getPartyGroupDescription()), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.setPartyGroupId(resp.getEntityId());
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "cModifyPartyGroup")
	@WebResult(name = "ModifyPartyGroupResponse")
	public BaseResp cModifyPartyGroup(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "ModifyPartyGroupRequest") ModifyPartyGroupRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpPartyGroupManager.getInstance().modifyPartyGroup(request.getPartyGroupId(), trim(request.getPartyGroupName()),
					trim(request.getPartyGroupDescription()), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "cDeletePartyGroup")
	@WebResult(name = "DeletePartyGroupResponse")
	public BaseResp cDeletePartyGroup(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "DeletePartyGroupRequest") DeletePartyGroupRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpPartyGroupManager.getInstance().deletePartyGroup(request.getPartyGroupId(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "cSearchPartyGroup")
	@WebResult(name = "SearchPartyGroupResponse")
	public SearchPartyGroupResp cSearchPartyGroup(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchPartyGroupRequest") SearchPartyGroupRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchPartyGroupResp wsResp = new SearchPartyGroupResp();
		try {
			SearchServiceResponse resp = SdpPartyGroupManager.getInstance().searchPartyGroup(request.getPartyGroupId(), trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.getPartyGroups().setPartyGroupList(
						PartyGroupBeanConverter.convertPartyGroupList((List<SdpPartyGroupResponseDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchAllPartyGroup")
	@WebResult(name = "SearchAllPartyGroupsResponse")
	public SearchPartyGroupResp searchAllPartyGroup(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchAllPartyGroupsRequest") BaseRequestPaginated request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchPartyGroupResp wsResp = new SearchPartyGroupResp();
		try {
			SearchServiceResponse resp = SdpPartyGroupManager.getInstance().searchAllPartyGroups(request.getStartPosition(), request.getMaxRecordsNumber(),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.setTotalResult(resp.getTotalResult());
				wsResp.getPartyGroups().setPartyGroupList(
						PartyGroupBeanConverter.convertPartyGroupList((List<SdpPartyGroupResponseDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

}