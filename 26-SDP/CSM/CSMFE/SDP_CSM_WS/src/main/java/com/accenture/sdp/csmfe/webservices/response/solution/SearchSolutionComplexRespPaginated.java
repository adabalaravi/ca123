package com.accenture.sdp.csmfe.webservices.response.solution;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseRespPaginated;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchSolutionComplexRespPaginated extends BaseRespPaginated {

	/**
	 * 
	 */
	private static final long serialVersionUID = -234211301293370465L;
	private SolutionComplexListResp solutions;
	
	public SearchSolutionComplexRespPaginated() {
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
