package com.accenture.sdp.csmfe.webservices.utils;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csm.dto.responses.RefSolutionTypeResponseDto;
import com.accenture.sdp.csmfe.webservices.response.solutiontype.SolutionTypeInfoResp;

public class SolutionTypeBeanConverter extends BaseBeanConverter {

	public static List<SolutionTypeInfoResp> convertSolutionType(List<RefSolutionTypeResponseDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<SolutionTypeInfoResp> infos = new ArrayList<SolutionTypeInfoResp>();
		for (RefSolutionTypeResponseDto dto : dtos) {
			SolutionTypeInfoResp info = new SolutionTypeInfoResp();
			info.setSolutionTypeId(dto.getSolutionTypeId());
			info.setSolutionTypeName(dto.getSolutionTypeName());
			info.setSolutionTypeDescription(dto.getSolutionTypeDescription());
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
