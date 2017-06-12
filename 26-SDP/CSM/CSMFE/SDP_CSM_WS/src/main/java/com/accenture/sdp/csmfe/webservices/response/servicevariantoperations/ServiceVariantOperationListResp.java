package com.accenture.sdp.csmfe.webservices.response.servicevariantoperations;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class ServiceVariantOperationListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<SdpServiceVariantOperationInfoResp> serviceVariantOperations;

	@XmlElement(name = "serviceVariantOperation")
	public List<SdpServiceVariantOperationInfoResp> getServiceVariantOperations() {
		return serviceVariantOperations;
	}

	public void setServiceVariantOperations(List<SdpServiceVariantOperationInfoResp> serviceVariantOperations) {
		this.serviceVariantOperations = serviceVariantOperations;
	}

}
