package com.accenture.sdp.csmfe.webservices.request.credential;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchCredentialsByPartyRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 861727496237721854L;

	private Long partyId;
	private Long startPosition;
	private Long maxRecordsNumber;
	
	public Long getPartyId() {
		return partyId;
	}
	public void setPartyId(Long partyId) {
		this.partyId = partyId;
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
