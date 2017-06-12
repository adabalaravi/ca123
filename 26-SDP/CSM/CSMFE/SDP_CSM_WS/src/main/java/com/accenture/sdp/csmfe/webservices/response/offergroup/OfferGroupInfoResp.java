package com.accenture.sdp.csmfe.webservices.response.offergroup;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseInfoResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class OfferGroupInfoResp extends BaseInfoResp {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7469792559999159114L;

	private Long groupId;
	private Long solutionOfferId;
	private String groupName;
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public Long getSolutionOfferId() {
		return solutionOfferId;
	}
	public void setSolutionOfferId(Long solutionOfferId) {
		this.solutionOfferId = solutionOfferId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	
}
