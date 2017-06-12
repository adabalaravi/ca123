package com.accenture.sdp.csmfe.webservices.request.price;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class DeletePriceRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8367551101202438222L;

	private Long priceCatalogId;

	public Long getPriceCatalogId() {
		return priceCatalogId;
	}

	public void setPriceCatalogId(Long priceCatalogId) {
		this.priceCatalogId = priceCatalogId;
	}
}

