package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 * The persistent class for the SDP_CREDENTIAL database table.
 * 
 */
@Entity
@Table(name = "SDP_CREDENTIAL_VIEW")
@NamedQueries({
		@NamedQuery(name = SdpCredential.QUERY_RETRIEVE_BY_PARTYID_AND_NOT_STATUS, query = "select p from SdpCredential p where p.sdpParty.partyId=:partyId and p.statusId<>:statusId"),
		@NamedQuery(name = SdpCredential.QUERY_RETRIEVE_BY_PARTYID, query = "select p from SdpCredential p where p.sdpParty.partyId=:partyId ORDER BY p.username"),
		@NamedQuery(name = SdpCredential.QUERY_COUNT_BY_PARTYID, query = "select COUNT(p.username) from SdpCredential p where p.sdpParty.partyId=:partyId"),
		@NamedQuery(name = SdpCredential.QUERY_COUNT_BY_ROLENAME, query = "select COUNT(p.username) from SdpCredential p where p.sdpRole=:roleName and p.statusId<>:statusId"),
		@NamedQuery(name = SdpCredential.QUERY_RETRIEVE_BY_EXTERNALID, query = "select p from SdpCredential p where p.externalId=:externalId") })
public class SdpCredential implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String QUERY_RETRIEVE_BY_PARTYID_AND_NOT_STATUS = "selectCredentialsByPartyIdAndStatusIdNotEqual";
	public static final String QUERY_RETRIEVE_BY_PARTYID = "selectCredentialByPartyId";
	public static final String QUERY_COUNT_BY_PARTYID = "countCredentialByPartyId";
	public static final String QUERY_COUNT_BY_ROLENAME = "countCredentialByRoleName";
	public static final String QUERY_RETRIEVE_BY_EXTERNALID = "selectCredentialByExternalId";
	
	public static final String PARAM_ROLE_NAME = "roleName";
	public static final String PARAM_PARTY_ID = "partyId";
	public static final String PARAM_STATUS_ID = "statusId";

	@Id
	@Column(name = "USERNAME")
	private String username;

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

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "STATUS_ID")
	private Long statusId;

	@Column(name = "CHG_STATUS_BY_ID")
	private String changeStatusById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CHG_STATUS_DATE")
	private Date changeStatusDate;

	@Column(name = "UPDATED_BY_ID")
	private String updatedById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	// bi-directional many-to-one association to SdpParty
	@ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name="PARTY_ID",insertable=false,updatable=false)
	@JoinColumn(name = "PARTY_ID")
	private SdpParty sdpParty;

	// bi-directional many-to-one association to SdpSecretQuestion
	@OneToMany(mappedBy = "sdpCredential", fetch = FetchType.LAZY)
	private List<SdpSecretQuestion> sdpSecretQuestions;

	@Column(name = "ROLE_NAME")
	private String sdpRole;

	// bi-direction one-to-many association to SdpSubscription
	@OneToMany(mappedBy = "sdpCredential", fetch = FetchType.LAZY)
	private List<SdpSubscription> sdpSubscriptions;

	public SdpCredential() {
		sdpSecretQuestions = new ArrayList<SdpSecretQuestion>();
		sdpSubscriptions = new ArrayList<SdpSubscription>();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getStatusId() {
		return this.statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
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

	public List<SdpSecretQuestion> getSdpSecretQuestions() {
		return this.sdpSecretQuestions;
	}

	public void setSdpSecretQuestions(List<SdpSecretQuestion> sdpSecretQuestions) {
		this.sdpSecretQuestions = sdpSecretQuestions;
	}

	public String getSdpRole() {
		return sdpRole;
	}

	public void setSdpRole(String sdpRole) {
		this.sdpRole = sdpRole;
	}

	public List<SdpSubscription> getSdpSubscriptions() {
		return sdpSubscriptions;
	}

	public void setSdpSubscriptions(List<SdpSubscription> sdpSubscriptions) {
		this.sdpSubscriptions = sdpSubscriptions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		SdpCredential other = (SdpCredential) obj;
		if (getUsername() == null) {
			if (other.getUsername() != null) {
				return false;
			}
		} else if (!getUsername().equals(other.getUsername())) {
			return false;
		}
		return true;
	}

}