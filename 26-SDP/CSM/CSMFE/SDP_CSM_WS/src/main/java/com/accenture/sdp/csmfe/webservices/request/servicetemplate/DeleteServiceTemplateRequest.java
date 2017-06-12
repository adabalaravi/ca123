package com.accenture.sdp.csmfe.webservices.request.servicetemplate;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class DeleteServiceTemplateRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long serviceTemplateId;
	
	
	public Long getServiceTemplateId() {
		return serviceTemplateId;
	}
	public void setServiceTemplateId(Long serviceTemplateId) {
		this.serviceTemplateId = serviceTemplateId;
	}
}
