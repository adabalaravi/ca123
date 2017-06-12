package com.accenture.sdp.csmfe.webservices.response.subscription;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchSubscriptionResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7271617010704710054L;

	private SubscriptionListResp subscriptions;

	public SearchSubscriptionResp() {
		subscriptions = new SubscriptionListResp();
	}

	public SubscriptionListResp getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(SubscriptionListResp subscriptions) {
		this.subscriptions = subscriptions;
	}
}
