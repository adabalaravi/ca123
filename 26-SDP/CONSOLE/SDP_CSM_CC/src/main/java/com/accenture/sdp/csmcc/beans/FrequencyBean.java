/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.sdp.csmcc.beans;

import java.io.Serializable;
import java.util.Date;

public class FrequencyBean implements Serializable {

	/**
	 * 
	 */
	public static final String FREQUENCY_NAME_FIELD ="frequencyName";
	public static final String FREQUENCY_DESC_FIELD ="frequencyDescription";
	public static final String FREQUENCY_CREATION_DATE_FIELD ="frequencyCreationDate";
	public static final String FREQUENCY_IN_DAYS ="frequencyDays";


	private static final long serialVersionUID = 1L;
	private Long frequencyId;
	private String frequencyName;
	private String frequencyDesc;
	private String frequencyDaysString;
	private Long frequencyDays;
	private Date frequencyCreationDate;
	private String frequencyTypeName;
	

	public FrequencyBean() {
		frequencyDays = null;
	}

	public Long getFrequencyId() {
		return frequencyId;
	}

	public Long getFrequencyDays() {
		return frequencyDays;
	}


	public void setFrequencyDays(Long frequencyDays) {
		this.frequencyDays = frequencyDays;
	}


	public void setFrequencyId(Long frequencyId) {
		this.frequencyId = frequencyId;
	}

	public String getFrequencyName() {
		return frequencyName;
	}

	public void setFrequencyName(String frequencyName) {
		this.frequencyName = frequencyName;
	}

	public String getFrequencyDesc() {
		return frequencyDesc;
	}

	public void setFrequencyDesc(String frequencyDesc) {
		this.frequencyDesc = frequencyDesc;
	}

	public Date getFrequencyCreationDate() {
		return frequencyCreationDate;
	}


	public void setFrequencyCreationDate(Date frequencyCreationDate) {
		this.frequencyCreationDate = frequencyCreationDate;
	}


	public String getFrequencyTypeName() {
		return frequencyTypeName;
	}


	public void setFrequencyTypeName(String frequencyTypeName) {
		this.frequencyTypeName = frequencyTypeName;
	}


	public String getFrequencyDaysString() {
		return frequencyDaysString;
	}


	public void setFrequencyDaysString(String frequencyDaysString) {
		this.frequencyDaysString = frequencyDaysString;
	}

}
