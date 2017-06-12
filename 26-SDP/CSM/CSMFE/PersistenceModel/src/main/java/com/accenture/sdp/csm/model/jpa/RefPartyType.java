package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the REF_SERVICE_TYPE database table.
 * 
 */
@Entity
@Table(name = "REF_PARTY_TYPE")
@NamedQueries({ @NamedQuery(name = RefPartyType.PARTY_TYPE_RETRIEVE_BY_NAME, query = "select a from RefPartyType a where a.partyTypeName=:partyTypeName") })
public class RefPartyType implements Serializable {
	private static final long serialVersionUID = 5919460026064624201L;

	public static final String PARTY_TYPE_RETRIEVE_BY_NAME = "selectPartyTypeByName";

	public static final String PARAM_PARTY_TYPE_NAME = "partyTypeName";

	@Id
	@Column(name = "PARTY_TYPE_ID")
	private long partyTypeId;

	@Column(name = "PARTY_TYPE_NAME")
	private String partyTypeName;

	@Column(name = "PARTY_TYPE_DESCRIPTION")
	private String partyTypeDescription;

	@Column(name = "CREATED_BY_ID")
	private String createdById;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "UPDATED_BY_ID")
	private String updatedById;

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	public RefPartyType() {
	}

	public long getPartyTypeId() {
		return partyTypeId;
	}

	public void setPartyTypeId(long partyTypeId) {
		this.partyTypeId = partyTypeId;
	}

	public String getPartyTypeName() {
		return partyTypeName;
	}

	public void setPartyTypeName(String partyTypeName) {
		this.partyTypeName = partyTypeName;
	}

	public String getPartyTypeDescription() {
		return partyTypeDescription;
	}

	public void setPartyTypeDescription(String partyTypeDescription) {
		this.partyTypeDescription = partyTypeDescription;
	}

	public String getCreatedById() {
		return createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedById() {
		return updatedById;
	}

	public void setUpdatedById(String updatedById) {
		this.updatedById = updatedById;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (partyTypeId ^ (partyTypeId >>> 32));
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
		RefPartyType other = (RefPartyType) obj;
		if (getPartyTypeId() != other.getPartyTypeId()) {
			return false;
		}
		return true;
	}

}