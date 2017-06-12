package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the SDP_PARTY database table.
 * 
 */
@Entity
@Table(name = "SDP_PARTY_VIEW")
@NamedQueries({
		@NamedQuery(name = "selectOrganizationPartyByNameLike", query = "select p from SdpParty p where p.partyName like :organizationName and p.refPartyType=1"),
		@NamedQuery(name = "selectOrganizationPartyByNameLikeCount", query = "select COUNT(p.partyId) from SdpParty p where p.partyName like :organizationName and p.refPartyType=1"),
		@NamedQuery(name = "selectOrganizationPartyByNameLikeAndPartyGroupId", query = "select p from SdpParty p, LnkPartyPartyGroup lnk where p.partyName like :organizationName and p.refPartyType=1 and P.partyId=lnk.id.partyId and lnk.id.partyGroupId=:partyGroupId"),
		@NamedQuery(name = "selectOrganizationPartyByNameLikeAndPartyGroupIdCount", query = "select COUNT(p.partyId) from SdpParty p, LnkPartyPartyGroup lnk where p.partyName like :organizationName and p.refPartyType=1 and P.partyId=lnk.id.partyId and lnk.id.partyGroupId=:partyGroupId"),
		@NamedQuery(name = "selectChildPartyByOrganizationIdAndPartyNameLike", query = "select p from SdpParty p where p.partyName like :partyName and p.refPartyType=2"),
		@NamedQuery(name = "selectChildPartyByOrganizationIdAndPartyNameLikeCount", query = "select COUNT(p.partyId) from SdpParty p where p.partyName like :partyName and p.refPartyType=2"),
		@NamedQuery(name = "selectChildPartyByOrganizationId", query = "select p from SdpParty p where p.parentParty.partyId=:organizationId and p.refPartyType=2 and p.parentParty.refPartyType=1"),
		@NamedQuery(name = "selectChildPartyByOrganizationIdAndPartyGroupId", query = "select p from SdpParty p, LnkPartyPartyGroup lnk where p.parentParty.partyId=:organizationId and p.refPartyType=2 and p.parentParty.refPartyType=1 and P.partyId=lnk.id.partyId and lnk.id.partyGroupId=:partyGroupId"),
		@NamedQuery(name = "selectChildPartyByOrganizationIdCount", query = "select count(p.partyId) from SdpParty p where p.parentParty.partyId=:organizationId  and p.refPartyType=2 and p.parentParty.refPartyType=1"),
		@NamedQuery(name = "selectChildPartyByOrganizationIdAndPartyGroupIdCount", query = "select count(p.partyId) from SdpParty p, LnkPartyPartyGroup lnk where p.parentParty.partyId=:organizationId  and p.refPartyType=2 and p.parentParty.refPartyType=1 and P.partyId=lnk.id.partyId and lnk.id.partyGroupId=:partyGroupId"),
		@NamedQuery(name = "selectChildPartyByOrganizatioIdAndStatusIdNotEqual", query = "select p from SdpParty p where p.parentParty.partyId=:organizationId and p.statusId <> :statusId"),
		@NamedQuery(name = "selectChildPartyBySiteIdAndPartyGroupId", query = "select p from SdpParty p, LnkPartyPartyGroup lnk where p.sdpPartyData.sdpPartySite.siteId=:siteId and P.partyId=lnk.id.partyId and lnk.id.partyGroupId=:partyGroupId"),
		@NamedQuery(name = "selectChildPartyBySiteIdAndPartyGroupIdCount", query = "select COUNT(p.partyId) from SdpParty p, LnkPartyPartyGroup lnk where p.sdpPartyData.sdpPartySite.siteId=:siteId and P.partyId=lnk.id.partyId and lnk.id.partyGroupId=:partyGroupId"),
		@NamedQuery(name = SdpParty.QUERY_CHILD_COUNT_BY_SITEID_AND_NOT_STATUS, query = "select COUNT(p.partyId) from SdpParty p where p.sdpPartyData.sdpPartySite.siteId=:siteId and p.statusId<>:statusId"),
		@NamedQuery(name = SdpParty.QUERY_RETRIEVE_BY_EXTERNALID, query = "select p from SdpParty p where p.externalId=:externalId") })
public class SdpParty implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String QUERY_CHILD_COUNT_BY_SITEID_AND_NOT_STATUS = "countChildPartiesBySiteIdAndStatusIdNotEqual";
	public static final String QUERY_RETRIEVE_BY_EXTERNALID = "selectAnyPartyByExternalId";

	public static final String PARAM_SITE_ID = "siteId";
	public static final String PARAM_STATUS_ID = "statusId";
	public static final String PARAM_PARTY_NAME = "partyName";
	public static final String PARAM_PARTY_GROUP_ID = "partyGroupId";
	public static final String PARAM_PARENT_PARTY_NAME = "organizationName";

	private static final String MAPPED_BY = "sdpParty";
	private static final String COLUMN_PARTY_ID = "PARTY_ID";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = COLUMN_PARTY_ID)
	private Long partyId;

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

	@Column(name = "PARTY_DESCRIPTION")
	private String partyDescription;

	@Column(name = "PARTY_NAME")
	private String partyName;

	@Lob()
	@Column(name = "PARTY_PROFILE")
	private String partyProfile;

	@Column(name = "UPDATED_BY_ID")
	private String updatedById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "CHG_STATUS_BY_ID")
	private String changeStatusById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CHG_STATUS_DATE")
	private Date changeStatusDate;

	// bi-directional many-to-many association to sdpPartyGroup
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(name = "LNK_PARTY_PARTY_GROUP", joinColumns = @JoinColumn(name = COLUMN_PARTY_ID), inverseJoinColumns = @JoinColumn(name = "PARTY_GROUP_ID"))
	private List<SdpPartyGroup> partyGroups;

	// bi-directional many-to-one association to SdpAccount
	@OneToMany(mappedBy = MAPPED_BY)
	private List<SdpAccount> sdpAccounts;

	// bi-directional many-to-one association to SdpCredential
	@OneToMany(mappedBy = MAPPED_BY, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<SdpCredential> sdpCredentials;

	// bi-directional many-to-one association to RefPartyType
	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "PARTY_TYPE_ID")
	@Column(name = "PARTY_TYPE_ID")
	private Long refPartyType;

	@Column(name = "STATUS_ID")
	private Long statusId;

	// // bi-directional many-to-one association to SdpParty
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_PARTY_ID")
	private SdpParty parentParty;

	// bi-directional many-to-one association to SdpParty
	@OneToMany(mappedBy = "parentParty")
	private List<SdpParty> childParties;

	// bi-directional one-to-one association to SdpPartyData
	@OneToOne(mappedBy = MAPPED_BY, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = COLUMN_PARTY_ID)
	private SdpPartyData sdpPartyData;

	// bi-directional one-to-one association to SdpPartyDeviceExt
	@OneToOne(mappedBy = MAPPED_BY, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = COLUMN_PARTY_ID)
	private SdpPartyDeviceExt sdpPartyDeviceExt;

	// bi-directional many-to-one association to SdpPartySite
	@OneToMany(mappedBy = MAPPED_BY, fetch = FetchType.LAZY)
	private List<SdpSite> sdpPartySites;

	// bi-directional many-to-one association to SdpSubscription
	@OneToMany(mappedBy = MAPPED_BY, fetch = FetchType.LAZY)
	private List<SdpSubscription> sdpSubscriptions;

	// bi-directional many-to-one association to SdpSubscription
	@OneToMany(mappedBy = MAPPED_BY, fetch = FetchType.LAZY)
	private List<SdpShoppingCart> sdpShoppingCarts;

	public SdpParty() {
		sdpPartySites = new ArrayList<SdpSite>();
		sdpSubscriptions = new ArrayList<SdpSubscription>();
		sdpAccounts = new ArrayList<SdpAccount>();
		sdpCredentials = new ArrayList<SdpCredential>();
		childParties = new ArrayList<SdpParty>();
		partyGroups = new ArrayList<SdpPartyGroup>();
		sdpPartyDeviceExt = new SdpPartyDeviceExt();
		sdpShoppingCarts = new ArrayList<SdpShoppingCart>();
	}

	public Long getPartyId() {
		return this.partyId;
	}

	public void setPartyId(Long partyId) {
		this.partyId = partyId;
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

	public String getPartyDescription() {
		return this.partyDescription;
	}

	public void setPartyDescription(String partyDescription) {
		this.partyDescription = partyDescription;
	}

	public String getPartyName() {
		return this.partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getPartyProfile() {
		return this.partyProfile;
	}

	public void setPartyProfile(String partyProfile) {
		this.partyProfile = partyProfile;
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
		return this.sdpAccounts;
	}

	public void setSdpAccounts(List<SdpAccount> sdpAccounts) {
		this.sdpAccounts = sdpAccounts;
	}

	public List<SdpCredential> getSdpCredentials() {
		return this.sdpCredentials;
	}

	public void setSdpCredentials(List<SdpCredential> sdpCredentials) {
		this.sdpCredentials = sdpCredentials;
	}

	public Long getRefPartyType() {
		return this.refPartyType;
	}

	public void setRefPartyType(Long refPartyType) {
		this.refPartyType = refPartyType;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public void setSdpSubscriptions(List<SdpSubscription> sdpSubscriptions) {
		this.sdpSubscriptions = sdpSubscriptions;
	}

	public SdpParty getParentParty() {
		return this.parentParty;
	}

	public void setParentParty(SdpParty parentParty) {
		this.parentParty = parentParty;
	}

	public List<SdpParty> getChildParties() {
		return this.childParties;
	}

	public void setChildParties(List<SdpParty> childParties) {
		this.childParties = childParties;
	}

	public SdpPartyData getSdpPartyData() {
		return this.sdpPartyData;
	}

	public void setSdpPartyData(SdpPartyData sdpPartyData) {
		this.sdpPartyData = sdpPartyData;
	}

	public List<SdpSite> getSdpPartySites() {
		return this.sdpPartySites;
	}

	public void setSdpPartySites(List<SdpSite> sdpPartySites) {
		this.sdpPartySites = sdpPartySites;
	}

	public List<SdpSubscription> getSdpSubscriptions() {
		return this.sdpSubscriptions;
	}

	public void setSdpSubscriptions1(List<SdpSubscription> sdpSubscriptions) {
		this.sdpSubscriptions = sdpSubscriptions;
	}

	public List<SdpPartyGroup> getPartyGroups() {
		return partyGroups;
	}

	public void setPartyGroups(List<SdpPartyGroup> partyGroups) {
		this.partyGroups = partyGroups;
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
		SdpParty other = (SdpParty) obj;
		if (getPartyId() == null) {
			if (other.getPartyId() != null) {
				return false;
			}
		} else if (!getPartyId().equals(other.getPartyId())) {
			return false;
		}
		return true;
	}

	public SdpPartyDeviceExt getSdpPartyDeviceExt() {
		return sdpPartyDeviceExt;
	}

	public void setSdpPartyDeviceExt(SdpPartyDeviceExt sdpPartyDeviceExt) {
		this.sdpPartyDeviceExt = sdpPartyDeviceExt;
	}

	public List<SdpShoppingCart> getSdpShoppingCarts() {
		return sdpShoppingCarts;
	}

	public void setSdpShoppingCarts(List<SdpShoppingCart> sdpShoppingCarts) {
		this.sdpShoppingCarts = sdpShoppingCarts;
	}

}