package com.accenture.sdp.csmfe.webservices.utils;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csm.dto.requests.ExternalIdDto;
import com.accenture.sdp.csm.dto.requests.SdpDiscountModifyRequestDto;
import com.accenture.sdp.csm.dto.requests.SdpDiscountRequestDto;
import com.accenture.sdp.csm.dto.requests.SdpSolutionOfferDetailRequestDto;
import com.accenture.sdp.csm.dto.responses.SdpSolutionOfferDto;
import com.accenture.sdp.csmfe.webservices.request.solutionoffer.DiscountInfoRequest;
import com.accenture.sdp.csmfe.webservices.request.solutionoffer.ExternalIdInfo;
import com.accenture.sdp.csmfe.webservices.request.solutionoffer.ModifyDiscountInfoRequest;
import com.accenture.sdp.csmfe.webservices.request.solutionoffer.SolutionOfferDetailInfoRequest;
import com.accenture.sdp.csmfe.webservices.response.solutionoffer.SolutionOfferComplexInfoResp;
import com.accenture.sdp.csmfe.webservices.response.solutionoffer.SolutionOfferDetailInfoResp;
import com.accenture.sdp.csmfe.webservices.response.solutionoffer.SolutionOfferInfoResp;

public class SolutionOfferBeanConverter extends BaseBeanConverter {

	private static void convertBaseSolutionOffer(SdpSolutionOfferDto from, SolutionOfferInfoResp to) {
		convertBaseInfo(from, to);
		to.setSolutionId(from.getSolutionId());
		to.setParentSolutionOfferId(from.getParentSolutionOfferId());
		to.setSolutionName(from.getSolutionName());
		to.setSolutionOfferId(from.getSolutionOfferId());
		to.setSolutionOfferName(from.getSolutionOfferName());
		to.setSolutionOfferDescription(from.getSolutionOfferDescription());
		to.setStatusId(from.getStatusId());
		to.getExternalIds().setExternalIdList(convertExternalIdResponses(from.getExternalIdList()));
		to.setStartDate(from.getStartDate());
		to.setEndDate(from.getEndDate());
		to.setProfile(from.getProfile());
		to.getPartyGroups().setPartyGroupList(PartyGroupBeanConverter.convertPartyGroupList(from.getPartyGroups()));
		to.setSolutionOfferType(from.getSolutionOfferType());
		to.setDuration(from.getDuration());
	}

	public static SolutionOfferInfoResp convertSolutionOffer(SdpSolutionOfferDto dto) {
		if (dto == null) {
			return null;
		}
		SolutionOfferInfoResp info = new SolutionOfferInfoResp();
		convertBaseSolutionOffer(dto, info);
		return info;
	}

	public static List<SolutionOfferInfoResp> convertSolutionOffers(List<SdpSolutionOfferDto> sodtos) {
		if (sodtos == null) {
			return null;
		}
		List<SolutionOfferInfoResp> soinfos = new ArrayList<SolutionOfferInfoResp>();
		for (SdpSolutionOfferDto so : sodtos) {
			soinfos.add(convertSolutionOffer(so));
		}
		return soinfos;
	}

	public static SolutionOfferComplexInfoResp convertComplexSolutionOffer(SdpSolutionOfferDto dto) {
		if (dto == null) {
			return null;
		}
		SolutionOfferComplexInfoResp info = new SolutionOfferComplexInfoResp();
		convertBaseSolutionOffer(dto, info);
		info.setStatusName(dto.getStatusName());
		return info;
	}

	public static List<SolutionOfferComplexInfoResp> convertSolutionOffersWithStatus(List<SdpSolutionOfferDto> sodtos) {
		if (sodtos == null) {
			return null;
		}
		List<SolutionOfferComplexInfoResp> soinfos = new ArrayList<SolutionOfferComplexInfoResp>();
		for (SdpSolutionOfferDto so : sodtos) {
			soinfos.add(convertComplexSolutionOffer(so));
		}
		return soinfos;
	}

	// creato per anomalia SDP00000237, da cancellare rel2.0
	public static List<SolutionOfferComplexInfoResp> convertSolutionOffersWithoutSolutionName(List<SdpSolutionOfferDto> sodtos) {
		if (sodtos == null) {
			return null;
		}
		List<SolutionOfferComplexInfoResp> soinfos = new ArrayList<SolutionOfferComplexInfoResp>();
		for (SdpSolutionOfferDto so : sodtos) {
			SolutionOfferComplexInfoResp soinfo = convertComplexSolutionOffer(so);
			// gran mossa per anomalia
			soinfo.setSolutionName(null);
			soinfos.add(soinfo);
		}
		return soinfos;
	}

	public static List<SdpSolutionOfferDetailRequestDto> convertSolutionOfferDetailReq(List<SolutionOfferDetailInfoRequest> sodinfos) {
		if (sodinfos == null) {
			return null;
		}
		List<SdpSolutionOfferDetailRequestDto> soddtos = new ArrayList<SdpSolutionOfferDetailRequestDto>();
		for (SolutionOfferDetailInfoRequest sod : sodinfos) {
			SdpSolutionOfferDetailRequestDto soddto = new SdpSolutionOfferDetailRequestDto();
			soddto.setRcPriceCatalogId(sod.getRcPriceCatalogId());
			soddto.setNrcPriceCatalogId(sod.getNrcPriceCatalogId());
			soddto.setCurrencyTypeID(sod.getCurrencyTypeId());
			soddto.setRcFrequencyTypeID(sod.getRcFrequencyTypeId());
			soddto.setRcFlagProrate(trim(sod.getRcFlagProrate()));
			soddto.setRcInAdvance(trim(sod.getRcInAdvance()));
			soddto.setPackageExternalId(trim(sod.getPackageExternalId()));
			soddto.setOfferId(sod.getOfferId());
			soddto.setMandatory(trim(sod.getIsMandatory()));
			soddto.setPackageProfile(trim(sod.getPackageProfile()));
			soddto.setGroupName(trim(sod.getGroupName()));
			soddto.setParentOfferId(sod.getParentOfferId());
			soddtos.add(soddto);
		}
		return soddtos;
	}

	public static List<SolutionOfferDetailInfoResp> convertSolutionOfferDetailResp(List<com.accenture.sdp.csm.dto.responses.SdpSolutionOfferDetailDto> soddtos) {
		if (soddtos == null) {
			return null;
		}
		List<SolutionOfferDetailInfoResp> sods = new ArrayList<SolutionOfferDetailInfoResp>();
		for (com.accenture.sdp.csm.dto.responses.SdpSolutionOfferDetailDto soddto : soddtos) {
			SolutionOfferDetailInfoResp sod = new SolutionOfferDetailInfoResp();
			sod.setPackageId(soddto.getPackageId());
			sod.setPackagePriceId(soddto.getPackagePriceId());
			sods.add(sod);
		}
		return sods;
	}

	public static List<SdpDiscountRequestDto> convertCreateDiscountsReq(List<DiscountInfoRequest> infos) {
		if (infos == null) {
			return null;
		}
		List<SdpDiscountRequestDto> dtos = new ArrayList<SdpDiscountRequestDto>();
		for (DiscountInfoRequest info : infos) {
			SdpDiscountRequestDto dto = new SdpDiscountRequestDto();
			dto.setPackageId(info.getPackageId());
			dto.setDiscountAbsNrc(info.getDiscountAbsNrc());
			dto.setDiscountAbsRc(info.getDiscountAbsRc());
			dto.setDiscountPercNrc(info.getDiscountPercNrc());
			dto.setDiscountPercRc(info.getDiscountPercRc());
			dtos.add(dto);
		}
		return dtos;
	}

	public static List<SdpDiscountModifyRequestDto> convertModifyDiscountsReq(List<ModifyDiscountInfoRequest> infos) {
		if (infos == null) {
			return null;
		}
		List<SdpDiscountModifyRequestDto> dtos = new ArrayList<SdpDiscountModifyRequestDto>();
		for (ModifyDiscountInfoRequest info : infos) {
			SdpDiscountModifyRequestDto dto = new SdpDiscountModifyRequestDto();
			dto.setDiscountId(info.getDiscountId());
			dto.setDiscountAbsNrc(info.getDiscountAbsNrc());
			dto.setDiscountAbsRc(info.getDiscountAbsRc());
			dto.setDiscountPercNrc(info.getDiscountPercNrc());
			dto.setDiscountPercRc(info.getDiscountPercRc());
			dtos.add(dto);
		}
		return dtos;
	}

	public static List<ExternalIdDto> convertExternalIdRequests(List<ExternalIdInfo> infos) {
		if (infos == null) {
			return null;
		}
		List<ExternalIdDto> dtos = new ArrayList<ExternalIdDto>();
		for (ExternalIdInfo info : infos) {
			ExternalIdDto dto = new ExternalIdDto();
			dto.setExternalId(trim(info.getExternalId()));
			dto.setExternalPlatformName(trim(info.getExternalPlatformName()));
			dtos.add(dto);
		}
		return dtos;
	}

	private static List<ExternalIdInfo> convertExternalIdResponses(List<ExternalIdDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<ExternalIdInfo> infos = new ArrayList<ExternalIdInfo>();
		for (ExternalIdDto dto : dtos) {
			ExternalIdInfo info = new ExternalIdInfo();
			info.setExternalId(dto.getExternalId());
			info.setExternalPlatformName(dto.getExternalPlatformName());
			infos.add(info);
		}
		return infos;
	}

}
