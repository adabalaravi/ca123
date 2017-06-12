/**
 * 
 */
package com.accenture.sdp.csm.dto.responses;


/**
 * @author alberto.marimpietri
 *
 */
public class SdpPartyGroupResponseDto extends SdpBaseResponseDto {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4103144892589364771L;
	
	private Long partyGroupId;
	private String partyGroupName;
	private String partyGroupDescription;
	
	public Long getPartyGroupId() {
		return partyGroupId;
	}

	public void setPartyGroupId(Long partyGroupId) {
		this.partyGroupId = partyGroupId;
	}

	

	public String getPartyGroupName() {
		return partyGroupName;
	}

	public void setPartyGroupName(String partyGroupName) {
		this.partyGroupName = partyGroupName;
	}

	public String getPartyGroupDescription() {
		return partyGroupDescription;
	}

	public void setPartyGroupDescription(String partyGroupDescription) {
		this.partyGroupDescription = partyGroupDescription;
	}

	public SdpPartyGroupResponseDto() {
		super();
	}
	
	@Override
	public String toString(){
		
		return "Party group : "+partyGroupId;
		
	}
	
	
	

}
