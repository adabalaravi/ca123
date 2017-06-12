package com.accenture.sdp.csmfe.webservices.response.packageprice;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class PackagePriceInfoResp implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long packagePriceId;
	private Long rcFrequencyTypeId;
	private Long currencyTypeId;
	private Long rcPriceCatalogId;
	private Long nrcPriceCatalogId;
	private String rcFlagProrate;
	private String rcInAdvance;	
	
	private String createdById;
	private Date createdDate;
	private String updatedById;
	private Date updatedDate;
	public Long getPackagePriceId() {
		return packagePriceId;
	}
	public void setPackagePriceId(Long packagePriceId) {
		this.packagePriceId = packagePriceId;
	}
	public Long getRcFrequencyTypeId() {
		return rcFrequencyTypeId;
	}
	public void setRcFrequencyTypeId(Long rcFrequencyTypeId) {
		this.rcFrequencyTypeId = rcFrequencyTypeId;
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
	
	

}
