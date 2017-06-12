package com.accenture.sdp.csmfe.webservices.response.solutionoffer;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SolutionOfferComplexListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8745236937888697601L;

	private List<SolutionOfferComplexInfoResp> solutionOfferList;

	@XmlElement(name = "solutionOffer")
	public List<SolutionOfferComplexInfoResp> getSolutionOfferList() {
		return solutionOfferList;
	}

	public void setSolutionOfferList(List<SolutionOfferComplexInfoResp> solutionOfferList) {
		this.solutionOfferList = solutionOfferList;
	}
}
