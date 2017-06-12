package com.accenture.sdp.csmfe.webservices.response.servicevariant;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchServiceVariantResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ServiceVariantListResp  servicevariantList;            

	public SearchServiceVariantResp() {
		super();
		servicevariantList = new ServiceVariantListResp();
	}

	public ServiceVariantListResp getServicevariantList() {
		return servicevariantList;
	}

	public void setServicevariantList(ServiceVariantListResp servicevariantList) {
		this.servicevariantList = servicevariantList;
	}
}
