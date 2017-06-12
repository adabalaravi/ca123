package com.accenture.sdp.csmfe.webservices.response.credential;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

public class ReserveUsernameResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -71034568358530238L;
	
	private AlternativeUsernameListResp alternativeUsername;

	public ReserveUsernameResp() {
		super();
		alternativeUsername = new AlternativeUsernameListResp();
	}

	public AlternativeUsernameListResp getAlternativeUsername() {
		return alternativeUsername;
	}

	public void setAlternativeUsername(AlternativeUsernameListResp alternativeUsername) {
		this.alternativeUsername = alternativeUsername;
	}
}
