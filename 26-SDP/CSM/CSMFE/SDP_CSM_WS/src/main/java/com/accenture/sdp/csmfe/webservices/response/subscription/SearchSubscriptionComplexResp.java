package com.accenture.sdp.csmfe.webservices.response.subscription;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchSubscriptionComplexResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7271617010704710054L;

	private SubscriptionComplexListResp subscriptions;
	
	public SearchSubscriptionComplexResp() {
		subscriptions = new SubscriptionComplexListResp();
	}

	public SubscriptionComplexListResp getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(SubscriptionComplexListResp subscriptions) {
		this.subscriptions = subscriptions;
	}
}
