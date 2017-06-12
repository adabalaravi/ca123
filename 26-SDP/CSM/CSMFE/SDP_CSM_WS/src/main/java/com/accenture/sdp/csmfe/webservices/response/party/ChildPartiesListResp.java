package com.accenture.sdp.csmfe.webservices.response.party;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class ChildPartiesListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3855023343016733713L;

	private List<ChildPartyInfoResp> partyList;

	@XmlElement(name = "party")
	public List<ChildPartyInfoResp> getPartyList() {
		return partyList;
	}

	public void setPartyList(List<ChildPartyInfoResp> partyList) {
		this.partyList = partyList;
	}
}
