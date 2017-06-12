package com.accenture.sdp.csmfe.webservices.request.partygroup;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class DeletePartyGroupRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5887008315807707340L;
	
	private Long partyGroupId;

	public Long getPartyGroupId() {
		return partyGroupId;
	}

	public void setPartyGroupId(Long partyGroupId) {
		this.partyGroupId = partyGroupId;
	}
	
}
