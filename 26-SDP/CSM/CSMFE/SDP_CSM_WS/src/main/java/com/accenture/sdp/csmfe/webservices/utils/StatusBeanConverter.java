package com.accenture.sdp.csmfe.webservices.utils;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csm.dto.responses.RefStatusTypeResponseDto;
import com.accenture.sdp.csmfe.webservices.response.status.StatusInfoResp;

public class StatusBeanConverter extends BaseBeanConverter {

	public static StatusInfoResp convertStatus(RefStatusTypeResponseDto s) {
		if (s == null) {
			return null;
		}
		StatusInfoResp pinfo = new StatusInfoResp();
		convertBaseInfo(s, pinfo);
		pinfo.setStatusId(s.getStatusId());
		pinfo.setStatusName(s.getStatusName());
		pinfo.setStatusDescription(s.getStatusDescription());
		return pinfo;
	}

	public static List<StatusInfoResp> convertListOfStatus(List<RefStatusTypeResponseDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<StatusInfoResp> infos = new ArrayList<StatusInfoResp>();
		for (RefStatusTypeResponseDto s : dtos) {
			infos.add(convertStatus(s));
		}
		return infos;
	}
}
