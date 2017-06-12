package com.accenture.sdp.csmfe.webservices.response.offergroup;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class OfferGroupListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6888804773304739033L;

	private List<OfferGroupInfoResp> offerGroupList;

	@XmlElement(name = "offerGroup")
	public List<OfferGroupInfoResp> getOfferGroupList() {
		return offerGroupList;
	}

	public void setOfferGroupList(List<OfferGroupInfoResp> offerGroupList) {
		this.offerGroupList = offerGroupList;
	}
}
