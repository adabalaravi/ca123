package com.accenture.sdp.csmfe.webservices.response.operators;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class TenantListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<TenantInfoResp> tenantList;

	public TenantListResp() {
		tenantList = new ArrayList<TenantInfoResp>();
	}

	@XmlElement(name = "tenant")
	public List<TenantInfoResp> getTenantList() {
		return tenantList;
	}

	public void setTenantList(List<TenantInfoResp> tenantList) {
		this.tenantList = tenantList;
	}
}