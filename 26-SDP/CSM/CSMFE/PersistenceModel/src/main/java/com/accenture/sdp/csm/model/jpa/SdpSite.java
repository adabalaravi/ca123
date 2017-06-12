package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the SDP_PARTY_SITE database table.
 * 
 */
@Entity
@Table(name = "SDP_PARTY_SITE")
@NamedQueries({
		@NamedQuery(name = SdpSite.QUERY_RETRIVE_BY_NAME, query = "select p from SdpSite p where p.siteName=:siteName"),
		@NamedQuery(name = SdpSite.QUERY_RETRIVE_BY_NAME_LIKE, query = "select p from SdpSite p where p.siteName like :siteName"),
		@NamedQuery(name = SdpSite.QUERY_COUNT_BY_NAME_LIKE, query = "select COUNT(p.siteId) from SdpSite p where p.siteName like :siteName"),
		@NamedQuery(name = SdpSite.QUERY_COUNT_BY_PARTYID_AND_NOT_STATUS, query = "select COUNT (p.siteId) from SdpSite p where p.sdpParty.partyId=:partyId and p.statusId <> :statusId"),
		@NamedQuery(name = SdpSite.QUERY_RETRIEVE_BY_PARTYID, query = "select p from SdpSite p where p.sdpParty.partyId=:partyId"),
		@NamedQuery(name = SdpSite.QUERY_COUNT_BY_PARTYID, query = "select COUNT(p.siteId) from SdpSite p where p.sdpParty.partyId=:partyId"),
		@NamedQuery(name = SdpSite.QUERY_RETRIEVE_BY_EXTERNALID, query = "select p from SdpSite p where p.externalId=:externalId") })
public class SdpSite implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String QUERY_RETRIVE_BY_NAME = "selectSiteByName";
	public static final String QUERY_RETRIVE_BY_NAME_LIKE = "selectSiteByNameLike";
	public static final String QUERY_COUNT_BY_NAME_LIKE = "countSiteByNameLike";
	public static final String QUERY_COUNT_BY_PARTYID_AND_NOT_STATUS = "countSiteByPartyIdAndStatusIdNotEqual";
	public static final String QUERY_RETRIEVE_BY_PARTYID = "selectSiteByPartyId";
	public static final String QUERY_COUNT_BY_PARTYID = "countSiteByPartyId";
	public static final String QUERY_RETRIEVE_BY_EXTERNALID = "selectSiteByExternalId";

	public static final String PARAM_SITE_NAME = "siteName";
	public static final String PARAM_PARTY_ID = "partyId";
	public static final String PARAM_STATUS_ID = "statusId";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SITE_ID")
	private Long siteId;

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

	@Column(name = "EXTERNAL_ID")
	private String externalId;

	@Column(name = "SITE_DESCRIPTION")
	private String siteDescription;

	@Column(name = "SITE_NAME")
	private String siteName;

	@Lob()
	@Column(name = "SITE_PROFILE")
	private String siteProfile;

	@Column(name = "UPDATED_BY_ID")
	private String updatedById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	// bi-directional many-to-one association to SdpAccount
	@OneToMany(mappedBy = "sdpPartySite", fetch = FetchType.LAZY)
	private List<SdpAccount> sdpPartySiteAccounts;

	// bi-directional many-to-one association to SdpPartyData
	@OneToMany(mappedBy = "sdpPartySite", fetch = FetchType.LAZY)
	private List<SdpPartyData> sdpPartySitePartyData;

	// bi-directional many-to-one association to RefStatusType
	@Column(name = "STATUS_ID")
	private Long statusId;

	// bi-directional many-to-one association to SdpParty
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARTY_ID")
	private SdpParty sdpParty;

	// bi-directional many-to-one association to SdpSubscription
	@OneToMany(mappedBy = "sdpPartySite", fetch = FetchType.LAZY)
	private List<SdpSubscription> sdpSubscriptions = new ArrayList<SdpSubscription>();

	@Column(name = "CHG_STATUS_BY_ID")
	private String changeStatusById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CHG_STATUS_DATE")
	private Date changeStatusDate;

	@Column(name = "STREET_ADDRESS")
	private String address;

	@Column(name = "CITY")
	private String city;

	@Column(name = "ZIP_CODE")
	private String zipCode;

	@Column(name = "PROVINCE")
	private String province;

	@Column(name = "COUNTRY")
	private String country;

	public SdpSite() {
		sdpPartySiteAccounts = new ArrayList<SdpAccount>();
		sdpPartySitePartyData = new ArrayList<SdpPartyData>();
		sdpSubscriptions = new ArrayList<SdpSubscription>();
	}

	public Long getSiteId() {
		return this.siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
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

	public String getExternalId() {
		return this.externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getSiteDescription() {
		return this.siteDescription;
	}

	public void setSiteDescription(String siteDescription) {
		this.siteDescription = siteDescription;
	}

	public String getSiteName() {
		return this.siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSiteProfile() {
		return this.siteProfile;
	}

	public void setSiteProfile(String siteProfile) {
		this.siteProfile = siteProfile;
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

	public List<SdpAccount> getSdpAccounts() {
		return this.sdpPartySiteAccounts;
	}

	public void setSdpPartySiteAccounts(List<SdpAccount> sdpAccounts) {
		this.sdpPartySiteAccounts = sdpAccounts;
	}

	public List<SdpPartyData> getSdpPartySitePartyData() {
		return this.sdpPartySitePartyData;
	}

	public void setSdpPartySitePartyData(List<SdpPartyData> sdpPartyData) {
		this.sdpPartySitePartyData = sdpPartyData;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public SdpParty getSdpParty() {
		return this.sdpParty;
	}

	public void setSdpParty(SdpParty sdpParty) {
		this.sdpParty = sdpParty;
	}

	public List<SdpSubscription> getSdpSubscriptions() {
		return this.sdpSubscriptions;
	}

	public void setSdpSubscriptions(List<SdpSubscription> sdpSubscriptions) {
		this.sdpSubscriptions = sdpSubscriptions;
	}

	public String getChangeStatusById() {
		return changeStatusById;
	}

	public void setChangeStatusById(String changeStatusById) {
		this.changeStatusById = changeStatusById;
	}

	public Date getChangeStatusDate() {
		return changeStatusDate;
	}

	public void setChangeStatusDate(Date changeStatusDate) {
		this.changeStatusDate = changeStatusDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public List<SdpAccount> getSdpPartySiteAccounts() {
		return sdpPartySiteAccounts;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((siteId == null) ? 0 : siteId.hashCode());
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
		SdpSite other = (SdpSite) obj;
		if (getSiteId() == null) {
			if (other.getSiteId() != null) {
				return false;
			}
		} else if (!getSiteId().equals(other.getSiteId())) {
			return false;
		}
		return true;
	}

}