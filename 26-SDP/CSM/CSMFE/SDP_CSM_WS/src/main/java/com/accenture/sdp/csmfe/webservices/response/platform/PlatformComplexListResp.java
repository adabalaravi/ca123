package com.accenture.sdp.csmfe.webservices.response.platform;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class PlatformComplexListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6338863079831345861L;

	private List<PlatformComplexInfoResp> platformList;

	@XmlElement(name = "platform")
	public List<PlatformComplexInfoResp> getPlatformList() {
		return platformList;
	}

	public void setPlatformList(List<PlatformComplexInfoResp> platformList) {
		this.platformList = platformList;
	}
}
