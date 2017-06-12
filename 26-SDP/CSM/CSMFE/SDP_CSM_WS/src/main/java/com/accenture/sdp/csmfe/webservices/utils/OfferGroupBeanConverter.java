package com.accenture.sdp.csmfe.webservices.utils;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csm.dto.responses.SdpOfferGroupDto;
import com.accenture.sdp.csmfe.webservices.response.offergroup.OfferGroupInfoResp;

public class OfferGroupBeanConverter extends BaseBeanConverter {

	public static List<OfferGroupInfoResp> convertOfferGroups(List<SdpOfferGroupDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<OfferGroupInfoResp> infos = new ArrayList<OfferGroupInfoResp>();
		for (SdpOfferGroupDto dto : dtos) {
			OfferGroupInfoResp info = new OfferGroupInfoResp();
			convertBaseInfo(dto, info);
			info.setGroupId(dto.getGroupId());
			info.setSolutionOfferId(dto.getSolutionOfferId());
			info.setGroupName(dto.getGroupName());
			infos.add(info);
		}
		return infos;
	}
}
