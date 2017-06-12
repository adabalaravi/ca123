package com.accenture.sdp.csmac.beans;

import java.io.Serializable;
import java.util.Date;

public class OfferBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4217977767774726565L;

	private Long id;
	private String name;
	private String description;
	private Long statusId;
	private String statusName;
	private String externalId;
	private String profile;
	private Date creationDate;

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date offerCreationDate) {
		this.creationDate = offerCreationDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long offerId) {
		this.id = offerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String offerName) {
		this.name = offerName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String offerDesc) {
		this.description = offerDesc;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String offerStatus) {
		this.statusName = offerStatus;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String offerExtId) {
		this.externalId = offerExtId;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String offerProfile) {
		this.profile = offerProfile;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}
}
