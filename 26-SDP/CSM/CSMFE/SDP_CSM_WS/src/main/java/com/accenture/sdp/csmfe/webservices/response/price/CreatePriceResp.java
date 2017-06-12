package com.accenture.sdp.csmfe.webservices.response.price;

import javax.xml.bind.annotation.XmlRootElement;
import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class CreatePriceResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5887008315807707340L;
	
	private Long priceCatalogId;

	public Long getPriceCatalogId() {
		return priceCatalogId;
	}

	public void setPriceCatalogId(Long priceCatalogId) {
		this.priceCatalogId = priceCatalogId;
	}
}
