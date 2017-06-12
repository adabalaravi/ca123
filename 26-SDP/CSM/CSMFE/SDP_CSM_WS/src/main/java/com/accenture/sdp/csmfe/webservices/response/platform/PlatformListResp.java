package com.accenture.sdp.csmfe.webservices.response.platform;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class PlatformListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6338863079831345861L;

	private List<PlatformInfoResp> platformList;

	@XmlElement(name = "platform")
	public List<PlatformInfoResp> getPlatformList() {
		return platformList;
	}

	public void setPlatformList(List<PlatformInfoResp> platformList) {
		this.platformList = platformList;
	}
}
