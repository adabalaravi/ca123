package com.accenture.ams.db.bean;

import java.io.Serializable;

public class CountryLangCode implements Serializable{

	private static final long serialVersionUID = 1L;
	private String countryCode;
	private String country;
	
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	
}
