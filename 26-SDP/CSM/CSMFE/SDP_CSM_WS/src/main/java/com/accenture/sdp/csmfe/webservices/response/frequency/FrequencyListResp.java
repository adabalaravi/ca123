package com.accenture.sdp.csmfe.webservices.response.frequency;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class FrequencyListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7035809419708273359L;

	private List<FrequencyInfoResp> frequencyList;

	@XmlElement(name = "frequency")
	public List<FrequencyInfoResp> getFrequencyList() {
		return frequencyList;
	}

	public void setFrequencyList(List<FrequencyInfoResp> frequencyList) {
		this.frequencyList = frequencyList;
	}
}
