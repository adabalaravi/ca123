package com.accenture.ams.getpricebyuserservice;

public enum GetPriceByUserServiceEnum {
	GET_PPV_PRICE(0),
	GET_CONTENT_PRICE(1),
	GET_PRODUCT_PRICE(2),
	GET_BUNDLE_PRICE(3);
	
	final private int localValue;
	private GetPriceByUserServiceEnum(int _int){
		this.localValue = _int;
	}
	
	public int getValue(){
		return this.localValue;
	}	

}
