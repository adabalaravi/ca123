package com.accenture.sdp.csmfe.webservices.services;

import java.util.Date;
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
import com.accenture.sdp.csm.dto.responses.SdpDeviceChannelResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpDeviceResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpPartyDeviceResponseDto;
import com.accenture.sdp.csm.managers.SdpDeviceManager;
import com.accenture.sdp.csm.managers.SdpPartyDeviceManager;
import com.accenture.sdp.csm.utilities.FieldConstants;
import com.accenture.sdp.csmfe.webservices.request.BaseRequest;
import com.accenture.sdp.csmfe.webservices.request.device.CheckDeviceAccessRequest;
import com.accenture.sdp.csmfe.webservices.request.device.RegisterDeviceRequest;
import com.accenture.sdp.csmfe.webservices.request.device.ResetDeviceAssociationsRequest;
import com.accenture.sdp.csmfe.webservices.request.device.ResetDeviceSafetyPeriodRequest;
import com.accenture.sdp.csmfe.webservices.request.device.SearchDeviceCountersByPartyIdRequest;
import com.accenture.sdp.csmfe.webservices.request.device.SearchDevicesByIdRequest;
import com.accenture.sdp.csmfe.webservices.request.device.SearchDevicesByPartyIdRequest;
import com.accenture.sdp.csmfe.webservices.request.device.UnregisterDeviceRequest;
import com.accenture.sdp.csmfe.webservices.request.device.UpdateDeviceLastFruitionDateRequest;
import com.accenture.sdp.csmfe.webservices.request.device.UpdateDeviceRequest;
import com.accenture.sdp.csmfe.webservices.response.BaseResp;
import com.accenture.sdp.csmfe.webservices.response.device.CheckDeviceAccessResp;
import com.accenture.sdp.csmfe.webservices.response.device.DeviceChannelType;
import com.accenture.sdp.csmfe.webservices.response.device.DeviceCounterType;
import com.accenture.sdp.csmfe.webservices.response.device.DeviceType;
import com.accenture.sdp.csmfe.webservices.response.device.SearchAllDeviceChannelsResp;
import com.accenture.sdp.csmfe.webservices.response.device.SearchDeviceByIdResp;
import com.accenture.sdp.csmfe.webservices.response.device.SearchDeviceCountersByPartyIdResp;
import com.accenture.sdp.csmfe.webservices.response.device.SearchDevicesByPartyIdResp;
import com.accenture.sdp.csmfe.webservices.utils.DeviceBeanConverter;

@WebService(serviceName = "SdpDeviceService", targetNamespace = "http://com.accenture.sdp.csmfe.webservices")
@SchemaValidation
// JWS annotation that specifies the mapping of the service onto the
// SOAP message protocol. In particular, it specifies that the SOAP messages
// are document literal.
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class SdpDeviceService extends BaseWebService {

	@WebMethod(action = "registerDevice")
	@WebResult(name = "RegisterDeviceResponse")
	public BaseResp registerDevice(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "RegisterDeviceRequest") RegisterDeviceRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpDeviceManager.getInstance().registerDevice(trim(request.getDeviceUUID()), trim(request.getDeviceUUIDType()),
					trim(request.getDeviceChannel()), trim(request.getDeviceBrand()), trim(request.getDeviceModel()), trim(request.getDeviceAlias()),
					trim(request.getUsername()), trim(request.getPassword()), request.isPairEnabled(), trim(tenantName));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}

		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "checkDeviceAccess")
	@WebResult(name = "CheckDeviceAccessResponse")
	public CheckDeviceAccessResp checkDeviceAccess(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "CheckDeviceAccessRequest") CheckDeviceAccessRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		CheckDeviceAccessResp wsResp = new CheckDeviceAccessResp();
		try {
			CreateServiceResponse resp = SdpDeviceManager.getInstance().checkDeviceAccess(trim(request.getDeviceUUID()), trim(request.getUsername()),
					trim(request.getPassword()), trim(request.getAuthMode()), trim(tenantName));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.setPartyId(resp.getEntityId());
			}
		} catch (Exception e) {
			log.logError(e);
		}

		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "updateDevice")
	@WebResult(name = "UpdateDeviceResponse")
	public BaseResp updateDevice(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "UpdateDeviceRequest") UpdateDeviceRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpDeviceManager.getInstance().updateDevice(trim(request.getDeviceUUID()), trim(request.getDeviceBrand()),
					trim(request.getDeviceModel()), trim(request.getDeviceAlias()), trim(tenantName));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}

		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "updateDeviceLastFruitionDate")
	@WebResult(name = "UpdateDeviceLastFruitionDateResponse")
	public BaseResp updateDeviceLastFruitionDate(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "UpdateDeviceLastFruitionDateRequest") UpdateDeviceLastFruitionDateRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			Date lastFruition = request.getLastFruitionDate() == null ? null : request.getLastFruitionDate().toGregorianCalendar().getTime();
			DataServiceResponse resp = SdpDeviceManager.getInstance().updateDeviceLastFruitionDate(trim(request.getDeviceUUID()), lastFruition,
					trim(tenantName));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}

		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "unregisterDevice")
	@WebResult(name = "UnregisterDeviceResponse")
	public BaseResp unregisterDevice(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "UnregisterDeviceRequest") UnregisterDeviceRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpDeviceManager.getInstance().unregisterDevice(trim(request.getDeviceUUID()), trim(tenantName));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}

		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "resetDeviceSafetyPeriod")
	@WebResult(name = "ResetDeviceSafetyPeriodResponse")
	public BaseResp resetDeviceSafetyPeriod(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "ResetDeviceSafetyPeriodRequest") ResetDeviceSafetyPeriodRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpPartyDeviceManager.getInstance().resetDeviceSafetyPeriod(request.getPartyId(), trim(tenantName));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}

		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "resetDeviceAssociations")
	@WebResult(name = "ResetDeviceAssociationsResponse")
	public BaseResp resetDeviceAssociations(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "ResetDeviceAssociationsRequest") ResetDeviceAssociationsRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		BaseResp wsResp = new BaseResp();
		try {
			DataServiceResponse resp = SdpPartyDeviceManager.getInstance().resetDeviceAssociations(request.getPartyId(), trim(tenantName));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}

		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchDeviceCountersByPartyId")
	@WebResult(name = "SearchDeviceCountersByPartyIdResponse")
	public SearchDeviceCountersByPartyIdResp searchDeviceCountersByPartyId(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchDeviceCountersByPartyIdRequest") SearchDeviceCountersByPartyIdRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchDeviceCountersByPartyIdResp wsResp = new SearchDeviceCountersByPartyIdResp();
		try {
			ComplexServiceResponse resp = SdpPartyDeviceManager.getInstance().searchDeviceCountersByPartyId(request.getPartyId(), trim(tenantName));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				SdpPartyDeviceResponseDto dto = (SdpPartyDeviceResponseDto) resp.getComplexObject();
				List<DeviceCounterType> temp = DeviceBeanConverter.convertDeviceCounters(dto.getCounters());
				wsResp.setDeviceCounters(new SearchDeviceCountersByPartyIdResp.DeviceCounters());
				wsResp.getDeviceCounters().getDeviceCounter().addAll(temp);
				wsResp.setRegistrationsDone(dto.getRegistrationsDone());
				wsResp.setSafetyPeriodExpirationDate(dto.getSafetyPeriodExpirationDate());
			}
		} catch (Exception e) {
			log.logError(e);
		}

		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchDevicesByPartyId")
	@WebResult(name = "SearchDevicesByPartyIdResponse")
	public SearchDevicesByPartyIdResp searchDevicesByPartyId(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchDevicesByPartyIdRequest") SearchDevicesByPartyIdRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchDevicesByPartyIdResp wsResp = new SearchDevicesByPartyIdResp();
		try {
			SearchServiceResponse resp = SdpDeviceManager.getInstance().searchDevicesByPartyId(request.getPartyId(), trim(tenantName));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				List<DeviceType> temp = DeviceBeanConverter.convertDevices((List<SdpDeviceResponseDto>) resp.getSearchResult());
				wsResp.setDevices(new SearchDevicesByPartyIdResp.Devices());
				wsResp.getDevices().getDevice().addAll(temp);
			}
		} catch (Exception e) {
			log.logError(e);
		}

		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchDeviceById")
	@WebResult(name = "SearchDeviceByIdResponse")
	public SearchDeviceByIdResp searchDeviceById(@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchDevicesByIdRequest") SearchDevicesByIdRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchDeviceByIdResp wsResp = new SearchDeviceByIdResp();
		try {
			ComplexServiceResponse resp = SdpDeviceManager.getInstance().searchDeviceById(trim(request.getDeviceUUID()), trim(tenantName));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.setDevice(DeviceBeanConverter.convertDevice((SdpDeviceResponseDto) resp.getComplexObject()));
			}
		} catch (Exception e) {
			log.logError(e);
		}

		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

	@WebMethod(action = "searchAllDeviceChannels")
	@WebResult(name = "SearchAllDeviceChannelsResponse")
	public SearchAllDeviceChannelsResp searchAllDeviceChannels(
			@WebParam(name = FieldConstants.TENANT_NAME, header = true, mode = Mode.INOUT) Holder<String> tenantName,
			@WebParam(name = "SearchAllDeviceChannelsRequest") BaseRequest request) {

		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		SearchAllDeviceChannelsResp wsResp = new SearchAllDeviceChannelsResp();
		try {
			SearchServiceResponse resp = SdpDeviceManager.getInstance().searchAllDeviceChannels(trim(tenantName));

			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				List<DeviceChannelType> temp = DeviceBeanConverter.convertChannels((List<SdpDeviceChannelResponseDto>) resp.getSearchResult());
				wsResp.setDeviceChannels(new SearchAllDeviceChannelsResp.DeviceChannels());
				wsResp.getDeviceChannels().getDeviceChannel().addAll(temp);
			}
		} catch (Exception e) {
			log.logError(e);
		}

		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}

}
