package com.accenture.avs.ca.be.json.bean;

import java.io.Serializable;

public class AppPurchaseResponseDetails implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String responseMessage;

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

}
