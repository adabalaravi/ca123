package com.accenture.sdp.csm.dto.responses;

import java.io.Serializable;

public class SdpEndpointInfoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3680409043882307366L;

	private String platformName;

	private String serviceName;

	private String operationName;

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

}
