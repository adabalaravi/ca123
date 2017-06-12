package com.accenture.sdp.csmfe.webservices.request.solutionoffer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class ModifyDiscountsRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5146583801989129162L;

	private List<ModifyDiscountInfoRequest> discounts;

	public ModifyDiscountsRequest() {
		discounts = new ArrayList<ModifyDiscountInfoRequest>();
	}

	@XmlElement(name = "discount")
	public List<ModifyDiscountInfoRequest> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(List<ModifyDiscountInfoRequest> discounts) {
		this.discounts = discounts;
	}
}
