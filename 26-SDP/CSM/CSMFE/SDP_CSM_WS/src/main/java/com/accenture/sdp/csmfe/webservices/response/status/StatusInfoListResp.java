package com.accenture.sdp.csmfe.webservices.response.status;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class StatusInfoListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2275378680611556303L;

	private List<StatusInfoResp> statusList;

	@XmlElement(name = "status")
	public List<StatusInfoResp> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<StatusInfoResp> statusList) {
		this.statusList = statusList;
	}
}
