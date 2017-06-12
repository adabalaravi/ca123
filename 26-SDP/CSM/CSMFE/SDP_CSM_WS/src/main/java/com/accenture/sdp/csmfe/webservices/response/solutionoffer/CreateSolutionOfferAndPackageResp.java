package com.accenture.sdp.csmfe.webservices.response.solutionoffer;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class CreateSolutionOfferAndPackageResp extends CreateSolutionOfferResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4901528090567702750L;

	private SolutionOfferDetailListResp solutionOfferDetails;
	
	public CreateSolutionOfferAndPackageResp() {
		super();
		solutionOfferDetails = new SolutionOfferDetailListResp();
	}

	public SolutionOfferDetailListResp getSolutionOfferDetails() {
		return solutionOfferDetails;
	}

	public void setSolutionOfferDetails(SolutionOfferDetailListResp solutionOfferDetails) {
		this.solutionOfferDetails = solutionOfferDetails;
	}
}
