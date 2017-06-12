package com.accenture.sdp.csmac.converters;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csmac.beans.party.SdpPartyBean;
import com.accenture.sdp.csmac.common.utils.Utilities;
import com.accenture.sdp.csmfe.webservices.clients.party.ChildPartyInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.party.ParentPartyInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.party.PartyInfoResp;

public abstract class SdpPartyConverter {

	public static List<SdpPartyBean> convertChildParties(List<ChildPartyInfoResp> infos) {
		List<SdpPartyBean> beanList = new ArrayList<SdpPartyBean>();
		for (ChildPartyInfoResp info : infos) {
			beanList.add(convertChildParty(info));
		}
		return beanList;
	}

	public static SdpPartyBean convertChildParty(ChildPartyInfoResp info) {
		SdpPartyBean bean = new SdpPartyBean();
		bean.setId(info.getPartyId());
		bean.setName(info.getPartyName());
		bean.setDescription(info.getPartyDescription());
		bean.setStatusId(info.getStatusId());
		bean.setStatusName(info.getStatus());
		bean.setExternalId(info.getExternalId());
		bean.setProfile(info.getPartyProfile());
		bean.setParentPartyId(info.getParentPartyId());
		bean.setParentPartyName(info.getParentPartyName());
		bean.setPartyTypeDescription(info.getPartyTypeDescription());
		bean.setDefaultSiteId(info.getDefaultSiteId());
		bean.setDefaultSiteName(info.getDefaultSiteName());
		bean.setFirstName(info.getFirstName());
		bean.setLastName(info.getLastName());
		bean.setStreetAddress(info.getStreetAddress());
		bean.setCity(info.getCity());
		bean.setZipCode(info.getZipCode());
		bean.setProvince(info.getProvince());
		bean.setCountry(info.getCountry());
		bean.setGender(info.getGender());
		bean.setBirthDate(Utilities.getDateFromGregorianCalendar(info.getBirthDate()));
		bean.setBirthProvince(info.getBirthProvince());
		bean.setBirthCountry(info.getBirthCountry());
		bean.setBirthCity(info.getBirthCity());
		bean.setPhoneNumber(info.getPhoneNumber());
		bean.setFaxNumber(info.getFaxNumber());
		bean.setEmail(info.getEmail());
		bean.setNote(info.getNote());
		bean.setCreationDate(Utilities.getDateFromGregorianCalendar(info.getCreatedDate()));
		return bean;
	}

	public static List<SdpPartyBean> convertParentParties(List<ParentPartyInfoResp> infos) {
		List<SdpPartyBean> beanList = new ArrayList<SdpPartyBean>();
		for (ParentPartyInfoResp info : infos) {
			beanList.add(convertParentParty(info));
		}
		return beanList;
	}

	public static SdpPartyBean convertParentParty(ParentPartyInfoResp info) {
		SdpPartyBean bean = new SdpPartyBean();
		bean.setId(info.getPartyId());
		bean.setName(info.getPartyName());
		bean.setDescription(info.getPartyDescription());
		bean.setStatusId(info.getStatusId());
		bean.setStatusName(info.getStatus());
		bean.setExternalId(info.getExternalId());
		bean.setProfile(info.getPartyProfile());
		bean.setCreationDate(Utilities.getDateFromGregorianCalendar(info.getCreatedDate()));
		return bean;
	}

	public static SdpPartyBean convertParty(PartyInfoResp info) {
		SdpPartyBean bean = new SdpPartyBean();
		bean.setBirthCity(info.getBirthCity());
		bean.setBirthCountry(info.getBirthCountry());
		bean.setBirthDate(Utilities.getDateFromGregorianCalendar(info.getBirthDate()));
		bean.setBirthProvince(info.getBirthProvince());
		bean.setCity(info.getCity());
		bean.setCountry(info.getCountry());
		bean.setCreationDate(Utilities.getDateFromGregorianCalendar(info.getCreatedDate()));
		bean.setDefaultSiteId(info.getDefaultSiteId());
		bean.setEmail(info.getEmail());
		bean.setExternalId(info.getExternalId());
		bean.setFaxNumber(info.getFaxNumber());
		bean.setFirstName(info.getFirstName());
		bean.setGender(info.getGender());
		bean.setLastName(info.getLastName());
		bean.setNote(info.getNote());
		bean.setParentPartyId(info.getParentPartyId());
		bean.setDescription(info.getPartyDescription());
		bean.setId(info.getPartyId());
		bean.setName(info.getPartyName());
		bean.setProfile(info.getPartyProfile());
		bean.setPhoneNumber(info.getPhoneNumber());
		bean.setProvince(info.getProvince());
		bean.setStatusId(info.getStatusId());
		bean.setStreetAddress(info.getStreetAddress());
		bean.setZipCode(info.getZipCode());
		if (info.getPartyGroups() != null) {
			bean.setPartyGroups(PartyGroupConverter.convertPartyGroupsOfParty(info.getPartyGroups().getPartyGroup()));
		}
		return bean;
	}

}
