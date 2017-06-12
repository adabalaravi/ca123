package com.accenture.sdp.csmfe.webservices.response.solution;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchSolutionComplexResp extends BaseResp{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8114610044818805612L;
	
	private SolutionComplexListResp solutions;
	
	public SearchSolutionComplexResp() {
		super();
		solutions = new SolutionComplexListResp();
	}

	public SolutionComplexListResp getSolutions() {
		return solutions;
	}

	public void setSolutions(SolutionComplexListResp solutions) {
		this.solutions = solutions;
	}
}
