package com.accenture.sdp.csmfe.webservices.request.party;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class SearchPartiesBySubscriptionRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6387928916385671828L;

	private Long subscriptionId;
	private Long partyGroupId;
	public Long getSubscriptionId() {
		return subscriptionId;
	}
	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}
	public Long getPartyGroupId() {
		return partyGroupId;
	}
	public void setPartyGroupId(Long partyGroupId) {
		this.partyGroupId = partyGroupId;
	}
	
	
}
