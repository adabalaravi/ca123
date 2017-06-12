package com.accenture.sdp.csmfe.webservices.response.solution;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchSolutionResp extends BaseResp{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8114610044818805612L;
	
	private SolutionListResp solutions;

	public SearchSolutionResp() {
		super();
		solutions = new SolutionListResp();
	}

	public SolutionListResp getSolutions() {
		return solutions;
	}

	public void setSolutions(SolutionListResp solutions) {
		this.solutions = solutions;
	}
}
