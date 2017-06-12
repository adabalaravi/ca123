package com.accenture.sdp.csmfe.webservices.response.servicetype;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class ServiceTypeListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6701399818077832055L;

	private List<ServiceTypeInfoResp> serviceTypeList;

	@XmlElement(name = "serviceType")
	public List<ServiceTypeInfoResp> getServiceTypeList() {
		return serviceTypeList;
	}

	public void setServiceTypeList(List<ServiceTypeInfoResp> serviceTypeList) {
		this.serviceTypeList = serviceTypeList;
	}
}
