/**
 * 
 */
package com.accenture.sdp.csm.dto.requests;

/**
 * @author patrizio.pontecorvi
 *
 */
public class SdpPartySiteRequestDto {
	
	private String siteName;
	private String siteDescription;
	private String address;
	private String city;
	private String zipCode;
	private String province;
	private String country;
	private String externalID;
	private String siteProfile;
	
	/**
	 * @return the siteName
	 */
	public String getSiteName() {
		return siteName;
	}

	/**
	 * @param siteName the siteName to set
	 */
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	/**
	 * @return the siteDescription
	 */
	public String getSiteDescription() {
		return siteDescription;
	}

	/**
	 * @param siteDescription the siteDescription to set
	 */
	public void setSiteDescription(String siteDescription) {
		this.siteDescription = siteDescription;
	}


	/**
	 * @return the streetAddress
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the externalID
	 */
	public String getExternalID() {
		return externalID;
	}

	/**
	 * @param externalID the externalID to set
	 */
	public void setExternalID(String externalID) {
		this.externalID = externalID;
	}

	/**
	 * @return the siteProfile
	 */
	public String getSiteProfile() {
		return siteProfile;
	}

	/**
	 * @param siteProfile the siteProfile to set
	 */
	public void setSiteProfile(String siteProfile) {
		this.siteProfile = siteProfile;
	}

	/**
	 * @param siteName
	 * @param siteDescription
	 * @param address
	 * @param city
	 * @param zipCode
	 * @param province
	 * @param country
	 * @param externalID
	 * @param siteProfile
	 */
	public SdpPartySiteRequestDto(String siteName, String siteDescription,
			String address,String city, String zipCode, String province, String country,
			String externalID, String siteProfile) {
		super();
		this.siteName = siteName;
		this.siteDescription = siteDescription;
		this.address = address;
		this.city = city;
		this.zipCode = zipCode;
		this.province = province;
		this.country = country;
		this.externalID = externalID;
		this.siteProfile = siteProfile;
	}

	/**
	 * 
	 */
	public SdpPartySiteRequestDto() {
		super();
	}

	@Override
	public String toString(){
		return  " siteName = "+ siteName+
				" siteDescription = "+siteDescription+
				" address = "+address+
				" city = "+city+
				" zipCode = "+zipCode+
				" province = "+province+
				" country = "+country+
				" externalId = "+externalID+
				" siteProfile = "+siteProfile+" ";		
	}


}
