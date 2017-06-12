package com.accenture.sdp.csmfe.webservices.response.avs;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchAvsPaymentTypeResp extends BaseResp {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8505778752808870309L;

	private AvsPaymentTypeListResp paymentTypes;
	
	public SearchAvsPaymentTypeResp() {
		super();
		paymentTypes = new AvsPaymentTypeListResp();
	}

	public AvsPaymentTypeListResp getPaymentTypes() {
		return paymentTypes;
	}

	public void setPaymentTypes(AvsPaymentTypeListResp paymentTypes) {
		this.paymentTypes = paymentTypes;
	}
}
