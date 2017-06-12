package com.accenture.sdp.csmac.beans.avs;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AvsPlatformBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5845353745075723717L;

	private Date creationDate;
	private Long platformId;
	private String platformName;
	private Date updateDate;
	
	private List<AvsDeviceIdTypeBean> deviceTypes;

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

	public List<AvsDeviceIdTypeBean> getDeviceTypes() {
		return deviceTypes;
	}

	public void setDeviceTypes(List<AvsDeviceIdTypeBean> deviceTypes) {
		this.deviceTypes = deviceTypes;
	}

}
