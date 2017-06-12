package com.accenture.sdp.csmfe.webservices.response.packages;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseInfoResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class PackageInfoResp extends BaseInfoResp {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5806083819504057225L;

	private Long packageId;
	private String externalId;
	private Long offerId;
	private String isMandatory;
	private Long packagePriceId;
	private Long basePackageId;
	private Long groupId;
	private String profile;
	private Long solutionOfferId;
	private Long statusId;
	public Long getPackageId() {
		return packageId;
	}
	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public Long getOfferId() {
		return offerId;
	}
	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}
	public String getIsMandatory() {
		return isMandatory;
	}
	public void setIsMandatory(String isMandatory) {
		this.isMandatory = isMandatory;
	}
	public Long getPackagePriceId() {
		return packagePriceId;
	}
	public void setPackagePriceId(Long packagePriceId) {
		this.packagePriceId = packagePriceId;
	}
	public Long getBasePackageId() {
		return basePackageId;
	}
	public void setBasePackageId(Long basePackageId) {
		this.basePackageId = basePackageId;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public Long getSolutionOfferId() {
		return solutionOfferId;
	}
	public void setSolutionOfferId(Long solutionOfferId) {
		this.solutionOfferId = solutionOfferId;
	}
	public Long getStatusId() {
		return statusId;
	}
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}
	
	
}
