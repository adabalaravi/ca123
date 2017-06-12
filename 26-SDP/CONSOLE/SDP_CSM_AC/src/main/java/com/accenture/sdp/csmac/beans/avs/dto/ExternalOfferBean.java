package com.accenture.sdp.csmac.beans.avs.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ExternalOfferBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String externalOfferId;
	private String packageId;
	private BigDecimal price;

	public String getExternalOfferId() {
		return externalOfferId;
	}

	public void setExternalOfferId(String externalOfferId) {
		this.externalOfferId = externalOfferId;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
