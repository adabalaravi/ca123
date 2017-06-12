package com.accenture.sdp.csmfe.webservices.response.servicevariant;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchServiceVariantListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<SearchServiceVariantInfoResp> serviceVariantList;

	@XmlElement(name = "serviceVariant")
	public List<SearchServiceVariantInfoResp> getServiceVariantList() {
		return serviceVariantList;
	}

	public void setServiceVariantList(List<SearchServiceVariantInfoResp> serviceVariantList) {
		this.serviceVariantList = serviceVariantList;
	}

}
