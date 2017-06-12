package com.accenture.sdp.csmfe.webservices.response.subscription;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseInfoResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SubscriptionInfoResp extends BaseInfoResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3096247335960207043L;

	private Long subscriptionId;
	private Long solutionOfferId;
	private Long statusId;	
	private Long partyId;
	private Long parentSubscriptionId;
	private String userName;
	private String roleName;
	private Long ownerId;
	private Long payeeId;
	private Long siteId;
	private String externalId;
	private Date startDate;
	private Date endDate;
	public Long getSubscriptionId() {
		return subscriptionId;
	}
	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
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
	public Long getPartyId() {
		return partyId;
	}
	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}
	public Long getParentSubscriptionId() {
		return parentSubscriptionId;
	}
	public void setParentSubscriptionId(Long parentSubscriptionId) {
		this.parentSubscriptionId = parentSubscriptionId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}
	public Long getPayeeId() {
		return payeeId;
	}
	public void setPayeeId(Long payeeId) {
		this.payeeId = payeeId;
	}
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
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
	
	
	
}
