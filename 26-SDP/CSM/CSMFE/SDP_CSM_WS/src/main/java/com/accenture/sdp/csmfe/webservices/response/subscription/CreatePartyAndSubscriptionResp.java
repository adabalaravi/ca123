package com.accenture.sdp.csmfe.webservices.response.subscription;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class CreatePartyAndSubscriptionResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -977251741347318844L;

	private Long subscriptionId;
	private Long partyId;
	public Long getSubscriptionId() {
		return subscriptionId;
	}
	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}
	public Long getPartyId() {
		return partyId;
	}
	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}
	
	
}
