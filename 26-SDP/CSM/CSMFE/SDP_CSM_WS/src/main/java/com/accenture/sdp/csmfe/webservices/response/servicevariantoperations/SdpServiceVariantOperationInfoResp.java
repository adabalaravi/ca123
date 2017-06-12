package com.accenture.sdp.csmfe.webservices.response.servicevariantoperations;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseInfoResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SdpServiceVariantOperationInfoResp extends BaseInfoResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long serviceVariantId;

	private String methodName;

	private Long serviceTemplateId;

	private String serviceVariantName;

	private String serviceTemplateName;

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

	public Long getServiceTemplateId() {
		return serviceTemplateId;
	}

	public void setServiceTemplateId(Long serviceTemplateId) {
		this.serviceTemplateId = serviceTemplateId;
	}

	public String getServiceVariantName() {
		return serviceVariantName;
	}

	public void setServiceVariantName(String serviceVariantName) {
		this.serviceVariantName = serviceVariantName;
	}

	public String getServiceTemplateName() {
		return serviceTemplateName;
	}

	public void setServiceTemplateName(String serviceTemplateName) {
		this.serviceTemplateName = serviceTemplateName;
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
