package com.accenture.sdp.csmfe.webservices.response.avs;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class AvsRetailerDomainListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7036229131620698388L;

	private List<AvsRetailerDomainInfoResp> retailerDomainList;

	@XmlElement(name = "retailerDomain")
	public List<AvsRetailerDomainInfoResp> getRetailerDomainList() {
		return retailerDomainList;
	}

	public void setRetailerDomainList(List<AvsRetailerDomainInfoResp> retailerDomainList) {
		this.retailerDomainList = retailerDomainList;
	}
}
