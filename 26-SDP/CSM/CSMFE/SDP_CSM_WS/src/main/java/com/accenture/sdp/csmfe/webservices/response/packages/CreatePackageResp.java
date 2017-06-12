package com.accenture.sdp.csmfe.webservices.response.packages;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class CreatePackageResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5190574133355761473L;

	private Long packageId;

	public Long getPackageId() {
		return packageId;
	}

	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}
}
