package com.accenture.sdp.csmfe.webservices.response.solutiontype;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchSolutionTypesResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7882796710688563379L;

	private SolutionTypeListResp solutionTypeList;
	
	public SearchSolutionTypesResp() {
		super();
		solutionTypeList = new SolutionTypeListResp();
	}

	public SolutionTypeListResp getSolutionTypeList() {
		return solutionTypeList;
	}

	public void setSolutionTypeList(SolutionTypeListResp solutionTypeList) {
		this.solutionTypeList = solutionTypeList;
	}
}
