package com.accenture.sdp.csmfe.webservices.response.partygroup;


import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseInfoResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class PartyGroupInfoResp extends BaseInfoResp {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9143418361601702375L;

	private Long partyGroupId;
	private String partyGroupName;
	private String partyGroupDescription;
	
	public Long getPartyGroupId() {
		return partyGroupId;
	}
	public void setPartyGroupId(Long partyGroupId) {
		this.partyGroupId = partyGroupId;
	}
	public String getPartyGroupName() {
		return partyGroupName;
	}
	public void setPartyGroupName(String partyGroupName) {
		this.partyGroupName = partyGroupName;
	}
	public String getPartyGroupDescription() {
		return partyGroupDescription;
	}
	public void setPartyGroupDescription(String partyGroupDescription) {
		this.partyGroupDescription = partyGroupDescription;
	}
	
	
	
}
