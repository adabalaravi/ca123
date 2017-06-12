package com.accenture.sdp.csmfe.webservices.request.packages;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class CreatePackageRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6646544854526839631L;

	private Long solutionOfferId;
	private String externalId;
	private Long offerId;
	private String isMandatory;
	private Long packagePriceId;
	private Long basePackageId;
	private Long groupId;
	private String profile;
	public Long getSolutionOfferId() {
		return solutionOfferId;
	}
	public void setSolutionOfferId(Long solutionOfferId) {
		this.solutionOfferId = solutionOfferId;
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
	
	
	
}
