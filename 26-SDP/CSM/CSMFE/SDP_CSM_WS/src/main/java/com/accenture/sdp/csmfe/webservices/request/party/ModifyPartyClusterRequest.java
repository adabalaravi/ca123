package com.accenture.sdp.csmfe.webservices.request.party;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.request.partygroup.PartyGroupOperationListRequest;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class ModifyPartyClusterRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long partyId;
	private PartyGroupOperationListRequest partyGroups;

	public ModifyPartyClusterRequest() {
		partyGroups = new PartyGroupOperationListRequest();
	}

	public Long getPartyId() {
		return partyId;
	}

	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}

	public PartyGroupOperationListRequest getPartyGroups() {
		return partyGroups;
	}

	public void setPartyGroups(PartyGroupOperationListRequest partyGroups) {
		this.partyGroups = partyGroups;
	}
	
	
}
