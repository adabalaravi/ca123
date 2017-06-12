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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the SDP_SUBSCRIPTION database table.
 * 
 */
@Entity
@Table(name = "SDP_SUBSCRIPTION_VIEW")
@NamedQueries({
		@NamedQuery(name = "selectSubscriptionsByParentSubscription", query = "select a from SdpSubscription a where a.parentSubscription.subscriptionId=:parentSubscriptionId"),
		@NamedQuery(name = "countSubscriptionsByParentSubscription", query = "select COUNT(a.subscriptionId) from SdpSubscription a where a.parentSubscription.subscriptionId=:parentSubscriptionId"),
		@NamedQuery(name = SdpSubscription.QUERY_RETRIEVE_BY_PARTYID, query = "select a from SdpSubscription a where a.sdpParty.partyId=:partyId"),
		@NamedQuery(name = SdpSubscription.QUERY_COUNT_BY_PARTYID, query = "select COUNT(a.subscriptionId) from SdpSubscription a where a.sdpParty.partyId=:partyId"),
		@NamedQuery(name = SdpSubscription.QUERY_RETRIEVE_BY_PARTYID_AND_NOT_STATUS, query = "select a from SdpSubscription a where a.sdpParty.partyId=:partyId and a.statusId<>:statusId"),
		@NamedQuery(name = "selectSubscriptionsByUsernameAndStatusIdNotEqual", query = "select a from SdpSubscription a where a.sdpCredential.username=:username and a.statusId<>:statusId"),
		@NamedQuery(name = "selectSubscriptionsByPayeeAccountIdAndStatusIdNotEqual", query = "select a from SdpSubscription a where a.sdpAccountPayee.accountId=:accountId and a.statusId<>:statusId"),
		@NamedQuery(name = "selectSubscriptionsByOwnerAccountIdAndStatusIdNotEqual", query = "select a from SdpSubscription a where a.sdpAccountOwner.accountId=:accountId and a.statusId<>:statusId"),
		@NamedQuery(name = "selectSubscriptionsByCredential", query = "select a from SdpSubscription a where a.sdpCredential.username=:username"),
		@NamedQuery(name = "countSubscriptionsByCredential", query = "select COUNT(a.subscriptionId) from SdpSubscription a where a.sdpCredential.username=:username"),
		@NamedQuery(name = "selectSubscriptionsByAccountOwnerAndAccountPayee", query = "SELECT a FROM SdpSubscription a WHERE a.sdpAccountOwner.accountId=:accountOwnerId AND a.sdpAccountPayee.accountId=:accountPayeeId"),
		@NamedQuery(name = "countSubscriptionsByAccountOwnerAndAccountPayee", query = "SELECT COUNT(a.subscriptionId) FROM SdpSubscription a WHERE a.sdpAccountOwner.accountId=:accountOwnerId AND a.sdpAccountPayee.accountId=:accountPayeeId"),
		@NamedQuery(name = "selectSubscriptionsByAccountOwner", query = "SELECT a FROM SdpSubscription a WHERE a.sdpAccountOwner.accountId=:accountOwnerId"),
		@NamedQuery(name = "countSubscriptionsByAccountOwner", query = "SELECT COUNT(a.subscriptionId) FROM SdpSubscription a WHERE a.sdpAccountOwner.accountId=:accountOwnerId"),
		@NamedQuery(name = "selectSubscriptionsByAccountPayee", query = "SELECT a FROM SdpSubscription a WHERE a.sdpAccountPayee.accountId=:accountPayeeId"),
		@NamedQuery(name = "countSubscriptionsByAccountPayee", query = "SELECT COUNT(a.subscriptionId) FROM SdpSubscription a WHERE a.sdpAccountPayee.accountId=:accountPayeeId"),
		@NamedQuery(name = "selectSubscriptionsByPartySite", query = "SELECT a FROM SdpSubscription a WHERE a.sdpPartySite.siteId=:partySiteId"),
		@NamedQuery(name = "countSubscriptionsByPartySite", query = "SELECT COUNT(a.subscriptionId) FROM SdpSubscription a WHERE a.sdpPartySite.siteId=:partySiteId"),
		@NamedQuery(name = "selectSubscriptionsByParentSubscriptionAndStatusIdNotEqual", query = "select a from SdpSubscription a where a.parentSubscription.subscriptionId=:parentSubscriptionId and a.statusId<>:statusId"),
		@NamedQuery(name = "selectSubscriptionsByRoleNameCount", query = "select COUNT(a.subscriptionId) from SdpSubscription a where a.sdpRole=:roleName and a.statusId<>:statusId"),
		@NamedQuery(name = "selectSubscriptionByExternalId", query = "select a from SdpSubscription a where a.externalId=:externalId"),
		@NamedQuery(name = "countSubscriptionsBySolutionOfferIdAndStatusNotEqual", query = "select COUNT(a.subscriptionId) from SdpSubscription a where a.sdpSolutionOffer.solutionOfferId=:solutionOfferId and a.statusId<>:statusId") })
public class SdpSubscription implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String QUERY_COUNT_BY_PARTYID = "countSubscriptionsByParty";
	public static final String QUERY_RETRIEVE_BY_PARTYID = "selectSubscriptionsByParty";
	public static final String QUERY_RETRIEVE_BY_PARTYID_AND_NOT_STATUS = "selectSubscriptionsByPartyIdAndStatusIdNotEqual";
	
	public static final String PARAM_USERNAME = "username";
	public static final String PARAM_PARTY_ID = "partyId";
	public static final String PARAM_SOLUTION_OFFER_ID = "solutionOfferId";
	public static final String PARAM_PARENT_SUBSCRIPTION_ID = "parentSubscriptionId";
	public static final String PARAM_STATUS_ID = "statusId";
	public static final String PARAM_ACCOUNT_OWNER_ID = "accountOwnerId";
	public static final String PARAM_ACCOUNT_PAYEE_ID = "accountPayeeId";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SUBSCRIPTION_ID")
	private Long subscriptionId;

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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_DATE")
	private Date endDate;

	@Column(name = "EXTERNAL_ID")
	private String externalId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_DATE")
	private Date startDate;

	@Column(name = "UPDATED_BY_ID")
	private String updatedById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	// bi-directional many-to-one association to RefStatusType
	@Column(name = "STATUS_ID")
	private Long statusId;

	@Column(name = "CHG_STATUS_BY_ID")
	private String changeStatusById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CHG_STATUS_DATE")
	private Date changeStatusDate;

	// bi-directional many-to-one association to SdpAccount
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAYEE_ID")
	private SdpAccount sdpAccountPayee;

	// bi-directional many-to-one association to SdpAccount
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OWNER_ID")
	private SdpAccount sdpAccountOwner;

	// bi-directional many-to-one association to SdpParty
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARTY_ID")
	private SdpParty sdpParty;

	// bi-direction many-to-one association to SdpCredential
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USERNAME")
	private SdpCredential sdpCredential;

	// bi-directional many-to-one association to SdpPartySite
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SITE_ID")
	private SdpSite sdpPartySite;

	// bi-directional many-to-one association to SdpSubscriptionDetail
	@OneToMany(mappedBy = "sdpSubscription", fetch = FetchType.LAZY)
	private List<SdpSubscriptionDetail> sdpSubscriptionDetails;

	// // bi-directional many-to-one association to SdpSubscription
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_SUBSCRIPTION_ID")
	private SdpSubscription parentSubscription;

	@Column(name = "ROLE_NAME")
	private String sdpRole;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SOLUTION_OFFER_ID")
	private SdpSolutionOffer sdpSolutionOffer;

	public SdpSubscription() {
		sdpSubscriptionDetails = new ArrayList<SdpSubscriptionDetail>();
	}

	public Long getSubscriptionId() {
		return this.subscriptionId;
	}

	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
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

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getExternalId() {
		return this.externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
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

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public SdpAccount getSdpAccountOwner() {
		return this.sdpAccountOwner;
	}

	public void setSdpAccountOwner(SdpAccount sdpAccountOwner) {
		this.sdpAccountOwner = sdpAccountOwner;
	}

	public SdpAccount getSdpAccountPayee() {
		return this.sdpAccountPayee;
	}

	public void setSdpAccountPayee(SdpAccount sdpAccountPayee) {
		this.sdpAccountPayee = sdpAccountPayee;
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

	public List<SdpSubscriptionDetail> getSdpSubscriptionDetails() {
		return this.sdpSubscriptionDetails;
	}

	public void setSdpSubscriptionDetails(List<SdpSubscriptionDetail> sdpSubscriptionDetails) {
		this.sdpSubscriptionDetails = sdpSubscriptionDetails;
	}

	public SdpCredential getSdpCredential() {
		return sdpCredential;
	}

	public void setSdpCredential(SdpCredential sdpCredential) {
		this.sdpCredential = sdpCredential;
	}

	public SdpSubscription getParentSubscription() {
		return parentSubscription;
	}

	public void setParentSubscription(SdpSubscription parentSubscription) {
		this.parentSubscription = parentSubscription;
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

	public String getSdpRole() {
		return sdpRole;
	}

	public void setSdpRole(String sdpRole) {
		this.sdpRole = sdpRole;
	}

	/**
	 * @return the sdpSolutionOffer
	 */
	public SdpSolutionOffer getSdpSolutionOffer() {
		return sdpSolutionOffer;
	}

	/**
	 * @param sdpSolutionOffer
	 *            the sdpSolutionOffer to set
	 */
	public void setSdpSolutionOffer(SdpSolutionOffer sdpSolutionOffer) {
		this.sdpSolutionOffer = sdpSolutionOffer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((subscriptionId == null) ? 0 : subscriptionId.hashCode());
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
		SdpSubscription other = (SdpSubscription) obj;
		if (getSubscriptionId() == null) {
			if (other.getSubscriptionId() != null) {
				return false;
			}
		} else if (!getSubscriptionId().equals(other.getSubscriptionId())) {
			return false;
		}
		return true;
	}

}