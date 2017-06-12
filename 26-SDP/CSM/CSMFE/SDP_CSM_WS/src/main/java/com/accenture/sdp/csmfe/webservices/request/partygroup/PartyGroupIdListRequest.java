package com.accenture.sdp.csmfe.webservices.request.partygroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class PartyGroupIdListRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -229960666058095139L;

	private List<Long> partyGroupIdList;

	public PartyGroupIdListRequest() {
		partyGroupIdList = new ArrayList<Long>();
	}

	@XmlElement(name = "partyGroupId")
	public List<Long> getPartyGroupIdList() {
		return partyGroupIdList;
	}

	public void setPartyGroupIdList(List<Long> partyGroupIdList) {
		this.partyGroupIdList = partyGroupIdList;
	}

}