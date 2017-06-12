package com.accenture.sdp.csmfe.webservices.response.price;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseRespPaginated;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchAllPricesResp extends BaseRespPaginated {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8208722272023144182L;

	private PriceCatalogListResp priceList;

	public SearchAllPricesResp() {
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
