package com.accenture.sdp.csmfe.webservices.request.partygroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class PartyGroupOperationListRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8981002162624031006L;

	private List<PartyGroupOperationInfoRequest> partyGroupList;

	public PartyGroupOperationListRequest() {
		partyGroupList = new ArrayList<PartyGroupOperationInfoRequest>();
	}

	@XmlElement(name = "partyGroup")
	public List<PartyGroupOperationInfoRequest> getPartyGroupList() {
		return partyGroupList;
	}

	public void setPartyGroupList(List<PartyGroupOperationInfoRequest> partyGroupList) {
		this.partyGroupList = partyGroupList;
	}
}
