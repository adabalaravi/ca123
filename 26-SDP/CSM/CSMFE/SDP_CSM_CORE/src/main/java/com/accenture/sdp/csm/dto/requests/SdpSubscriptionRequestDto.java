package com.accenture.sdp.csm.dto.requests;

import java.io.Serializable;
import java.util.Date;

public class SdpSubscriptionRequestDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long parentSubscriptionId;
	private String userName; 
	private String roleName; 
	private String ownerAccountName; 
	private String payeeAccountName;
	private String siteName;
	private String externalId;
	private Date startDate;
	private Date endDate;
	private Long solutionOfferId;
	public SdpSubscriptionRequestDto() {
		super();
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

	@Override
	public String toString() {
		return " solutionOfferId = " +solutionOfferId+
				" parentSubscriptionId = " + parentSubscriptionId +
				" userName = " + userName + "" +
				" roleName = " + roleName +
				" ownerAccountName = " + ownerAccountName +
				" payeeAccountName = " + payeeAccountName +
				" siteName = " + siteName +
				" externalId = " + externalId +
				" startDate = " + startDate +
				" endDate = " + endDate+" ";
	}
	/**
	 * @return the solutionOfferId
	 */
	public Long getSolutionOfferId() {
		return solutionOfferId;
	}
	/**
	 * @param solutionOfferId the solutionOfferId to set
	 */
	public void setSolutionOfferId(Long solutionOfferId) {
		this.solutionOfferId = solutionOfferId;
	}
}
