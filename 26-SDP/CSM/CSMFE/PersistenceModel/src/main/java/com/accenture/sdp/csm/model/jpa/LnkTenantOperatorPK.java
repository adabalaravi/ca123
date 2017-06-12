package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the LNK_TENANT_OPERATOR database table.
 * 
 */
@Embeddable
public class LnkTenantOperatorPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 978869615366556444L;

	@Column(name = "TENANT_NAME")
	private String tenantName;

	@Column(name = "USERNAME")
	private String username;

	public LnkTenantOperatorPK() {
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tenantName == null) ? 0 : tenantName.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		LnkTenantOperatorPK other = (LnkTenantOperatorPK) obj;
		if (tenantName == null) {
			if (other.tenantName != null) {
				return false;
			}
		} else if (!tenantName.equals(other.tenantName)) {
			return false;
		}
		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}
		return true;
	}

}