package com.accenture.sdp.csmfe.webservices.response.solution;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.partygroup.PartyGroupListResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SolutionComplexInfoResp extends SolutionInfoResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5422867890970268171L;

	private String solutionTypeName;
	private String statusName;
	private PartyGroupListResp partyGroups;

	public SolutionComplexInfoResp() {
		super();
		partyGroups = new PartyGroupListResp();
	}

	public String getSolutionTypeName() {
		return solutionTypeName;
	}

	public void setSolutionTypeName(String solutionTypeName) {
		this.solutionTypeName = solutionTypeName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public PartyGroupListResp getPartyGroups() {
		return partyGroups;
	}

	public void setPartyGroups(PartyGroupListResp partyGroups) {
		this.partyGroups = partyGroups;
	}

}
