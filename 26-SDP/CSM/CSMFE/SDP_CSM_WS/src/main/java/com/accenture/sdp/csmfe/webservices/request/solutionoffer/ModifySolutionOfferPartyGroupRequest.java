package com.accenture.sdp.csmfe.webservices.request.solutionoffer;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.request.partygroup.PartyGroupOperationListRequest;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class ModifySolutionOfferPartyGroupRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long solutionOfferId;
	private PartyGroupOperationListRequest partyGroups;

	public ModifySolutionOfferPartyGroupRequest() {
		partyGroups = new PartyGroupOperationListRequest();
	}

	public PartyGroupOperationListRequest getPartyGroups() {
		return partyGroups;
	}

	public void setPartyGroups(PartyGroupOperationListRequest partyGroups) {
		this.partyGroups = partyGroups;
	}

	public Long getSolutionOfferId() {
		return solutionOfferId;
	}

	public void setSolutionOfferId(Long solutionOfferId) {
		this.solutionOfferId = solutionOfferId;
	}
}
