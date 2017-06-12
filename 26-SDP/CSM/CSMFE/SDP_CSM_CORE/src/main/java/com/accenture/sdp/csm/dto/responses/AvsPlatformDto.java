package com.accenture.sdp.csm.dto.responses;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AvsPlatformDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5845353745075723717L;

	private Date creationDate;
	private int platformId;
	private String platformName;
	private Date updateDate;
	private List<AvsDeviceIdTypeDto> deviceTypes;

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public int getPlatformId() {
		return platformId;
	}

	public void setPlatformId(int platformId) {
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

	public List<AvsDeviceIdTypeDto> getDeviceTypes() {
		return deviceTypes;
	}

	public void setDeviceTypes(List<AvsDeviceIdTypeDto> deviceTypes) {
		this.deviceTypes = deviceTypes;
	}

}
