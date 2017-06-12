package com.accenture.sdp.csmfe.webservices.utils;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csm.dto.requests.SdpDevicePolicyConfigRequestDto;
import com.accenture.sdp.csm.dto.requests.SdpPartyDeviceExtRequestDto;
import com.accenture.sdp.csm.dto.responses.SdpDeviceAccessResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpDevicePolicyConfigResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpDevicePolicyResponseDto;
import com.accenture.sdp.csmfe.webservices.request.device.MaximumAllowedDevicesType;
import com.accenture.sdp.csmfe.webservices.request.device.SetPartyDevicePolicyRequest;
import com.accenture.sdp.csmfe.webservices.response.device.GetBlackListResp;
import com.accenture.sdp.csmfe.webservices.response.device.PolicyType;

public class DevicePolicyBeanConverter extends BaseBeanConverter {

	public static SdpDevicePolicyConfigRequestDto convertDevicePolicyConfig(MaximumAllowedDevicesType.Device info) {
		if (info == null) {
			return null;
		}
		SdpDevicePolicyConfigRequestDto dto = new SdpDevicePolicyConfigRequestDto();
		dto.setDeviceChannel(trim(info.getDeviceChannel()));
		dto.setMaximumNumber(info.getMaximumNumber());
		return dto;
	}

	public static List<SdpDevicePolicyConfigRequestDto> convertDevicePolicyConfig(List<MaximumAllowedDevicesType.Device> infos) {
		if (infos == null) {
			return null;
		}
		List<SdpDevicePolicyConfigRequestDto> dtos = new ArrayList<SdpDevicePolicyConfigRequestDto>();
		for (MaximumAllowedDevicesType.Device info : infos) {
			dtos.add(convertDevicePolicyConfig(info));
		}
		return dtos;
	}

	public static List<SdpDevicePolicyConfigRequestDto> convertDevicePolicyConfig(MaximumAllowedDevicesType infos) {
		if (infos == null) {
			return null;
		}
		return convertDevicePolicyConfig(infos.getDevice());
	}

	private static GetBlackListResp.Items.Item convertBlacklist(SdpDeviceAccessResponseDto dto) {
		if (dto == null) {
			return null;
		}
		GetBlackListResp.Items.Item info = new GetBlackListResp.Items.Item();
		info.setId(dto.getId());
		info.setItemType(dto.getItemType());
		info.setReason(dto.getReason());
		return info;
	}

	public static List<GetBlackListResp.Items.Item> convertBlacklist(List<SdpDeviceAccessResponseDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<GetBlackListResp.Items.Item> infos = new ArrayList<GetBlackListResp.Items.Item>();
		for (SdpDeviceAccessResponseDto dto : dtos) {
			infos.add(convertBlacklist(dto));
		}
		return infos;
	}

	public static SdpPartyDeviceExtRequestDto convertPartyDevice(SetPartyDevicePolicyRequest.Policies.Policy info) {
		if (info == null) {
			return null;
		}
		SdpPartyDeviceExtRequestDto dto = new SdpPartyDeviceExtRequestDto();
		dto.setPartyId(info.getPartyId());
		dto.setPolicyId(info.getPolicyId());
		return dto;
	}

	public static List<SdpPartyDeviceExtRequestDto> convertPartyDevice(List<SetPartyDevicePolicyRequest.Policies.Policy> infos) {
		if (infos == null) {
			return null;
		}
		List<SdpPartyDeviceExtRequestDto> dtos = new ArrayList<SdpPartyDeviceExtRequestDto>();
		for (SetPartyDevicePolicyRequest.Policies.Policy info : infos) {
			dtos.add(convertPartyDevice(info));
		}
		return dtos;
	}

	public static List<SdpPartyDeviceExtRequestDto> convertPartyDevice(SetPartyDevicePolicyRequest.Policies infos) {
		if (infos == null) {
			return null;
		}
		return convertPartyDevice(infos.getPolicy());
	}

	public static PolicyType convertDevicePolicy(SdpDevicePolicyResponseDto dto) {
		if (dto == null) {
			return null;
		}
		PolicyType info = new PolicyType();
		info.setIsDefaultPolicy(dto.getIsDefaultPolicy());
		info.setMaxAssociationsNumber(dto.getMaxAssociationsNumber());
		info.setPolicyId(dto.getPolicyId());
		info.setPolicyName(dto.getPolicyName());
		info.setSafetyPeriodDuration(dto.getSafetyPeriodDuration());
		// setto configurazioni
		info.setMaximumAllowedDevices(new PolicyType.MaximumAllowedDevices());
		info.getMaximumAllowedDevices().getDevice().addAll(convertDevicePolicyConfigResponse(dto.getConfigs()));
		return info;
	}

	public static List<PolicyType> convertDevicePolicy(List<SdpDevicePolicyResponseDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<PolicyType> infos = new ArrayList<PolicyType>();
		for (SdpDevicePolicyResponseDto dto : dtos) {
			infos.add(convertDevicePolicy(dto));
		}
		return infos;
	}

	public static PolicyType.MaximumAllowedDevices.Device convertDevicePolicyConfigResponse(SdpDevicePolicyConfigResponseDto dto) {
		if (dto == null) {
			return null;
		}
		PolicyType.MaximumAllowedDevices.Device info = new PolicyType.MaximumAllowedDevices.Device();
		info.setDeviceChannel(dto.getDeviceChannel());
		info.setMaximumNumber(dto.getMaximumNumber());
		return info;
	}

	public static List<PolicyType.MaximumAllowedDevices.Device> convertDevicePolicyConfigResponse(List<SdpDevicePolicyConfigResponseDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<PolicyType.MaximumAllowedDevices.Device> infos = new ArrayList<PolicyType.MaximumAllowedDevices.Device>();
		for (SdpDevicePolicyConfigResponseDto dto : dtos) {
			infos.add(convertDevicePolicyConfigResponse(dto));
		}
		return infos;
	}

}
