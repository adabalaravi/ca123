package com.accenture.sdp.csm.dto.responses;

import java.io.Serializable;
import java.util.Date;

public class AvsDeviceIdTypeDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1626203265000709578L;

	private Date creationDate;
	private String typeDescription;
	private int typeId;
	private Date updateDate;
	private String value;
	
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getTypeDescription() {
		return typeDescription;
	}
	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
