package com.accenture.sdp.csmfe.webservices.response.subscription;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SubscriptionComplexListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8304613439369502913L;

	private List<SubscriptionComplexInfoResp> subscriptionList;

	@XmlElement(name = "subscription")
	public List<SubscriptionComplexInfoResp> getSubscriptionList() {
		return subscriptionList;
	}

	public void setSubscriptionList(List<SubscriptionComplexInfoResp> subscriptionList) {
		this.subscriptionList = subscriptionList;
	}

}
