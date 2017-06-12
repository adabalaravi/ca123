package com.accenture.sdp.csmcc.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class OperatorInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 18580048296601985L;

	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private OperatorRoleInfo role;

	private String password;

	private Long statusId;
	private String statusName;

	private List<TenantBean> tenants;
	
	public List<String> getTenantNames() {
		// return a list of tenant names to be shown on table
		List<String> result = new ArrayList<String>();
		if (tenants != null) {
			for (TenantBean tenant : tenants) {
				result.add(tenant.getTenantName());
			}
		}
		return result;
	}

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

	public OperatorInfo() {
		tenants = new ArrayList<TenantBean>();
	}

	public OperatorRoleInfo getRole() {
		return role;
	}

	public void setRole(OperatorRoleInfo role) {
		this.role = role;
	}
}
