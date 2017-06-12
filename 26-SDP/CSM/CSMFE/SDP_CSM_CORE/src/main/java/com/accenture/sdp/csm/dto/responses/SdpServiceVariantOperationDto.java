package com.accenture.sdp.csm.dto.responses;

public class SdpServiceVariantOperationDto extends SdpBaseResponseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4137450555474510334L;

	private Long serviceVariantId;

	private String methodName;

	private String inputParameters;

	private String serviceVariantName;

	private Long serviceTemplateId;

	private String serviceTemplateName;

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

	public Long getServiceTemplateId() {
		return serviceTemplateId;
	}

	public void setServiceTemplateId(Long serviceTemplateId) {
		this.serviceTemplateId = serviceTemplateId;
	}

	public String getServiceTemplateName() {
		return serviceTemplateName;
	}

	public void setServiceTemplateName(String serviceTemplateName) {
		this.serviceTemplateName = serviceTemplateName;
	}

	public String getServiceVariantName() {
		return serviceVariantName;
	}

	public void setServiceVariantName(String serviceVariantName) {
		this.serviceVariantName = serviceVariantName;
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

	@Override
	public String toString() {
		return "SdpServiceVariantOperationDto [serviceVariantId="
				+ serviceVariantId + " methodName=" + methodName
				+ " inputParameters=" + inputParameters
				+ " serviceVariantName=" + serviceVariantName
				+ " serviceTemplateId=" + serviceTemplateId
				+ " serviceTemplateName=" + serviceTemplateName + " inputXslt="
				+ inputXslt + " outputXslt=" + outputXslt + " uddiKey="
				+ uddiKey + "]";
	}

}
