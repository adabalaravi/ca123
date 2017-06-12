package com.accenture.sdp.csmfe.webservices.response.platform;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchPlatformByServiceVariantResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2705827715946220458L;

	private PlatformByServiceVariantInfoResp platforms;

	public SearchPlatformByServiceVariantResp() {
		super();
		platforms = new PlatformByServiceVariantInfoResp();
	}

	public PlatformByServiceVariantInfoResp getPlatforms() {
		return platforms;
	}

	public void setPlatforms(PlatformByServiceVariantInfoResp platforms) {
		this.platforms = platforms;
	}
}
