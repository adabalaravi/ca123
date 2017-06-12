package com.accenture.sdp.csm.dto.responses;

public class RefSolutionTypeResponseDto extends SdpBaseResponseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2773325909513859410L;

	private long solutionTypeId;
	private String solutionTypeDescription;
	private String solutionTypeName;

	public long getSolutionTypeId() {
		return solutionTypeId;
	}
	public void setSolutionTypeId(long solutionTypeId) {
		this.solutionTypeId = solutionTypeId;
	}
	public String getSolutionTypeDescription() {
		return solutionTypeDescription;
	}
	public void setSolutionTypeDescription(String solutionTypeDescription) {
		this.solutionTypeDescription = solutionTypeDescription;
	}
	public String getSolutionTypeName() {
		return solutionTypeName;
	}
	public void setSolutionTypeName(String solutionTypeName) {
		this.solutionTypeName = solutionTypeName;
	}
}
