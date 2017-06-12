package com.accenture.sdp.csmfe.webservices.response.partygroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class PartyGroupListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2275378680611556303L;

	private List<PartyGroupInfoResp> partyGroupList;

	public PartyGroupListResp() {
		partyGroupList = new ArrayList<PartyGroupInfoResp>();
	}

	@XmlElement(name = "partyGroup")
	public List<PartyGroupInfoResp> getPartyGroupList() {
		return partyGroupList;
	}

	public void setPartyGroupList(List<PartyGroupInfoResp> partyGroupList) {
		this.partyGroupList = partyGroupList;
	}
}
