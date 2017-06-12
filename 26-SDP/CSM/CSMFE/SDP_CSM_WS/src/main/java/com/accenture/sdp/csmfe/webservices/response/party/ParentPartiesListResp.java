package com.accenture.sdp.csmfe.webservices.response.party;

import java.io.Serializable;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class ParentPartiesListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -806164201109631047L;

	private List<ParentPartyInfoResp> partyList;

	@XmlElement(name = "party")
	public List<ParentPartyInfoResp> getPartyList() {
		return partyList;
	}

	public void setPartyList(List<ParentPartyInfoResp> partyList) {
		this.partyList = partyList;
	}
}
