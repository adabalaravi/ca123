package com.accenture.sdp.csmfe.webservices.request.price;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class SearchPriceRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8685587710441503663L;

	private Long packagePriceId;

	public Long getPackagePriceId() {
		return packagePriceId;
	}

	public void setPackagePriceId(Long packagePriceId) {
		this.packagePriceId = packagePriceId;
	}
}
