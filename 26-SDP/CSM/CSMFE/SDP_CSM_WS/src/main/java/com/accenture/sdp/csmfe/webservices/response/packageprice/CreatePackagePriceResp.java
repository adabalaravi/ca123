package com.accenture.sdp.csmfe.webservices.response.packageprice;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class CreatePackagePriceResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5887008315807707340L;
	
	private Long packagePriceId;

	public Long getPackagePriceId() {
		return packagePriceId;
	}

	public void setPackagePriceId(Long packagePriceId) {
		this.packagePriceId = packagePriceId;
	}
}
