package com.accenture.sdp.csmfe.webservices.request.platform;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class CreatePlatformRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3144005482375486699L;

	private String platformName;
	private String platformDescription;
	private String externalId;
	private String platformProfile;
	public String getPlatformName() {
		return platformName;
	}
	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}
	public String getPlatformDescription() {
		return platformDescription;
	}
	public void setPlatformDescription(String platformDescription) {
		this.platformDescription = platformDescription;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getPlatformProfile() {
		return platformProfile;
	}
	public void setPlatformProfile(String platformProfile) {
		this.platformProfile = platformProfile;
	}
	
	
}
