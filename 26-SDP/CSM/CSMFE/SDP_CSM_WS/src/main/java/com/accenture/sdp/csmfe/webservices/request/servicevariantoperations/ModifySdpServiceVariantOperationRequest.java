package com.accenture.sdp.csmfe.webservices.request.servicevariantoperations;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class ModifySdpServiceVariantOperationRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	private Long serviceVariantId;
	private String methodName;
	private String inputParameters;
	private String inputXslt;
	private String outputXslt;
	private String uddiKey;
	private String operationType;
	public Long getServiceVariantId() {
		return serviceVariantId;
	}
	public void setServiceVariantId(Long serviceVariantId) {
		this.serviceVariantId = serviceVariantId;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getInputParameters() {
		return inputParameters;
	}
	public void setInputParameters(String inputParameters) {
		this.inputParameters = inputParameters;
	}
	public String getInputXslt() {
		return inputXslt;
	}
	public void setInputXslt(String inputXslt) {
		this.inputXslt = inputXslt;
	}
	public String getOutputXslt() {
		return outputXslt;
	}
	public void setOutputXslt(String outputXslt) {
		this.outputXslt = outputXslt;
	}
	public String getUddiKey() {
		return uddiKey;
	}
	public void setUddiKey(String uddiKey) {
		this.uddiKey = uddiKey;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	
	
}
