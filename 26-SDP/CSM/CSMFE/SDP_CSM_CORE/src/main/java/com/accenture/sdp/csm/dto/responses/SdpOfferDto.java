package com.accenture.sdp.csm.dto.responses;

import java.io.Serializable;

public class SdpOfferDto extends SdpBaseResponseDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long offerId;
	private String offerName;
	private String offerDescription;
	private String offerProfile;
	private Long statusId;
	private String statusName;
	private String externalId;
	private Long serviceVariantId;
	private String serviceVariantName;

	private String platformName;
	private Long platformId;
	private String serviceTemplateName;
	private Long serviceTemplateId;

	/**
	 * @return the offerId
	 */
	public Long getOfferId() {
		return offerId;
	}

	/**
	 * @param offerId
	 *            the offerId to set
	 */
	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	/**
	 * @return the offerName
	 */
	public String getOfferName() {
		return offerName;
	}

	/**
	 * @param offerName
	 *            the offerName to set
	 */
	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	/**
	 * @return the offerDescription
	 */
	public String getOfferDescription() {
		return offerDescription;
	}

	/**
	 * @param offerDescription
	 *            the offerDescription to set
	 */
	public void setOfferDescription(String offerDescription) {
		this.offerDescription = offerDescription;
	}

	/**
	 * @return the statusId
	 */
	public Long getStatusId() {
		return statusId;
	}

	/**
	 * @param statusId
	 *            the statusId to set
	 */
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	/**
	 * @return the statusName
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * @param statusName
	 *            the statusName to set
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	/**
	 * @return the externalId
	 */
	public String getExternalId() {
		return externalId;
	}

	/**
	 * @param externalId
	 *            the externalId to set
	 */
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	/**
	 * @return the serviceVariantId
	 */
	public Long getServiceVariantId() {
		return serviceVariantId;
	}

	/**
	 * @param serviceVariantId
	 *            the serviceVariantId to set
	 */
	public void setServiceVariantId(Long serviceVariantId) {
		this.serviceVariantId = serviceVariantId;
	}

	/**
	 * @return the offerProfile
	 */
	public String getOfferProfile() {
		return offerProfile;
	}

	/**
	 * @param offerProfile
	 *            the offerProfile to set
	 */
	public void setOfferProfile(String offerProfile) {
		this.offerProfile = offerProfile;
	}

	/**
	 * @return the serviceVariantName
	 */
	public String getServiceVariantName() {
		return serviceVariantName;
	}

	/**
	 * @param serviceVariantName
	 *            the serviceVariantName to set
	 */
	public void setServiceVariantName(String serviceVariantName) {
		this.serviceVariantName = serviceVariantName;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public Long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}

	public String getServiceTemplateName() {
		return serviceTemplateName;
	}

	public void setServiceTemplateName(String serviceTemplateName) {
		this.serviceTemplateName = serviceTemplateName;
	}

	public Long getServiceTemplateId() {
		return serviceTemplateId;
	}

	public void setServiceTemplateId(Long serviceTemplateId) {
		this.serviceTemplateId = serviceTemplateId;
	}

}