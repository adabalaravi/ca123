package com.accenture.sdp.csmfe.webservices.response.servicetemplate;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class ServiceTemplateSearchByVariantInfoResp extends ServiceTemplateInfoResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5422867890970268171L;
	
	private String platformName;
	private String statusName;              
	private String serviceTypeName;
	
	public String getPlatformName() {
		return platformName;
	}
	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}
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
	
	
	

}
