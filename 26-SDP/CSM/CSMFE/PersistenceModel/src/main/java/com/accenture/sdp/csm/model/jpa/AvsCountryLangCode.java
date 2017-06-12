package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the AVS_Country_Lang_Code database table.
 * 
 */
@Entity
@Table(name = "AVS_Country_Lang_Code")
@NamedQueries({ @NamedQuery(name = AvsCountryLangCode.QUERY_RETRIEVE_ALL, query = "select o from AvsCountryLangCode o ORDER BY o.country") })
public class AvsCountryLangCode implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String QUERY_RETRIEVE_ALL = "selectAllAvsCountryLangCodes";

	private String country;

	@Id
	@Column(name = "country_code")
	private String countryCode;

	public AvsCountryLangCode() {
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

}