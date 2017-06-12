package com.accenture.sdp.csmfe.webservices.request.solution;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.request.partygroup.PartyGroupOperationListRequest;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class ModifySolutionPartyGroupRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long solutionId;
	private PartyGroupOperationListRequest partyGroups;

	public ModifySolutionPartyGroupRequest() {
		partyGroups = new PartyGroupOperationListRequest();
	}

	public Long getSolutionId() {
		return solutionId;
	}

	public void setSolutionId(Long solutionId) {
		this.solutionId = solutionId;
	}

	public PartyGroupOperationListRequest getPartyGroups() {
		return partyGroups;
	}

	public void setPartyGroups(PartyGroupOperationListRequest partyGroups) {
		this.partyGroups = partyGroups;
	}

}
