package com.accenture.sdp.csmfe.webservices.response.platform;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class CreatePlatformResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7122379136459096285L;

	private Long platformId;

	public Long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}
}
