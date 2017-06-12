package com.accenture.sdp.csmfe.webservices.request.servicevariant;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class SearchServiceVariantByOfferRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1278078889362036350L;
	
	private String offerName;

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}
}
