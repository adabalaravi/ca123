package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the SDP_TOKEN database table.
 * 
 */
@Entity
@Table(name = "SDP_TOKEN")
@NamedQueries({
		@NamedQuery(name = "selectTokenByUsernameAndPlatform", query = "select o from SdpToken o where o.userIdentifier = :username and o.refTokenProvider.tokenProvider = :platformName"),
		@NamedQuery(name = "selectTokenById", query = "select o from SdpToken o where o.tokenId = :tokenId and o.tokenProvider = :tokenProvider") })
@IdClass(SdpTokenPK.class)
public class SdpToken implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "TOKEN_ID")
	private String tokenId;

	@Id
	@Column(name = "TOKEN_PROVIDER")
	private String tokenProvider;

	@Column(name = "CREATED_BY_ID")
	private String createdById;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "TOKEN_TIMESTAMP")
	private Long tokenTimestamp;

	@Column(name = "UPDATED_BY_ID")
	private String updatedById;

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "USER_IDENTIFIER")
	private String userIdentifier;

	// bi-directional many-to-one association to RefTokenProvider
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TOKEN_PROVIDER", insertable = false, updatable = false)
	private RefTokenProvider refTokenProvider;

	public SdpToken() {
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getTokenProvider() {
		return tokenProvider;
	}

	public void setTokenProvider(String tokenProvider) {
		this.tokenProvider = tokenProvider;
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

	public Long getTokenTimestamp() {
		return tokenTimestamp;
	}

	public void setTokenTimestamp(Long tokenTimestamp) {
		this.tokenTimestamp = tokenTimestamp;
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

	public String getUserIdentifier() {
		return this.userIdentifier;
	}

	public void setUserIdentifier(String userIdentifier) {
		this.userIdentifier = userIdentifier;
	}

	public RefTokenProvider getRefTokenProvider() {
		return this.refTokenProvider;
	}

	public void setRefTokenProvider(RefTokenProvider refTokenProvider) {
		this.refTokenProvider = refTokenProvider;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tokenId == null) ? 0 : tokenId.hashCode());
		result = prime * result + ((tokenProvider == null) ? 0 : tokenProvider.hashCode());
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
		SdpToken other = (SdpToken) obj;
		if (getTokenId() == null) {
			if (other.getTokenId() != null) {
				return false;
			}
		} else if (!getTokenId().equals(other.getTokenId())) {
			return false;
		}
		if (getTokenProvider() == null) {
			if (other.getTokenProvider() != null) {
				return false;
			}
		} else if (!getTokenProvider().equals(other.getTokenProvider())) {
			return false;
		}
		return true;
	}

}