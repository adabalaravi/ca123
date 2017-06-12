package com.accenture.sdp.csmfe.webservices.request.operators;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class ModifyOperatorRoleRightsRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long roleId;
	private ModifyOperatorRoleRightListRequest rights;

	public ModifyOperatorRoleRightsRequest() {
		rights = new ModifyOperatorRoleRightListRequest();
	}

	public ModifyOperatorRoleRightListRequest getRights() {
		return rights;
	}

	public void setRights(ModifyOperatorRoleRightListRequest rights) {
		this.rights = rights;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}
