package com.accenture.sdp.csmfe.webservices.request.subscription;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class SearchSubscriptionsByAccountRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2537682725468155781L;

	private String ownerAccountName;
	private String payeeAccountName;
	private Long startPosition;
	private Long maxRecordsNumber;
	
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
	public Long getStartPosition() {
		return startPosition;
	}
	public void setStartPosition(Long startPosition) {
		this.startPosition = startPosition;
	}
	public Long getMaxRecordsNumber() {
		return maxRecordsNumber;
	}
	public void setMaxRecordsNumber(Long maxRecordsNumber) {
		this.maxRecordsNumber = maxRecordsNumber;
	}
	
	
}
