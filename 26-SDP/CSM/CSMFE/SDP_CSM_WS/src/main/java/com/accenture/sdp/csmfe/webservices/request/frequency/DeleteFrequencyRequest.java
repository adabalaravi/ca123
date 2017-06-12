package com.accenture.sdp.csmfe.webservices.request.frequency;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class DeleteFrequencyRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5887008315807707340L;
	
	private Long frequencyTypeId;

	public Long getFrequencyTypeId() {
		return frequencyTypeId;
	}

	public void setFrequencyTypeId(Long frequencyTypeId) {
		this.frequencyTypeId = frequencyTypeId;
	}
	
}
