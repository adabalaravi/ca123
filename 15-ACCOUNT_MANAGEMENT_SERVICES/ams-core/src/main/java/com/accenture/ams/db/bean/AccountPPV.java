package com.accenture.ams.db.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class AccountPPV implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long subscriptionOfferId;
	private Long liveContentId;
	private Long packageId;
	private Long price;
	private Timestamp startDate;
	private Timestamp endDate;
	private String crmAccountId;
	private Long userId;
	private String externalId;
	private Long workOrderId;
	
	public Long getSubscriptionOfferId() {
		return subscriptionOfferId;
	}
	public void setSubscriptionOfferId(Long subscriptionOfferId) {
		this.subscriptionOfferId = subscriptionOfferId;
	}
	public Long getLiveContentId() {
		return liveContentId;
	}
	public void setLiveContentId(Long liveContentId) {
		this.liveContentId = liveContentId;
	}
	public Long getPackageId() {
		return packageId;
	}
	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public String getCrmAccountId() {
		return crmAccountId;
	}
	public void setCrmAccountId(String crmAccountId) {
		this.crmAccountId = crmAccountId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public Long getWorkOrderId() {
		return workOrderId;
	}
	public void setWorkOrderId(Long workOrderId) {
		this.workOrderId = workOrderId;
	}
}
