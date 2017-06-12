package com.accenture.sdp.csmfe.webservices.response.offergroup;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class CreateOfferGroupResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -324349407140858122L;

	private Long groupId;

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
}
