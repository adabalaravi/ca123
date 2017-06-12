package com.accenture.sdp.csmfe.webservices.response.avs;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchAvsDigitalGoodResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1313125850761095948L;

	private AvsDigitalGoodListResp digitalGoods;

	public SearchAvsDigitalGoodResp() {
		super();
		digitalGoods = new AvsDigitalGoodListResp();
	}

	public AvsDigitalGoodListResp getDigitalGoods() {
		return digitalGoods;
	}

	public void setDigitalGoods(AvsDigitalGoodListResp digitalGoods) {
		this.digitalGoods = digitalGoods;
	}

}
