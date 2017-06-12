package com.accenture.sdp.csmfe.webservices.response.role;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class RolesListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<RoleInfoResp> roleList;

	@XmlElement(name = "role")
	public List<RoleInfoResp> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<RoleInfoResp> roleList) {
		this.roleList = roleList;
	}
}
