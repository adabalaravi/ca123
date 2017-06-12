package com.accenture.sdp.csmfe.webservices.utils;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csm.dto.responses.SdpSolutionDto;
import com.accenture.sdp.csmfe.webservices.request.solution.PartyGroupNameInfoRequest;
import com.accenture.sdp.csmfe.webservices.response.solution.SolutionComplexInfoResp;
import com.accenture.sdp.csmfe.webservices.response.solution.SolutionInfoResp;

public class SolutionBeanConverter extends BaseBeanConverter {

	public static List<SolutionInfoResp> convertSolution(List<SdpSolutionDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<SolutionInfoResp> ainfos = new ArrayList<SolutionInfoResp>();
		for (SdpSolutionDto a : dtos) {
			ainfos.add(convertSolution(a));
		}
		return ainfos;
	}

	public static SolutionInfoResp convertSolution(SdpSolutionDto dto) {
		if (dto == null) {
			return null;
		}
		SolutionInfoResp info = new SolutionInfoResp();
		convertBaseInfo(dto, info);
		info.setEndDate(dto.getEndDate());
		info.setExternalId(dto.getExternalId());
		info.setSolutionDescription(dto.getSolutionDescription());
		info.setSolutionId(dto.getSolutionId());
		info.setSolutionName(dto.getSolutionName());
		info.setSolutionTypeId(dto.getSolutionTypeId());
		info.setStartDate(dto.getStartDate());
		info.setStatusId(dto.getStatusId());
		info.setProfile(dto.getProfile());
		return info;
	}

	public static List<SolutionComplexInfoResp> convertSolutionComplex(List<SdpSolutionDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<SolutionComplexInfoResp> ainfos = new ArrayList<SolutionComplexInfoResp>();
		for (SdpSolutionDto a : dtos) {
			SolutionComplexInfoResp ainfo = new SolutionComplexInfoResp();
			convertBaseInfo(a, ainfo);
			ainfo.setEndDate(a.getEndDate());
			ainfo.setExternalId(a.getExternalId());
			ainfo.setSolutionDescription(a.getSolutionDescription());
			ainfo.setSolutionId(a.getSolutionId());
			ainfo.setSolutionName(a.getSolutionName());
			ainfo.setSolutionTypeId(a.getSolutionTypeId());
			ainfo.setStartDate(a.getStartDate());
			ainfo.setStatusId(a.getStatusId());
			ainfo.setStatusName(a.getStatusName());
			ainfo.setSolutionTypeName(a.getSolutionTypeName());
			ainfo.getPartyGroups().setPartyGroupList(PartyGroupBeanConverter.convertPartyGroupList(a.getPartyGroups()));
			ainfo.setProfile(a.getProfile());
			ainfos.add(ainfo);
		}
		return ainfos;
	}

	public static List<String> convertPartyGroupNames(List<PartyGroupNameInfoRequest> infos) {
		if (infos == null) {
			return null;
		}
		List<String> dtos = new ArrayList<String>();
		for (PartyGroupNameInfoRequest info : infos) {
			dtos.add(info.getPartyGroupName());
		}
		return dtos;
	}

}
