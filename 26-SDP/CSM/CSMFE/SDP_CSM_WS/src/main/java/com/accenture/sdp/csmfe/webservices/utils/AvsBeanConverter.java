package com.accenture.sdp.csmfe.webservices.utils;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csm.dto.responses.AvsCountryLangCodeDto;
import com.accenture.sdp.csm.dto.responses.AvsDeviceIdTypeDto;
import com.accenture.sdp.csm.dto.responses.AvsDigitalGoodDto;
import com.accenture.sdp.csm.dto.responses.AvsPaymentTypeDto;
import com.accenture.sdp.csm.dto.responses.AvsPcExtendedRatingDto;
import com.accenture.sdp.csm.dto.responses.AvsPcLevelDto;
import com.accenture.sdp.csm.dto.responses.AvsPlatformDto;
import com.accenture.sdp.csm.dto.responses.AvsRetailerDomainDto;
import com.accenture.sdp.csmfe.webservices.response.avs.AvsCountryLangCodeInfoResp;
import com.accenture.sdp.csmfe.webservices.response.avs.AvsDeviceIdTypeInfoResp;
import com.accenture.sdp.csmfe.webservices.response.avs.AvsDigitalGoodInfoResp;
import com.accenture.sdp.csmfe.webservices.response.avs.AvsPaymentTypeInfoResp;
import com.accenture.sdp.csmfe.webservices.response.avs.AvsPcExtendedRatingInfoResp;
import com.accenture.sdp.csmfe.webservices.response.avs.AvsPcLevelInfoResp;
import com.accenture.sdp.csmfe.webservices.response.avs.AvsPlatformInfoResp;
import com.accenture.sdp.csmfe.webservices.response.avs.AvsRetailerDomainInfoResp;

public class AvsBeanConverter extends BaseBeanConverter {

	public static List<AvsCountryLangCodeInfoResp> convertAvsCountryLangCodes(List<AvsCountryLangCodeDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<AvsCountryLangCodeInfoResp> infos = new ArrayList<AvsCountryLangCodeInfoResp>();
		for (AvsCountryLangCodeDto dto : dtos) {
			AvsCountryLangCodeInfoResp info = new AvsCountryLangCodeInfoResp();
			info.setCountry(dto.getCountry());
			info.setCountryCode(dto.getCountryCode());
			infos.add(info);
		}
		return infos;
	}

	public static List<AvsDeviceIdTypeInfoResp> convertAvsDeviceIdTypes(List<AvsDeviceIdTypeDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<AvsDeviceIdTypeInfoResp> infos = new ArrayList<AvsDeviceIdTypeInfoResp>();
		for (AvsDeviceIdTypeDto dto : dtos) {
			AvsDeviceIdTypeInfoResp info = new AvsDeviceIdTypeInfoResp();
			info.setCreationDate(dto.getCreationDate());
			info.setTypeDescription(dto.getTypeDescription());
			info.setTypeId(Long.valueOf(dto.getTypeId()));
			info.setUpdateDate(dto.getUpdateDate());
			info.setValue(dto.getValue());
			infos.add(info);
		}
		return infos;
	}

	public static List<AvsPaymentTypeInfoResp> convertAvsPaymentTypes(List<AvsPaymentTypeDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<AvsPaymentTypeInfoResp> infos = new ArrayList<AvsPaymentTypeInfoResp>();
		for (AvsPaymentTypeDto dto : dtos) {
			AvsPaymentTypeInfoResp info = new AvsPaymentTypeInfoResp();
			info.setCreationDate(dto.getCreationDate());
			info.setUpdateDate(dto.getUpdateDate());
			info.setPaymentMethod(dto.getPaymentMethod());
			info.setPaymentTypeId(Long.valueOf(dto.getPaymentTypeId()));
			infos.add(info);
		}
		return infos;
	}

	public static List<AvsPcExtendedRatingInfoResp> convertAvsPcExtendedRatings(List<AvsPcExtendedRatingDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<AvsPcExtendedRatingInfoResp> infos = new ArrayList<AvsPcExtendedRatingInfoResp>();
		for (AvsPcExtendedRatingDto dto : dtos) {
			AvsPcExtendedRatingInfoResp info = new AvsPcExtendedRatingInfoResp();
			info.setCreationDate(dto.getCreationDate());
			info.setUpdateDate(dto.getUpdateDate());
			info.setPcDescription(dto.getPcDescription());
			info.setPcId(Long.valueOf(dto.getPcId()));
			info.setPcValue(dto.getPcValue());
			infos.add(info);
		}
		return infos;
	}

	public static List<AvsPcLevelInfoResp> convertAvsPcLevels(List<AvsPcLevelDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<AvsPcLevelInfoResp> infos = new ArrayList<AvsPcLevelInfoResp>();
		for (AvsPcLevelDto dto : dtos) {
			AvsPcLevelInfoResp info = new AvsPcLevelInfoResp();
			info.setCreationDate(dto.getCreationDate());
			info.setUpdateDate(dto.getUpdateDate());
			info.setDescription(dto.getDescription());
			info.setValue(dto.getValue());
			infos.add(info);
		}
		return infos;
	}

	public static List<AvsPlatformInfoResp> convertAvsPlatforms(List<AvsPlatformDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<AvsPlatformInfoResp> infos = new ArrayList<AvsPlatformInfoResp>();
		for (AvsPlatformDto dto : dtos) {
			AvsPlatformInfoResp info = new AvsPlatformInfoResp();
			info.setCreationDate(dto.getCreationDate());
			info.setUpdateDate(dto.getUpdateDate());
			info.setPlatformId(Long.valueOf(dto.getPlatformId()));
			info.setPlatformName(dto.getPlatformName());
			info.getDeviceIdTypes().setDeviceIdTypeList(convertAvsDeviceIdTypes(dto.getDeviceTypes()));
			infos.add(info);
		}
		return infos;
	}

	public static List<AvsRetailerDomainInfoResp> convertAvsRetailerDomains(List<AvsRetailerDomainDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<AvsRetailerDomainInfoResp> infos = new ArrayList<AvsRetailerDomainInfoResp>();
		for (AvsRetailerDomainDto dto : dtos) {
			AvsRetailerDomainInfoResp info = new AvsRetailerDomainInfoResp();
			info.setCreationDate(dto.getCreationDate());
			info.setUpdateDate(dto.getUpdateDate());
			info.setDescription(dto.getDescription());
			info.setHostDomain(dto.getHostDomain());
			info.setRetailerId(dto.getRetailerId());
			infos.add(info);
		}
		return infos;
	}

	public static List<AvsDigitalGoodInfoResp> convertAvsDigitalGoods(List<AvsDigitalGoodDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<AvsDigitalGoodInfoResp> infos = new ArrayList<AvsDigitalGoodInfoResp>();
		for (AvsDigitalGoodDto dto : dtos) {
			AvsDigitalGoodInfoResp info = new AvsDigitalGoodInfoResp();
			info.setId(dto.getId());
			info.setName(dto.getName());
			info.setDescription(dto.getDescription());
			info.setExternalId(dto.getExternalId());
			info.setType(dto.getType());
			info.setCreationDate(dto.getCreationDate());
			info.setUpdateDate(dto.getUpdateDate());
			info.setPrice(dto.getPrice());
			infos.add(info);
		}
		return infos;
	}

}
