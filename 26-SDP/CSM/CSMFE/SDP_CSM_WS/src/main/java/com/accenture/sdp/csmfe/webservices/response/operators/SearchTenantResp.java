package com.accenture.sdp.csmfe.webservices.response.operators;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchTenantResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6960399718422260649L;

	private TenantListResp tenants;

	public SearchTenantResp() {
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
