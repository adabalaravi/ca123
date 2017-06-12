package com.accenture.avs.console.beans.operator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.accenture.avs.console.common.LoggerWrapper;
import com.accenture.avs.console.common.constants.ApplicationConstants;
import com.accenture.avs.console.common.exception.ServiceErrorException;
import com.accenture.avs.console.common.utils.Utilities;
import com.accenture.avs.console.controllers.PopupController;
import com.accenture.avs.console.services.OperatorService;

public class OperatorInfo implements Cloneable, Serializable {

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

	private List<TenantInfo> tenants;
	
	public String getTenantNames() {
		// return the list of tenants as
		// a string to be shown on table
		StringBuffer result = new StringBuffer();
		if (tenants != null) {
			Iterator<TenantInfo> i  = tenants.iterator();
			while (i.hasNext()) {
				result.append(i.next().getTenantName());
				if (i.hasNext()) {
					result.append("<br/>");
				}
			}
		}
		return result.toString();
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

	public OperatorInfo clone() {
		OperatorInfo clone = null;
		try {
			clone = (OperatorInfo) super.clone();
			if (role != null){
				clone.setRole((OperatorRoleInfo)role.clone());
			}
			if (tenants != null) {
				clone.setTenants(new ArrayList<TenantInfo>(tenants));
			}
		} catch (Exception e) {
			LoggerWrapper log = new LoggerWrapper(this.getClass());
			log.logError(e);
		}
		return clone;
	}

	public List<TenantInfo> getTenants() {
		return tenants;
	}

	public void setTenants(List<TenantInfo> tenants) {
		this.tenants = tenants;
	}

	public OperatorInfo() {
		tenants = new ArrayList<TenantInfo>();
	}

	public OperatorRoleInfo getRole() {
		if (role == null) {
			loadRole();
		}
		return role;
	}

	public void setRole(OperatorRoleInfo role) {
		this.role = role;
	}

	private void loadRole() {
		if (this.role==null && username!=null) {
			try{
				OperatorService service = Utilities.findBean(ApplicationConstants.SERVICE_OPERATOR_BEAN, OperatorService.class);
				this.role = service.searchOperatorRights(username);
			} catch (ServiceErrorException e) {
				if (ApplicationConstants.CODE_ZERO_RECORD_FOUND.equals(e.getCode())){
					this.role = new OperatorRoleInfo();
				} else {
					PopupController.handleServiceErrorException(e);
				}
			}
		}
	}
}
