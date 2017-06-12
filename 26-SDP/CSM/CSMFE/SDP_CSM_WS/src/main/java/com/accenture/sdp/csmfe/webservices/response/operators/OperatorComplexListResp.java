package com.accenture.sdp.csmfe.webservices.response.operators;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class OperatorComplexListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<OperatorComplexInfoResp> operatorsList;

	public OperatorComplexListResp() {
		operatorsList = new ArrayList<OperatorComplexInfoResp>();
	}

	@XmlElement(name = "operator")
	public List<OperatorComplexInfoResp> getOperatorsList() {
		return operatorsList;
	}

	public void setOperatorsList(List<OperatorComplexInfoResp> operatorsList) {
		this.operatorsList = operatorsList;
	}
}