package com.accenture.sdp.csmfe.webservices.request.servicevariantoperations;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class SearchSdpServiceVariantOperationByServiceVariantRequest implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1836637442740074178L;

	private Long serviceVariantId;

	public Long getServiceVariantId() {
		return serviceVariantId;
	}

	public void setServiceVariantId(Long serviceVariantId) {
		this.serviceVariantId = serviceVariantId;
	}
	
}
