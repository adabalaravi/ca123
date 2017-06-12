package com.accenture.sdp.csmfe.webservices.request.solution;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class PartyGroupNameListRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -229960666058095139L;

	private List<PartyGroupNameInfoRequest> partyGroupList;

	public PartyGroupNameListRequest() {
		partyGroupList = new ArrayList<PartyGroupNameInfoRequest>();
	}

	@XmlElement(name = "partyGroup")
	public List<PartyGroupNameInfoRequest> getPartyGroupList() {
		return partyGroupList;
	}

	public void setPartyGroupList(List<PartyGroupNameInfoRequest> partyGroupList) {
		this.partyGroupList = partyGroupList;
	}
}
