package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the REF_TOKEN_PROVIDER database table.
 * 
 */
@Entity
@Table(name = "REF_TOKEN_PROVIDER")
public class RefTokenProvider implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "TOKEN_PROVIDER")
	private String tokenProvider;

	@Column(name = "CREATED_BY_ID")
	private String createdById;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "TOKEN_VALIDITY_SECOND")
	private Long tokenValiditySecond;

	@Column(name = "UPDATED_BY_ID")
	private String updatedById;

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	// bi-directional many-to-one association to SdpToken
	@OneToMany(mappedBy = "refTokenProvider")
	private List<SdpToken> sdpTokens;

	public RefTokenProvider() {
	}

	public String getTokenProvider() {
		return this.tokenProvider;
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

	public Long getTokenValiditySecond() {
		return this.tokenValiditySecond;
	}

	public void setTokenValiditySecond(Long tokenValiditySecond) {
		this.tokenValiditySecond = tokenValiditySecond;
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

	public List<SdpToken> getSdpTokens() {
		return this.sdpTokens;
	}

	public void setSdpTokens(List<SdpToken> sdpTokens) {
		this.sdpTokens = sdpTokens;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		RefTokenProvider other = (RefTokenProvider) obj;
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