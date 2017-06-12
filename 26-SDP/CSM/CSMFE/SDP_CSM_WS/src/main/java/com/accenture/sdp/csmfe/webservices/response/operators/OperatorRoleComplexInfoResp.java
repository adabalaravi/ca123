package com.accenture.sdp.csmfe.webservices.response.operators;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class OperatorRoleComplexInfoResp extends OperatorRoleInfoResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private OperatorRightListResp rights;
	
	public OperatorRoleComplexInfoResp() {
		rights = new OperatorRightListResp();
	}

	public OperatorRightListResp getRights() {
		return rights;
	}

	public void setRights(OperatorRightListResp rights) {
		this.rights = rights;
	}
}
