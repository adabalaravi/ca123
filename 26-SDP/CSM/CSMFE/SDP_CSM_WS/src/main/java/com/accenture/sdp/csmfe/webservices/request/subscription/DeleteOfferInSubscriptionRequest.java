package com.accenture.sdp.csmfe.webservices.request.subscription;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class DeleteOfferInSubscriptionRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6276654117886688932L;
	
	private Long subscriptionId;

	private PackageIdListRequest packages;
	
	public DeleteOfferInSubscriptionRequest() {
		packages = new PackageIdListRequest();
	}

	public Long getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public PackageIdListRequest getPackages() {
		return packages;
	}

	public void setPackages(PackageIdListRequest packages) {
		this.packages = packages;
	}
	
	
}
