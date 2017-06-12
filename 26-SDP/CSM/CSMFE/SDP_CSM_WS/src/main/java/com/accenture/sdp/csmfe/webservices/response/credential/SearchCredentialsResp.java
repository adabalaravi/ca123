package com.accenture.sdp.csmfe.webservices.response.credential;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchCredentialsResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4962157119133562440L;

	private CredentialListResp credentials;
	
	public SearchCredentialsResp() {
		super();
		credentials = new CredentialListResp();
	}

	public CredentialListResp getCredentials() {
		return credentials;
	}

	public void setCredentials(CredentialListResp credentials) {
		this.credentials = credentials;
	}
}
