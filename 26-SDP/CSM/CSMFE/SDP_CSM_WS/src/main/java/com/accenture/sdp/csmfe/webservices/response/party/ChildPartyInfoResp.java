package com.accenture.sdp.csmfe.webservices.response.party;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class ChildPartyInfoResp extends ParentPartyInfoResp { 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3271692023502059738L;

	private Long parentPartyId;
	private String parentPartyName;
	private String partyTypeDescription;
	private Long defaultSiteId;
	private String defaultSiteName;
	private String firstName;
	private String lastName;
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
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	
	
}
