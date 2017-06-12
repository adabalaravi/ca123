package com.accenture.sdp.csmfe.webservices.request.solution;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class PartyGroupNameInfoRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1876672806574295152L;
	
	private String partyGroupName;

	public String getPartyGroupName() {
		return partyGroupName;
	}

	public void setPartyGroupName(String partyGroupName) {
		this.partyGroupName = partyGroupName;
	} 
}
