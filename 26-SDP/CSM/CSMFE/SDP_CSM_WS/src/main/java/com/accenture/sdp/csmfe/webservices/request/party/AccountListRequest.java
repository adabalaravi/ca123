package com.accenture.sdp.csmfe.webservices.request.party;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class AccountListRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 210935256736185096L;

	private List<AccountInfoRequest> accountList;

	public AccountListRequest() {
		accountList = new ArrayList<AccountInfoRequest>();
	}

	@XmlElement(name = "account")
	public List<AccountInfoRequest> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<AccountInfoRequest> accountList) {
		this.accountList = accountList;
	}
}
