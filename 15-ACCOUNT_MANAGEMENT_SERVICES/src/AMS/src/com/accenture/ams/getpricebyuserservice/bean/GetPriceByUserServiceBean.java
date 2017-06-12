package com.accenture.ams.getpricebyuserservice.bean;

import java.io.Serializable;

public class GetPriceByUserServiceBean implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Double price;
	private String currency;
	private Double priceDiscount;
	
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Double getPriceDiscount() {
		return priceDiscount;
	}
	public void setPriceDiscount(Double priceDiscount) {
		this.priceDiscount = priceDiscount;
	}
	
	@Override
	public String toString() {
		return "GetPriceByUserServiceBean [price=" + price + ", currency="
				+ currency + ", priceDiscount=" + priceDiscount + "]";
	}

	
	
	
}
