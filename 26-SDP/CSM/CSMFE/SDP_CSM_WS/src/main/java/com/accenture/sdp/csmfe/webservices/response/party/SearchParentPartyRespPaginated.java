package com.accenture.sdp.csmfe.webservices.response.party;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseRespPaginated;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchParentPartyRespPaginated extends BaseRespPaginated {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6015365596000757729L;
	
	private ParentPartiesListResp parties;
	
	public SearchParentPartyRespPaginated() {
		super();
		parties = new ParentPartiesListResp();
	}

	public ParentPartiesListResp getParties() {
		return parties;
	}

	public void setParties(ParentPartiesListResp parties) {
		this.parties = parties;
	}
}
