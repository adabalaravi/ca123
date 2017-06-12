package com.accenture.sdp.csmfe.webservices.response.offergroup;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchOfferGroupResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1243936368519093198L;

	private OfferGroupListResp offerGroups;

	public SearchOfferGroupResp() {
		super();
		offerGroups = new OfferGroupListResp();
	}

	public OfferGroupListResp getOfferGroups() {
		return offerGroups;
	}

	public void setOfferGroups(OfferGroupListResp offerGroups) {
		this.offerGroups = offerGroups;
	}
}
