package com.accenture.sdp.csmfe.webservices.request.servicevariantoperations;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class SearchSdpServiceVariantOperationByNameRequest implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1836637442740074178L;

	private String serviceVariantName;
	private String methodName;
	
	public String getServiceVariantName() {
		return serviceVariantName;
	}
	public void setServiceVariantName(String serviceVariantName) {
		this.serviceVariantName = serviceVariantName;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	
}
