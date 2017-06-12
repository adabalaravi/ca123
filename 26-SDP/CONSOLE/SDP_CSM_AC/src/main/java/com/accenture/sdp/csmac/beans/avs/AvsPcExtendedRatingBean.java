package com.accenture.sdp.csmac.beans.avs;

import java.io.Serializable;
import java.util.Date;

public class AvsPcExtendedRatingBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8099077702255382613L;

	private Date creationDate;
	private String description;
	private Long id;
	private String value;
	private Date updateDate;

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String pcDescription) {
		this.description = pcDescription;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long pcId) {
		this.id = pcId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String pcValue) {
		this.value = pcValue;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
