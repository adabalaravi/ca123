package com.accenture.sdp.csmfe.webservices.response.avs;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchAvsDeviceIdTypeResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8505778752808870309L;

	private AvsDeviceIdTypeListResp deviceIdTypes;

	public SearchAvsDeviceIdTypeResp() {
		super();
		deviceIdTypes = new AvsDeviceIdTypeListResp();
	}

	public AvsDeviceIdTypeListResp getDeviceIdTypes() {
		return deviceIdTypes;
	}

	public void setDeviceIdTypes(AvsDeviceIdTypeListResp deviceIdTypes) {
		this.deviceIdTypes = deviceIdTypes;
	}
}
