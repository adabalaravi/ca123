package com.accenture.sdp.csmfe.webservices.response.account;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class CreateAccountResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7899062726501389937L;
	
	private Long accountId;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
}
