package com.accenture.sdp.csmfe.webservices.response.party;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseRespPaginated;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchChildPartyRespPaginated extends BaseRespPaginated {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8008959640145309838L;

	private ChildPartiesListResp parties;

	public SearchChildPartyRespPaginated() {
		super();
		parties = new ChildPartiesListResp();
	}

	public ChildPartiesListResp getParties() {
		return parties;
	}

	public void setParties(ChildPartiesListResp parties) {
		this.parties = parties;
	}
}
