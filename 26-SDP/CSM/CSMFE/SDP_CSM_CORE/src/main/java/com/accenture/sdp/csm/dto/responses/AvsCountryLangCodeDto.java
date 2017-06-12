package com.accenture.sdp.csm.dto.responses;

import java.io.Serializable;

public class AvsCountryLangCodeDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8695511431499584252L;
	
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
