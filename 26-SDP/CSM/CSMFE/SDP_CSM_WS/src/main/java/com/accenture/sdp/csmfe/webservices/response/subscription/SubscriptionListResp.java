package com.accenture.sdp.csmfe.webservices.response.subscription;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SubscriptionListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8304613439369502913L;

	private List<SubscriptionInfoResp> subscriptionList;

	@XmlElement(name = "subscription")
	public List<SubscriptionInfoResp> getSubscriptionList() {
		return subscriptionList;
	}

	public void setSubscriptionList(List<SubscriptionInfoResp> subscriptionList) {
		this.subscriptionList = subscriptionList;
	}

}
