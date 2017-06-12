package com.accenture.sdp.csmfe.webservices.response.currency;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseRespPaginated;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchAllCurrenciesResp extends BaseRespPaginated {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8208722272023144182L;
	
	private CurrencyInfoListResp currencyList;

	public SearchAllCurrenciesResp() {
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
