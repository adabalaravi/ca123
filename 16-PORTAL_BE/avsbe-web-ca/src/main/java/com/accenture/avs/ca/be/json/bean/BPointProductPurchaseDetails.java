package com.accenture.avs.ca.be.json.bean;

import java.io.Serializable;

/**
 * @author h.kumar.satkuri
 * 
 * This class is used for sending the response to FE from ProductPurchaseBPoint API
 *
 */
public class BPointProductPurchaseDetails implements Serializable{

	private static final long serialVersionUID = 1L;

	private String contentId;
	private String contentTitle;
	private String contentType;
	private String currency;
	private String price;
	private String responseCode;
	private String token;
	private String errorText;
	private String crn;
	
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	public String getContentTitle() {
		return contentTitle;
	}
	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getErrorText() {
		return errorText;
	}
	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}
	public String getCrn() {
		return crn;
	}
	public void setCrn(String crn) {
		this.crn = crn;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
