package com.accenture.sdp.csmfe.webservices.response.platform;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseInfoResp;


@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class PlatformInfoResp extends BaseInfoResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5422867890970268171L;
	
	private Long platformId;
	private String platformName;
	private String platformDescription;
	private Long statusId;
	private String externalId;
	private String platformProfile;
	
	
	public Long getPlatformId() {
		return platformId;
	}
	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}
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
	public String getPlatformProfile() {
		return platformProfile;
	}
	public void setPlatformProfile(String platformProfile) {
		this.platformProfile = platformProfile;
	}
	
	
}
