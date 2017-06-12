package com.accenture.sdp.csmfe.webservices.response.solutionoffer;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SolutionOfferComplexInfoResp extends SolutionOfferInfoResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 591916346224937596L;

	private String statusName;

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}
