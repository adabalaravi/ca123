package com.accenture.sdp.csmac.beans.avs;

import java.io.Serializable;
import java.util.Date;

public class AvsPcLevelBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5603720368835046974L;

	private Date creationDate;
	private String description;
	private Date updateDate;
	private String value;

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
