package com.accenture.sdp.csmfe.webservices.request.account;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchAccountRequest  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1951464741021155944L;

	private String accountName;

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
}
