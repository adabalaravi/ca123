package com.accenture.avs.ca.be.db.bean;

import java.io.Serializable;

/**
 * @author h.kumar.satkuri
 *
 */
public class SolutionOfferinclDurationDetailsBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long solutionOfferID;
	private Long duration;
	private String solutionOfferType;
	
	public Long getSolutionOfferID() {
		return solutionOfferID;
	}
	public void setSolutionOfferID(Long solutionOfferID) {
		this.solutionOfferID = solutionOfferID;
	}
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public String getSolutionOfferType() {
		return solutionOfferType;
	}
	public void setSolutionOfferType(String solutionOfferType) {
		this.solutionOfferType = solutionOfferType;
	}

}
