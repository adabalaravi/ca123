package com.accenture.sdp.csmfe.webservices.request.party;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.request.partygroup.PartyGroupIdListRequest;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class CreateParentPartyRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7937432142563599009L;

	private String partyName;
	private String partyDescription;
	private String externalId;
	private String partyProfile;
	private PartyGroupIdListRequest partyGroups;

	public CreateParentPartyRequest() {
		partyGroups = new PartyGroupIdListRequest();
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

	public PartyGroupIdListRequest getPartyGroups() {
		return partyGroups;
	}

	public void setPartyGroups(PartyGroupIdListRequest partyGroups) {
		this.partyGroups = partyGroups;
	}

}
