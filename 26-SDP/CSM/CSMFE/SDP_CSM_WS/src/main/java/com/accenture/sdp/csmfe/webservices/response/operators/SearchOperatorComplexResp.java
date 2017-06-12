package com.accenture.sdp.csmfe.webservices.response.operators;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchOperatorComplexResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6960399718422260649L;

	private OperatorComplexListResp operators;

	public SearchOperatorComplexResp() {
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
