package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the SDP_DISCOUNT database table.
 * 
 */
@Entity
@Table(name = "SDP_DISCOUNT")
@NamedQueries({ @NamedQuery(name = "selectDiscountsBySolutionOfferId", query = "select d from SdpDiscount d where d.solutionOffer.solutionOfferId=:solutionOfferId") })
public class SdpDiscount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DISCOUNT_ID")
	private Long id;

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

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "SOLUTION_OFFER_ID")
	private SdpSolutionOffer solutionOffer;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "PACKAGE_ID")
	private SdpPackage sdpPackage;

	@Column(name = "DISCOUNT_ABS_RC")
	private BigDecimal discountAbsRC;

	@Column(name = "DISCOUNT_ABS_NRC")
	private BigDecimal discountAbsNRC;

	@Column(name = "DISCOUNT_PERC_RC")
	private BigDecimal discountPercRC;

	@Column(name = "DISCOUNT_PERC_NRC")
	private BigDecimal discountPercNRC;

	public SdpDiscount() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public SdpSolutionOffer getSolutionOffer() {
		return solutionOffer;
	}

	public void setSolutionOffer(SdpSolutionOffer solutionOffer) {
		this.solutionOffer = solutionOffer;
	}

	public SdpPackage getSdpPackage() {
		return sdpPackage;
	}

	public void setSdpPackage(SdpPackage sdpPackage) {
		this.sdpPackage = sdpPackage;
	}

	public BigDecimal getDiscountAbsRC() {
		return discountAbsRC;
	}

	public void setDiscountAbsRC(BigDecimal discountAbsRC) {
		this.discountAbsRC = discountAbsRC;
	}

	public BigDecimal getDiscountAbsNRC() {
		return discountAbsNRC;
	}

	public void setDiscountAbsNRC(BigDecimal discountAbsNRC) {
		this.discountAbsNRC = discountAbsNRC;
	}

	public BigDecimal getDiscountPercRC() {
		return discountPercRC;
	}

	public void setDiscountPercRC(BigDecimal discountPercRC) {
		this.discountPercRC = discountPercRC;
	}

	public BigDecimal getDiscountPercNRC() {
		return discountPercNRC;
	}

	public void setDiscountPercNRC(BigDecimal discountPercNRC) {
		this.discountPercNRC = discountPercNRC;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		SdpDiscount other = (SdpDiscount) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}