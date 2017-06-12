package com.accenture.sdp.csm.dto.responses;


import java.io.Serializable;
import java.util.List;






public class SdpSolutionSolutionOfferDto  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private SdpSolutionDto solutionDto;
	
	private List<SdpSolutionOfferDto> solutionOfferDto;

	/**
	 * @return the solutionOfferDto
	 */
	public List<SdpSolutionOfferDto> getSolutionOfferDto() {
		return solutionOfferDto;
	}

	/**
	 * @param solutionOfferDto the solutionOfferDto to set
	 */
	public void setSolutionOfferDto(List<SdpSolutionOfferDto> solutionOfferDto) {
		this.solutionOfferDto = solutionOfferDto;
	}

	/**
	 * @return the solutionDto
	 */
	public SdpSolutionDto getSolutionDto() {
		return solutionDto;
	}

	/**
	 * @param solutionDto the solutionDto to set
	 */
	public void setSolutionDto(SdpSolutionDto solutionDto) {
		this.solutionDto = solutionDto;
	}

	
	

	
}