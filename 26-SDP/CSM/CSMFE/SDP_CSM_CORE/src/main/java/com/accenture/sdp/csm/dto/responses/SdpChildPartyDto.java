package com.accenture.sdp.csm.dto.responses;

import java.util.Date;

public class SdpChildPartyDto extends SdpParentPartyDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8131545737271563875L;

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
	private String contactTel;
	private String contactFax;
	private String email;
	private String note;

	/**
	 * @return the parentPartyId
	 */
	public Long getParentPartyId() {
		return parentPartyId;
	}

	/**
	 * @param parentPartyId
	 *            the parentPartyId to set
	 */
	public void setParentPartyId(Long parentPartyId) {
		this.parentPartyId = parentPartyId;
	}

	/**
	 * @return the parentPartyName
	 */
	public String getParentPartyName() {
		return parentPartyName;
	}

	/**
	 * @param parentPartyName
	 *            the parentPartyName to set
	 */
	public void setParentPartyName(String parentPartyName) {
		this.parentPartyName = parentPartyName;
	}

	/**
	 * @return the partyTypeDescription
	 */
	public String getPartyTypeDescription() {
		return partyTypeDescription;
	}

	/**
	 * @param partyTypeDescription
	 *            the partyTypeDescription to set
	 */
	public void setPartyTypeDescription(String partyTypeDescription) {
		this.partyTypeDescription = partyTypeDescription;
	}

	/**
	 * @return the defaultSiteId
	 */
	public Long getDefaultSiteId() {
		return defaultSiteId;
	}

	/**
	 * @param defaultSiteId
	 *            the defaultSiteId to set
	 */
	public void setDefaultSiteId(Long defaultSiteId) {
		this.defaultSiteId = defaultSiteId;
	}

	/**
	 * @return the defaultSiteName
	 */
	public String getDefaultSiteName() {
		return defaultSiteName;
	}

	/**
	 * @param defaultSiteName
	 *            the defaultSiteName to set
	 */
	public void setDefaultSiteName(String defaultSiteName) {
		this.defaultSiteName = defaultSiteName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the streetAddress
	 */
	public String getStreetAddress() {
		return streetAddress;
	}

	/**
	 * @param streetAddress
	 *            the streetAddress to set
	 */
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * @param zipCode
	 *            the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param province
	 *            the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the birthDate
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * @param birthDate
	 *            the birthDate to set
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * @return the birthProvince
	 */
	public String getBirthProvince() {
		return birthProvince;
	}

	/**
	 * @param birthProvince
	 *            the birthProvince to set
	 */
	public void setBirthProvince(String birthProvince) {
		this.birthProvince = birthProvince;
	}

	/**
	 * @return the birthCountry
	 */
	public String getBirthCountry() {
		return birthCountry;
	}

	/**
	 * @param birthCountry
	 *            the birthCountry to set
	 */
	public void setBirthCountry(String birthCountry) {
		this.birthCountry = birthCountry;
	}

	/**
	 * @return the birthCity
	 */
	public String getBirthCity() {
		return birthCity;
	}

	/**
	 * @param birthCity
	 *            the birthCity to set
	 */
	public void setBirthCity(String birthCity) {
		this.birthCity = birthCity;
	}

	/**
	 * @return the contactTel
	 */
	public String getContactTel() {
		return contactTel;
	}

	/**
	 * @param contactTel
	 *            the contactTel to set
	 */
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	/**
	 * @return the contactFax
	 */
	public String getContactFax() {
		return contactFax;
	}

	/**
	 * @param contactFax
	 *            the contactFax to set
	 */
	public void setContactFax(String contactFax) {
		this.contactFax = contactFax;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

}
