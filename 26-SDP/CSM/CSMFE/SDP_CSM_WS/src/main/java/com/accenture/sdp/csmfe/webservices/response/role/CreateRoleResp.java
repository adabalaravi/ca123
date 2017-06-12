package com.accenture.sdp.csmfe.webservices.response.role;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class CreateRoleResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8763961662275773410L;
	private Long roleId;
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}
