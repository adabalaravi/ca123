package com.accenture.sdp.csmac.beans;

import java.io.Serializable;
import java.math.BigDecimal;

public class PackageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2888602214319966212L;

	private Long packageId;
	private Long offerId;
	private BigDecimal nrcPrice;
	private BigDecimal rcPrice;
	private Long discountId;
	private BigDecimal discountAbsNrc;
	private BigDecimal discountAbsRc;
	private BigDecimal discountPercNrc;
	private BigDecimal discountPercRc;
	private String frequencyName;

	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	public Long getPackageId() {
		return packageId;
	}

	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}

	public BigDecimal getNrcPrice() {
		return nrcPrice;
	}

	public void setNrcPrice(BigDecimal nrcPrice) {
		this.nrcPrice = nrcPrice;
	}

	public BigDecimal getRcPrice() {
		return rcPrice;
	}

	public void setRcPrice(BigDecimal rcPrice) {
		this.rcPrice = rcPrice;
	}

	public String getFrequencyName() {
		return frequencyName;
	}

	public void setFrequencyName(String frequencyName) {
		this.frequencyName = frequencyName;
	}

	public BigDecimal getDiscountAbsNrc() {
		return discountAbsNrc;
	}

	public void setDiscountAbsNrc(BigDecimal discountAbsNrc) {
		this.discountAbsNrc = discountAbsNrc;
	}

	public BigDecimal getDiscountAbsRc() {
		return discountAbsRc;
	}

	public void setDiscountAbsRc(BigDecimal discountAbsRc) {
		this.discountAbsRc = discountAbsRc;
	}

	public BigDecimal getDiscountPercNrc() {
		return discountPercNrc;
	}

	public void setDiscountPercNrc(BigDecimal discountPercNrc) {
		this.discountPercNrc = discountPercNrc;
	}

	public BigDecimal getDiscountPercRc() {
		return discountPercRc;
	}

	public void setDiscountPercRc(BigDecimal discountPercRc) {
		this.discountPercRc = discountPercRc;
	}

	public Long getDiscountId() {
		return discountId;
	}

	public void setDiscountId(Long discountId) {
		this.discountId = discountId;
	}

}
