package com.accenture.sdp.csmfe.webservices.response.servicetemplate;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class ServiceTemplateSearchAllListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6338863079831345861L;

	private List<ServiceTemplateSearchAllInfoResp> serviceTemplateList;

	@XmlElement(name = "serviceTemplate")
	public List<ServiceTemplateSearchAllInfoResp> getServiceTemplateList() {
		return serviceTemplateList;
	}

	public void setServiceTemplateList(List<ServiceTemplateSearchAllInfoResp> serviceTemplateList) {
		this.serviceTemplateList = serviceTemplateList;
	}
}
