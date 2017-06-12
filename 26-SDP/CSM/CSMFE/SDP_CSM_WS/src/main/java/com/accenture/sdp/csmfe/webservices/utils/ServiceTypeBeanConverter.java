package com.accenture.sdp.csmfe.webservices.utils;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csm.dto.responses.RefServiceTypeResponseDto;
import com.accenture.sdp.csmfe.webservices.response.servicetype.ServiceTypeInfoResp;

public class ServiceTypeBeanConverter extends BaseBeanConverter {

	public static List<ServiceTypeInfoResp> convertServiceType(List<RefServiceTypeResponseDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<ServiceTypeInfoResp> infos = new ArrayList<ServiceTypeInfoResp>();
		for (RefServiceTypeResponseDto dto : dtos) {
			ServiceTypeInfoResp info = new ServiceTypeInfoResp();
			info.setServiceTypeId(dto.getServiceTypeId());
			info.setServiceTypeName(dto.getServiceTypeName());
			info.setServiceTypeDescription(dto.getServiceTypeDescription());
			info.setCreatedById(dto.getCreatedById());
			info.setCreatedDate(dto.getCreatedDate());
			info.setUpdatedById(dto.getUpdatedById());
			info.setUpdatedDate(dto.getUpdatedDate());
			info.setDeletedById(dto.getDeletedById());
			info.setDeletedDate(dto.getDeletedDate());
			infos.add(info);
		}
		return infos;
	}
}
