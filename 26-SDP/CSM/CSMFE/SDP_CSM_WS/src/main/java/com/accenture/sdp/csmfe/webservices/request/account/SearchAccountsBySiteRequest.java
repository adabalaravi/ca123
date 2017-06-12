package com.accenture.sdp.csmfe.webservices.request.account;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class SearchAccountsBySiteRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5579094213082767453L;

	private Long siteId;
	private Long startPosition;
	private Long maxRecordsNumber;
	
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
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
