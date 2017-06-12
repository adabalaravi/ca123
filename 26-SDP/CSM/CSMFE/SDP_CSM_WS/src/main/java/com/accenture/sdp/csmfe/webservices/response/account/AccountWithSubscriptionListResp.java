package com.accenture.sdp.csmfe.webservices.response.account;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class AccountWithSubscriptionListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8591248524154481560L;

	private List<AccountInfoWithSubscriptionResp> accountList;

	@XmlElement(name = "account")
	public List<AccountInfoWithSubscriptionResp> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<AccountInfoWithSubscriptionResp> accountList) {
		this.accountList = accountList;
	}
}
