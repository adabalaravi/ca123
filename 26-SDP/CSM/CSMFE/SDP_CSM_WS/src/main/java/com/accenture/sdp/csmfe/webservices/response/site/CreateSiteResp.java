package com.accenture.sdp.csmfe.webservices.response.site;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class CreateSiteResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7122379136459096285L;

	private Long siteId;

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
}
