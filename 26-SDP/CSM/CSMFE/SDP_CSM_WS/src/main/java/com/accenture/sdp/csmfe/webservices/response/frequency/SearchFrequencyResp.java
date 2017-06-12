package com.accenture.sdp.csmfe.webservices.response.frequency;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchFrequencyResp extends BaseResp {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8505778752808870309L;

	private FrequencyListResp frequencyList;
	
	public SearchFrequencyResp() {
		super();
		frequencyList = new FrequencyListResp();
	}

	public FrequencyListResp getFrequencyList() {
		return frequencyList;
	}

	public void setFrequencyList(FrequencyListResp frequencyList) {
		this.frequencyList = frequencyList;
	}
}
