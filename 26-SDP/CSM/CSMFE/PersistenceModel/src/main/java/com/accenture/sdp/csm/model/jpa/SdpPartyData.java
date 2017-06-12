package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the SDP_PARTY_DATA database table.
 * 
 */

@Entity
@Table(name = "SDP_PARTY_DATA_VIEW")
public class SdpPartyData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PARTY_ID")
	private Long partyId;

	@Column(name = "BIRTH_CITY")
	private String birthCity;

	@Column(name = "BIRTH_COUNTRY")
	private String birthCountry;

	@Temporal(TemporalType.DATE)
	@Column(name = "BIRTH_DATE")
	private Date birthDate;

	@Column(name = "BIRTH_PROVINCE")
	private String birthProvince;

	private String city;

	@Column(name = "CONTACT_FAX")
	private String contactFax;

	@Column(name = "CONTACT_TEL")
	private String contactTel;

	private String country;

	@Column(name = "CREATED_BY_ID")
	private String createdById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "DELETED_BY_ID")
	private String deletedById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DELETED_DATE")
	private Date deletedDate;

	private String email;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "LAST_NAME")
	private String lastName;

	private String note;

	private String province;

	@Column(name = "STREET_ADDRESS")
	private String address;

	@Column(name = "UPDATED_BY_ID")
	private String updatedById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "ZIP_CODE")
	private String zipCode;

	// //bi-directional one-to-one association to SdpParty
	@OneToOne()
	@PrimaryKeyJoinColumn(name = "PARTY_ID", referencedColumnName = "PARTY_ID")
	private SdpParty sdpParty;

	// bi-directional many-to-one association to SdpPartySite
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_SITE_ID")
	private SdpSite sdpPartySite;

	public SdpPartyData() {
	}

	public Long getPartyId() {
		return this.partyId;
	}

	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}

	public String getBirthCity() {
		return this.birthCity;
	}

	public void setBirthCity(String birthCity) {
		this.birthCity = birthCity;
	}

	public String getBirthCountry() {
		return this.birthCountry;
	}

	public void setBirthCountry(String birthCountry) {
		this.birthCountry = birthCountry;
	}

	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getBirthProvince() {
		return this.birthProvince;
	}

	public void setBirthProvince(String birthProvince) {
		this.birthProvince = birthProvince;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getContactFax() {
		return this.contactFax;
	}

	public void setContactFax(String contactFax) {
		this.contactFax = contactFax;
	}

	public String getContactTel() {
		return this.contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCreatedById() {
		return this.createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getDeletedById() {
		return this.deletedById;
	}

	public void setDeletedById(String deletedById) {
		this.deletedById = deletedById;
	}

	public Date getDeletedDate() {
		return this.deletedDate;
	}

	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUpdatedById() {
		return this.updatedById;
	}

	public void setUpdatedById(String updatedById) {
		this.updatedById = updatedById;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public SdpParty getSdpParty() {
		return this.sdpParty;
	}

	public void setSdpParty(SdpParty sdpParty) {
		this.sdpParty = sdpParty;
		if (sdpParty != null) {
			this.partyId = sdpParty.getPartyId();

		}
	}

	public SdpSite getSdpPartySite() {
		return this.sdpPartySite;
	}

	public void setSdpPartySite(SdpSite sdpPartySite) {
		this.sdpPartySite = sdpPartySite;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((partyId == null) ? 0 : partyId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SdpPartyData other = (SdpPartyData) obj;
		if (getPartyId() == null) {
			if (other.getPartyId() != null) {
				return false;
			}
		} else if (!getPartyId().equals(other.getPartyId())) {
			return false;
		}
		return true;
	}

}