package com.accenture.sdp.csmfe.webservices.utils;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csm.dto.responses.SdpPackagePriceDto;
import com.accenture.sdp.csmfe.webservices.response.packageprice.PackagePriceInfoResp;

public class PackagePriceBeanConverter extends BaseBeanConverter {

	private static PackagePriceInfoResp convertPackagePrice(SdpPackagePriceDto o) {
		if (o == null) {
			return null;
		}
		PackagePriceInfoResp info = new PackagePriceInfoResp();
		info.setCreatedById(o.getCreatedById());
		info.setCreatedDate(o.getCreatedDate());
		info.setUpdatedById(o.getUpdatedById());
		info.setUpdatedDate(o.getUpdatedDate());
		info.setCurrencyTypeId(o.getCurrencyTypeId());
		info.setNrcPriceCatalogId(o.getNrcPriceCatalogId());
		info.setRcPriceCatalogId(o.getRcPriceCatalogId());
		info.setRcFlagProrate(o.getRcFlagProrate());
		info.setRcFrequencyTypeId(o.getFrequencyTypeId());
		info.setRcInAdvance(o.getRcInAdvance());
		info.setPackagePriceId(o.getPackagePriceId());
		return info;
	}

	public static List<PackagePriceInfoResp> convertPackagePrices(List<SdpPackagePriceDto> odtos) {
		if (odtos == null) {
			return null;
		}
		List<PackagePriceInfoResp> oinfos = new ArrayList<PackagePriceInfoResp>();
		for (SdpPackagePriceDto o : odtos) {
			oinfos.add(convertPackagePrice(o));
		}
		return oinfos;
	}

}
