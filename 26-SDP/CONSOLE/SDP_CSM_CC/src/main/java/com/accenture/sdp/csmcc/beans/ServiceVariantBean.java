package com.accenture.sdp.csmcc.beans;

import java.io.Serializable;

import javax.faces.event.ActionEvent;

import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.constants.StatusConstants;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmcc.managers.ServiceVariantManager;

public class ServiceVariantBean implements Serializable {

	public static final String SERVICEVARIANT_NAME_FIELD ="serviceVariantName";
	public static final String SERVICEVARIANT_DESC_FIELD ="serviceVariantDesc";
	public static final String TEMPLATE_NAME_FIELD ="templateName";
	public static final String SERVICEVARIANT_STATUS_FIELD ="serviceVariantStatus";
	public static final String SERVICEVARIANT_EXTID_FIELD ="serviceVariantExtId";
	public static final String SERVICEVARIANT_CREATION_DATE_FIELD ="serviceVariantCreationDate";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long serviceVariantId;
	private String serviceVariantName;
	private String serviceVariantDesc;
	private String serviceVariantStatus;
	private String serviceVariantExtId;
	private String serviceVariantProfile;
	private String serviceVariantCreationDate;
	private String serviceVariantTypeName;
	private String templateName;
	private String platformName;
	private ServiceTemplateBean parentServiceTemplate;
	private Boolean selected;
	private String serviceVariantNameStored;
	private String serviceVariantDescStored;
	private String serviceVariantExtIdStored;
	private String serviceVariantProfileStored;
	private String templateNameStored;
	private ServiceTemplateBean parentServiceTemplateStored;
	private boolean parentReadOnly;




	public ServiceVariantBean() {
	}


	public String getPlatformName() {
		return platformName;
	}


	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getTemplateName() {
		return templateName;
	}


	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}


	public ServiceTemplateBean getParentServiceTemplate() {
		return parentServiceTemplate;
	}


	public void setParentServiceTemplate(ServiceTemplateBean parentServiceTemplate) {
		this.parentServiceTemplate = parentServiceTemplate;
	}


	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		ServiceVariantManager tableBean = Utilities.findBean(ApplicationConstants.SERVICEVARIANT_TABLE_BEAN_NAME, ServiceVariantManager.class);
		if (tableBean.getPreviusSelectedBean() != null){
			tableBean.getPreviusSelectedBean().selected = false;
		}
		tableBean.setPreviusSelectedBean(this);
		this.selected = selected;
	}


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

	public String getServiceVariantDesc() {
		return serviceVariantDesc;
	}

	public void setServiceVariantDesc(String serviceVariantDesc) {
		this.serviceVariantDesc = serviceVariantDesc;
	}

	public String getServiceVariantStatus() {
		return serviceVariantStatus;
	}

	public void setServiceVariantStatus(String serviceVariantStatus) {
		this.serviceVariantStatus = serviceVariantStatus;
	}

	public String getServiceVariantExtId() {
		return serviceVariantExtId;
	}

	public void setServiceVariantExtId(String serviceVariantExtId) {
		this.serviceVariantExtId = serviceVariantExtId;
	}

	public String getServiceVariantProfile() {
		return serviceVariantProfile;
	}

	public void setServiceVariantProfile(String serviceVariantProfile) {
		this.serviceVariantProfile = serviceVariantProfile;
	}

	public String getServiceVariantCreationDate() {
		return serviceVariantCreationDate;
	}

	public void setServiceVariantCreationDate(String serviceVariantCreationDate) {
		this.serviceVariantCreationDate = serviceVariantCreationDate;
	}

	public String getServiceVariantTypeName() {
		return serviceVariantTypeName;
	}

	public void setServiceVariantTypeName(String serviceVariantTypeName) {
		this.serviceVariantTypeName = serviceVariantTypeName;
	}

	public boolean isParentReadOnly() {
		return parentReadOnly;
	}


	public void setParentReadOnly(boolean parentReadOnly) {
		this.parentReadOnly = parentReadOnly;
	}

	public void cleanServiceVariant(){
		this.serviceVariantName = "";
		this.serviceVariantDesc = "";
		this.serviceVariantExtId = "";
		this.serviceVariantProfile = "";
		this.templateName = "";
		this.parentServiceTemplate = null;
	}

	public void selectRow(){
		setSelected(true);
	}
	public void cancelFields(ActionEvent event) {
		cleanServiceVariant();
	}

	public void  storeValues(){
		this.serviceVariantNameStored=this.serviceVariantName;
		this.serviceVariantDescStored=this.serviceVariantDesc;
		this.serviceVariantExtIdStored=this.serviceVariantExtId;
		this.serviceVariantProfileStored=this.serviceVariantProfile;
		this.templateNameStored=this.templateName;
		this.parentServiceTemplateStored=this.parentServiceTemplate;

	}
	public void resetServiceVariant(){
		this.serviceVariantName=this.serviceVariantNameStored;
		this.serviceVariantDesc=this.serviceVariantDescStored;
		this.serviceVariantExtId=this.serviceVariantExtIdStored;
		this.serviceVariantProfile=this.serviceVariantProfileStored;
		this.templateName=this.templateNameStored;
		this.parentServiceTemplate=this.parentServiceTemplateStored;

	}

	public boolean isActive(){
		return StatusConstants.ACTIVE.equalsIgnoreCase(serviceVariantStatus);
	}

	public boolean isSuspended(){
		return StatusConstants.SUSPENDED.equalsIgnoreCase(serviceVariantStatus);
	}

	public boolean isInactive(){
		return StatusConstants.INACTIVE.equalsIgnoreCase(serviceVariantStatus);
	}


}
