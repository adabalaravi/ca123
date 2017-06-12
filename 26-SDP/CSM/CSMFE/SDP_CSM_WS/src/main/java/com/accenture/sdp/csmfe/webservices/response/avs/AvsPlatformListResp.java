package com.accenture.sdp.csmfe.webservices.response.avs;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class AvsPlatformListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7036229131620698388L;

	private List<AvsPlatformInfoResp> platformList;

	@XmlElement(name = "platform")
	public List<AvsPlatformInfoResp> getPlatformList() {
		return platformList;
	}

	public void setPlatformList(List<AvsPlatformInfoResp> platformList) {
		this.platformList = platformList;
	}
}
