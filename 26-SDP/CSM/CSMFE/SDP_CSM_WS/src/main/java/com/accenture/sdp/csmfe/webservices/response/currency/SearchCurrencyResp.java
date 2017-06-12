package com.accenture.sdp.csmfe.webservices.response.currency;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchCurrencyResp extends BaseResp {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8505778752808870309L;

	private CurrencyInfoListResp currencyList;
	
	public SearchCurrencyResp() {
		super();
		currencyList = new CurrencyInfoListResp();
	}

	public CurrencyInfoListResp getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(CurrencyInfoListResp currencyList) {
		this.currencyList = currencyList;
	}
}
