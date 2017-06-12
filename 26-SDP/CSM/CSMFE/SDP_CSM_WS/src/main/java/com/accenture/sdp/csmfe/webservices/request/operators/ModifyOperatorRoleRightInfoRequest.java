package com.accenture.sdp.csmfe.webservices.request.operators;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class ModifyOperatorRoleRightInfoRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2087357607282066868L;

	private Long rightId;
	private String operation;
	public Long getRightId() {
		return rightId;
	}
	public void setRightId(Long rightId) {
		this.rightId = rightId;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	
}
