package com.accenture.sdp.csmfe.webservices.response.frequency;

import javax.xml.bind.annotation.XmlRootElement;
import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class CreateFrequencyResp extends BaseResp {

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
