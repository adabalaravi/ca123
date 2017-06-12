package com.accenture.sdp.csmfe.webservices.response.avs;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchAvsPlatformResp extends BaseResp {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8505778752808870309L;

	private AvsPlatformListResp platforms;
	
	public SearchAvsPlatformResp() {
		super();
		platforms = new AvsPlatformListResp();
	}

	public AvsPlatformListResp getPlatforms() {
		return platforms;
	}

	public void setPlatforms(AvsPlatformListResp platforms) {
		this.platforms = platforms;
	}
}
