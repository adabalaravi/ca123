package com.accenture.sdp.csmfe.webservices.response.status;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseRespPaginated;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchAllStatusResp extends BaseRespPaginated {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8208722272023144182L;
	
	private StatusInfoListResp statusList;

	public SearchAllStatusResp() {
		super();
		statusList  = new StatusInfoListResp();
	}

	public StatusInfoListResp getStatusList() {
		return statusList;
	}

	public void setStatusList(StatusInfoListResp statusList) {
		this.statusList = statusList;
	}
}
