package com.accenture.sdp.csmfe.webservices.response.party;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class CreateChildPartyAndParentResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1114068682990228958L;
	
	private Long partyChildId;
	private Long partyParentId;
	public Long getPartyChildId() {
		return partyChildId;
	}
	public void setPartyChildId(Long partyChildId) {
		this.partyChildId = partyChildId;
	}
	public Long getPartyParentId() {
		return partyParentId;
	}
	public void setPartyParentId(Long partyParentId) {
		this.partyParentId = partyParentId;
	}
	
	
}
