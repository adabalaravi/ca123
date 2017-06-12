package com.accenture.sdp.csmfe.webservices.response.servicevariant;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class CreateServiceVariantResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long serviceVariantId;
	
	public Long getServiceVariantId() {
		return serviceVariantId;
	}
	public void setServiceVariantId(Long serviceVariantId) {
		this.serviceVariantId = serviceVariantId;
	}
}
