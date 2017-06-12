package com.accenture.ams.getpricebyuserservice.stub.component;

public class ItemPrice {
	private Double price;
	private String currency;
	private Double priceDiscounted;
	
	
	

	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getPriceDiscounted() {
		return priceDiscounted;
	}
	public void setPriceDiscounted(Double priceDiscounted) {
		this.priceDiscounted = priceDiscounted;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	@Override
	public String toString() {
		return "ItemPrice [price=" + price + ", currency=" + currency
				+ ", priceDiscounted=" + priceDiscounted + "]";
	}

	
	
}
