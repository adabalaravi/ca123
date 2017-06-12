package com.accenture.sdp.csmfe.webservices.response.platform;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class PlatformByServiceVariantInfoResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1056090919399094676L;

	private PlatformInfoResp platform;
	private ServiceTemplateInfoPlatformResp serviceTemplate;
	private ServiceVariantInfoPlatformResp serviceVariant;
	
	public PlatformInfoResp getPlatform() {
		return platform;
	}
	public void setPlatform(PlatformInfoResp platform) {
		this.platform = platform;
	}
	public ServiceTemplateInfoPlatformResp getServiceTemplate() {
		return serviceTemplate;
	}
	public void setServiceTemplate(ServiceTemplateInfoPlatformResp serviceTemplate) {
		this.serviceTemplate = serviceTemplate;
	}
	public ServiceVariantInfoPlatformResp getServiceVariant() {
		return serviceVariant;
	}
	public void setServiceVariant(ServiceVariantInfoPlatformResp serviceVariant) {
		this.serviceVariant = serviceVariant;
	}
	
	
}
