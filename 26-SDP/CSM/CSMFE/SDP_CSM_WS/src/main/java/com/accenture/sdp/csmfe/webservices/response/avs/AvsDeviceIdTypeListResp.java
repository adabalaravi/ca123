package com.accenture.sdp.csmfe.webservices.response.avs;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class AvsDeviceIdTypeListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7036229131620698388L;

	private List<AvsDeviceIdTypeInfoResp> deviceIdTypeList;

	@XmlElement(name = "deviceIdType")
	public List<AvsDeviceIdTypeInfoResp> getDeviceIdTypeList() {
		return deviceIdTypeList;
	}

	public void setDeviceIdTypeList(List<AvsDeviceIdTypeInfoResp> deviceIdTypeList) {
		this.deviceIdTypeList = deviceIdTypeList;
	}
}
