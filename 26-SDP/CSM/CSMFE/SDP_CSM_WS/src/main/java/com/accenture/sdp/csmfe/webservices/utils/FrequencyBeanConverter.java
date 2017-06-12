package com.accenture.sdp.csmfe.webservices.utils;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csm.dto.responses.RefFrequencyTypeResponseDto;
import com.accenture.sdp.csmfe.webservices.response.frequency.FrequencyInfoResp;

public class FrequencyBeanConverter extends BaseBeanConverter {

	private static FrequencyInfoResp convertFrequency(RefFrequencyTypeResponseDto o) {
		if (o == null) {
			return null;
		}
		FrequencyInfoResp info = new FrequencyInfoResp();
		info.setFrequencyTypeId(o.getFrequencyTypeId());
		info.setFrequencyTypeName(o.getFrequencyTypeName());
		info.setFrequencyTypeDescription(o.getFrequencyTypeDescription());
		info.setFrequencyDays(o.getFrequencyDays());
		info.setCreatedById(o.getCreatedById());
		info.setCreatedDate(o.getCreatedDate());
		info.setUpdatedById(o.getUpdatedById());
		info.setUpdatedDate(o.getUpdatedDate());
		return info;
	}

	public static List<FrequencyInfoResp> convertFrequencies(List<RefFrequencyTypeResponseDto> odtos) {
		if (odtos == null) {
			return null;
		}
		List<FrequencyInfoResp> oinfos = new ArrayList<FrequencyInfoResp>();
		for (RefFrequencyTypeResponseDto o : odtos) {
			oinfos.add(convertFrequency(o));
		}
		return oinfos;
	}
}
