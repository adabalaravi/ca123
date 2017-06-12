package com.accenture.sdp.csmfe.webservices.request.account;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class DeleteAccountRequest  implements Serializable{

	
	 /**
	 * 
	 */
	private static final long serialVersionUID = -7874229929488040834L;
	
	private Long accountId;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
}
