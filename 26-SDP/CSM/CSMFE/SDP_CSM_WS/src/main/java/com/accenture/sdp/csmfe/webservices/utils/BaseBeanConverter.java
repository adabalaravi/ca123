package com.accenture.sdp.csmfe.webservices.utils;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csm.dto.responses.ParameterDto;
import com.accenture.sdp.csm.dto.responses.SdpBaseResponseDto;
import com.accenture.sdp.csmfe.webservices.response.BaseInfoResp;
import com.accenture.sdp.csmfe.webservices.response.ParameterInfoResp;
import com.accenture.sdp.csmfe.webservices.response.ParameterListResp;

public abstract class BaseBeanConverter {

	protected static String trim(String input) {
		return Utilities.trim(input);
	}

	protected static void convertBaseInfo(SdpBaseResponseDto from, BaseInfoResp to) {
		if (from != null && to != null) {
			to.setCreatedById(from.getCreatedById());
			to.setCreatedDate(from.getCreatedDate());
			to.setUpdatedById(from.getUpdatedById());
			to.setUpdatedDate(from.getUpdatedDate());
			to.setDeletedById(from.getDeletedById());
			to.setDeletedDate(from.getDeletedDate());
			to.setChangeStatusById(from.getChangeStatusById());
			to.setChangeStatusDate(from.getChangeStatusDate());
		}
	}

	public static ParameterListResp convertParameterInfo(List<ParameterDto> dtos) {
		if (dtos == null) {
			return null;
		}
		ParameterListResp resp = new ParameterListResp();
		resp.setParameterList(new ArrayList<ParameterInfoResp>());
		for (ParameterDto dto : dtos) {
			ParameterInfoResp info = new ParameterInfoResp();
			info.setName(dto.getName());
			info.setValue(dto.getValue());
			resp.getParameterList().add(info);
		}
		return resp;
	}
}
