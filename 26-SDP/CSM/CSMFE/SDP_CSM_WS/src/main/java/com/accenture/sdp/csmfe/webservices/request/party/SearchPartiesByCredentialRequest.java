package com.accenture.sdp.csmfe.webservices.request.party;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class SearchPartiesByCredentialRequest  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7323472295909782866L;

	private String username;
	private Long partyGroupId;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Long getPartyGroupId() {
		return partyGroupId;
	}
	public void setPartyGroupId(Long partyGroupId) {
		this.partyGroupId = partyGroupId;
	}
	
	
}
