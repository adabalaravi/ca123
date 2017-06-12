package com.accenture.sdp.csm.dto.responses;

import java.io.Serializable;

public class SdpSolutionOfferDetailDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7760635949665320520L;
	private Long packagePriceId;
	private Long packageId;

	public Long getPackagePriceId() {
		return packagePriceId;
	}

	public void setPackagePriceId(Long packagePriceId) {
		this.packagePriceId = packagePriceId;
	}

	public Long getPackageId() {
		return packageId;
	}

	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}

}
