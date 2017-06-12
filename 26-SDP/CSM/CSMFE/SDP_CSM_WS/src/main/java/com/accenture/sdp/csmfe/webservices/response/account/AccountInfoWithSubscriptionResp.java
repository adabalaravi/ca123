package com.accenture.sdp.csmfe.webservices.response.account;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class AccountInfoWithSubscriptionResp extends AccountInfoResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7964040496533349156L;

	private String accountType;

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	} 
}
