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
import com.accenture.sdp.csm.dto.responses.SdpOfferGroupDto;
import com.accenture.sdp.csm.managers.SdpOfferGroupManager;
import com.accenture.sdp.csm.utilities.FieldConstants;
import com.accenture.sdp.csmfe.webservices.request.offergroup.CreateOfferGroupRequest;
import com.accenture.sdp.csmfe.webservices.request.offergroup.DeleteOfferGroupRequest;
import com.accenture.sdp.csmfe.webservices.request.offergroup.ModifyOfferGroupRequest;
import com.accenture.sdp.csmfe.webservices.response.BaseResp;
import com.accenture.sdp.csmfe.webservices.response.offergroup.CreateOfferGroupResp;
import com.accenture.sdp.csmfe.webservices.response.offergroup.SearchOfferGroupResp;
import com.accenture.sdp.csmfe.webservices.utils.OfferGroupBeanConverter;
import org.apache.cxf.annotations.SchemaValidation;

/**
 * @author elia.furiozzi
 * 
 */
@WebService(serviceName = "SdpOfferGroupService", targetNamespace = "http://com.accenture.sdp.csmfe.webservices")
@SchemaValidation
// JWS annotation that specifies the mapping of the service onto the
// SOAP message protocol. In particular, it specifies that the SOAP messages
// are document literal.
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class SdpOfferGroupService extends BaseWebService {

	@WebMethod(action = "cCreateOfferGroup")
	@WebResult(name = "CreateOfferGroupResponse")
	public CreateOfferGroupResp cCreateOfferGroup(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "CreateOfferRequest") CreateOfferGroupRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		CreateOfferGroupResp wsResp = new CreateOfferGroupResp();
		try {
			CreateServiceResponse resp = SdpOfferGroupManager.getInstance().createOfferGroup(
					trim(request.getGroupName()),
					request.getSolutionOfferId(),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.setGroupId(resp.getEntityId());
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}


	@WebMethod(action = "cSearchOfferGroup")
	@WebResult(name = "SearchOfferGroupResponse")
	public SearchOfferGroupResp cSearchOfferGroup(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchOfferRequest") CreateOfferGroupRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchOfferGroupResp wsResp = new SearchOfferGroupResp();
		try {
			SearchServiceResponse resp = SdpOfferGroupManager.getInstance().searchOfferGroup(
					trim(request.getGroupName()),
					request.getSolutionOfferId(),
					trim(tenantName.value));

			if (resp != null) {
				convertBaseResponse(resp,wsResp);
				wsResp.getOfferGroups().setOfferGroupList(OfferGroupBeanConverter
					.convertOfferGroups((List<SdpOfferGroupDto>) resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
	

	@WebMethod(action = "cModifyOfferGroup")
	@WebResult(name = "ModifyOfferGroupResponse")
	public BaseResp cModifyOfferGroup(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "ModifyOfferRequest") ModifyOfferGroupRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpOfferGroupManager.getInstance().modifyOfferGroup(
					request.getGroupId(),
					trim(request.getGroupName()),
					request.getSolutionOfferId(),
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
	

	@WebMethod(action = "cDeleteOfferGroup")
	@WebResult(name = "DeleteOfferGroupResponse")
	public BaseResp cDeleteOfferGroup(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "DeleteOfferRequest") DeleteOfferGroupRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpOfferGroupManager.getInstance().deleteOfferGroup(
					request.getGroupId(),
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
}
