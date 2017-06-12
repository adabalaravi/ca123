package com.accenture.sdp.csmfe.webservices.response.packageprice;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class PackagePriceListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7036229131620698388L;

	private List<PackagePriceInfoResp> packagePriceList;

	@XmlElement(name = "packagePrice")
	public List<PackagePriceInfoResp> getPackagePriceList() {
		return packagePriceList;
	}

	public void setPackagePriceList(List<PackagePriceInfoResp> packagePriceList) {
		this.packagePriceList = packagePriceList;
	}
}
