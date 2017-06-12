package com.accenture.sdp.csmfe.webservices.response.platform;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseInfoResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class ServiceTemplateInfoPlatformResp extends BaseInfoResp{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long serviceTemplateId;
	private String serviceTemplateName;
	private String serviceTemplateDescription;
	private String serviceTemplateProfile;
	private Long platformId;
	private Long statusId;
	private String externalId;
	private Long serviceTypeId;
	public Long getServiceTemplateId() {
		return serviceTemplateId;
	}
	public void setServiceTemplateId(Long serviceTemplateId) {
		this.serviceTemplateId = serviceTemplateId;
	}
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
	public Long getStatusId() {
		return statusId;
	}
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
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
