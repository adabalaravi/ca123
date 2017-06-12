package com.accenture.sdp.csm.dto.responses;

public class RefCurrencyTypeResponseDto extends SdpBaseResponseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long currencyTypeId;
	private String currencyTypeName;

	public RefCurrencyTypeResponseDto() {
		super();
	}

	public Long getCurrencyTypeId() {
		return currencyTypeId;
	}

	public void setCurrencyTypeId(Long currencyTypeId) {
		this.currencyTypeId = currencyTypeId;
	}

	public String getCurrencyTypeName() {
		return currencyTypeName;
	}

	public void setCurrencyTypeName(String currencyTypeName) {
		this.currencyTypeName = currencyTypeName;
	}

}
