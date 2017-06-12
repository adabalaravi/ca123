package com.accenture.sdp.csmfe.webservices.response.avs;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class AvsCountryLangCodeListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7036229131620698388L;

	private List<AvsCountryLangCodeInfoResp> countryLangCodeList;

	@XmlElement(name = "countryLangCode")
	public List<AvsCountryLangCodeInfoResp> getCountryLangCodeList() {
		return countryLangCodeList;
	}

	public void setCountryLangCodeList(List<AvsCountryLangCodeInfoResp> countryLangCodeList) {
		this.countryLangCodeList = countryLangCodeList;
	}
}
