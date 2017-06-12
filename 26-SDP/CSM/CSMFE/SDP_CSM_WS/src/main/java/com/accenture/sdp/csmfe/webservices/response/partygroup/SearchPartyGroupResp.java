package com.accenture.sdp.csmfe.webservices.response.partygroup;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseRespPaginated;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchPartyGroupResp extends BaseRespPaginated {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8208722272023144182L;
	
	private PartyGroupListResp partyGroups;

	public SearchPartyGroupResp() {
		partyGroups = new PartyGroupListResp();
	}

	public PartyGroupListResp getPartyGroups() {
		return partyGroups;
	}

	public void setPartyGroups(PartyGroupListResp partyGroups) {
		this.partyGroups = partyGroups;
	}
}
