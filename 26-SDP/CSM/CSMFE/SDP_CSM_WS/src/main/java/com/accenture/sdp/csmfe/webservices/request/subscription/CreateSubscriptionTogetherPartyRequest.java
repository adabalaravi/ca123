package com.accenture.sdp.csmfe.webservices.request.subscription;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class CreateSubscriptionTogetherPartyRequest implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1744651177870706275L;

	private Long solutionOfferId;
	private String username;
	private String roleName;
	private String ownerAccountName;
	private String payeeAccountName;
	private String siteName;
	private String externalId;
	private Date startDate;
	private Date endDate;
	private SubscriptionDetailListRequest subscriptionDetails;

	public CreateSubscriptionTogetherPartyRequest() {
		super();
		subscriptionDetails = new SubscriptionDetailListRequest();
	}

	public Long getSolutionOfferId() {
		return solutionOfferId;
	}

	public void setSolutionOfferId(Long solutionOfferId) {
		this.solutionOfferId = solutionOfferId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getOwnerAccountName() {
		return ownerAccountName;
	}

	public void setOwnerAccountName(String ownerAccountName) {
		this.ownerAccountName = ownerAccountName;
	}

	public String getPayeeAccountName() {
		return payeeAccountName;
	}

	public void setPayeeAccountName(String payeeAccountName) {
		this.payeeAccountName = payeeAccountName;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
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

	public SubscriptionDetailListRequest getSubscriptionDetails() {
		return subscriptionDetails;
	}

	public void setSubscriptionDetails(
			SubscriptionDetailListRequest subscriptionDetails) {
		this.subscriptionDetails = subscriptionDetails;
	}
	
	

}
