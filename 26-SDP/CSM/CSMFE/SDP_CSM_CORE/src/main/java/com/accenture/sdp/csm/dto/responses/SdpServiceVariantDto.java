package com.accenture.sdp.csm.dto.responses;





public class SdpServiceVariantDto extends SdpBaseResponseDto {
	private static final long serialVersionUID = 1L;
	
	private Long serviceVariantId;

	
	private String serviceVariantDescription;

	
	private String serviceVariantName;
	
	
	private String serviceVariantProfile;


	private String externalId;
	
	
	private Long statusId;

	
	
	//campi non presenti nel model
	
	
	private String statusName;
	
	
	private String serviceTemplateName;
	
	
	private Long serviceTemplateId;


	public Long getServiceVariantId() {
		return serviceVariantId;
	}


	public void setServiceVariantId(Long serviceVariantId) {
		this.serviceVariantId = serviceVariantId;
	}


	public Long getStatusId() {
		return statusId;
	}


	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}


	public String getStatusName() {
		return statusName;
	}


	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}


	public Long getServiceTemplateId() {
		return serviceTemplateId;
	}


	public void setServiceTemplateId(Long serviceTemplateId) {
		this.serviceTemplateId = serviceTemplateId;
	}


	public SdpServiceVariantDto() {
   
    }


	public String getExternalId() {
		return this.externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}


	

	public String getServiceVariantDescription() {
		return serviceVariantDescription;
	}


	public void setServiceVariantDescription(String serviceVariantDescription) {
		this.serviceVariantDescription = serviceVariantDescription;
	}


	public String getServiceVariantName() {
		return serviceVariantName;
	}


	public void setServiceVariantName(String serviceVariantName) {
		this.serviceVariantName = serviceVariantName;
	}


	public String getServiceVariantProfile() {
		return serviceVariantProfile;
	}


	public void setServiceVariantProfile(String serviceVariantProfile) {
		this.serviceVariantProfile = serviceVariantProfile;
	}


	public String getServiceTemplateName() {
		return serviceTemplateName;
	}


	public void setServiceTemplateName(String serviceTemplateName) {
		this.serviceTemplateName = serviceTemplateName;
	}



	
	
}