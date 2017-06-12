package com.accenture.sdp.csmfe.webservices.response.partygroup;

import javax.xml.bind.annotation.XmlRootElement;
import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class CreatePartyGroupResp extends BaseResp {

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
