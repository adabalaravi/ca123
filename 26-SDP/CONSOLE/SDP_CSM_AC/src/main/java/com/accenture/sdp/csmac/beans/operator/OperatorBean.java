package com.accenture.sdp.csmac.beans.operator;

import java.io.Serializable;
import java.util.List;

public class OperatorBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 18580048296601985L;

	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private OperatorRoleBean role;
	private String password;
	private Long statusId;
	private String statusName;
	private List<TenantBean> tenants;

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

	public OperatorRoleBean getRole() {
		return role;
	}

	public void setRole(OperatorRoleBean role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public List<TenantBean> getTenants() {
		return tenants;
	}

	public void setTenants(List<TenantBean> tenants) {
		this.tenants = tenants;
	}
}
