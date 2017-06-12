package com.accenture.sdp.csm.dto.responses;

import java.util.List;

public class SdpCredentialDto extends SdpBaseResponseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6520747192219588939L;

	private String username;
	private String externalId;
	
	private String partyName;
	private String parentPartyName;
	private List<SdpSecretQuestionDto> secretQuestions;
	private String roleName;
	private String statusName;
	private Long statusID;
	private Long partyId;
	private Long parentPartyId;
	
	
	public String getParentPartyName() {
		return parentPartyName;
	}
	public void setParentPartyName(String parentPartyName) {
		this.parentPartyName = parentPartyName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public List<SdpSecretQuestionDto> getSecretQuestions() {
		return secretQuestions;
	}
	public void setSecretQuestions(List<SdpSecretQuestionDto> secretQuestions) {
		this.secretQuestions = secretQuestions;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public Long getStatusID() {
		return statusID;
	}
	public void setStatusID(Long statusID) {
		this.statusID = statusID;
	}
	public Long getPartyId() {
		return partyId;
	}
	public void setPartyId(Long partyId) {
		this.partyId = partyId;
	}
	public Long getParentPartyId() {
		return parentPartyId;
	}
	public void setParentPartyId(Long parentPartyId) {
		this.parentPartyId = parentPartyId;
	}
}
