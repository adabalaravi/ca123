package com.accenture.sdp.csmfe.webservices.request.partygroup;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class CreatePartyGroupRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5887008315807707340L;
	
	private String partyGroupName;
	private String partyGroupDescription;
	
	public String getPartyGroupName() {
		return partyGroupName;
	}
	public void setPartyGroupName(String partyGroupName) {
		this.partyGroupName = partyGroupName;
	}
	public String getPartyGroupDescription() {
		return partyGroupDescription;
	}
	public void setPartyGroupDescription(String partyGroupDescription) {
		this.partyGroupDescription = partyGroupDescription;
	}
	
	
}
