package com.accenture.sdp.csmfe.webservices.utils;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csm.dto.requests.SdpPackageRequestDto;
import com.accenture.sdp.csm.dto.responses.SdpPackageDto;
import com.accenture.sdp.csmfe.webservices.request.packages.ModifyPackageInfoRequest;
import com.accenture.sdp.csmfe.webservices.response.packages.PackageComplexInfoResp;
import com.accenture.sdp.csmfe.webservices.response.packages.PackageInfoResp;

public class PackageBeanConverter extends BaseBeanConverter {

	public static List<PackageInfoResp> convertPackages(List<SdpPackageDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<PackageInfoResp> infos = new ArrayList<PackageInfoResp>();
		for (SdpPackageDto dto : dtos) {
			PackageInfoResp info = new PackageInfoResp();
			convertBaseInfo(dto, info);
			info.setPackageId(dto.getPackageId());
			info.setExternalId(dto.getExternalId());
			info.setOfferId(dto.getOfferId());
			String trimmed = trim(dto.getIsMandatory());
			if (trimmed != null && trimmed.length() > 0) {
				info.setIsMandatory(trimmed);
			}
			info.setPackagePriceId(dto.getPackagePriceId());
			info.setBasePackageId(dto.getBasePackageId());
			info.setGroupId(dto.getOfferGroupId());
			info.setProfile(dto.getProfile());
			info.setSolutionOfferId(dto.getSolutionOfferId());
			info.setStatusId(dto.getStatusId());
			infos.add(info);
		}
		return infos;
	}

	public static List<PackageComplexInfoResp> convertComplexPackages(List<SdpPackageDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<PackageComplexInfoResp> infos = new ArrayList<PackageComplexInfoResp>();
		for (SdpPackageDto dto : dtos) {
			PackageComplexInfoResp info = new PackageComplexInfoResp();
			convertBaseInfo(dto, info);
			info.setPackageId(dto.getPackageId());
			info.setExternalId(dto.getExternalId());
			info.setOfferId(dto.getOfferId());
			String trimmed = trim(dto.getIsMandatory());
			if (trimmed != null && trimmed.length() > 0) {
				info.setIsMandatory(trimmed);
			}
			info.setPackagePriceId(dto.getPackagePriceId());
			info.setBasePackageId(dto.getBasePackageId());
			info.setGroupId(dto.getOfferGroupId());
			info.setProfile(dto.getProfile());
			info.setSolutionOfferId(dto.getSolutionOfferId());
			info.setStatusId(dto.getStatusId());
			info.setBaseOfferName(dto.getBaseOfferName());
			info.setOfferName(dto.getOfferName());
			info.setStatusName(dto.getStatusName());
			info.setGroupName(dto.getOfferGroupName());
			info.setNrcPrice(dto.getNrcPrice());
			info.setRcPrice(dto.getRcPrice());
			info.setFrequencyPriceName(dto.getFrequencyPriceName());
			info.setCurrencyPriceName(dto.getCurrencyPriceName());
			info.setProrate(dto.getProrate());
			info.setInAdvance(dto.getInAdvance());
			infos.add(info);
			info.setRcPriceId(dto.getRcPriceId());
			info.setNrcPriceId(dto.getNrcPriceId());
			info.setFrequencyPriceId(dto.getFrequencyTypeId());
			info.setCurrencyPriceId(dto.getCurrencyTypeId());
			info.setFrequencyPriceDays(dto.getFrequencyDays());
			info.setDiscountId(dto.getDiscountId());
			info.setDiscountAbsNrc(dto.getDiscountAbsNrc());
			info.setDiscountAbsRc(dto.getDiscountAbsRc());
			info.setDiscountPercNrc(dto.getDiscountPercNrc());
			info.setDiscountPercRc(dto.getDiscountPercRc());
		}
		return infos;
	}

	public static List<SdpPackageRequestDto> convertSolutionOfferDetailReq(List<ModifyPackageInfoRequest> mpinfos) {
		if (mpinfos == null) {
			return null;
		}
		List<SdpPackageRequestDto> dtos = new ArrayList<SdpPackageRequestDto>();
		for (ModifyPackageInfoRequest mp : mpinfos) {
			SdpPackageRequestDto dto = new SdpPackageRequestDto();
			dto.setBasePackageId(mp.getBasePackageId());
			dto.setCurrencyTypeId(mp.getCurrencyTypeId());
			dto.setExternalId(trim(mp.getExternalId()));
			dto.setGroupId(mp.getGroupId());
			dto.setIsMandatory(trim(mp.getIsMandatory()));
			dto.setNrcPriceCatalogId(mp.getNrcPriceCatalogId());
			dto.setOfferId(mp.getOfferId());
			dto.setPackageId(mp.getPackageId());
			dto.setPackagePriceId(mp.getPackagePriceId());
			dto.setProfile(trim(mp.getProfile()));
			dto.setRcFlagProrate(trim(mp.getRcFlagProrate()));
			dto.setRcFrequencyTypeId(mp.getRcFrequencyTypeId());
			dto.setRcInAdvance(trim(mp.getRcInAdvance()));
			dto.setRcPriceCatalogId(mp.getRcPriceCatalogId());
			dtos.add(dto);
		}
		return dtos;
	}
}
