package com.accenture.sdp.csmfe.webservices.response.servicevariantoperations;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchSdpServiceVariantOperationResp extends
		BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ServiceVariantOperationListResp serviceVariantOperations;
	public ServiceVariantOperationListResp getServiceVariantOperations() {
		return serviceVariantOperations;
	}
	public void setServiceVariantOperations(ServiceVariantOperationListResp serviceVariantOperations) {
		this.serviceVariantOperations = serviceVariantOperations;
	}
}
