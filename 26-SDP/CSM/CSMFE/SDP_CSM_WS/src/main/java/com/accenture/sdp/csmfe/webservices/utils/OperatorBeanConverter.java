package com.accenture.sdp.csmfe.webservices.utils;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csm.dto.responses.SdpOperatorResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpOperatorRightResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpOperatorRoleResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpTenantResponseDto;
import com.accenture.sdp.csmfe.webservices.response.operators.OperatorComplexInfoResp;
import com.accenture.sdp.csmfe.webservices.response.operators.OperatorInfoResp;
import com.accenture.sdp.csmfe.webservices.response.operators.OperatorRightInfoResp;
import com.accenture.sdp.csmfe.webservices.response.operators.OperatorRoleComplexInfoResp;
import com.accenture.sdp.csmfe.webservices.response.operators.TenantInfoResp;

public class OperatorBeanConverter extends BaseBeanConverter {

	private static void convertBaseOperator(SdpOperatorResponseDto dto, OperatorInfoResp info) {
		info.setUsername(dto.getUsername());
		info.setFirstName(dto.getFirstName());
		info.setLastName(dto.getLastName());
		info.setStatusId(dto.getStatusId());
		info.setStatusName(dto.getStatusName());
		info.setEmail(dto.getEmail());
	}

	public static List<OperatorComplexInfoResp> convertComplexOperators(List<SdpOperatorResponseDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<OperatorComplexInfoResp> infos = new ArrayList<OperatorComplexInfoResp>();
		for (SdpOperatorResponseDto dto : dtos) {
			OperatorComplexInfoResp info = new OperatorComplexInfoResp();
			convertBaseInfo(dto, info);
			convertBaseOperator(dto, info);
			info.getTenants().setTenantList(convertTenants(dto.getTenants()));
			infos.add(info);
		}
		return infos;
	}

	public static List<TenantInfoResp> convertTenants(List<SdpTenantResponseDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<TenantInfoResp> infos = new ArrayList<TenantInfoResp>();
		for (SdpTenantResponseDto dto : dtos) {
			TenantInfoResp info = new TenantInfoResp();
			info.setTenantName(dto.getTenantName());
			info.setTenantDescription(dto.getTenantDescription());
			info.setStatusId(dto.getStatusId());
			info.setStatusName(dto.getStatusName());
			convertBaseInfo(dto, info);
			infos.add(info);
		}
		return infos;
	}

	public static List<OperatorRoleComplexInfoResp> convertComplexOperatorRoles(List<SdpOperatorRoleResponseDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<OperatorRoleComplexInfoResp> infos = new ArrayList<OperatorRoleComplexInfoResp>();
		for (SdpOperatorRoleResponseDto dto : dtos) {
			OperatorRoleComplexInfoResp info = new OperatorRoleComplexInfoResp();
			info.setCreatedById(dto.getCreatedById());
			info.setCreatedDate(dto.getCreatedDate());
			info.setUpdatedById(dto.getUpdatedById());
			info.setUpdatedDate(dto.getUpdatedDate());
			info.setRoleDescription(dto.getRoleDescription());
			info.setRoleId(dto.getRoleId());
			info.setRoleName(dto.getRoleName());
			info.getRights().setOperatorRightsList(convertOperatorRights(dto.getRights()));
			infos.add(info);
		}
		return infos;
	}

	public static List<OperatorRightInfoResp> convertOperatorRights(List<SdpOperatorRightResponseDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<OperatorRightInfoResp> infos = new ArrayList<OperatorRightInfoResp>();
		for (SdpOperatorRightResponseDto dto : dtos) {
			OperatorRightInfoResp info = new OperatorRightInfoResp();
			info.setCreatedById(dto.getCreatedById());
			info.setCreatedDate(dto.getCreatedDate());
			info.setUpdatedById(dto.getUpdatedById());
			info.setUpdatedDate(dto.getUpdatedDate());
			info.setRightDescription(dto.getRightDescription());
			info.setRightId(dto.getRightId());
			info.setRightName(dto.getRightName());
			info.setResourceId(dto.getOperatorResourceId());
			info.setResourceName(dto.getOperatorResourceName());
			info.setResourceDescription(dto.getOperatorResourceDescription());
			infos.add(info);
		}
		return infos;
	}
}
