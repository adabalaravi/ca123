package com.accenture.sdp.csm.dto.responses;

public class SdpRoleDto extends SdpBaseResponseDto {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7812468618714251642L;

	private String roleName;
	
	private String roleDescription;
		
	
	public String getRoleDescription() {
		return roleDescription;
	}
	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
