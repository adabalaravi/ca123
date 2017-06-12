package com.accenture.sdp.csmfe.webservices.response.solutionoffer;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchSolutionOfferResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1617736783383523292L;

	private SolutionOfferListResp solutionOffers;

	public SearchSolutionOfferResp() {
		super();
		solutionOffers = new SolutionOfferListResp();
	}

	public SolutionOfferListResp getSolutionOffers() {
		return solutionOffers;
	}

	public void setSolutionOffers(SolutionOfferListResp solutionOffers) {
		this.solutionOffers = solutionOffers;
	}
}
