package com.accenture.sdp.csmfe.webservices.response.avs;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class AvsDigitalGoodListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7036229131620698388L;

	private List<AvsDigitalGoodInfoResp> digitalGoodList;

	@XmlElement(name = "digitalGood")
	public List<AvsDigitalGoodInfoResp> getDigitalGoodList() {
		return digitalGoodList;
	}

	public void setDigitalGoodList(List<AvsDigitalGoodInfoResp> digitalGoodList) {
		this.digitalGoodList = digitalGoodList;
	}

}
