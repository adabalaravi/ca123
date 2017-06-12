package com.accenture.sdp.csm.dto.responses;

public class RefFrequencyTypeResponseDto  extends SdpBaseResponseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Long frequencyTypeId;
	private String frequencyTypeName;
	private String frequencyTypeDescription;
	private Long frequencyDays;
	
	
	public RefFrequencyTypeResponseDto() {
		super();
	}


	public Long getFrequencyTypeId() {
		return frequencyTypeId;
	}


	public void setFrequencyTypeId(Long frequencyTypeId) {
		this.frequencyTypeId = frequencyTypeId;
	}


	public String getFrequencyTypeName() {
		return frequencyTypeName;
	}


	public void setFrequencyTypeName(String frequencyTypeName) {
		this.frequencyTypeName = frequencyTypeName;
	}


	public String getFrequencyTypeDescription() {
		return frequencyTypeDescription;
	}


	public void setFrequencyTypeDescription(String frequencyTypeDescription) {
		this.frequencyTypeDescription = frequencyTypeDescription;
	}


	public Long getFrequencyDays() {
		return frequencyDays;
	}


	public void setFrequencyDays(Long frequencyDays) {
		this.frequencyDays = frequencyDays;
	}
	
	
	

}
