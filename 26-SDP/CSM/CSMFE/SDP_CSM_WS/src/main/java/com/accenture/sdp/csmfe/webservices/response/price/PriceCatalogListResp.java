package com.accenture.sdp.csmfe.webservices.response.price;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class PriceCatalogListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6388093666033541005L;

	private List<PriceCatalogResp> priceList;

	@XmlElement(name = "price")
	public List<PriceCatalogResp> getPriceList() {
		return priceList;
	}

	public void setPriceList(List<PriceCatalogResp> priceList) {
		this.priceList = priceList;
	}

}
