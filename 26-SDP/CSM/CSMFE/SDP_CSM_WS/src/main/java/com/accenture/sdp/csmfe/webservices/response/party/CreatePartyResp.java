package com.accenture.sdp.csmfe.webservices.response.party;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class CreatePartyResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5887008315807707340L;
	
	private Long partyId;

	public Long getPartyId() {
		return partyId;
	}

	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}
}
