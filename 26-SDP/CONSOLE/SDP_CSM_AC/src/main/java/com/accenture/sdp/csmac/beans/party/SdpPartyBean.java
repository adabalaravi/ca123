package com.accenture.sdp.csmac.beans.party;

import java.util.Date;

public class SdpPartyBean extends PartyBean implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String description;
	private Long statusId;
	private String statusName;
	private String externalId;
	private String profile;
	private Date creationDate;
	private String streetAddress;
	private String city;
	private String zipCode;
	private String province;
	private String country;
	private String gender;
	private Date birthDate;
	private String birthProvince;
	private String birthCountry;
	private String birthCity;
	private String phoneNumber;
	private String faxNumber;
	private String email;
	private String note;
	private Long parentPartyId;
	private String parentPartyName;
	private String partyTypeDescription;
	private Long defaultSiteId;
	private String defaultSiteName;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getBirthProvince() {
		return birthProvince;
	}

	public void setBirthProvince(String birthProvince) {
		this.birthProvince = birthProvince;
	}

	public String getBirthCountry() {
		return birthCountry;
	}

	public void setBirthCountry(String birthCountry) {
		this.birthCountry = birthCountry;
	}

	public String getBirthCity() {
		return birthCity;
	}

	public void setBirthCity(String birthCity) {
		this.birthCity = birthCity;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getParentPartyId() {
		return parentPartyId;
	}

	public void setParentPartyId(Long parentPartyId) {
		this.parentPartyId = parentPartyId;
	}

	public String getParentPartyName() {
		return parentPartyName;
	}

	public void setParentPartyName(String parentPartyName) {
		this.parentPartyName = parentPartyName;
	}

	public String getPartyTypeDescription() {
		return partyTypeDescription;
	}

	public void setPartyTypeDescription(String partyTypeDescription) {
		this.partyTypeDescription = partyTypeDescription;
	}

	public Long getDefaultSiteId() {
		return defaultSiteId;
	}

	public void setDefaultSiteId(Long defaultSiteId) {
		this.defaultSiteId = defaultSiteId;
	}

	public String getDefaultSiteName() {
		return defaultSiteName;
	}

	public void setDefaultSiteName(String defaultSiteName) {
		this.defaultSiteName = defaultSiteName;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
