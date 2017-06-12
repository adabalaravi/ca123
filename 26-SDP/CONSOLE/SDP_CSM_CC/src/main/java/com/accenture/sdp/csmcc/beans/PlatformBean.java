/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.sdp.csmcc.beans;

import java.io.Serializable;

import javax.faces.event.ActionEvent;

import com.accenture.sdp.csmcc.common.constants.StatusConstants;

public class PlatformBean implements Serializable {

	public static final String PLATFORM_NAME_FIELD ="platformName";
	public static final String PLATFORM_DESC_FIELD ="platformDesc";
	public static final String PLATFORM_STATUS_FIELD ="platformStatus";
	public static final String PLATFORM_EXTID_FIELD ="platformExtId";
	public static final String PLATFORM_CREATION_DATE_FIELD ="platformCreationDate";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long platformId;
	private String platformName;
	private String platformDesc;
	private String platformStatus;
	private String platformExtId;
	private String platformProfile;
	private String platformCreationDate;
	private String platformNameStored;
	private String platformDescStored;
	private String platformExtIdStored;
	private String platformProfileStored;
	


	public Long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}

	public String getPlatformStatus() {
		return platformStatus;
	}

	public void setPlatformStatus(String platformStatus) {
		this.platformStatus = platformStatus;
	}

	public String getPlatformExtId() {
		return platformExtId;
	}

	public void setPlatformExtId(String platformExtId) {
		this.platformExtId = platformExtId;
	}

	public String getPlatformProfile() {
		return platformProfile;
	}

	public void setPlatformProfile(String platformProfile) {
		this.platformProfile = platformProfile;
	}

	public String getPlatformCreationDate() {
		return platformCreationDate;
	}

	public void setPlatformCreationDate(String platformCreationDate) {
		this.platformCreationDate = platformCreationDate;
	}

	public PlatformBean() {
		super();
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getPlatformDesc() {
		return platformDesc;
	}

	public void setPlatformDesc(String platformDesc) {
		this.platformDesc = platformDesc;
	}


	
	public void cancelFields(ActionEvent event) {
		cleanPlatform();
	}


	

	public void cleanPlatform(){
		this.platformName="";
		this.platformDesc="";
		this.platformExtId="";
		this.platformProfile="";
	}
	
	public void resetPlatform(){
		this.platformName = this.platformNameStored;
		this.platformDesc = this.platformDescStored;
		this.platformExtId= this.platformExtIdStored;
		this.platformProfile = this.platformProfileStored;
	}
	
	public void storeValues(){
		this.platformNameStored = this.platformName;
		this.platformDescStored = this.platformDesc;
		this.platformExtIdStored= this.platformExtId;
		this.platformProfileStored = this.platformProfile;
	}
	
	
	public boolean isActive(){
		 return StatusConstants.ACTIVE.equalsIgnoreCase(platformStatus);
	}
	
	public boolean isSuspended(){
		 return StatusConstants.SUSPENDED.equalsIgnoreCase(platformStatus);
	}
	
	public boolean isInactive(){
		 return StatusConstants.INACTIVE.equalsIgnoreCase(platformStatus);
	}

}
