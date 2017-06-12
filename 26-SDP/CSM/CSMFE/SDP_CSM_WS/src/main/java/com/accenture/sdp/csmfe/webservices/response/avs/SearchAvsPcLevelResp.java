package com.accenture.sdp.csmfe.webservices.response.avs;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchAvsPcLevelResp extends BaseResp {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8505778752808870309L;

	private AvsPcLevelListResp pcLevels;
	
	public SearchAvsPcLevelResp() {
		super();
		pcLevels = new AvsPcLevelListResp();
	}

	public AvsPcLevelListResp getPcLevels() {
		return pcLevels;
	}

	public void setPcLevels(AvsPcLevelListResp pcLevels) {
		this.pcLevels = pcLevels;
	}
}
