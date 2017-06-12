package com.accenture.sdp.csm.dto.responses;

import java.util.List;

public class SdpOperatorRoleResponseDto extends SdpBaseResponseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long roleId;
	private String roleName;
	private String roleDescription;
	
	private List<SdpOperatorRightResponseDto> rights;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

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

	public List<SdpOperatorRightResponseDto> getRights() {
		return rights;
	}

	public void setRights(List<SdpOperatorRightResponseDto> rights) {
		this.rights = rights;
	}

	
}