package com.accenture.sdp.csmfe.webservices.response.solutionoffer;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SolutionOfferDetailListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6746359244643558662L;

	private List<SolutionOfferDetailInfoResp> solutionOfferDetailList;

	@XmlElement(name = "solutionOfferDetail")
	public List<SolutionOfferDetailInfoResp> getSolutionOfferDetailList() {
		return solutionOfferDetailList;
	}

	public void setSolutionOfferDetailList(List<SolutionOfferDetailInfoResp> solutionOfferDetailList) {
		this.solutionOfferDetailList = solutionOfferDetailList;
	}
}
