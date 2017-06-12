package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.util.Date;

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

@Entity
@Table(name = "SDP_VOUCHER")
@NamedQueries({
		@NamedQuery(name = SdpVoucher.QUERY_COUNT_BY_SOLUTIONOFFERID, query = "SELECT COUNT(v.voucherId) FROM SdpVoucher v WHERE v.sdpVoucherCampaign.sdpSolutionOffer.solutionOfferId=:solutionOfferId"),
		@NamedQuery(name = SdpVoucher.QUERY_COUNT_BY_AVAILABLE_AND_SOLUTIONOFFERID, query = "SELECT count(v.voucherId) FROM SdpVoucher v WHERE v.sdpVoucherCampaign.sdpSolutionOffer.solutionOfferId=:solutionOfferId and v.sdpParty is null"),
		@NamedQuery(name = SdpVoucher.QUERY_COUNT_BY_VOUCHERTYPE, query = "SELECT COUNT(v.voucherId) FROM SdpVoucher v WHERE v.sdpVoucherCampaign.voucherType=:voucherType"),
    	@NamedQuery(name = SdpVoucher.QUERY_RETRIEVE_BY_SOLUTIONOFFERID, query = "SELECT v FROM SdpVoucher v WHERE v.sdpVoucherCampaign.sdpSolutionOffer.solutionOfferId=:solutionOfferId"),
		@NamedQuery(name = SdpVoucher.QUERY_RETRIEVE_BY_AVAILABLE_AND_SOLUTIONOFFERID, query = "SELECT v FROM SdpVoucher v WHERE v.sdpVoucherCampaign.sdpSolutionOffer.solutionOfferId=:solutionOfferId and v.sdpParty is null"),
		@NamedQuery(name = SdpVoucher.QUERY_RETRIEVE_BY_VOUCHERCODE, query = "SELECT v FROM SdpVoucher v WHERE v.voucherCode=:voucherCode"),
		@NamedQuery(name = SdpVoucher.QUERY_RETRIEVE_BY_VOUCHERCODE_LIKE, query = "SELECT v FROM SdpVoucher v WHERE v.voucherCode like :voucherCode"),
		@NamedQuery(name = SdpVoucher.QUERY_COUNT_BY_VOUCHERCODE_LIKE, query = "SELECT COUNT(v.voucherId) FROM SdpVoucher v WHERE v.voucherCode like :voucherCode") })
public class SdpVoucher implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String QUERY_COUNT_BY_SOLUTIONOFFERID = "countVoucherBySolutionOfferId";
	public static final String QUERY_COUNT_BY_AVAILABLE_AND_SOLUTIONOFFERID = "countAvailableVouchersBySolutionOfferId";
	public static final String QUERY_COUNT_BY_VOUCHERTYPE = "countVoucherByType";
	public static final String QUERY_RETRIEVE_BY_SOLUTIONOFFERID = "selectVoucherBySolutionOfferId";
	public static final String QUERY_RETRIEVE_BY_AVAILABLE_AND_SOLUTIONOFFERID = "selectAvailableVouchersBySolutionOfferId";
	public static final String QUERY_RETRIEVE_BY_VOUCHERCODE = "selectVoucherByVoucherCode";
	public static final String QUERY_RETRIEVE_BY_VOUCHERCODE_LIKE = "selectVoucherByVoucherCodeLike";
	public static final String QUERY_COUNT_BY_VOUCHERCODE_LIKE = "countVoucherByVoucherCodeLike";

	public static final String PARAM_SOLUTION_OFFER_ID = "solutionOfferId";
	public static final String PARAM_VOUCHER_TYPE = "voucherType";
	public static final String PARAM_VOUCHER_CODE = "voucherCode";
	public static final String PARAM_VALIDITY_PERIOD = "validityPeriod";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "VOUCHER_ID")
	private Long voucherId;

	@Column(name = "VOUCHER_CODE")
	private String voucherCode;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VOUCHER_TYPE")
	private SdpVoucherCampaign sdpVoucherCampaign;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARTY_ID")
	private SdpParty sdpParty;

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
	
	public SdpVoucher() {
		super();
	}

	public Long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(Long voucherId) {
		this.voucherId = voucherId;
	}

	public String getVoucherCode() {
		return voucherCode;
	}

	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}

	public SdpParty getSdpParty() {
		return sdpParty;
	}

	public void setSdpParty(SdpParty sdpParty) {
		this.sdpParty = sdpParty;
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

	public SdpVoucherCampaign getSdpVoucherCampaign() {
		return sdpVoucherCampaign;
	}

	public void setSdpVoucherCampaign(SdpVoucherCampaign sdpVoucherCampaign) {
		this.sdpVoucherCampaign = sdpVoucherCampaign;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((voucherId == null) ? 0 : voucherId.hashCode());
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
		SdpVoucher other = (SdpVoucher) obj;
		if (voucherId == null) {
			if (other.voucherId != null) {
				return false;
			}
		} else if (!voucherId.equals(other.voucherId)) {
			return false;
		}
		return true;
	}

}