package com.accenture.sdp.csmfe.webservices.response.account;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchAccountsResp extends BaseResp{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3222168319758424853L;
	
	private AccountListResp accounts;
	
	public SearchAccountsResp() {
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
