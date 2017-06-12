package com.accenture.sdp.csmfe.webservices.response.avs;

import java.io.Serializable;
import java.util.Date;

public class AvsPlatformInfoResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5845353745075723717L;

	private Date creationDate;
	private Long platformId;
	private String platformName;
	private Date updateDate;

	private AvsDeviceIdTypeListResp deviceIdTypes;

	public AvsPlatformInfoResp() {
		super();
		deviceIdTypes = new AvsDeviceIdTypeListResp();
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

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

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public AvsDeviceIdTypeListResp getDeviceIdTypes() {
		return deviceIdTypes;
	}

	public void setDeviceIdTypes(AvsDeviceIdTypeListResp deviceIdTypes) {
		this.deviceIdTypes = deviceIdTypes;
	}

	
}
