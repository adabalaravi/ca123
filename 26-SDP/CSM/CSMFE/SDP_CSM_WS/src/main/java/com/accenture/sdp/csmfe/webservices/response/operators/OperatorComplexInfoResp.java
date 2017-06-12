package com.accenture.sdp.csmfe.webservices.response.operators;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class OperatorComplexInfoResp extends OperatorInfoResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TenantListResp tenants;
	
	public OperatorComplexInfoResp() {
		super();
		tenants = new TenantListResp();
	}

	public TenantListResp getTenants() {
		return tenants;
	}

	public void setTenants(TenantListResp tenants) {
		this.tenants = tenants;
	}
}
