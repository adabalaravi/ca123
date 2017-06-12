package com.accenture.sdp.csmfe.webservices.request.operators;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class ModifyOperatorRightsRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	private ModifyOperatorRoleRightListRequest rights;

	public ModifyOperatorRightsRequest() {
		rights = new ModifyOperatorRoleRightListRequest();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ModifyOperatorRoleRightListRequest getRights() {
		return rights;
	}

	public void setRights(ModifyOperatorRoleRightListRequest rights) {
		this.rights = rights;
	}
	
	
}
