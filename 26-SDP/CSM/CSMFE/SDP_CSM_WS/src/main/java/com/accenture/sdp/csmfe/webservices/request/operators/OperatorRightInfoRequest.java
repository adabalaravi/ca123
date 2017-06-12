package com.accenture.sdp.csmfe.webservices.request.operators;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class OperatorRightInfoRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7573440785538673986L;
	
	private Long rightId;

	public Long getRightId() {
		return rightId;
	}

	public void setRightId(Long rightId) {
		this.rightId = rightId;
	}
}
