package com.accenture.sdp.csmfe.webservices.response.currency;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class CreateCurrencyResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -324349407140858122L;

	private Long currencyTypeId;

	public Long getCurrencyTypeId() {
		return currencyTypeId;
	}

	public void setCurrencyTypeId(Long currencyTypeId) {
		this.currencyTypeId = currencyTypeId;
	}
}
