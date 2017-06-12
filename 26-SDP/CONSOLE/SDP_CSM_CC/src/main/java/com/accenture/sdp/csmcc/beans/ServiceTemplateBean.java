/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.sdp.csmcc.beans;

import java.io.Serializable;

import javax.faces.event.ActionEvent;

import com.accenture.sdp.csmcc.common.constants.StatusConstants;

public class ServiceTemplateBean implements Serializable {

	public static final String SERVICETEMPLATE_NAME_FIELD ="serviceTemplateName";
	public static final String SERVICETEMPLATE_DESC_FIELD ="serviceTemplateDesc";
	public static final String PLATFORM_NAME_FIELD ="platformName";
	public static final String SERVICETEMPLATE_STATUS_FIELD ="serviceTemplateStatus";
	public static final String SERVICETEMPLATE_EXTID_FIELD ="serviceTemplateExtId";
	public static final String SERVICETYPE_FIELD ="serviceTypeName";
	public static final String SERVICETEMPLATE_CREATION_DATE_FIELD ="serviceTemplateCreationDate";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long serviceTemplateId;
	private String serviceTemplateName;
	private String serviceTemplateDesc;
	private String serviceTemplateStatus;
	private String serviceTemplateExtId;
	private String serviceTemplateProfile;
	private String serviceTemplateCreationDate;
	private String serviceTemplateTypeName;
	private String platformName;
	private Long platformId;
	private Long serviceTemplateTypeId;
	private String serviceTemplateNameStored;
	private String serviceTemplateDescStored;
	private String serviceTemplateExtIdStored;
	private String serviceTemplateProfileStored;
	private String platformNameStored;
	private Long platformIdStored;
	private Long serviceTemplateTypeIdStored;
	private String serviceTemplateTypeNameStored;
	private boolean parentReadOnly;
	



	public Long getServiceTemplateTypeId() {
		return serviceTemplateTypeId;
	}


	public void setServiceTemplateTypeId(Long serviceTemplateTypeId) {
		this.serviceTemplateTypeId = serviceTemplateTypeId;
	}

	

	public ServiceTemplateBean() {
	}




	public Long getPlatformId() {
		return platformId;
	}




	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}




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




	public String getServiceTemplateDesc() {
		return serviceTemplateDesc;
	}




	public void setServiceTemplateDesc(String serviceTemplateDesc) {
		this.serviceTemplateDesc = serviceTemplateDesc;
	}




	public String getServiceTemplateStatus() {
		return serviceTemplateStatus;
	}




	public void setServiceTemplateStatus(String serviceTemplateStatus) {
		this.serviceTemplateStatus = serviceTemplateStatus;
	}




	public String getServiceTemplateExtId() {
		return serviceTemplateExtId;
	}




	public void setServiceTemplateExtId(String serviceTemplateExtId) {
		this.serviceTemplateExtId = serviceTemplateExtId;
	}




	public String getServiceTemplateProfile() {
		return serviceTemplateProfile;
	}




	public void setServiceTemplateProfile(String serviceTemplateProfile) {
		this.serviceTemplateProfile = serviceTemplateProfile;
	}




	public String getServiceTemplateCreationDate() {
		return serviceTemplateCreationDate;
	}




	public void setServiceTemplateCreationDate(String serviceTemplateCreationDate) {
		this.serviceTemplateCreationDate = serviceTemplateCreationDate;
	}




	public String getServiceTemplateTypeName() {
		return serviceTemplateTypeName;
	}




	public void setServiceTemplateTypeName(String serviceTemplateTypeName) {
		this.serviceTemplateTypeName = serviceTemplateTypeName;
	}




	public String getPlatformName() {
		return platformName;
	}




	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}


	public boolean isParentReadOnly() {
		return parentReadOnly;
	}


	public void setParentReadOnly(boolean parentReadOnly) {
		this.parentReadOnly = parentReadOnly;
	}


	

	// 
	//	
	public void cleanServiceTemplate(){
		this.serviceTemplateName="";
		this.serviceTemplateDesc="";
		this.serviceTemplateExtId="";
		this.serviceTemplateProfile="";
		this.platformName="";
		this.platformId=null;
		this.serviceTemplateTypeId=null;
		this.serviceTemplateTypeName="";
	}


	public void cancelFields(ActionEvent event) {
		cleanServiceTemplate();
	}
	
	public void  storeValues(){
		this.serviceTemplateNameStored=this.serviceTemplateName;
		this.serviceTemplateDescStored=this.serviceTemplateDesc;
		this.serviceTemplateExtIdStored=this.serviceTemplateExtId;
		this.serviceTemplateProfileStored=this.serviceTemplateProfile;
		this.platformNameStored=this.platformName;
		this.platformIdStored=this.platformId;
		this.serviceTemplateTypeIdStored=this.serviceTemplateTypeId;
		this.serviceTemplateTypeNameStored=this.serviceTemplateTypeName;
	}
	public void resetServiceTemplate(){
		this.serviceTemplateName=this.serviceTemplateNameStored;
		this.serviceTemplateDesc=this.serviceTemplateDescStored;
		this.serviceTemplateExtId=this.serviceTemplateExtIdStored;
		this.serviceTemplateProfile=this.serviceTemplateProfileStored;
		this.platformName=this.platformNameStored;
		this.platformId=this.platformIdStored;
		this.serviceTemplateTypeId=this.serviceTemplateTypeIdStored;
		this.serviceTemplateTypeName=this.serviceTemplateTypeNameStored;

	}

	public boolean isActive(){
		return StatusConstants.ACTIVE.equalsIgnoreCase(serviceTemplateStatus);
	}

	public boolean isSuspended(){
		return StatusConstants.SUSPENDED.equalsIgnoreCase(serviceTemplateStatus);
	}

	public boolean isInactive(){
		return StatusConstants.INACTIVE.equalsIgnoreCase(serviceTemplateStatus);
	}
}
