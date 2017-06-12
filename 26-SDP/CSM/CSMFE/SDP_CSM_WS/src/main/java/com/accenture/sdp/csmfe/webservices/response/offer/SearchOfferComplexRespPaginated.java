package com.accenture.sdp.csmfe.webservices.response.offer;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseRespPaginated;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchOfferComplexRespPaginated extends BaseRespPaginated {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8505778752808870309L;

	private OfferComplexListResp offers;
	
	public SearchOfferComplexRespPaginated() {
		super();
		offers = new OfferComplexListResp();
	}

	public OfferComplexListResp getOffers() {
		return offers;
	}

	public void setOffers(OfferComplexListResp offers) {
		this.offers = offers;
	}
}
