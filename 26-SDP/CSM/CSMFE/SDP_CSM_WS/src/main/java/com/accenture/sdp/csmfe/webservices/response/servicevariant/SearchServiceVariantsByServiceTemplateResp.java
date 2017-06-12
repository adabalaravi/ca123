package com.accenture.sdp.csmfe.webservices.response.servicevariant;

import com.accenture.sdp.csmfe.webservices.response.BaseRespPaginated;

public class SearchServiceVariantsByServiceTemplateResp extends BaseRespPaginated{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SearchServiceVariantListResp  serviceVariantList;
	
	public SearchServiceVariantsByServiceTemplateResp() {
		super();
		serviceVariantList = new SearchServiceVariantListResp();
	}

	public SearchServiceVariantListResp getServiceVariantList() {
		return serviceVariantList;
	}

	public void setServiceVariantList(SearchServiceVariantListResp serviceVariantList) {
		this.serviceVariantList = serviceVariantList;
	}
}
