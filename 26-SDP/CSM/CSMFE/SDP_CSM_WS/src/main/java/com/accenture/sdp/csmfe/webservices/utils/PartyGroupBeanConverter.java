package com.accenture.sdp.csmfe.webservices.utils;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csm.dto.requests.SdpPartyGroupRequestDto;
import com.accenture.sdp.csm.dto.responses.SdpPartyGroupResponseDto;
import com.accenture.sdp.csmfe.webservices.request.partygroup.PartyGroupOperationInfoRequest;
import com.accenture.sdp.csmfe.webservices.response.partygroup.PartyGroupInfoResp;

public class PartyGroupBeanConverter extends BaseBeanConverter {

	public static List<PartyGroupInfoResp> convertPartyGroupList(List<SdpPartyGroupResponseDto> partyGroups) {
		if (partyGroups == null) {
			return null;
		}
		List<PartyGroupInfoResp> result = new ArrayList<PartyGroupInfoResp>();
		for (SdpPartyGroupResponseDto cp : partyGroups) {
			PartyGroupInfoResp cpinfo = new PartyGroupInfoResp();
			convertBaseInfo(cp, cpinfo);
			cpinfo.setPartyGroupId(cp.getPartyGroupId());
			cpinfo.setPartyGroupName(cp.getPartyGroupName());
			cpinfo.setPartyGroupDescription(cp.getPartyGroupDescription());
			result.add(cpinfo);
		}
		return result;
	}

	public static List<SdpPartyGroupRequestDto> convertPartyGroupsOperations(List<PartyGroupOperationInfoRequest> infos) {
		if (infos == null) {
			return null;
		}
		List<SdpPartyGroupRequestDto> dtos = new ArrayList<SdpPartyGroupRequestDto>();
		for (PartyGroupOperationInfoRequest info : infos) {
			SdpPartyGroupRequestDto dto = new SdpPartyGroupRequestDto();
			dto.setOperation(trim(info.getOperation()));
			dto.setPartyGroupId(info.getPartyGroupId());
			dtos.add(dto);
		}
		return dtos;
	}
}
