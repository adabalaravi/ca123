package com.accenture.sdp.csmfe.webservices.utils;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csm.dto.responses.SdpPriceCatalogDto;
import com.accenture.sdp.csmfe.webservices.response.price.PriceCatalogResp;

public class PriceCatalogBeanConverter extends BaseBeanConverter {

	public static List<PriceCatalogResp> convertPriceCatalogList(List<SdpPriceCatalogDto> priceList) {
		if (priceList == null) {
			return null;
		}
		List<PriceCatalogResp> result = new ArrayList<PriceCatalogResp>();
		for (SdpPriceCatalogDto cp : priceList) {
			PriceCatalogResp cpinfo = new PriceCatalogResp();
			convertBaseInfo(cp, cpinfo);
			cpinfo.setPriceCatalogId(cp.getPriceCatalogId());
			cpinfo.setPrice(cp.getPrice());
			result.add(cpinfo);
		}
		return result;
	}

	private static PriceCatalogResp convertPrices(SdpPriceCatalogDto o) {
		if (o == null) {
			return null;
		}
		PriceCatalogResp info = new PriceCatalogResp();
		convertBaseInfo(o, info);
		info.setPrice(o.getPrice());
		info.setPriceCatalogId(o.getPriceCatalogId());
		return info;
	}

	public static List<PriceCatalogResp> convertPrices(List<SdpPriceCatalogDto> odtos) {
		if (odtos == null) {
			return null;
		}
		List<PriceCatalogResp> oinfos = new ArrayList<PriceCatalogResp>();
		for (SdpPriceCatalogDto o : odtos) {
			oinfos.add(convertPrices(o));
		}
		return oinfos;
	}
}
