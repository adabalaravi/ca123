package com.accenture.sdp.csmfe.webservices.response.asyncflows;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class EndpointInfoListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<EndpointFlowResp> endPointList;

	@XmlElement(name = "endPoint")
	public List<EndpointFlowResp> getEndPointList() {
		return endPointList;
	}

	public void setEndPointList(List<EndpointFlowResp> endPointList) {
		this.endPointList = endPointList;
	}
}
