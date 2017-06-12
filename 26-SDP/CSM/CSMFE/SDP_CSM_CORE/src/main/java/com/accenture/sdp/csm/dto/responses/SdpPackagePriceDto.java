package com.accenture.sdp.csm.dto.responses;

public class SdpPackagePriceDto  extends SdpBaseResponseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2284678536487378659L;
	
	private Long packagePriceId;
	private Long frequencyTypeId;
	private Long currencyTypeId;
	private Long rcPriceCatalogId;
	private Long nrcPriceCatalogId;
	private String rcFlagProrate;
	private String rcInAdvance;

	public Long getPackagePriceId() {
		return packagePriceId;
	}

	public void setPackagePriceId(Long packagePriceId) {
		this.packagePriceId = packagePriceId;
	}

	public Long getFrequencyTypeId() {
		return frequencyTypeId;
	}

	public void setFrequencyTypeId(Long frequencyTypeId) {
		this.frequencyTypeId = frequencyTypeId;
	}

	public Long getCurrencyTypeId() {
		return currencyTypeId;
	}

	public void setCurrencyTypeId(Long currencyTypeId) {
		this.currencyTypeId = currencyTypeId;
	}

	public Long getRcPriceCatalogId() {
		return rcPriceCatalogId;
	}

	public void setRcPriceCatalogId(Long rcPriceCatalogId) {
		this.rcPriceCatalogId = rcPriceCatalogId;
	}

	public Long getNrcPriceCatalogId() {
		return nrcPriceCatalogId;
	}

	public void setNrcPriceCatalogId(Long nrcPriceCatalogId) {
		this.nrcPriceCatalogId = nrcPriceCatalogId;
	}

	public String getRcFlagProrate() {
		return rcFlagProrate;
	}

	public void setRcFlagProrate(String rcFlagProrate) {
		this.rcFlagProrate = rcFlagProrate;
	}

	public String getRcInAdvance() {
		return rcInAdvance;
	}

	public void setRcInAdvance(String rcInAdvance) {
		this.rcInAdvance = rcInAdvance;
	}
	
}