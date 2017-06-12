package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the REF_CURRENCY_TYPE database table.
 * 
 */
@Entity
@Table(name = "REF_CURRENCY_TYPE")
@NamedQueries({ @NamedQuery(name = RefCurrencyType.CURRENCY_RETRIEVE_ALL, query = "select c from RefCurrencyType c ORDER BY c.currencyTypeId"),
		@NamedQuery(name = RefCurrencyType.CURRENCY_COUNT_ALL, query = "select COUNT(c.currencyTypeId) from RefCurrencyType c"),
		@NamedQuery(name = RefCurrencyType.CURRENCY_RETRIEVE_BY_NAME, query = "select c from RefCurrencyType c WHERE c.currencyTypeName=:currencyTypeName") })
public class RefCurrencyType implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String CURRENCY_RETRIEVE_ALL = "searchAllCurrencies";
	public static final String CURRENCY_COUNT_ALL = "searchAllCurrenciesCount";
	public static final String CURRENCY_RETRIEVE_BY_NAME = "searchCurrencyByName";

	public static final String PARAM_CURRENCY_TYPE_NAME = "currencyTypeName";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CURRENCY_TYPE_ID")
	private Long currencyTypeId;

	@Column(name = "CREATED_BY_ID")
	private String createdById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "CURRENCY_TYPE_NAME")
	private String currencyTypeName;

	@Column(name = "UPDATED_BY_ID")
	private String updatedById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	// bi-directional many-to-one association to SdpPackagePrice
	@OneToMany(mappedBy = "refCurrencyType")
	private List<SdpPackagePrice> sdpCurrencyPackagePrices;

	public RefCurrencyType() {
	}

	public Long getCurrencyTypeId() {
		return this.currencyTypeId;
	}

	public void setCurrencyTypeId(Long currencyTypeId) {
		this.currencyTypeId = currencyTypeId;
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

	public String getCurrencyTypeName() {
		return this.currencyTypeName;
	}

	public void setCurrencyTypeName(String currencyTypeName) {
		this.currencyTypeName = currencyTypeName;
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

	public List<SdpPackagePrice> getSdpCurrencyPackagePrices() {
		return sdpCurrencyPackagePrices;
	}

	public void setSdpCurrencyPackagePrices(List<SdpPackagePrice> sdpCurrencyPackagePrices) {
		this.sdpCurrencyPackagePrices = sdpCurrencyPackagePrices;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currencyTypeId == null) ? 0 : currencyTypeId.hashCode());
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
		RefCurrencyType other = (RefCurrencyType) obj;
		if (getCurrencyTypeId() == null) {
			if (other.getCurrencyTypeId() != null) {
				return false;
			}
		} else if (!getCurrencyTypeId().equals(other.getCurrencyTypeId())) {
			return false;
		}
		return true;
	}

}