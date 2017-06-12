package com.accenture.sdp.csmfe.webservices.response.subscription;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SubscriptionDetailListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4830321519878564333L;

	private List<SubscriptionDetailInfoResp> subscriptionDetailList;

	@XmlElement(name = "subscription")
	public List<SubscriptionDetailInfoResp> getSubscriptionDetailList() {
		return subscriptionDetailList;
	}

	public void setSubscriptionDetailList(List<SubscriptionDetailInfoResp> subscriptionDetailList) {
		this.subscriptionDetailList = subscriptionDetailList;
	}

}
