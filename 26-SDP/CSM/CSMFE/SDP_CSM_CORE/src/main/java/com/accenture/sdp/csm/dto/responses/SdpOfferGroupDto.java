package com.accenture.sdp.csm.dto.responses;

public class SdpOfferGroupDto extends SdpBaseResponseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1280461242966334464L;

	private Long groupId;

	private String groupName;

	private Long solutionOfferId;

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Long getSolutionOfferId() {
		return solutionOfferId;
	}

	public void setSolutionOfferId(Long solutionOfferId) {
		this.solutionOfferId = solutionOfferId;
	}

}
