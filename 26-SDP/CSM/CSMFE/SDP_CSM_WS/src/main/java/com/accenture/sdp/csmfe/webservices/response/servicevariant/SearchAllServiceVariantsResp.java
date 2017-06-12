package com.accenture.sdp.csmfe.webservices.response.servicevariant;

import com.accenture.sdp.csmfe.webservices.response.BaseRespPaginated;

public class SearchAllServiceVariantsResp extends BaseRespPaginated{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SearchServiceVariantListResp  servicevariantList;
	
	public SearchAllServiceVariantsResp() {
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
