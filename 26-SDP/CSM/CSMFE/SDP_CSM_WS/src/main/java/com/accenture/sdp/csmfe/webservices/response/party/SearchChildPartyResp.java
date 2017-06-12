package com.accenture.sdp.csmfe.webservices.response.party;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchChildPartyResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8208722272023144182L;

	private ChildPartiesListResp parties;

	public SearchChildPartyResp() {
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