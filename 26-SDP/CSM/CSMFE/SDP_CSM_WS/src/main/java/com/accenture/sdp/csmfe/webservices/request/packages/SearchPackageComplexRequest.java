package com.accenture.sdp.csmfe.webservices.request.packages;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class SearchPackageComplexRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4599025024052074016L;

	private String solutionOfferName;
	private String offerName;
	private Long startPosition;
	private Long maxRecordsNumber;
	public String getSolutionOfferName() {
		return solutionOfferName;
	}
	public void setSolutionOfferName(String solutionOfferName) {
		this.solutionOfferName = solutionOfferName;
	}
	public String getOfferName() {
		return offerName;
	}
	public void setOfferName(String offerName) {
		this.offerName = offerName;
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
