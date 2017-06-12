package com.accenture.sdp.csm.dto.responses;

import java.io.Serializable;
import java.util.Date;

public class AvsPcExtendedRatingDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8099077702255382613L;

	private Date creationDate;
	private String pcDescription;
	private int pcId;
	private String pcValue;
	private Date updateDate;

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getPcDescription() {
		return pcDescription;
	}

	public void setPcDescription(String pcDescription) {
		this.pcDescription = pcDescription;
	}

	public int getPcId() {
		return pcId;
	}

	public void setPcId(int pcId) {
		this.pcId = pcId;
	}

	public String getPcValue() {
		return pcValue;
	}

	public void setPcValue(String pcValue) {
		this.pcValue = pcValue;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
