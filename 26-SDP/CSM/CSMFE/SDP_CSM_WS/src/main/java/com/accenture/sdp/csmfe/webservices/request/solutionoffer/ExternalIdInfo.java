package com.accenture.sdp.csmfe.webservices.request.solutionoffer;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class ExternalIdInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5807306333999245139L;

	private String externalId;
	private String externalPlatformName;

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getExternalPlatformName() {
		return externalPlatformName;
	}

	public void setExternalPlatformName(String externalPlatformName) {
		this.externalPlatformName = externalPlatformName;
	}

}
