package com.accenture.sdp.csmfe.webservices.response.avs;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchAvsRetailerDomainResp extends BaseResp {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8505778752808870309L;

	private AvsRetailerDomainListResp retailerDomains;
	
	public SearchAvsRetailerDomainResp() {
		super();
		retailerDomains = new AvsRetailerDomainListResp();
	}

	public AvsRetailerDomainListResp getRetailerDomains() {
		return retailerDomains;
	}

	public void setRetailerDomains(AvsRetailerDomainListResp retailerDomains) {
		this.retailerDomains = retailerDomains;
	}
}
