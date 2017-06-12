package com.accenture.sdp.csmfe.webservices.response.platform;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseInfoResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class ServiceVariantInfoPlatformResp extends BaseInfoResp{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9210413132340722867L;
	
	
	private Long serviceVariantId;           
	private String serviceVariantName;         
	private String serviceVariantDescription;
	private String serviceVariantProfile;
	private Long serviceTemplateId;     
	private Long statusId;
	private String externalId;
	public Long getServiceVariantId() {
		return serviceVariantId;
	}
	public void setServiceVariantId(Long serviceVariantId) {
		this.serviceVariantId = serviceVariantId;
	}
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
	
}
