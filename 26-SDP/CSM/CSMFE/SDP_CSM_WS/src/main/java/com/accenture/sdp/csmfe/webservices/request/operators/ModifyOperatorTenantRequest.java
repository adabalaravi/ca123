package com.accenture.sdp.csmfe.webservices.request.operators;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class ModifyOperatorTenantRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	private ModifyOperatorTenantListRequest tenants;

	public ModifyOperatorTenantRequest() {
		tenants = new ModifyOperatorTenantListRequest();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ModifyOperatorTenantListRequest getTenants() {
		return tenants;
	}

	public void setTenants(ModifyOperatorTenantListRequest tenants) {
		this.tenants = tenants;
	}
}
