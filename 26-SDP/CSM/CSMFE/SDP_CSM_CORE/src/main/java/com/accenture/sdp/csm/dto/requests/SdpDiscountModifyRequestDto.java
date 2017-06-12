package com.accenture.sdp.csm.dto.requests;

import java.math.BigDecimal;

public class SdpDiscountModifyRequestDto {

	private Long discountId;
	private BigDecimal discountAbsRc;
	private BigDecimal discountAbsNrc;
	private BigDecimal discountPercRc;
	private BigDecimal discountPercNrc;

	public Long getDiscountId() {
		return discountId;
	}
	public void setDiscountId(Long discountId) {
		this.discountId = discountId;
	}
	public BigDecimal getDiscountAbsRc() {
		return discountAbsRc;
	}
	public void setDiscountAbsRc(BigDecimal discountAbsRc) {
		this.discountAbsRc = discountAbsRc;
	}
	public BigDecimal getDiscountAbsNrc() {
		return discountAbsNrc;
	}
	public void setDiscountAbsNrc(BigDecimal discountAbsNrc) {
		this.discountAbsNrc = discountAbsNrc;
	}
	public BigDecimal getDiscountPercRc() {
		return discountPercRc;
	}
	public void setDiscountPercRc(BigDecimal discountPercRc) {
		this.discountPercRc = discountPercRc;
	}
	public BigDecimal getDiscountPercNrc() {
		return discountPercNrc;
	}
	public void setDiscountPercNrc(BigDecimal discountPercNrc) {
		this.discountPercNrc = discountPercNrc;
	}

	@Override
	public String toString() {
		return " discountId = " + discountId +
				" discountAbsRc = " + discountAbsRc +
				" discountAbsNrc = " + discountAbsNrc + 
				" discountPercRc = " + discountPercRc +
				" discountPercNrc = " + discountPercNrc +" ";
	}

}
