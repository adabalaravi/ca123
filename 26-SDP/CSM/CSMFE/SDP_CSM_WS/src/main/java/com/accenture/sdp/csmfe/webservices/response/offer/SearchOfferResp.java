package com.accenture.sdp.csmfe.webservices.response.offer;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchOfferResp extends BaseResp {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8505778752808870309L;

	private OfferListResp offers;
	
	public SearchOfferResp() {
		super();
		offers = new OfferListResp();
	}

	public OfferListResp getOffers() {
		return offers;
	}

	public void setOffers(OfferListResp offers) {
		this.offers = offers;
	}
}
