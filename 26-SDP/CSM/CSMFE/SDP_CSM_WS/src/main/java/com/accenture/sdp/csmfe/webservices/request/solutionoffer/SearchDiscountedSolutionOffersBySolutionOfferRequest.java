package com.accenture.sdp.csmfe.webservices.request.solutionoffer;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class SearchDiscountedSolutionOffersBySolutionOfferRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5953318389764318009L;
	
	private String solutionOfferName;
	private Long startPosition;
	private Long maxRecordsNumber;
	
	public String getSolutionOfferName() {
		return solutionOfferName;
	}
	public void setSolutionOfferName(String solutionOfferName) {
		this.solutionOfferName = solutionOfferName;
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
