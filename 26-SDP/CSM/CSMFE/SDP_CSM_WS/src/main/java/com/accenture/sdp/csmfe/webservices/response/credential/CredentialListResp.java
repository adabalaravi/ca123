package com.accenture.sdp.csmfe.webservices.response.credential;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class CredentialListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7167608214520214075L;

	private List<CredentialInfoResp> credentialList;

	@XmlElement(name = "credential")
	public List<CredentialInfoResp> getCredentialList() {
		return credentialList;
	}

	public void setCredentialList(List<CredentialInfoResp> credentialList) {
		this.credentialList = credentialList;
	}
}
