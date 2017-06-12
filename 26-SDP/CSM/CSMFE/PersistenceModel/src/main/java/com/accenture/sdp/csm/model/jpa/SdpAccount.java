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
 * The persistent class for the SDP_ACCOUNT database table.
 * 
 */
@Entity
@Table(name = "SDP_ACCOUNT_VIEW")
@NamedQueries({
		@NamedQuery(name = SdpAccount.QUERY_RETRIEVE_BY_NAME, query = "select a from SdpAccount a where a.accountName=:accountName"),
		@NamedQuery(name = SdpAccount.QUERY_RETRIEVE_BY_PARTYID_AND_NOT_STATUS, query = "select a from SdpAccount a where a.sdpParty.partyId=:partyId and a.statusId<>:statusId"),
		@NamedQuery(name = SdpAccount.QUERY_RETRIEVE_BY_PARTYID, query = "select a from SdpAccount a where a.sdpParty.partyId=:partyId ORDER BY a.accountId"),
		@NamedQuery(name = SdpAccount.QUERY_COUNT_BY_PARTYID, query = "select COUNT(a.accountId) from SdpAccount a where a.sdpParty.partyId=:partyId"),
		@NamedQuery(name = SdpAccount.QUERY_RETRIEVE_BY_SITEID, query = "select a from SdpAccount a where a.sdpPartySite.siteId=:siteId ORDER BY a.accountId"),
		@NamedQuery(name = SdpAccount.QUERY_COUNT_BY_SITEID, query = "select COUNT(a.accountId) from SdpAccount a where a.sdpPartySite.siteId=:siteId"),
		@NamedQuery(name = SdpAccount.QUERY_COUNT_BY_SITEID_AND_NOT_STATUS, query = "select COUNT(a.accountId) from SdpAccount a where a.sdpPartySite.siteId=:siteId and a.statusId<>:statusId"),
		@NamedQuery(name = SdpAccount.QUERY_RETRIEVE_BY_EXTERNALID, query = "select a from SdpAccount a where a.externalId=:externalId") })
public class SdpAccount implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String QUERY_RETRIEVE_BY_NAME = "selectAccountByName";
	public static final String QUERY_RETRIEVE_BY_PARTYID_AND_NOT_STATUS = "selectAccountsByPartyIdAndStatusIdNotEqual";
	public static final String QUERY_RETRIEVE_BY_PARTYID = "selectAccountByPartyId";
	public static final String QUERY_COUNT_BY_PARTYID = "selectAccountByPartyIdCount";
	public static final String QUERY_RETRIEVE_BY_SITEID = "selectAccountBySiteId";
	public static final String QUERY_COUNT_BY_SITEID = "selectAccountBySiteIdCount";
	public static final String QUERY_COUNT_BY_SITEID_AND_NOT_STATUS = "countAccountsBySiteIdAndStatusIdNotEqual";
	public static final String QUERY_RETRIEVE_BY_EXTERNALID = "selectAccountByExternalId";

	public static final String PARAM_ACCOUNT_NAME = "accountName";
	public static final String PARAM_PARTY_ID = "partyId";
	public static final String PARAM_SITE_ID = "siteId";
	public static final String PARAM_STATUS_ID = "statusId";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ACCOUNT_ID")
	private Long accountId;

	@Column(name = "ACCOUNT_NAME")
	private String accountName;

	@Column(name = "ACCOUNT_DESCRIPTION")
	private String accountDescription;

	@Column(name = "STATUS_ID")
	private Long statusId;

	@Column(name = "IS_DEFAULT_PARTY_ACCOUNT")
	private String isDefaultPartyAccount;

	// bi-directional many-to-one association to SdpParty
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARTY_ID")
	private SdpParty sdpParty;

	// bi-directional many-to-one association to SdpPartySite
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_SITE_ID")
	private SdpSite sdpPartySite;

	@Column(name = "EXTERNAL_ID")
	private String externalId;

	@Lob()
	@Column(name = "ACCOUNT_PROFILE")
	private String accountProfile;

	@Column(name = "CREATED_BY_ID")
	private String createdById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "UPDATED_BY_ID")
	private String updatedById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "DELETED_BY_ID")
	private String deletedById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DELETED_DATE")
	private Date deletedDate;

	@Column(name = "CHG_STATUS_BY_ID")
	private String changeStatusById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CHG_STATUS_DATE")
	private Date changeStatusDate;

	// bi-directional many-to-one association to SdpSubscription
	@OneToMany(mappedBy = "sdpAccountOwner")
	private List<SdpSubscription> sdpSubscriptionsOwner;

	// bi-directional many-to-one association to SdpSubscription
	@OneToMany(mappedBy = "sdpAccountPayee")
	private List<SdpSubscription> sdpSubscriptionsPayee;

	public SdpAccount() {
		sdpSubscriptionsOwner = new ArrayList<SdpSubscription>();
		sdpSubscriptionsPayee = new ArrayList<SdpSubscription>();
	}

	public Long getAccountId() {
		return this.accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getAccountDescription() {
		return this.accountDescription;
	}

	public void setAccountDescription(String accountDescription) {
		this.accountDescription = accountDescription;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getAccountName() {
		return this.accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountProfile() {
		return this.accountProfile;
	}

	public void setAccountProfile(String accountProfile) {
		this.accountProfile = accountProfile;
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

	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	public String getExternalId() {
		return this.externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getIsDefaultPartyAccount() {
		return this.isDefaultPartyAccount;
	}

	public void setIsDefaultPartyAccount(String isDefaultPartyAccount) {
		this.isDefaultPartyAccount = isDefaultPartyAccount;
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

	public SdpParty getSdpParty() {
		return this.sdpParty;
	}

	public void setSdpParty(SdpParty sdpParty) {
		this.sdpParty = sdpParty;
	}

	public SdpSite getSdpPartySite() {
		return this.sdpPartySite;
	}

	public void setSdpPartySite(SdpSite sdpPartySite) {
		this.sdpPartySite = sdpPartySite;
	}

	public List<SdpSubscription> getSdpSubscriptionsOwner() {
		return sdpSubscriptionsOwner;
	}

	public void setSdpSubscriptionsOwner(List<SdpSubscription> sdpSubscriptionsOwner) {
		this.sdpSubscriptionsOwner = sdpSubscriptionsOwner;
	}

	public List<SdpSubscription> getSdpSubscriptionsPayee() {
		return sdpSubscriptionsPayee;
	}

	public void setSdpSubscriptionsPayee(List<SdpSubscription> sdpSubscriptionsPayee) {
		this.sdpSubscriptionsPayee = sdpSubscriptionsPayee;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountId == null) ? 0 : accountId.hashCode());
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
		SdpAccount other = (SdpAccount) obj;
		if (getAccountId() == null) {
			if (other.getAccountId() != null) {
				return false;
			}
		} else if (!getAccountId().equals(other.getAccountId())) {
			return false;
		}
		return true;
	}

}