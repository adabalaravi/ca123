package com.accenture.sdp.csmfe.webservices.response.currency;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class CurrencyInfoListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2275378680611556303L;

	private List<CurrencyInfoResp> currencyList;

	@XmlElement(name = "currency")
	public List<CurrencyInfoResp> getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(List<CurrencyInfoResp> currencyList) {
		this.currencyList = currencyList;
	}
}
