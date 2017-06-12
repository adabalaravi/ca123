package com.accenture.sdp.csmfe.webservices.response.avs;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class AvsCountryLangCodeInfoResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4947819021855611728L;

	private String country;
	private String countryCode;
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

}
