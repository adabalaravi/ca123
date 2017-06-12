package com.accenture.sdp.csmfe.webservices.response.frequency;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseRespPaginated;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchAllFrequenciesResp extends BaseRespPaginated {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8208722272023144182L;
	
	private FrequencyListResp frequencyList;

	public SearchAllFrequenciesResp() {
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
