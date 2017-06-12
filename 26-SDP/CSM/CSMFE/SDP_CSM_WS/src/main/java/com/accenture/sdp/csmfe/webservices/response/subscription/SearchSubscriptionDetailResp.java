package com.accenture.sdp.csmfe.webservices.response.subscription;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchSubscriptionDetailResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7271617010704710054L;

	private SubscriptionDetailListResp subscriptionsDetail;
	
	public SearchSubscriptionDetailResp() {
		subscriptionsDetail = new SubscriptionDetailListResp();
	}
 
	public SubscriptionDetailListResp getSubscriptionsDetail() {
		return subscriptionsDetail;
	}

	public void setSubscriptionsDetail(SubscriptionDetailListResp subscriptionsDetail) {
		this.subscriptionsDetail = subscriptionsDetail;
	}
}
