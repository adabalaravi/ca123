package com.accenture.sdp.csmfe.webservices.response.solutionoffer;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class CreateDiscountListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6746359244643558662L;

	private List<Long> discountIdList;

	@XmlElement(name = "discountId")
	public List<Long> getDiscountIdList() {
		return discountIdList;
	}

	public void setDiscountIdList(List<Long> discountIdList) {
		this.discountIdList = discountIdList;
	}
}
