package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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

@Entity
@Table(name = "SDP_VOUCHER_CAMPAIGN")

@NamedQueries({
	@NamedQuery(name = SdpVoucherCampaign.QUERY_RETRIEVE_BY_SOLUTIONOFFERID, query = "SELECT v FROM SdpVoucherCampaign v WHERE v.sdpSolutionOffer.solutionOfferId=:solutionOfferId"),
	@NamedQuery(name = SdpVoucherCampaign.QUERY_VOUCHERTYPE_RETRIEVE_BY_AVAILABLE, query = "SELECT v FROM SdpVoucherCampaign v WHERE v.sdpSolutionOffer is null")
})

public class SdpVoucherCampaign implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String QUERY_RETRIEVE_BY_SOLUTIONOFFERID = "selectVoucherCampaignBySolutionOfferId";
	public static final String QUERY_VOUCHERTYPE_RETRIEVE_BY_AVAILABLE="selectVoucherTypeByAvailable";

	@Id
	@Column(name = "VOUCHER_TYPE")
	private String voucherType;

	@Column(name = "VALIDITY_PERIOD")
	private Long validityPeriod;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SOLUTION_OFFER_ID")
	private SdpSolutionOffer sdpSolutionOffer;

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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_DATE")
	private Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_DATE")
	private Date endDate;

	@OneToMany(mappedBy = "sdpVoucherCampaign", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<SdpVoucher> sdpVouchers;

	public String getVoucherType() {
		return voucherType;
	}

	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}

	public Long getValidityPeriod() {
		return validityPeriod;
	}

	public void setValidityPeriod(Long validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	public SdpSolutionOffer getSdpSolutionOffer() {
		return sdpSolutionOffer;
	}

	public void setSdpSolutionOffer(SdpSolutionOffer sdpSolutionOffer) {
		this.sdpSolutionOffer = sdpSolutionOffer;
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

	public List<SdpVoucher> getSdpVouchers() {
		return sdpVouchers;
	}

	public void setSdpVouchers(List<SdpVoucher> sdpVouchers) {
		this.sdpVouchers = sdpVouchers;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((voucherType == null) ? 0 : voucherType.hashCode());
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
		SdpVoucherCampaign other = (SdpVoucherCampaign) obj;
		if (voucherType == null) {
			if (other.voucherType != null) {
				return false;
			}
		} else if (!voucherType.equals(other.voucherType)) {
			return false;
		}
		return true;
	}

}