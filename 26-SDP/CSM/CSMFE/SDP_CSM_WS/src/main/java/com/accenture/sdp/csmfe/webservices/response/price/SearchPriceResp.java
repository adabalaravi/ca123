package com.accenture.sdp.csmfe.webservices.response.price;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchPriceResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8505778752808870309L;

	private PriceCatalogListResp priceList;

	public SearchPriceResp() {
		super();
		priceList = new PriceCatalogListResp();
	}

	public PriceCatalogListResp getPriceList() {
		return priceList;
	}

	public void setPriceList(PriceCatalogListResp priceList) {
		this.priceList = priceList;
	}
}
