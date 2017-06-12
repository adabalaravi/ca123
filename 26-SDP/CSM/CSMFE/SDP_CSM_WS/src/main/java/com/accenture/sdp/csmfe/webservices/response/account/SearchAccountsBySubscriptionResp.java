package com.accenture.sdp.csmfe.webservices.response.account;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchAccountsBySubscriptionResp extends BaseResp{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7878873217802950653L;
	
	private AccountWithSubscriptionListResp accounts;

	public SearchAccountsBySubscriptionResp() {
		super();
		accounts = new AccountWithSubscriptionListResp();
	}

	public AccountWithSubscriptionListResp getAccounts() {
		return accounts;
	}

	public void setAccounts(AccountWithSubscriptionListResp accounts) {
		this.accounts = accounts;
	}
}
