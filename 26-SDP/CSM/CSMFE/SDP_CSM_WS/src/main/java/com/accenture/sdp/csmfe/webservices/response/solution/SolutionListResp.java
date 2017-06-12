package com.accenture.sdp.csmfe.webservices.response.solution;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SolutionListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6338863079831345861L;

	private List<SolutionInfoResp> solutionList;

	@XmlElement(name = "solution")
	public List<SolutionInfoResp> getSolutionList() {
		return solutionList;
	}

	public void setSolutionList(List<SolutionInfoResp> solutionList) {
		this.solutionList = solutionList;
	}
}
