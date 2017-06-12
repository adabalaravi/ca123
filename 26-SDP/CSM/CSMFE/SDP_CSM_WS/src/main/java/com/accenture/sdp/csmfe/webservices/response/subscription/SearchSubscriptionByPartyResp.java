package com.accenture.sdp.csmfe.webservices.response.subscription;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseRespPaginated;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchSubscriptionByPartyResp extends BaseRespPaginated {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7271617010704710054L;

	private SubscriptionByPartyListResp subscriptions;

	public SearchSubscriptionByPartyResp() {
		subscriptions = new SubscriptionByPartyListResp();
	}

	public SubscriptionByPartyListResp getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(SubscriptionByPartyListResp subscriptions) {
		this.subscriptions = subscriptions;
	}
}
