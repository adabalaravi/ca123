package com.accenture.sdp.csmfe.webservices.response.offer;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class OfferComplexWithStatusNameInfoResp extends OfferInfoResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6013698386305849476L;
	
	private String statusName;

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}
