package com.accenture.sdp.csmfe.webservices.request.frequency;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class SearchFraquencyRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8685587710441503663L;

	private Long frequencyTypeId;

	public Long getFrequencyTypeId() {
		return frequencyTypeId;
	}

	public void setFrequencyTypeId(Long frequencyTypeId) {
		this.frequencyTypeId = frequencyTypeId;
	}
}
