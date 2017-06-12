package com.accenture.sdp.csm.dto.responses;

import java.util.List;

public class SdpOperatorResponseDto extends SdpBaseResponseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	private String firstName;
	private String lastName;
	private String statusName;
	private Long statusId;
	private Long roleId;
	private String roleName;
	private String email;
	
	private List<SdpTenantResponseDto> tenants;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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

	public List<SdpTenantResponseDto> getTenants() {
		return tenants;
	}

	public void setTenants(List<SdpTenantResponseDto> tenants) {
		this.tenants = tenants;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}
