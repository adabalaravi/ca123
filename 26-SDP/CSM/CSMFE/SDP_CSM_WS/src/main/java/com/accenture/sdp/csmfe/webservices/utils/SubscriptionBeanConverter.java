package com.accenture.sdp.csmfe.webservices.utils;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csm.dto.requests.SdpSubscriptionDetailRequestDto;
import com.accenture.sdp.csm.dto.requests.SdpSubscriptionRequestDto;
import com.accenture.sdp.csm.dto.responses.SdpSubscriptionDetailResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpSubscriptionResponseDto;
import com.accenture.sdp.csmfe.webservices.request.subscription.CreateSubscriptionTogetherPartyRequest;
import com.accenture.sdp.csmfe.webservices.request.subscription.SubscriptionDetailInfoRequest;
import com.accenture.sdp.csmfe.webservices.response.subscription.OfferInfoResp;
import com.accenture.sdp.csmfe.webservices.response.subscription.SubscriptionByPartyInfoResp;
import com.accenture.sdp.csmfe.webservices.response.subscription.SubscriptionComplexInfoResp;
import com.accenture.sdp.csmfe.webservices.response.subscription.SubscriptionDetailInfoResp;
import com.accenture.sdp.csmfe.webservices.response.subscription.SubscriptionInfoResp;

public class SubscriptionBeanConverter extends BaseBeanConverter {

	public static List<SubscriptionInfoResp> convertSubscriptions(List<SdpSubscriptionResponseDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<SubscriptionInfoResp> infos = new ArrayList<SubscriptionInfoResp>();
		for (SdpSubscriptionResponseDto dto : dtos) {
			infos.add(convertSubscription(dto));
		}
		return infos;
	}

	private static SubscriptionInfoResp convertSubscription(SdpSubscriptionResponseDto dto) {
		if (dto == null) {
			return null;
		}
		SubscriptionInfoResp info = new SubscriptionInfoResp();
		convertBaseInfo(dto, info);
		info.setSubscriptionId(dto.getSubscriptionId());
		info.setStatusId(dto.getStatusId());
		info.setPartyId(dto.getPartyId());
		info.setParentSubscriptionId(dto.getParentSubscriptionId());
		info.setUserName(dto.getUserName());
		info.setRoleName(dto.getRoleName());
		info.setOwnerId(dto.getOwnerId());
		info.setPayeeId(dto.getPayeeId());
		info.setSiteId(dto.getSiteId());
		info.setExternalId(dto.getExternalId());
		info.setStartDate(dto.getStartDate());
		info.setEndDate(dto.getEndDate());
		info.setSolutionOfferId(dto.getSolutionOfferId());
		return info;
	}

	public static List<SubscriptionComplexInfoResp> convertSubscriptionsComplex(List<SdpSubscriptionResponseDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<SubscriptionComplexInfoResp> infos = new ArrayList<SubscriptionComplexInfoResp>();
		for (SdpSubscriptionResponseDto dto : dtos) {
			SubscriptionComplexInfoResp info = new SubscriptionComplexInfoResp();
			convertBaseInfo(dto, info);
			info.setSubscriptionId(dto.getSubscriptionId());
			info.setStatusId(dto.getStatusId());
			info.setPartyId(dto.getPartyId());
			info.setParentSubscriptionId(dto.getParentSubscriptionId());
			info.setUserName(dto.getUserName());
			info.setRoleName(dto.getRoleName());
			info.setOwnerId(dto.getOwnerId());
			info.setPayeeId(dto.getPayeeId());
			info.setSiteId(dto.getSiteId());
			info.setExternalId(dto.getExternalId());
			info.setStartDate(dto.getStartDate());
			info.setEndDate(dto.getEndDate());
			info.setStatusName(dto.getStatusName());
			info.setPartyName(dto.getPartyName());
			info.setOwnerAccountName(dto.getOwnerAccountName());
			info.setPayeeAccountName(dto.getPayeeAccountName());
			info.setSiteName(dto.getSiteName());
			info.setSolutionOfferId(dto.getSolutionOfferId());
			info.setSolutionOfferName(dto.getSolutionOfferName());
			info.getOffers().setOfferList(convertOffers(dto.getOffers()));
			infos.add(info);
		}
		return infos;
	}

	public static List<SubscriptionByPartyInfoResp> convertSubscriptionsByParty(List<SdpSubscriptionResponseDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<SubscriptionByPartyInfoResp> infos = new ArrayList<SubscriptionByPartyInfoResp>();
		for (SdpSubscriptionResponseDto dto : dtos) {
			SubscriptionByPartyInfoResp info = new SubscriptionByPartyInfoResp();
			convertBaseInfo(dto, info);
			info.setSubscriptionId(dto.getSubscriptionId());
			info.setStatusId(dto.getStatusId());
			info.setPartyId(dto.getPartyId());
			info.setParentSubscriptionId(dto.getParentSubscriptionId());
			info.setUserName(dto.getUserName());
			info.setRoleName(dto.getRoleName());
			info.setOwnerId(dto.getOwnerId());
			info.setPayeeId(dto.getPayeeId());
			info.setSiteId(dto.getSiteId());
			info.setExternalId(dto.getExternalId());
			info.setStartDate(dto.getStartDate());
			info.setEndDate(dto.getEndDate());
			info.setStatusName(dto.getStatusName());
			info.setPartyName(dto.getPartyName());
			info.setOwnerAccountName(dto.getOwnerAccountName());
			info.setPayeeAccountName(dto.getPayeeAccountName());
			info.setSiteName(dto.getSiteName());
			info.setSolutionOfferId(dto.getSolutionOfferId());
			info.setSolutionOfferName(dto.getSolutionOfferName());
			info.getOffers().setOfferList(convertOffers(dto.getOffers()));
			// aggiunti 2 parametri rispetto al metodo convertSubscriptionsComplex...
			info.setPartyTypeId(dto.getPartyTypeId());
			info.setPartyTypeName(dto.getPartyTypeName());

			infos.add(info);
		}
		return infos;
	}

	public static List<OfferInfoResp> convertOffers(List<SdpSubscriptionDetailResponseDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<OfferInfoResp> infos = new ArrayList<OfferInfoResp>();
		for (SdpSubscriptionDetailResponseDto dto : dtos) {
			OfferInfoResp info = new OfferInfoResp();
			convertBaseInfo(dto, info);
			info.setPackageId(dto.getPackageId());
			info.setOfferId(dto.getOfferId());
			info.setOfferName(dto.getOfferName());
			info.setSubscriptionOfferProfile(dto.getSubscriptionOfferProfile());
			info.setExternalId(dto.getDetailExternalId());
			info.setStatusId(dto.getStatusId());
			info.setStatusName(dto.getStatusName());
			infos.add(info);
		}
		return infos;
	}

	public static List<SdpSubscriptionDetailRequestDto> convertSubscriptionDetailReq(List<SubscriptionDetailInfoRequest> infos) {
		if (infos == null) {
			return null;
		}
		List<SdpSubscriptionDetailRequestDto> dtos = new ArrayList<SdpSubscriptionDetailRequestDto>();
		for (SubscriptionDetailInfoRequest info : infos) {
			SdpSubscriptionDetailRequestDto dto = new SdpSubscriptionDetailRequestDto();
			dto.setExternalId(trim(info.getExternalId()));
			dto.setPackageId(info.getPackageId());
			dto.setSubscriptionOfferProfile(trim(info.getSubscriptionOfferProfile()));
			dtos.add(dto);
		}
		return dtos;
	}

	public static SdpSubscriptionRequestDto convertSubscriptionReq(CreateSubscriptionTogetherPartyRequest info) {
		if (info == null) {
			return null;
		}
		SdpSubscriptionRequestDto dto = new SdpSubscriptionRequestDto();
		dto.setUserName(trim(info.getUsername()));
		dto.setRoleName(trim(info.getRoleName()));
		dto.setOwnerAccountName(trim(info.getOwnerAccountName()));
		dto.setPayeeAccountName(trim(info.getPayeeAccountName()));
		dto.setSiteName(trim(info.getSiteName()));
		dto.setExternalId(trim(info.getExternalId()));
		dto.setStartDate(info.getStartDate());
		dto.setEndDate(info.getEndDate());
		dto.setSolutionOfferId(info.getSolutionOfferId());
		return dto;
	}

	public static List<SubscriptionDetailInfoResp> convertSubscriptionDetailResp(List<SdpSubscriptionDetailResponseDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<SubscriptionDetailInfoResp> infos = new ArrayList<SubscriptionDetailInfoResp>();
		for (SdpSubscriptionDetailResponseDto dto : dtos) {
			SubscriptionDetailInfoResp info = new SubscriptionDetailInfoResp();
			convertBaseInfo(dto, info);
			info.setSubscriptionId(dto.getSubscriptionId());
			info.setPackageId(dto.getPackageId());
			info.setStatusId(dto.getStatusId());
			info.setSubscriptionOfferProfile(dto.getSubscriptionOfferProfile());
			info.setExternalId(dto.getDetailExternalId());
			infos.add(info);
		}
		return infos;
	}
}
