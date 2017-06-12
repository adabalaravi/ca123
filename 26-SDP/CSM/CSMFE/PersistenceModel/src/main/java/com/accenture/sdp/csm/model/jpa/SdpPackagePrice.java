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
 * The persistent class for the SDP_PACKAGE_PRICE database table.
 * 
 */
@Entity
@Table(name = "SDP_PACKAGE_PRICE")
@NamedQueries({
		@NamedQuery(name = "selectPackagePriceByFrequencyIdCount", query = "select COUNT(p.packagePriceId) from SdpPackagePrice p where  p.refFrequencyType.frequencyTypeId=:frequencyTypeId"),
		@NamedQuery(name = "selectPackagePriceByPriceCatalogeIdCount", query = "select COUNT(p.packagePriceId) from SdpPackagePrice p where  p.refPriceCatalogRC.priceCatalogId=:priceCatalogId"),
		@NamedQuery(name = "selectPackagePriceByCurrencyTypeIdCount", query = "select COUNT(p.packagePriceId) from SdpPackagePrice p where  p.refCurrencyType.currencyTypeId=:currencyTypeId ") })
public class SdpPackagePrice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PACKAGE_PRICE_ID")
	private Long packagePriceId;

	@Column(name = "CREATED_BY_ID")
	private String createdById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "RC_FLAG_PRORATE")
	private String rcFlagProrate;

	@Column(name = "RC_IN_ADVANCE")
	private String rcInAdvance;

	@Column(name = "UPDATED_BY_ID")
	private String updatedById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	// bi-directional many-to-one association to SdpPackage
	@OneToMany(mappedBy = "sdpPackagePrice")
	private List<SdpPackage> sdpPackages;

	// bi-directional many-to-one association to RefFrequencyType
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RC_FREQUENCY_TYPE_ID")
	private RefFrequencyType refFrequencyType;

	// bi-directional many-to-one association to RefCurrencyType
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CURRENCY_TYPE_ID")
	private RefCurrencyType refCurrencyType;

	// bi-directional many-to-one association to RefPriceCatalog
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RC_PRICE_CATALOG_ID")
	private SdpPriceCatalog refPriceCatalogRC;

	// bi-directional many-to-one association to RefPriceCatalog
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NRC_PRICE_CATALOG_ID")
	private SdpPriceCatalog refPriceCatalogNRC;

	public SdpPackagePrice() {
		sdpPackages = new ArrayList<SdpPackage>();
	}

	public Long getPackagePriceId() {
		return this.packagePriceId;
	}

	public void setPackagePriceId(Long packagePriceId) {
		this.packagePriceId = packagePriceId;
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

	public String getRcFlagProrate() {
		return this.rcFlagProrate;
	}

	public void setRcFlagProrate(String rcFlagProrate) {
		this.rcFlagProrate = rcFlagProrate;
	}

	public String getRcInAdvance() {
		return this.rcInAdvance;
	}

	public void setRcInAdvance(String rcInAdvance) {
		this.rcInAdvance = rcInAdvance;
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

	public List<SdpPackage> getSdpPackages() {
		return this.sdpPackages;
	}

	public void setSdpPackages(List<SdpPackage> sdpPackages) {
		this.sdpPackages = sdpPackages;
	}

	public RefFrequencyType getRefFrequencyType() {
		return this.refFrequencyType;
	}

	public void setRefFrequencyType(RefFrequencyType refFrequencyType) {
		this.refFrequencyType = refFrequencyType;
	}

	public SdpPriceCatalog getRefPriceCatalogRC() {
		return this.refPriceCatalogRC;
	}

	public void setRefPriceCatalog1(SdpPriceCatalog refPriceCatalogRC) {
		this.refPriceCatalogRC = refPriceCatalogRC;
	}

	public SdpPriceCatalog getRefPriceCatalogNRC() {
		return this.refPriceCatalogNRC;
	}

	public void setRefPriceCatalog2(SdpPriceCatalog refPriceCatalogNRC) {
		this.refPriceCatalogNRC = refPriceCatalogNRC;
	}

	public RefCurrencyType getRefCurrencyType() {
		return refCurrencyType;
	}

	public void setRefCurrencyType(RefCurrencyType refCurrencyType) {
		this.refCurrencyType = refCurrencyType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((packagePriceId == null) ? 0 : packagePriceId.hashCode());
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
		SdpPackagePrice other = (SdpPackagePrice) obj;
		if (getPackagePriceId() == null) {
			if (other.getPackagePriceId() != null) {
				return false;
			}
		} else if (!getPackagePriceId().equals(other.getPackagePriceId())) {
			return false;
		}
		return true;
	}

}