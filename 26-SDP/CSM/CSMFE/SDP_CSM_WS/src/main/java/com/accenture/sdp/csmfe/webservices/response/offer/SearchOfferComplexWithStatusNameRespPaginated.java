package com.accenture.sdp.csmfe.webservices.response.offer;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseRespPaginated;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchOfferComplexWithStatusNameRespPaginated extends BaseRespPaginated {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8505778752808870309L;

	private OfferComplexWithStatusNameListResp offers;
	
	public SearchOfferComplexWithStatusNameRespPaginated() {
		super();
		offers = new OfferComplexWithStatusNameListResp();
	}

	public OfferComplexWithStatusNameListResp getOffers() {
		return offers;
	}

	public void setOffers(OfferComplexWithStatusNameListResp offers) {
		this.offers = offers;
	}
}
