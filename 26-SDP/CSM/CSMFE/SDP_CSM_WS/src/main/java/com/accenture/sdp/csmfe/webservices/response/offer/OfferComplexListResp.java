package com.accenture.sdp.csmfe.webservices.response.offer;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class OfferComplexListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4137295561125145094L;

	private List<OfferComplexInfoResp> offerList;

	@XmlElement(name = "offer")
	public List<OfferComplexInfoResp> getOfferList() {
		return offerList;
	}

	public void setOfferList(List<OfferComplexInfoResp> offerList) {
		this.offerList = offerList;
	}
}
