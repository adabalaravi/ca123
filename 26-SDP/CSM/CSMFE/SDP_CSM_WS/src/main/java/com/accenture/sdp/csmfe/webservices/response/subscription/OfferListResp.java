package com.accenture.sdp.csmfe.webservices.response.subscription;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class OfferListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 668049753190988238L;

	private List<OfferInfoResp> offerList;

	@XmlElement(name = "offer")
	public List<OfferInfoResp> getOfferList() {
		return offerList;
	}

	public void setOfferList(List<OfferInfoResp> offerList) {
		this.offerList = offerList;
	}
}
