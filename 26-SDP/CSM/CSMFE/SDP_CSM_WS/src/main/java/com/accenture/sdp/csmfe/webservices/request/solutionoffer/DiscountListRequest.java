package com.accenture.sdp.csmfe.webservices.request.solutionoffer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class DiscountListRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1846215844187017662L;

	private List<DiscountInfoRequest> discountList;

	public DiscountListRequest() {
		discountList = new ArrayList<DiscountInfoRequest>();
	}

	@XmlElement(name = "discount")
	public List<DiscountInfoRequest> getDiscountList() {
		return discountList;
	}

	public void setDiscountList(List<DiscountInfoRequest> discountList) {
		this.discountList = discountList;
	}

}
