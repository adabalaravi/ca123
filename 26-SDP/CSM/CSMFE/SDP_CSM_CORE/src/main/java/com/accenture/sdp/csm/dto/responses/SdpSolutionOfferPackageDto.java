package com.accenture.sdp.csm.dto.responses;


import java.io.Serializable;
import java.util.List;






public class SdpSolutionOfferPackageDto  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private SdpSolutionOfferDto solutionOfferDto;
	
	private List<SdpSolutionOfferDetailDto> solutionOfferDetailDto;

	/**
	 * @return the solutionOfferDto
	 */
	public SdpSolutionOfferDto getSolutionOfferDto() {
		return solutionOfferDto;
	}

	/**
	 * @param solutionOfferDto the solutionOfferDto to set
	 */
	public void setSolutionOfferDto(SdpSolutionOfferDto solutionOfferDto) {
		this.solutionOfferDto = solutionOfferDto;
	}

	/**
	 * @return the solutionOfferDetailDto
	 */
	public List<SdpSolutionOfferDetailDto> getSolutionOfferDetailDto() {
		return solutionOfferDetailDto;
	}

	/**
	 * @param solutionOfferDetailDto the solutionOfferDetailDto to set
	 */
	public void setSolutionOfferDetailDto(
			List<SdpSolutionOfferDetailDto> solutionOfferDetailDto) {
		this.solutionOfferDetailDto = solutionOfferDetailDto;
	}

	

	
}