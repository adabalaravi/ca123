package com.accenture.sdp.csmcc.beans;

import java.io.Serializable;
import java.util.Date;

public class PartyGroupBean implements Serializable{

	/**
	 * 
	 */
	
	public static final String PARTY_GROUP_NAME = "partyGroupName";
	public static final String PARTY_GROUP_DESCRIPTION = "partyGroupDescription";
	public static final String CREATION_DATE_FIELD = "creationDate";
	
	private static final long serialVersionUID = 1L;
	private String partyGroupName;
	private String partyGroupDescription;
	private String partyGroupNameStored;
	private String partyGroupDescriptionStored;
	private Long partyGroupId;
	private String operation;
	private Date creationDate;
	
	
	public String getPartyGroupName() {
		return partyGroupName;
	}
	public void setPartyGroupName(String partyGroupName) {
		this.partyGroupName = partyGroupName;
	}
	public String getPartyGroupDescription() {
		return partyGroupDescription;
	}
	public void setPartyGroupDescription(String partyGroupDescription) {
		this.partyGroupDescription = partyGroupDescription;
	}
	public Long getPartyGroupId() {
		return partyGroupId;
	}
	public void setPartyGroupId(Long partyGroupId) {
		this.partyGroupId = partyGroupId;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	
	public void cancelFields(){
		partyGroupName = null;
		partyGroupDescription = null;
	}
	
	public void resetValues(){
		partyGroupName = partyGroupNameStored;
		partyGroupDescription = partyGroupDescriptionStored;
	}
	
	public void storeValues(){
		partyGroupNameStored = partyGroupName;
		partyGroupDescriptionStored = partyGroupDescription;
	}
	
	

}
