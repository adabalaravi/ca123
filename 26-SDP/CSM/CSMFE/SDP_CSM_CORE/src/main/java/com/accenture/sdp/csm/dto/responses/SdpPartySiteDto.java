package com.accenture.sdp.csm.dto.responses;

public class SdpPartySiteDto extends SdpBaseResponseDto {
	private static final long serialVersionUID = 1L;

	private Long siteId;
	private String siteName;
	private String siteDescription;
	private String status;
	private Long statusId;
	private Long partyId;
	private String partyName;
	private String externalID;
	private String siteProfile;
	private String streetAddress;
	private String city;
	private String zipCode;
	private String province;
	private String country;

	public SdpPartySiteDto() {
	}

	/**
	 * @return the siteId
	 */
	public Long getSiteId() {
		return siteId;
	}

	/**
	 * @param siteId the siteId to set
	 */
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the statusId
	 */
	public Long getStatusId() {
		return statusId;
	}

	/**
	 * @param statusId the statusId to set
	 */
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	/**
	 * @return the partyId
	 */
	public Long getPartyId() {
		return partyId;
	}

	/**
	 * @param partyId the partyId to set
	 */
	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}

	/**
	 * @return the partyName
	 */
	public String getPartyName() {
		return partyName;
	}

	/**
	 * @param partyName the partyName to set
	 */
	public void setPartyName(String partyName) {
		this.partyName = partyName;
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
	 * @return the streetAddress
	 */
	public String getStreetAddress() {
		return streetAddress;
	}

	/**
	 * @param streetAddress the streetAddress to set
	 */
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
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
	
}
