package com.accenture.sdp.csmfe.webservices.request.party;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class SearchChildPartyRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7620161235994087591L;

	private Long parentPartyId;
	private String partyName;
	private Long startPosition;
	private Long maxRecordsNumber;

	public Long getParentPartyId() {
		return parentPartyId;
	}

	public void setParentPartyId(Long parentPartyId) {
		this.parentPartyId = parentPartyId;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
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
