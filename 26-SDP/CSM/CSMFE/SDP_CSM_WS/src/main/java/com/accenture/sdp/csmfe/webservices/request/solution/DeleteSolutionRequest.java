package com.accenture.sdp.csmfe.webservices.request.solution;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class DeleteSolutionRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long solutionId;
	public Long getSolutionId() {
		return solutionId;
	}
	public void setSolutionId(Long solutionId) {
		this.solutionId = solutionId;
	}
}
