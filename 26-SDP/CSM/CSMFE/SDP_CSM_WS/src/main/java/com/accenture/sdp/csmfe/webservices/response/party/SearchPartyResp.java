package com.accenture.sdp.csmfe.webservices.response.party;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchPartyResp extends BaseResp {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3584096752782738162L;

	private PartyInfoResp party;

	public PartyInfoResp getParty() {
		return party;
	}

	public void setParty(PartyInfoResp party) {
		this.party = party;
	}
}
