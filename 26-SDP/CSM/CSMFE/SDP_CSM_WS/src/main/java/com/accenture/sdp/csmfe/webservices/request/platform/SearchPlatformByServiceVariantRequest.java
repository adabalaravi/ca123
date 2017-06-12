package com.accenture.sdp.csmfe.webservices.request.platform;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class SearchPlatformByServiceVariantRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 169396465392618663L;
	
	private String serviceVariantName;

	public String getServiceVariantName() {
		return serviceVariantName;
	}

	public void setServiceVariantName(String serviceVariantName) {
		this.serviceVariantName = serviceVariantName;
	}
}
