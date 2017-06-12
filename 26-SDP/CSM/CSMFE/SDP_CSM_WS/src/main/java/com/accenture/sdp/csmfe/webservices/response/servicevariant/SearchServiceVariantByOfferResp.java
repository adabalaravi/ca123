package com.accenture.sdp.csmfe.webservices.response.servicevariant;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchServiceVariantByOfferResp extends BaseResp{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SearchServiceVariantListResp  servicevariantList;
	
	public SearchServiceVariantByOfferResp() {
		super();
		servicevariantList = new SearchServiceVariantListResp();
	}

	public SearchServiceVariantListResp getServicevariantList() {
		return servicevariantList;
	}

	public void setServicevariantList(SearchServiceVariantListResp servicevariantList) {
		this.servicevariantList = servicevariantList;
	}
}
