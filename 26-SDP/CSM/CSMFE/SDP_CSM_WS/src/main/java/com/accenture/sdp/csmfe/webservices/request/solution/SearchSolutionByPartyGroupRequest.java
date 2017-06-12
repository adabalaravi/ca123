package com.accenture.sdp.csmfe.webservices.request.solution;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class SearchSolutionByPartyGroupRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String partyGroupName;
	private Long startPosition;
	private Long maxRecordsNumber;
	public String getPartyGroupName() {
		return partyGroupName;
	}
	public void setPartyGroupName(String partyGroupName) {
		this.partyGroupName = partyGroupName;
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
