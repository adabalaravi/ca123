package com.accenture.sdp.csm.dto.responses;

import java.io.Serializable;
import java.util.List;

public class SdpSolutionOfferAndDiscountDto  implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long solutionOfferId;

	private List<Long> discountIdList;

	public Long getSolutionOfferId() {
		return solutionOfferId;
	}

	public void setSolutionOfferId(Long solutionOfferId) {
		this.solutionOfferId = solutionOfferId;
	}

	public List<Long> getDiscountIdList() {
		return discountIdList;
	}

	public void setDiscountIdList(List<Long> discountIdList) {
		this.discountIdList = discountIdList;
	}
}