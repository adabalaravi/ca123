package com.accenture.sdp.csmfe.webservices.response.avs;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class AvsPaymentTypeListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7036229131620698388L;

	private List<AvsPaymentTypeInfoResp> paymentTypeList;

	@XmlElement(name = "paymentType")
	public List<AvsPaymentTypeInfoResp> getPaymentTypeList() {
		return paymentTypeList;
	}

	public void setPaymentTypeList(List<AvsPaymentTypeInfoResp> paymentTypeList) {
		this.paymentTypeList = paymentTypeList;
	}
}
