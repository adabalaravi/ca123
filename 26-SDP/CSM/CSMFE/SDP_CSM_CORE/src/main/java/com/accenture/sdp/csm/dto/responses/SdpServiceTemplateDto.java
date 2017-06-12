package com.accenture.sdp.csm.dto.responses;

import java.io.Serializable;

public class SdpServiceTemplateDto extends SdpBaseResponseDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long serviceTemplateId;

	private String serviceTemplateDescription;

	private String serviceTemplateName;

	private String serviceTemplateProfile;

	private String externalId;

	private Long statusId;

	// campi non presenti nel model

	private Long platformId;

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	private String platformName;

	private Long serviceTypeId;

	private String serviceTypeName;

	private String statusName;

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getServiceTypeName() {
		return serviceTypeName;
	}

	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}

	public SdpServiceTemplateDto() {

	}

	public Long getServiceTemplateId() {
		return serviceTemplateId;
	}

	public void setServiceTemplateId(Long serviceTemplateId) {
		this.serviceTemplateId = serviceTemplateId;
	}

	public String getServiceTemplateDescription() {
		return serviceTemplateDescription;
	}

	public void setServiceTemplateDescription(String serviceTemplateDescription) {
		this.serviceTemplateDescription = serviceTemplateDescription;
	}

	public String getServiceTemplateName() {
		return serviceTemplateName;
	}

	public void setServiceTemplateName(String serviceTemplateName) {
		this.serviceTemplateName = serviceTemplateName;
	}

	public String getServiceTemplateProfile() {
		return serviceTemplateProfile;
	}

	public void setServiceTemplateProfile(String serviceTemplateProfile) {
		this.serviceTemplateProfile = serviceTemplateProfile;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public Long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}

	public Long getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(Long serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}

}