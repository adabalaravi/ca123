package com.accenture.sdp.csmfe.webservices.response.platform;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseRespPaginated;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchPlatformComplexRespPaginated extends BaseRespPaginated {

	/**
	 * 
	 */
	private static final long serialVersionUID = -234211301293370465L;
	private PlatformComplexListResp platforms;

	public SearchPlatformComplexRespPaginated() {
		super();
		platforms = new PlatformComplexListResp();
	}

	public PlatformComplexListResp getPlatforms() {
		return platforms;
	}

	public void setPlatforms(PlatformComplexListResp platforms) {
		this.platforms = platforms;
	}
}
