package com.accenture.sdp.csmfe.webservices.request.site;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class SearchSitesByPartyRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4396661635025118171L;

	private Long parentPartyId;
	private Long startPosition;
	private Long maxRecordsNumber;
	
	public Long getParentPartyId() {
		return parentPartyId;
	}
	public void setParentPartyId(Long parentPartyId) {
		this.parentPartyId = parentPartyId;
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
