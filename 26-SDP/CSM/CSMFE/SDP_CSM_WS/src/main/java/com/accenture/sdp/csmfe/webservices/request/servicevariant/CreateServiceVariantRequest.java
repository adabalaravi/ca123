package com.accenture.sdp.csmfe.webservices.request.servicevariant;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class CreateServiceVariantRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4315467173275181889L;

	private String serviceVariantName;
	private String serviceVariantDescription;
	private String serviceVariantProfile;
	private Long serviceTemplateId;
	private String externalId;
	public String getServiceVariantName() {
		return serviceVariantName;
	}
	public void setServiceVariantName(String serviceVariantName) {
		this.serviceVariantName = serviceVariantName;
	}
	public String getServiceVariantDescription() {
		return serviceVariantDescription;
	}
	public void setServiceVariantDescription(String serviceVariantDescription) {
		this.serviceVariantDescription = serviceVariantDescription;
	}
	public String getServiceVariantProfile() {
		return serviceVariantProfile;
	}
	public void setServiceVariantProfile(String serviceVariantProfile) {
		this.serviceVariantProfile = serviceVariantProfile;
	}
	public Long getServiceTemplateId() {
		return serviceTemplateId;
	}
	public void setServiceTemplateId(Long serviceTemplateId) {
		this.serviceTemplateId = serviceTemplateId;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	
	

}
