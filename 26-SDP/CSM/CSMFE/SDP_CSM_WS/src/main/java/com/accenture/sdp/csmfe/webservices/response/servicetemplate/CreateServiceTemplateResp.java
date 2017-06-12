package com.accenture.sdp.csmfe.webservices.response.servicetemplate;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class CreateServiceTemplateResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7122379136459096285L;

	private Long serviceTemplateId;

	public Long getServiceTemplateId() {
		return serviceTemplateId;
	}

	public void setServiceTemplateId(Long serviceTemplateId) {
		this.serviceTemplateId = serviceTemplateId;
	}
}
