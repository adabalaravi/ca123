package com.accenture.sdp.csmfe.webservices.response.packages;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class PackageComplexInfoResp extends PackageInfoResp {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5806083819504057225L;
	
	private String baseOfferName;
	private String offerName;
	private String statusName;
	private String groupName;
	private BigDecimal nrcPrice;
	private BigDecimal rcPrice;
	private String frequencyPriceName;
	private String currencyPriceName;
	private String prorate;
	private String inAdvance;
	
	private Long nrcPriceId;
	private Long rcPriceId;
	private Long frequencyPriceId;
	private Long currencyPriceId;
	
	private Long frequencyPriceDays;
	
	private Long discountId;
	private BigDecimal discountAbsRc;
	private BigDecimal discountAbsNrc;
	private BigDecimal discountPercRc;
	private BigDecimal discountPercNrc;
	public String getBaseOfferName() {
		return baseOfferName;
	}
	public void setBaseOfferName(String baseOfferName) {
		this.baseOfferName = baseOfferName;
	}
	public String getOfferName() {
		return offerName;
	}
	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
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
	public String getFrequencyPriceName() {
		return frequencyPriceName;
	}
	public void setFrequencyPriceName(String frequencyPriceName) {
		this.frequencyPriceName = frequencyPriceName;
	}
	public String getCurrencyPriceName() {
		return currencyPriceName;
	}
	public void setCurrencyPriceName(String currencyPriceName) {
		this.currencyPriceName = currencyPriceName;
	}
	public String getProrate() {
		return prorate;
	}
	public void setProrate(String prorate) {
		this.prorate = prorate;
	}
	public String getInAdvance() {
		return inAdvance;
	}
	public void setInAdvance(String inAdvance) {
		this.inAdvance = inAdvance;
	}
	public Long getNrcPriceId() {
		return nrcPriceId;
	}
	public void setNrcPriceId(Long nrcPriceId) {
		this.nrcPriceId = nrcPriceId;
	}
	public Long getRcPriceId() {
		return rcPriceId;
	}
	public void setRcPriceId(Long rcPriceId) {
		this.rcPriceId = rcPriceId;
	}
	public Long getFrequencyPriceId() {
		return frequencyPriceId;
	}
	public void setFrequencyPriceId(Long frequencyPriceId) {
		this.frequencyPriceId = frequencyPriceId;
	}
	public Long getCurrencyPriceId() {
		return currencyPriceId;
	}
	public void setCurrencyPriceId(Long currencyPriceId) {
		this.currencyPriceId = currencyPriceId;
	}
	public Long getFrequencyPriceDays() {
		return frequencyPriceDays;
	}
	public void setFrequencyPriceDays(Long frequencyPriceDays) {
		this.frequencyPriceDays = frequencyPriceDays;
	}
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
	
	
	
}
