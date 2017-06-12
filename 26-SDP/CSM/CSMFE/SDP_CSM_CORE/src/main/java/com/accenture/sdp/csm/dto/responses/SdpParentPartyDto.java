package com.accenture.sdp.csm.dto.responses;

import java.util.ArrayList;
import java.util.List;

public class SdpParentPartyDto extends SdpBaseResponseDto {

	/**
	 * @author patrizio.pontecorvi
	 * 
	 */

	public SdpParentPartyDto() {
		super();
		partyGroups = new ArrayList<SdpPartyGroupResponseDto>();
	}

	private static final long serialVersionUID = -1910590116995686266L;
	private Long partyId;
	private String partyName;
	private String partyDescription;
	private Long statusId;
	private String status;
	private String externalId;
	private String partyProfile;
	private List<SdpPartyGroupResponseDto> partyGroups;

	private boolean isBlacklisted;
	private boolean isWhitelisted;

	/**
	 * @return the partyId
	 */
	public Long getPartyId() {
		return partyId;
	}

	/**
	 * @param partyId
	 *            the partyId to set
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
	 * @param partyName
	 *            the partyName to set
	 */
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	/**
	 * @return the partyDescription
	 */
	public String getPartyDescription() {
		return partyDescription;
	}

	/**
	 * @param partyDescription
	 *            the partyDescription to set
	 */
	public void setPartyDescription(String partyDescription) {
		this.partyDescription = partyDescription;
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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
	 * @return the partyProfile
	 */
	public String getPartyProfile() {
		return partyProfile;
	}

	/**
	 * @param partyProfile
	 *            the partyProfile to set
	 */

	public void setPartyProfile(String partyProfile) {
		this.partyProfile = partyProfile;
	}

	public List<SdpPartyGroupResponseDto> getPartyGroups() {
		return partyGroups;
	}

	public void setPartyGroups(List<SdpPartyGroupResponseDto> partyGroups) {
		this.partyGroups = partyGroups;
	}

	public boolean isBlacklisted() {
		return isBlacklisted;
	}

	public void setBlacklisted(boolean isBlacklisted) {
		this.isBlacklisted = isBlacklisted;
	}

	public boolean isWhitelisted() {
		return isWhitelisted;
	}

	public void setWhitelisted(boolean isWhitelisted) {
		this.isWhitelisted = isWhitelisted;
	}

}
