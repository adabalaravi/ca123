package com.accenture.sdp.csmfe.webservices.request.partygroup;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class PartyGroupOperationInfoRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6993034186660521164L;

	private Long partyGroupId; 
	private String operation;
	public Long getPartyGroupId() {
		return partyGroupId;
	}
	public void setPartyGroupId(Long partyGroupId) {
		this.partyGroupId = partyGroupId;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	} 
	
	
	
}
