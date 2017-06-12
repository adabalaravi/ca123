package com.accenture.sdp.csmfe.webservices.response.asyncflows;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchEndpointsInfoResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private EndpointInfoListResp endPoints;

	public SearchEndpointsInfoResp() {
		super();
		endPoints = new EndpointInfoListResp();
	}

	public EndpointInfoListResp getEndPoints() {
		return endPoints;
	}

	public void setEndPoints(EndpointInfoListResp endPoints) {
		this.endPoints = endPoints;
	}
}
