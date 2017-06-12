package com.accenture.sdp.csmfe.webservices.response.operators;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class OperatorRoleComplexListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<OperatorRoleComplexInfoResp> operatorRolesList;

	public OperatorRoleComplexListResp() {
		operatorRolesList = new ArrayList<OperatorRoleComplexInfoResp>();
	}

	@XmlElement(name = "role")
	public List<OperatorRoleComplexInfoResp> getOperatorRolesList() {
		return operatorRolesList;
	}

	public void setOperatorRolesList(List<OperatorRoleComplexInfoResp> operatorRolesList) {
		this.operatorRolesList = operatorRolesList;
	}
}