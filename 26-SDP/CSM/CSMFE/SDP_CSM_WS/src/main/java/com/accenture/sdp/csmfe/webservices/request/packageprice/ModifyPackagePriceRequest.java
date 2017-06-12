package com.accenture.sdp.csmfe.webservices.request.packageprice;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class ModifyPackagePriceRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5887008315807707340L;
	
	private Long packagePriceId;
	private Long rcPriceCatalogId;
	private Long nrcPriceCatalogId;
	private Long currencyTypeId; 
	private Long rcFrequencyTypeId; 
	private String rcFlagProrate;
	private String rcInAdvance;
	public Long getPackagePriceId() {
		return packagePriceId;
	}
	public void setPackagePriceId(Long packagePriceId) {
		this.packagePriceId = packagePriceId;
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
	public Long getCurrencyTypeId() {
		return currencyTypeId;
	}
	public void setCurrencyTypeId(Long currencyTypeId) {
		this.currencyTypeId = currencyTypeId;
	}
	public Long getRcFrequencyTypeId() {
		return rcFrequencyTypeId;
	}
	public void setRcFrequencyTypeId(Long rcFrequencyTypeId) {
		this.rcFrequencyTypeId = rcFrequencyTypeId;
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
