package com.accenture.sdp.csmfe.webservices.utils;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csm.dto.responses.RefCurrencyTypeResponseDto;
import com.accenture.sdp.csmfe.webservices.response.currency.CurrencyInfoResp;

public class CurrencyBeanConverter extends BaseBeanConverter {

	private static CurrencyInfoResp convertCurrency(RefCurrencyTypeResponseDto o) {
		if (o == null) {
			return null;
		}
		CurrencyInfoResp info = new CurrencyInfoResp();
		info.setCurrencyTypeName(o.getCurrencyTypeName());
		info.setCurrencyTypeId(o.getCurrencyTypeId());
		info.setCreatedById(o.getCreatedById());
		info.setCreatedDate(o.getCreatedDate());
		info.setUpdatedById(o.getUpdatedById());
		info.setUpdatedDate(o.getUpdatedDate());
		return info;
	}

	public static List<CurrencyInfoResp> convertCurrencies(List<RefCurrencyTypeResponseDto> cdtos) {
		if (cdtos == null) {
			return null;
		}
		List<CurrencyInfoResp> cinfos = new ArrayList<CurrencyInfoResp>();
		for (RefCurrencyTypeResponseDto o : cdtos) {
			cinfos.add(convertCurrency(o));
		}
		return cinfos;
	}

}
