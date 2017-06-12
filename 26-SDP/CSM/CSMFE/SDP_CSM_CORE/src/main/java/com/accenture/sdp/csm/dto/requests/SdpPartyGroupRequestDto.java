/**
 * 
 */
package com.accenture.sdp.csm.dto.requests;

import java.io.Serializable;

/**
 * @author alberto.marimpietri
 *
 */
public class SdpPartyGroupRequestDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2665006396889714145L;
	
	private Long partyGroupId;
	private String operation;
	
	public Long getPartyGroupId() {
		return partyGroupId;
	}
	public void setPartyGroupId(Long partyGroupId) {
		this.partyGroupId = partyGroupId;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public SdpPartyGroupRequestDto() {
		super();
	}
	@Override
	public String toString() {
		return " partyGroupId = " + partyGroupId
				+ " operation = " + operation+ " ";
	}
	
	
}
