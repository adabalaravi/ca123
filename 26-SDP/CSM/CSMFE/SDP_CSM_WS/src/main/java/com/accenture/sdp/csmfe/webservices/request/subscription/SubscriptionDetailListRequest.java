package com.accenture.sdp.csmfe.webservices.request.subscription;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SubscriptionDetailListRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 172966273097293982L;

	private List<SubscriptionDetailInfoRequest> subscriptionDetailList;

	@XmlElement(name = "subscriptionDetail")
	public List<SubscriptionDetailInfoRequest> getSubscriptionDetailList() {
		return subscriptionDetailList;
	}

	public void setSubscriptionDetailList(List<SubscriptionDetailInfoRequest> subscriptionDetailList) {
		this.subscriptionDetailList = subscriptionDetailList;
	}

}
