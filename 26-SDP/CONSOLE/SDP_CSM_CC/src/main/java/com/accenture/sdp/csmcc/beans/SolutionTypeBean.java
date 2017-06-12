package com.accenture.sdp.csmcc.beans;

import java.io.Serializable;

public class SolutionTypeBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String solutionTypeName;
	private Long solutionTypeId;
	public String getSolutionTypeName() {
		return solutionTypeName;
	}
	public void setSolutionTypeName(String solutionTypeName) {
		this.solutionTypeName = solutionTypeName;
	}
	public Long getSolutionTypeId() {
		return solutionTypeId;
	}
	public void setSolutionTypeId(Long solutionTypeId) {
		this.solutionTypeId = solutionTypeId;
	}
	
	
	

}
