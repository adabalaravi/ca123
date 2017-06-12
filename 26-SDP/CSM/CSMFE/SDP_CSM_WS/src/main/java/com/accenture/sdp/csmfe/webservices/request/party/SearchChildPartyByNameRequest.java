package com.accenture.sdp.csmfe.webservices.request.party;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class SearchChildPartyByNameRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7534072204123227841L;

	private String firstName;
	private String lastName;
	private String partyName;
	private String parentPartyName;
	private Long startPosition;
	private Long maxRecordsNumber;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getParentPartyName() {
		return parentPartyName;
	}

	public void setParentPartyName(String parentPartyName) {
		this.parentPartyName = parentPartyName;
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
