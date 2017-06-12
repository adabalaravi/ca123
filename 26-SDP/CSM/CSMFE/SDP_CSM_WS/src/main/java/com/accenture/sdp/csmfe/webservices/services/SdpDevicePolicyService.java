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
import com.accenture.sdp.csm.dto.responses.SdpDevicePolicyResponseDto;
import com.accenture.sdp.csm.managers.SdpDevicePolicyManager;
import com.accenture.sdp.csm.utilities.FieldConstants;
import com.accenture.sdp.csmfe.webservices.request.BaseRequest;
import com.accenture.sdp.csmfe.webservices.request.device.CreateDevicePolicyRequest;
import com.accenture.sdp.csmfe.webservices.request.device.DeleteDevicePolicyRequest;
import com.accenture.sdp.csmfe.webservices.request.device.SearchDevicePolicyByIdRequest;
import com.accenture.sdp.csmfe.webservices.request.device.SearchDevicePolicyByPartyIdRequest;
import com.accenture.sdp.csmfe.webservices.request.device.SetPartyDevicePolicyRequest;
import com.accenture.sdp.csmfe.webservices.request.device.UpdateDevicePolicyRequest;
import com.accenture.sdp.csmfe.webservices.response.BaseResp;
import com.accenture.sdp.csmfe.webservices.response.device.CreateDevicePolicyResp;
import com.accenture.sdp.csmfe.webservices.response.device.PolicyType;
import com.accenture.sdp.csmfe.webservices.response.device.SearchAllDevicePoliciesResp;
import com.accenture.sdp.csmfe.webservices.response.device.SearchDevicePolicyByIdResp;
import com.accenture.sdp.csmfe.webservices.response.device.SearchDevicePolicyByPartyIdResp;
import com.accenture.sdp.csmfe.webservices.utils.DevicePolicyBeanConverter;

@WebService(serviceName = "SdpDevicePolicyService", targetNamespace = "http://com.accenture.sdp.csmfe.webservices")
@SchemaValidation
// JWS annotation that specifies the mapping of the service onto the
// SOAP message protocol. In particular, it specifies that the SOAP messages
// are document literal.
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class SdpDevicePolicyService extends BaseWebService {

	@WebMethod(action = "createDevicePolicy")
	@WebResult(name = "CreateDevicePolicyResponse")
	public CreateDevicePolicyResp createDevicePolicy(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "CreateDevicePolicyRequest") CreateDevicePolicyRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		CreateDevicePolicyResp wsResp = new CreateDevicePolicyResp();
		try {
			CreateServiceResponse resp = SdpDevicePolicyManager.getInstance().createDevicePolicy(trim(request.getPolicyName()), request.getMaxAssociationsNumber(),
					request.getSafetyPeriodDuration(), DevicePolicyBeanConverter.convertDevicePolicyConfig(request.getMaximumAllowedDevices()),
					trim(tenantName));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.setPolicyId(resp.getEntityId());
			}
		} catch (Exception e) {
			log.logError(e);
		}

		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "updateDevicePolicy")
	@WebResult(name = "UpdateDevicePolicyResponse")
	public BaseResp updateDevicePolicy(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "UpdateDevicePolicyRequest") UpdateDevicePolicyRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpDevicePolicyManager.getInstance().updateDevicePolicy(request.getPolicyId(), request.getPolicyName(),
					request.getMaxAssociationsNumber(), request.getSafetyPeriodDuration(),
					DevicePolicyBeanConverter.convertDevicePolicyConfig(request.getMaximumAllowedDevices()), trim(tenantName));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}

		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "deleteDevicePolicy")
	@WebResult(name = "DeleteDevicePolicyResponse")
	public BaseResp deleteDevicePolicy(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "DeleteDevicePolicyRequest") DeleteDevicePolicyRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpDevicePolicyManager.getInstance().deleteDevicePolicy(request.getPolicyId(), trim(tenantName));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}

		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "setPartyDevicePolicy")
	@WebResult(name = "SetPartyDevicePolicyResponse")
	public BaseResp setPartyDevicePolicy(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SetPartyDevicePolicyRequest") SetPartyDevicePolicyRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpDevicePolicyManager.getInstance().setPartyDevicePolicy(
					DevicePolicyBeanConverter.convertPartyDevice(request.getPolicies()), trim(tenantName));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}

		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchDevicePolicyByPartyId")
	@WebResult(name = "SearchDevicePolicyByPartyIdResponse")
	public SearchDevicePolicyByPartyIdResp searchDevicePolicyByPartyId(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchDevicePolicyByPartyIdRequest") SearchDevicePolicyByPartyIdRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchDevicePolicyByPartyIdResp wsResp = new SearchDevicePolicyByPartyIdResp();
		try {
			ComplexServiceResponse resp = SdpDevicePolicyManager.getInstance().searchDevicePolicyByPartyId(request.getPartyId(), trim(tenantName));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.setPolicy(DevicePolicyBeanConverter.convertDevicePolicy((SdpDevicePolicyResponseDto) resp.getComplexObject()));
			}
		} catch (Exception e) {
			log.logError(e);
		}

		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchAllDevicePolicies")
	@WebResult(name = "SearchAllDevicePoliciesResponse")
	public SearchAllDevicePoliciesResp searchAllDevicePolicies(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchAllDevicePoliciesRequest") BaseRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchAllDevicePoliciesResp wsResp = new SearchAllDevicePoliciesResp();
		try {
			SearchServiceResponse resp = SdpDevicePolicyManager.getInstance().searchAllDevicePolicies(trim(tenantName));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				List<PolicyType> temp = DevicePolicyBeanConverter.convertDevicePolicy((List<SdpDevicePolicyResponseDto>) resp.getSearchResult());
				wsResp.setPolicies(new SearchAllDevicePoliciesResp.Policies());
				wsResp.getPolicies().getPolicy().addAll(temp);

			}
		} catch (Exception e) {
			log.logError(e);
		}

		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchDevicePolicyById")
	@WebResult(name = "SearchDevicePolicyByIdResponse")
	public SearchDevicePolicyByIdResp searchDevicePolicyById(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchDevicePolicyByIdRequest") SearchDevicePolicyByIdRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchDevicePolicyByIdResp wsResp = new SearchDevicePolicyByIdResp();
		try {
			ComplexServiceResponse resp = SdpDevicePolicyManager.getInstance().searchDevicePolicyById(request.getPolicyId(), trim(tenantName));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.setPolicy(DevicePolicyBeanConverter.convertDevicePolicy((SdpDevicePolicyResponseDto) resp.getComplexObject()));
			}
		} catch (Exception e) {
			log.logError(e);
		}

		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

}