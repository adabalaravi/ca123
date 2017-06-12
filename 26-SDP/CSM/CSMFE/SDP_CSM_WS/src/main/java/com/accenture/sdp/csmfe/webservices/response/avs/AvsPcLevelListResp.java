package com.accenture.sdp.csmfe.webservices.response.avs;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class AvsPcLevelListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7036229131620698388L;

	private List<AvsPcLevelInfoResp> pcLevelList;

	@XmlElement(name = "pcLevel")
	public List<AvsPcLevelInfoResp> getPcLevelList() {
		return pcLevelList;
	}

	public void setPcLevelList(List<AvsPcLevelInfoResp> pcLevelList) {
		this.pcLevelList = pcLevelList;
	}

}
