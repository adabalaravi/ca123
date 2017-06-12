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

import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.responses.SdpDeviceAccessResponseDto;
import com.accenture.sdp.csm.managers.SdpDeviceAccessManager;
import com.accenture.sdp.csm.utilities.FieldConstants;
import com.accenture.sdp.csmfe.webservices.request.device.GetBlackListRequest;
import com.accenture.sdp.csmfe.webservices.request.device.GetWhiteListRequest;
import com.accenture.sdp.csmfe.webservices.request.device.ManageBlackListRequest;
import com.accenture.sdp.csmfe.webservices.request.device.ManageWhiteListRequest;
import com.accenture.sdp.csmfe.webservices.response.BaseResp;
import com.accenture.sdp.csmfe.webservices.response.device.GetBlackListResp;
import com.accenture.sdp.csmfe.webservices.response.device.GetWhiteListResp;
import com.accenture.sdp.csmfe.webservices.utils.DeviceAccessBeanConverter;

@WebService(serviceName = "SdpDeviceAccessService", targetNamespace = "http://com.accenture.sdp.csmfe.webservices")
@SchemaValidation
// JWS annotation that specifies the mapping of the service onto the
// SOAP message protocol. In particular, it specifies that the SOAP messages
// are document literal.
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class SdpDeviceAccessService extends BaseWebService {

	@WebMethod(action = "manageBlackList")
	@WebResult(name = "ManageBlackListResponse")
	public BaseResp manageBlackList(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "ManageBlackListRequest") ManageBlackListRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpDeviceAccessManager.getInstance().manageBlackList(
					DeviceAccessBeanConverter.convertBLOperations(request.getOperations().getOperation()), trim(tenantName));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}

		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "getBlackList")
	@WebResult(name = "GetBlackListResponse")
	public GetBlackListResp getBlackList(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "GetBlackListRequest") GetBlackListRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		GetBlackListResp wsResp = new GetBlackListResp();
		try {
			SearchServiceResponse resp = SdpDeviceAccessManager.getInstance().getBlackList(trim(request.getFilter()), trim(tenantName));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				List<GetBlackListResp.Items.Item> temp = DeviceAccessBeanConverter.convertBlacklist((List<SdpDeviceAccessResponseDto>) resp.getSearchResult());
				wsResp.setItems(new GetBlackListResp.Items());
				wsResp.getItems().getItem().addAll(temp);
			}
		} catch (Exception e) {
			log.logError(e);
		}

		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "manageWhiteList")
	@WebResult(name = "ManageWhiteListResponse")
	public BaseResp manageWhiteList(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "ManageWhiteListRequest") ManageWhiteListRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpDeviceAccessManager.getInstance().manageWhiteList(
					DeviceAccessBeanConverter.convertWLOperations(request.getOperations().getOperation()), trim(tenantName));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}

		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "getWhiteList")
	@WebResult(name = "GetWhiteListResponse")
	public GetWhiteListResp getWhiteList(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "GetWhiteListRequest") GetWhiteListRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		GetWhiteListResp wsResp = new GetWhiteListResp();
		try {
			SearchServiceResponse resp = SdpDeviceAccessManager.getInstance().getWhiteList(trim(request.getFilter()), trim(tenantName));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				List<GetWhiteListResp.Items.Item> temp = DeviceAccessBeanConverter.convertWhitelist((List<SdpDeviceAccessResponseDto>) resp.getSearchResult());
				wsResp.setItems(new GetWhiteListResp.Items());
				wsResp.getItems().getItem().addAll(temp);
			}
		} catch (Exception e) {
			log.logError(e);
		}

		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
}
