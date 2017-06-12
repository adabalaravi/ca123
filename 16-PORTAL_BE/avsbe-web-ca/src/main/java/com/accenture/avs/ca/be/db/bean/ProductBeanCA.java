package com.accenture.avs.ca.be.db.bean;

import java.io.Serializable;

import com.accenture.avs.be.db.bean.ProductBean;

/**
 * @author aditya.madhav.k
 * 
 */
public class ProductBeanCA extends ProductBean {

	private static final long serialVersionUID = 1L;

	private String productType="";
	private String appleStoreId;
	private String playStoreId;
	// NEW CR as on 09-MAR-2015 - RESTRICTING DAY PASS FOR 365 SUBSCRIBED USERS
	//eligible field for each product will be processed either true or false
	private String eligible;
	
	public String getProductType() {
		return productType;
	}
	public void setProductType(String type) {
		this.productType = type;
	}
	public String getAppleStoreId() {
		return appleStoreId;
	}
	public void setAppleStoreId(String appleStoreId) {
		this.appleStoreId = appleStoreId;
	}
	public String getPlayStoreId() {
		return playStoreId;
	}
	public void setPlayStoreId(String playStoreId) {
		this.playStoreId = playStoreId;
	}
	
	public String getEligible() {
		return eligible;
	}
	public void setEligible(String eligible) {
		this.eligible = eligible;
	}

}
