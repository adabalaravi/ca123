package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * The persistent class for the REF_PRICE_CATALOG database table.
 * 
 */
@Entity
@Table(name = "REF_PRICE_CATALOG")
@NamedQueries({ @NamedQuery(name = "searchAllPrices", query = "select p from SdpPriceCatalog p ORDER BY p.priceCatalogId"),
		@NamedQuery(name = "searchAllPricesCount", query = "select COUNT(p.priceCatalogId) from SdpPriceCatalog p"),
		@NamedQuery(name = "searchByPrice", query = "select p from SdpPriceCatalog p WHERE p.price=:price") })
public class SdpPriceCatalog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PRICE_CATALOG_ID")
	private Long priceCatalogId;

	@Column(name = "CREATED_BY_ID")
	private String createdById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	private BigDecimal price;

	@Column(name = "UPDATED_BY_ID")
	private String updatedById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	// bi-directional many-to-one association to SdpPackagePrice
	@OneToMany(mappedBy = "refPriceCatalogRC")
	private List<SdpPackagePrice> sdpPackagePricesRC;

	// bi-directional many-to-one association to SdpPackagePrice
	@OneToMany(mappedBy = "refPriceCatalogNRC")
	private List<SdpPackagePrice> sdpPackagePricesNRC;

	public SdpPriceCatalog() {
	}

	public Long getPriceCatalogId() {
		return this.priceCatalogId;
	}

	public void setPriceCatalogId(Long priceCatalogId) {
		this.priceCatalogId = priceCatalogId;
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

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
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

	public List<SdpPackagePrice> getSdpPackagePricesRC() {
		return this.sdpPackagePricesRC;
	}

	public void setSdpPackagePrices1(List<SdpPackagePrice> sdpPackagePricesRC) {
		this.sdpPackagePricesRC = sdpPackagePricesRC;
	}

	public List<SdpPackagePrice> getSdpPackagePricesNRC() {
		return this.sdpPackagePricesNRC;
	}

	public void setSdpPackagePricesNRC(List<SdpPackagePrice> sdpPackagePricesNRC) {
		this.sdpPackagePricesNRC = sdpPackagePricesNRC;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((priceCatalogId == null) ? 0 : priceCatalogId.hashCode());
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
		SdpPriceCatalog other = (SdpPriceCatalog) obj;
		if (getPriceCatalogId() == null) {
			if (other.getPriceCatalogId() != null) {
				return false;
			}
		} else if (!getPriceCatalogId().equals(other.getPriceCatalogId())) {
			return false;
		}
		return true;
	}

}