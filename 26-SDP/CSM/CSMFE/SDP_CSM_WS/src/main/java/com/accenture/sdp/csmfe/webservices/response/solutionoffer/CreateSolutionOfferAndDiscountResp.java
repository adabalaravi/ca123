package com.accenture.sdp.csmfe.webservices.response.solutionoffer;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class CreateSolutionOfferAndDiscountResp extends CreateSolutionOfferResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3244793643200818219L;

	private CreateDiscountListResp discounts;

	public CreateSolutionOfferAndDiscountResp() {
		super();
		discounts = new CreateDiscountListResp();
	}

	public CreateDiscountListResp getDiscounts() {
		return discounts;
	}

	public void setDiscounts(CreateDiscountListResp discounts) {
		this.discounts = discounts;
	}
}
