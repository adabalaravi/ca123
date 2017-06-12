package com.accenture.sdp.csmfe.webservices.response.offer;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class OfferComplexWithStatusNameListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4137295561125145094L;

	private List<OfferComplexWithStatusNameInfoResp> offerList;

	@XmlElement(name = "offer")
	public List<OfferComplexWithStatusNameInfoResp> getOfferList() {
		return offerList;
	}

	public void setOfferList(List<OfferComplexWithStatusNameInfoResp> offerList) {
		this.offerList = offerList;
	}
}
