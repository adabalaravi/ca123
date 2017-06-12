package com.accenture.sdp.csmfe.webservices.response.solutionoffer;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SolutionOfferDetailInfoResp implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9097884949257734526L;

	private Long packagePriceId;
	private Long packageId;
	public Long getPackagePriceId() {
		return packagePriceId;
	}
	public void setPackagePriceId(Long packagePriceId) {
		this.packagePriceId = packagePriceId;
	}
	public Long getPackageId() {
		return packageId;
	}
	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}
	
	
}
