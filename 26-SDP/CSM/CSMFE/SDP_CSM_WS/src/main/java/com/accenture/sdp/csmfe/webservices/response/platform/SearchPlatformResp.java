package com.accenture.sdp.csmfe.webservices.response.platform;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchPlatformResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8114610044818805612L;
	private PlatformListResp platforms;

	public SearchPlatformResp() {
		super();
		platforms = new PlatformListResp();
	}

	public PlatformListResp getPlatforms() {
		return platforms;
	}

	public void setPlatforms(PlatformListResp platforms) {
		this.platforms = platforms;
	}
}
