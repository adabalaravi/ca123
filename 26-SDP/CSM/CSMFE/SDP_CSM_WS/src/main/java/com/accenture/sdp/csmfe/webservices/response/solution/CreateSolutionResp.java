package com.accenture.sdp.csmfe.webservices.response.solution;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class CreateSolutionResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7122379136459096285L;

	private Long solutionId;

	public Long getSolutionId() {
		return solutionId;
	}

	public void setSolutionId(Long solutionId) {
		this.solutionId = solutionId;
	}
}
