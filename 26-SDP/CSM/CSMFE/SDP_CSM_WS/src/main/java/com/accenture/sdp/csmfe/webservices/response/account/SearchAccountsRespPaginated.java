package com.accenture.sdp.csmfe.webservices.response.account;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseRespPaginated;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchAccountsRespPaginated extends BaseRespPaginated{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3222168319758424853L;
	
	private AccountListResp accounts;
	
	public SearchAccountsRespPaginated() {
		super();
		accounts = new AccountListResp();
	}

	public AccountListResp getAccounts() {
		return accounts;
	}

	public void setAccounts(AccountListResp accounts) {
		this.accounts = accounts;
	}
}
