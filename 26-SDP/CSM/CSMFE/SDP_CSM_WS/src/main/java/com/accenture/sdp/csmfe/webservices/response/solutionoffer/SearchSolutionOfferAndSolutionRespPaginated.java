package com.accenture.sdp.csmfe.webservices.response.solutionoffer;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseRespPaginated;
import com.accenture.sdp.csmfe.webservices.response.solution.SolutionInfoResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchSolutionOfferAndSolutionRespPaginated extends BaseRespPaginated {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1617736783383523292L;
	
	private SolutionOfferComplexListResp solutionOffers;
	
	public SearchSolutionOfferAndSolutionRespPaginated() {
		super();
		solutionOffers = new SolutionOfferComplexListResp();
	}
	
	private SolutionInfoResp solution;

	public SolutionOfferComplexListResp getSolutionOffers() {
		return solutionOffers;
	}

	public void setSolutionOffers(SolutionOfferComplexListResp solutionOffers) {
		this.solutionOffers = solutionOffers;
	}

	public SolutionInfoResp getSolution() {
		return solution;
	}

	public void setSolution(SolutionInfoResp solution) {
		this.solution = solution;
	}
	
	
}
