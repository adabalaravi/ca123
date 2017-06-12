package com.accenture.sdp.csmfe.webservices.request.solutionoffer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SolutionOfferDetailListRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8055490234909237213L;

	private List<SolutionOfferDetailInfoRequest> solutionOfferDetails;

	public SolutionOfferDetailListRequest() {
		solutionOfferDetails = new ArrayList<SolutionOfferDetailInfoRequest>();
	}

	@XmlElement(name = "solutionOfferDetail")
	public List<SolutionOfferDetailInfoRequest> getSolutionOfferDetails() {
		return solutionOfferDetails;
	}

	public void setSolutionOfferDetails(List<SolutionOfferDetailInfoRequest> solutionOfferDetails) {
		this.solutionOfferDetails = solutionOfferDetails;
	}

}
