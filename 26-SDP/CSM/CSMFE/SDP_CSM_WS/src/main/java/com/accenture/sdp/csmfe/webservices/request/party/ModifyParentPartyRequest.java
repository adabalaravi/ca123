package com.accenture.sdp.csmfe.webservices.request.party;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class ModifyParentPartyRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2131377928534489252L;
	
	private Long partyId;
	private String partyName;
	private String partyDescription;
	private String externalId;
	private String partyProfile;
	public Long getPartyId() {
		return partyId;
	}
	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public String getPartyDescription() {
		return partyDescription;
	}
	public void setPartyDescription(String partyDescription) {
		this.partyDescription = partyDescription;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getPartyProfile() {
		return partyProfile;
	}
	public void setPartyProfile(String partyProfile) {
		this.partyProfile = partyProfile;
	}
	
	
	
}
