package com.accenture.sdp.csmfe.webservices.response.avs;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchAvsCountryLangCodeResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8505778752808870309L;

	private AvsCountryLangCodeListResp countryLangCodes;

	public SearchAvsCountryLangCodeResp() {
		super();
		countryLangCodes = new AvsCountryLangCodeListResp();
	}

	public AvsCountryLangCodeListResp getCountryLangCodes() {
		return countryLangCodes;
	}

	public void setCountryLangCodes(AvsCountryLangCodeListResp countryLangCodes) {
		this.countryLangCodes = countryLangCodes;
	}
}
