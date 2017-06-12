package com.accenture.sdp.csmfe.webservices.response.subscription;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SubscriptionComplexInfoResp extends SubscriptionInfoResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3096247335960207043L;

	private String statusName;
	private String partyName;
	private String ownerAccountName;
	private String payeeAccountName;
	private String siteName;
	private String solutionOfferName;
	
	private OfferListResp offers;
	
	public SubscriptionComplexInfoResp() {
		offers = new OfferListResp();
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getOwnerAccountName() {
		return ownerAccountName;
	}

	public void setOwnerAccountName(String ownerAccountName) {
		this.ownerAccountName = ownerAccountName;
	}

	public String getPayeeAccountName() {
		return payeeAccountName;
	}

	public void setPayeeAccountName(String payeeAccountName) {
		this.payeeAccountName = payeeAccountName;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSolutionOfferName() {
		return solutionOfferName;
	}

	public void setSolutionOfferName(String solutionOfferName) {
		this.solutionOfferName = solutionOfferName;
	}

	public OfferListResp getOffers() {
		return offers;
	}

	public void setOffers(OfferListResp offers) {
		this.offers = offers;
	}
	
	
}
