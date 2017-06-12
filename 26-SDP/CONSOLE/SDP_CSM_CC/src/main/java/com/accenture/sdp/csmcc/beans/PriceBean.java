package com.accenture.sdp.csmcc.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PriceBean implements Serializable {

	public static final String PRICE_FIELD = "price";
	public static final String PRICE_CREATION_DATE_FIELD = "priceCreationDate";
	private static final long serialVersionUID = 1L;
	private Long priceId;
	private BigDecimal price;
	private String priceString;
	private Date priceCreationDate;
	
	public PriceBean() {
	}


	public Long getPriceId() {
		return priceId;
	}

	public void setPriceId(Long priceId) {
		this.priceId = priceId;
	}

	public Date getpriceCreationDate() {
		return priceCreationDate;
	}

	public void setpriceCreationDate(Date priceCreationDate) {
		this.priceCreationDate = priceCreationDate;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void cleanPrice() {
		this.price = null;
	}

	public String getPriceString() {
		return priceString;
	}

	public void setPriceString(String priceString) {
		this.priceString = priceString;
	}

}
