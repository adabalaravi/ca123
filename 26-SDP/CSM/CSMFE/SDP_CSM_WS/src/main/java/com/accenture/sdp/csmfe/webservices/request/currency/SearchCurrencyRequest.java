package com.accenture.sdp.csmfe.webservices.request.currency;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class SearchCurrencyRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8685587710441503663L;

	private Long currencyTypeId;

	public Long getCurrencyTypeId() {
		return currencyTypeId;
	}

	public void setCurrencyTypeId(Long currencyTypeId) {
		this.currencyTypeId = currencyTypeId;
	}
}
