package com.accenture.sdp.csmfe.webservices.response.role;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseInfoResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class RoleInfoResp extends BaseInfoResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 329588262822139247L;

	
	private String roleName;
	private String roleDescription;
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDescription() {
		return roleDescription;
	}
	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
	
	
}
