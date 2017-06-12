package com.accenture.sdp.csmfe.webservices.response.role;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseRespPaginated;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchAllRolesResp extends BaseRespPaginated {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6960399718422260649L;

	private RolesListResp roles;

	public SearchAllRolesResp() {
		super();
		roles = new RolesListResp();
	}

	public RolesListResp getRoles() {
		return roles;
	}

	public void setRoles(RolesListResp roles) {
		this.roles = roles;
	}
}
