package com.accenture.sdp.csmfe.webservices.utils;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csm.dto.requests.SdpDeviceAccessOperationBLRequestDto;
import com.accenture.sdp.csm.dto.requests.SdpDeviceAccessOperationRequestDto;
import com.accenture.sdp.csm.dto.responses.SdpDeviceAccessResponseDto;
import com.accenture.sdp.csmfe.webservices.request.device.ManageBlackListRequest;
import com.accenture.sdp.csmfe.webservices.request.device.ManageWhiteListRequest;
import com.accenture.sdp.csmfe.webservices.response.device.GetBlackListResp;
import com.accenture.sdp.csmfe.webservices.response.device.GetWhiteListResp;

public class DeviceAccessBeanConverter extends BaseBeanConverter {

	public static SdpDeviceAccessOperationBLRequestDto convertBLOperation(ManageBlackListRequest.Operations.Operation info) {
		if (info == null) {
			return null;
		}
		SdpDeviceAccessOperationBLRequestDto dto = new SdpDeviceAccessOperationBLRequestDto();
		dto.setId(trim(info.getId()));
		dto.setItemType(trim(info.getItemType()));
		dto.setOpType(trim(info.getOpType()));
		dto.setReason(trim(info.getReason()));
		return dto;
	}

	public static SdpDeviceAccessOperationRequestDto convertWLOperation(ManageWhiteListRequest.Operations.Operation info) {
		if (info == null) {
			return null;
		}
		SdpDeviceAccessOperationRequestDto dto = new SdpDeviceAccessOperationRequestDto();
		dto.setId(trim(info.getId()));
		dto.setItemType(trim(info.getItemType()));
		dto.setOpType(trim(info.getOpType()));
		return dto;
	}

	public static List<SdpDeviceAccessOperationBLRequestDto> convertBLOperations(List<ManageBlackListRequest.Operations.Operation> infos) {
		if (infos == null) {
			return null;
		}
		List<SdpDeviceAccessOperationBLRequestDto> dtos = new ArrayList<SdpDeviceAccessOperationBLRequestDto>();
		for (ManageBlackListRequest.Operations.Operation info : infos) {
			dtos.add(convertBLOperation(info));
		}
		return dtos;
	}

	public static List<SdpDeviceAccessOperationRequestDto> convertWLOperations(List<ManageWhiteListRequest.Operations.Operation> infos) {
		if (infos == null) {
			return null;
		}
		List<SdpDeviceAccessOperationRequestDto> dtos = new ArrayList<SdpDeviceAccessOperationRequestDto>();
		for (com.accenture.sdp.csmfe.webservices.request.device.ManageWhiteListRequest.Operations.Operation info : infos) {
			dtos.add(convertWLOperation(info));
		}
		return dtos;
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

	private static GetWhiteListResp.Items.Item convertWhitelist(SdpDeviceAccessResponseDto dto) {
		if (dto == null) {
			return null;
		}
		GetWhiteListResp.Items.Item info = new GetWhiteListResp.Items.Item();
		info.setId(dto.getId());
		info.setItemType(dto.getItemType());
		return info;
	}

	public static List<GetWhiteListResp.Items.Item> convertWhitelist(List<SdpDeviceAccessResponseDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<GetWhiteListResp.Items.Item> infos = new ArrayList<GetWhiteListResp.Items.Item>();
		for (SdpDeviceAccessResponseDto dto : dtos) {
			infos.add(convertWhitelist(dto));
		}
		return infos;
	}

}
