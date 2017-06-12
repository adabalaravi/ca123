package com.accenture.sdp.csmfe.webservices.response.price;


import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;
import com.accenture.sdp.csmfe.webservices.response.BaseInfoResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class PriceCatalogResp extends BaseInfoResp {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9143418361601702375L;

	private Long priceCatalogId;
	private BigDecimal price;
	public Long getPriceCatalogId() {
		return priceCatalogId;
	}
	public void setPriceCatalogId(Long priceCatalogId) {
		this.priceCatalogId = priceCatalogId;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	
}
