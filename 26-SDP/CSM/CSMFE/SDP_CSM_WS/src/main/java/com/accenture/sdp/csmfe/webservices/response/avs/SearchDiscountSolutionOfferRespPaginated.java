package com.accenture.sdp.csmfe.webservices.response.avs;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseRespPaginated;
import com.accenture.sdp.csmfe.webservices.response.solutionoffer.SolutionOfferComplexListResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchDiscountSolutionOfferRespPaginated extends BaseRespPaginated {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1617736783383523292L;
	
	private SolutionOfferComplexListResp solutionOffers;
	
	public SearchDiscountSolutionOfferRespPaginated() {
		super();
		solutionOffers = new SolutionOfferComplexListResp();
	}

	public SolutionOfferComplexListResp getSolutionOffers() {
		return solutionOffers;
	}

	public void setSolutionOffers(SolutionOfferComplexListResp solutionOffers) {
		this.solutionOffers = solutionOffers;
	}
}
