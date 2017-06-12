package com.accenture.sdp.csmfe.webservices.request.frequency;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class CreateFrequencyRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5887008315807707340L;
	
	private String frequencyTypeName;
	private String frequencyTypeDescription;
	private Long frequencyDays;
	public String getFrequencyTypeName() {
		return frequencyTypeName;
	}
	public void setFrequencyTypeName(String frequencyTypeName) {
		this.frequencyTypeName = frequencyTypeName;
	}
	public String getFrequencyTypeDescription() {
		return frequencyTypeDescription;
	}
	public void setFrequencyTypeDescription(String frequencyTypeDescription) {
		this.frequencyTypeDescription = frequencyTypeDescription;
	}
	public Long getFrequencyDays() {
		return frequencyDays;
	}
	public void setFrequencyDays(Long frequencyDays) {
		this.frequencyDays = frequencyDays;
	}
	
	
}
