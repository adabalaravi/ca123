package com.accenture.sdp.csmfe.webservices.response.avs;

import java.io.Serializable;
import java.util.Date;

public class AvsPcExtendedRatingInfoResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8099077702255382613L;

	private Date creationDate;
	private String pcDescription;
	private Long pcId;
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
	public Long getPcId() {
		return pcId;
	}
	public void setPcId(Long pcId) {
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
