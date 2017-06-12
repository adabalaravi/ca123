package com.accenture.sdp.csmfe.webservices.response.role;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchRoleResp extends BaseResp{

	/**
	 * 
	 */
	private static final long serialVersionUID = -641449631548104410L;

	private RolesListResp roles;
	
	public SearchRoleResp() {
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
