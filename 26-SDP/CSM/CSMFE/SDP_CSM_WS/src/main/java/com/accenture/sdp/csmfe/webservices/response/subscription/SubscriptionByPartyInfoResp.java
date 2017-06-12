package com.accenture.sdp.csmfe.webservices.response.subscription;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SubscriptionByPartyInfoResp extends SubscriptionComplexInfoResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3096247335960207043L;

	private Long partyTypeId;
	private String partyTypeName;
	public Long getPartyTypeId() {
		return partyTypeId;
	}
	public void setPartyTypeId(Long partyTypeId) {
		this.partyTypeId = partyTypeId;
	}
	public String getPartyTypeName() {
		return partyTypeName;
	}
	public void setPartyTypeName(String partyTypeName) {
		this.partyTypeName = partyTypeName;
	}
	
	
}
