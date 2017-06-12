package com.accenture.sdp.csmfe.webservices.response.packageprice;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchPackagePriceResp extends BaseResp {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8505778752808870309L;

	private PackagePriceListResp packagePriceList;
	
	public SearchPackagePriceResp() {
		super();
		packagePriceList = new PackagePriceListResp();
	}

	public PackagePriceListResp getPackagePriceList() {
		return packagePriceList;
	}

	public void setPackagePriceList(PackagePriceListResp packagePriceList) {
		this.packagePriceList = packagePriceList;
	}
}
