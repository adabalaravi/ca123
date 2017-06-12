package com.accenture.sdp.csmfe.webservices.response.account;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class AccountListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -267300344758399015L;

	private List<AccountInfoResp> accountList;

	@XmlElement(name = "account")
	public List<AccountInfoResp> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<AccountInfoResp> accountList) {
		this.accountList = accountList;
	}
}
