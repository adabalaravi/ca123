package com.accenture.sdp.csmfe.webservices.response.operators;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseRespPaginated;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchOperatorComplexRespPaginated extends BaseRespPaginated {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6960399718422260649L;

	private OperatorComplexListResp operators;

	public SearchOperatorComplexRespPaginated() {
		super();
		operators = new OperatorComplexListResp();
	}

	public OperatorComplexListResp getOperators() {
		return operators;
	}

	public void setOperators(OperatorComplexListResp operators) {
		this.operators = operators;
	}
}
