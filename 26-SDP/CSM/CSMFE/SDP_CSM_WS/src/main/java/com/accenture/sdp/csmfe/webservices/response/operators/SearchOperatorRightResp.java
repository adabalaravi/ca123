package com.accenture.sdp.csmfe.webservices.response.operators;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchOperatorRightResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6960399718422260649L;

	private OperatorRightListResp rights;

	public SearchOperatorRightResp() {
		super();
		rights = new OperatorRightListResp();
	}

	public OperatorRightListResp getRights() {
		return rights;
	}

	public void setRights(OperatorRightListResp rights) {
		this.rights = rights;
	}
}
