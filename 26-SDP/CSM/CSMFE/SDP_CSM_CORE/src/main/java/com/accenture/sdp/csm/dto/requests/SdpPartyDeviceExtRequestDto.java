package com.accenture.sdp.csm.dto.requests;

public class SdpPartyDeviceExtRequestDto {

	private Long partyId;
	private Long policyId;

	public Long getPartyId() {
		return partyId;
	}

	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}

	public Long getPolicyId() {
		return policyId;
	}

	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}

	@Override
	public String toString() {
		return " partyId = " + partyId + " policyId = " + policyId + " ";
	}

}
