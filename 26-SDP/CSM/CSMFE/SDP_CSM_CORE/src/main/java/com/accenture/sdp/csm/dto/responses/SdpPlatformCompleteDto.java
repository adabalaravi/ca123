package com.accenture.sdp.csm.dto.responses;


import java.io.Serializable;

public class SdpPlatformCompleteDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private SdpPlatformDto sdpPlatformDto;
	private SdpServiceTemplateDto sdpServiceTemplateDto;
	private SdpServiceVariantDto sdpServiceVariantDto;
	
	public SdpPlatformDto getSdpPlatformDto() {
		return sdpPlatformDto;
	}
	public void setSdpPlatformDto(SdpPlatformDto sdpPlatformDto) {
		this.sdpPlatformDto = sdpPlatformDto;
	}
	public SdpServiceTemplateDto getSdpServiceTemplateDto() {
		return sdpServiceTemplateDto;
	}
	public void setSdpServiceTemplateDto(SdpServiceTemplateDto sdpServiceTemplateDto) {
		this.sdpServiceTemplateDto = sdpServiceTemplateDto;
	}
	public SdpServiceVariantDto getSdpServiceVariantDto() {
		return sdpServiceVariantDto;
	}
	public void setSdpServiceVariantDto(SdpServiceVariantDto sdpServiceVariantDto) {
		this.sdpServiceVariantDto = sdpServiceVariantDto;
	}
}