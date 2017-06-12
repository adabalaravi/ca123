package com.accenture.sdp.csmac.converters;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csmac.beans.party.PartyGroupBean;

public abstract class PartyGroupConverter {

	public static List<PartyGroupBean> convertPartyGroupsOfParty(List<com.accenture.sdp.csmfe.webservices.clients.party.PartyGroupInfoResp> infos) {
		if (infos == null) {
			return null;
		}
		List<PartyGroupBean> beans = new ArrayList<PartyGroupBean>();
		for (com.accenture.sdp.csmfe.webservices.clients.party.PartyGroupInfoResp info : infos) {
			beans.add(convertPartyGroupOfParty(info));
		}
		return beans;
	}

	public static PartyGroupBean convertPartyGroupOfParty(com.accenture.sdp.csmfe.webservices.clients.party.PartyGroupInfoResp info) {
		PartyGroupBean bean = new PartyGroupBean();
		bean.setId(info.getPartyGroupId());
		bean.setName(info.getPartyGroupName());
		bean.setDescription(info.getPartyGroupDescription());
		return bean;
	}

	public static List<PartyGroupBean> convertPartyGroups(List<com.accenture.sdp.csmfe.webservices.clients.partygroup.PartyGroupInfoResp> infos) {
		if (infos == null) {
			return null;
		}
		List<PartyGroupBean> beans = new ArrayList<PartyGroupBean>();
		for (com.accenture.sdp.csmfe.webservices.clients.partygroup.PartyGroupInfoResp info : infos) {
			beans.add(convertPartyGroup(info));
		}
		return beans;
	}

	public static PartyGroupBean convertPartyGroup(com.accenture.sdp.csmfe.webservices.clients.partygroup.PartyGroupInfoResp info) {
		PartyGroupBean bean = new PartyGroupBean();
		bean.setId(info.getPartyGroupId());
		bean.setName(info.getPartyGroupName());
		bean.setDescription(info.getPartyGroupDescription());
		return bean;
	}

}
