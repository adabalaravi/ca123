package com.accenture.ams.accountmgmtservice;

public class ProductBuyedException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final String DEF_ERR_MSG = "Product/Subscription already buyed by user";
	
	public ProductBuyedException(String message){
		super(message);
	}
	
	public ProductBuyedException(){
		super(DEF_ERR_MSG);
	}
}
