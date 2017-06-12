package com.accenture.sdp.csmfe.webservices.response.operators;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class OperatorRightListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<OperatorRightInfoResp> operatorRightsList;

	public OperatorRightListResp() {
		operatorRightsList = new ArrayList<OperatorRightInfoResp>();
	}

	@XmlElement(name = "right")
	public List<OperatorRightInfoResp> getOperatorRightsList() {
		return operatorRightsList;
	}

	public void setOperatorRightsList(List<OperatorRightInfoResp> operatorRightsList) {
		this.operatorRightsList = operatorRightsList;
	}
}