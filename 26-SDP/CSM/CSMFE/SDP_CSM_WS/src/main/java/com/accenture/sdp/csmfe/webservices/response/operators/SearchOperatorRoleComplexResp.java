package com.accenture.sdp.csmfe.webservices.response.operators;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchOperatorRoleComplexResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6960399718422260649L;

	private OperatorRoleComplexListResp roles;

	public SearchOperatorRoleComplexResp() {
		super(); 
		roles = new OperatorRoleComplexListResp();
	}

	public OperatorRoleComplexListResp getRoles() {
		return roles;
	}

	public void setRoles(OperatorRoleComplexListResp roles) {
		this.roles = roles;
	}
}
