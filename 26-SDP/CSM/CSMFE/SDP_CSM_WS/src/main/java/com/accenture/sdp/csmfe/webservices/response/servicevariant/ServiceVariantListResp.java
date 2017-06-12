package com.accenture.sdp.csmfe.webservices.response.servicevariant;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class ServiceVariantListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<ServiceVariantInfoResp> servicevariantList;

	@XmlElement(name = "servicevariant")
	public List<ServiceVariantInfoResp> getServicevariantList() {
		return servicevariantList;
	}

	public void setServicevariantList(List<ServiceVariantInfoResp> servicevariantList) {
		this.servicevariantList = servicevariantList;
	}
}
