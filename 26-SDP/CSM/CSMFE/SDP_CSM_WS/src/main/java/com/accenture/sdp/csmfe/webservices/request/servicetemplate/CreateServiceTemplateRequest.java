package com.accenture.sdp.csmfe.webservices.request.servicetemplate;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class CreateServiceTemplateRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String serviceTemplateName;
	private String serviceTemplateDescription;
	private String serviceTemplateProfile;
	private Long platformId;
	private String externalId;
	private Long serviceTypeId;
	public String getServiceTemplateName() {
		return serviceTemplateName;
	}
	public void setServiceTemplateName(String serviceTemplateName) {
		this.serviceTemplateName = serviceTemplateName;
	}
	public String getServiceTemplateDescription() {
		return serviceTemplateDescription;
	}
	public void setServiceTemplateDescription(String serviceTemplateDescription) {
		this.serviceTemplateDescription = serviceTemplateDescription;
	}
	public String getServiceTemplateProfile() {
		return serviceTemplateProfile;
	}
	public void setServiceTemplateProfile(String serviceTemplateProfile) {
		this.serviceTemplateProfile = serviceTemplateProfile;
	}
	public Long getPlatformId() {
		return platformId;
	}
	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public Long getServiceTypeId() {
		return serviceTypeId;
	}
	public void setServiceTypeId(Long serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}
	
	
}
