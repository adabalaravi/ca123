package com.accenture.sdp.csmfe.webservices.request.party;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.request.partygroup.PartyGroupIdListRequest;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class CreateChildPartyAndParentDummyRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2368731423523614346L;

	private String partyName;
	private String partyDescription;
	private String externalId;
	private String partyProfile;
	private String firstName;
	private String lastName;
	private String userDefaultSiteName;
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
	private CredentialInfoRequest credential;
	private SiteListRequest sites;
	private PartyGroupIdListRequest partyGroups;
	
	
	public String getPartyName() {
		return partyName;
	}


	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}


	public String getPartyDescription() {
		return partyDescription;
	}


	public void setPartyDescription(String partyDescription) {
		this.partyDescription = partyDescription;
	}


	public String getExternalId() {
		return externalId;
	}


	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}


	public String getPartyProfile() {
		return partyProfile;
	}


	public void setPartyProfile(String partyProfile) {
		this.partyProfile = partyProfile;
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


	public String getUserDefaultSiteName() {
		return userDefaultSiteName;
	}


	public void setUserDefaultSiteName(String userDefaultSiteName) {
		this.userDefaultSiteName = userDefaultSiteName;
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


	public CredentialInfoRequest getCredential() {
		return credential;
	}


	public void setCredential(CredentialInfoRequest credential) {
		this.credential = credential;
	}


	public SiteListRequest getSites() {
		return sites;
	}


	public void setSites(SiteListRequest sites) {
		this.sites = sites;
	}


	public PartyGroupIdListRequest getPartyGroups() {
		return partyGroups;
	}


	public void setPartyGroups(PartyGroupIdListRequest partyGroups) {
		this.partyGroups = partyGroups;
	}


	
	
	public CreateChildPartyAndParentDummyRequest() {
		super();
		credential = new CredentialInfoRequest();
		sites = new SiteListRequest();
		partyGroups = new PartyGroupIdListRequest();
	}
	
	
}
