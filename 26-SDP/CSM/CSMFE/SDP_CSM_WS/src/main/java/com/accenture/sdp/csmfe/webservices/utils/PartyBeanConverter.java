package com.accenture.sdp.csmfe.webservices.utils;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csm.dto.requests.SdpAccountRequestDto;
import com.accenture.sdp.csm.dto.requests.SdpPartySiteRequestDto;
import com.accenture.sdp.csm.dto.responses.SdpChildPartyDto;
import com.accenture.sdp.csm.dto.responses.SdpParentPartyDto;
import com.accenture.sdp.csm.dto.responses.SdpPartyResponseDto;
import com.accenture.sdp.csmfe.webservices.request.party.AccountInfoRequest;
import com.accenture.sdp.csmfe.webservices.request.party.SiteInfoRequest;
import com.accenture.sdp.csmfe.webservices.response.party.ChildPartyInfoResp;
import com.accenture.sdp.csmfe.webservices.response.party.ParentPartyInfoResp;
import com.accenture.sdp.csmfe.webservices.response.party.PartyInfoResp;

public class PartyBeanConverter extends BaseBeanConverter {

	public static List<ChildPartyInfoResp> convertChildParties(List<SdpChildPartyDto> cpdtos) {
		if (cpdtos == null) {
			return null;
		}
		List<ChildPartyInfoResp> cpinfos = new ArrayList<ChildPartyInfoResp>();
		for (SdpChildPartyDto cp : cpdtos) {
			ChildPartyInfoResp cpinfo = new ChildPartyInfoResp();
			convertBaseInfo(cp, cpinfo);
			cpinfo.setPartyId(cp.getPartyId());
			cpinfo.setPartyName(cp.getPartyName());
			cpinfo.setPartyDescription(cp.getPartyDescription());
			cpinfo.setStatusId(cp.getStatusId());
			cpinfo.setStatus(cp.getStatus());
			cpinfo.setExternalId(cp.getExternalId());
			cpinfo.setPartyProfile(cp.getPartyProfile());
			cpinfo.setParentPartyId(cp.getParentPartyId());
			cpinfo.setParentPartyName(cp.getParentPartyName());
			cpinfo.setPartyTypeDescription(cp.getPartyTypeDescription());
			cpinfo.setDefaultSiteId(cp.getDefaultSiteId());
			cpinfo.setDefaultSiteName(cp.getDefaultSiteName());
			cpinfo.setFirstName(cp.getFirstName());
			cpinfo.setLastName(cp.getLastName());
			cpinfo.setStreetAddress(cp.getStreetAddress());
			cpinfo.setCity(cp.getCity());
			cpinfo.setZipCode(cp.getZipCode());
			cpinfo.setProvince(cp.getProvince());
			cpinfo.setCountry(cp.getCountry());
			cpinfo.setGender(cp.getGender());
			cpinfo.setBirthDate(cp.getBirthDate());
			cpinfo.setBirthProvince(cp.getBirthProvince());
			cpinfo.setBirthCountry(cp.getBirthCountry());
			cpinfo.setBirthCity(cp.getBirthCity());
			cpinfo.setPhoneNumber(cp.getContactTel());
			cpinfo.setFaxNumber(cp.getContactFax());
			cpinfo.setEmail(cp.getEmail());
			cpinfo.setNote(cp.getNote());
			cpinfo.setBlacklisted(cp.isBlacklisted());
			cpinfo.setWhitelisted(cp.isWhitelisted());
			cpinfos.add(cpinfo);
		}
		return cpinfos;
	}

	public static List<ParentPartyInfoResp> convertParentParties(List<SdpParentPartyDto> ppdtos) {
		if (ppdtos == null) {
			return null;
		}
		List<ParentPartyInfoResp> ppinfos = new ArrayList<ParentPartyInfoResp>();
		for (SdpParentPartyDto pp : ppdtos) {
			ParentPartyInfoResp ppinfo = new ParentPartyInfoResp();
			convertBaseInfo(pp, ppinfo);
			ppinfo.setPartyId(pp.getPartyId());
			ppinfo.setPartyName(pp.getPartyName());
			ppinfo.setPartyDescription(pp.getPartyDescription());
			ppinfo.setStatusId(pp.getStatusId());
			ppinfo.setStatus(pp.getStatus());
			ppinfo.setExternalId(pp.getExternalId());
			ppinfo.setPartyProfile(pp.getPartyProfile());
			ppinfo.setBlacklisted(pp.isBlacklisted());
			ppinfo.setWhitelisted(pp.isWhitelisted());
			ppinfos.add(ppinfo);
		}
		return ppinfos;
	}

	public static List<SdpAccountRequestDto> convertAccounts(List<AccountInfoRequest> ainfos) {
		List<SdpAccountRequestDto> adtos = new ArrayList<SdpAccountRequestDto>();
		for (AccountInfoRequest a : ainfos) {
			SdpAccountRequestDto adto = new SdpAccountRequestDto();
			adto.setAccountDescription(trim(a.getAccountDescription()));
			adto.setAccountName(trim(a.getAccountName()));
			adto.setAccountProfile(trim(a.getAccountProfile()));
			adto.setDefaultPartyAccount(a.isDefaultAccount());
			adto.setExternalID(trim(a.getAccountExternalId()));
			adto.setAccountSiteName(trim(a.getAccountSiteName()));
			adtos.add(adto);
		}
		return adtos;
	}

	public static List<SdpPartySiteRequestDto> convertSites(List<SiteInfoRequest> sinfos) {
		if (sinfos == null) {
			return null;
		}
		List<SdpPartySiteRequestDto> sdtos = new ArrayList<SdpPartySiteRequestDto>();
		for (SiteInfoRequest s : sinfos) {
			SdpPartySiteRequestDto sdto = new SdpPartySiteRequestDto();
			sdto.setAddress(trim(s.getStreetAddress()));
			sdto.setCity(trim(s.getCity()));
			sdto.setCountry(trim(s.getCountry()));
			sdto.setExternalID(trim(s.getExternalId()));
			sdto.setProvince(trim(s.getProvince()));
			sdto.setSiteDescription(trim(s.getSiteDescription()));
			sdto.setSiteName(trim(s.getSiteName()));
			sdto.setSiteProfile(trim(s.getSiteProfile()));
			sdto.setZipCode(trim(s.getZipCode()));
			sdtos.add(sdto);
		}
		return sdtos;
	}

	public static PartyInfoResp convertParty(SdpPartyResponseDto dto) {
		if (dto == null) {
			return null;
		}
		PartyInfoResp info = new PartyInfoResp();
		convertBaseInfo(dto, info);
		info.setPartyId(dto.getPartyId());
		info.setPartyTypeId(dto.getPartyTypeId());
		info.setPartyName(dto.getPartyName());
		info.setPartyDescription(dto.getPartyDescription());
		info.setParentPartyId(dto.getParentPartyId());
		info.setStatusId(dto.getStatusId());
		info.setExternalId(dto.getExternalId());
		info.setPartyProfile(dto.getPartyProfile());
		info.setFirstName(dto.getFirstName());
		info.setLastName(dto.getLastName());
		info.setDefaultSiteId(dto.getUserSiteId());
		info.setStreetAddress(dto.getStreetAddress());
		info.setCity(dto.getCity());
		info.setZipCode(dto.getZipCode());
		info.setProvince(dto.getProvince());
		info.setCountry(dto.getCountry());
		info.setGender(dto.getGender());
		info.setBirthDate(dto.getBirthDate());
		info.setBirthProvince(dto.getBirthProvince());
		info.setBirthCountry(dto.getBirthCountry());
		info.setBirthCity(dto.getBirthProvince());
		info.setPhoneNumber(dto.getContactTel());
		info.setFaxNumber(dto.getContactFax());
		info.setEmail(dto.getEmail());
		info.setNote(dto.getNote());
		info.getPartyGroups().setPartyGroupList(PartyGroupBeanConverter.convertPartyGroupList(dto.getPartyGroups()));
		info.setBlacklisted(dto.isBlacklisted());
		info.setWhitelisted(dto.isWhitelisted());
		return info;
	}
}
