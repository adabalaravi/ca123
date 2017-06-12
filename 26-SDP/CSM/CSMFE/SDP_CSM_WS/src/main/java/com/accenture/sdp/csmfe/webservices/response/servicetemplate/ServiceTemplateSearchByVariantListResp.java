package com.accenture.sdp.csmfe.webservices.response.servicetemplate;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class ServiceTemplateSearchByVariantListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6338863079831345861L;

	private List<ServiceTemplateSearchByVariantInfoResp> serviceTemplateList;

	@XmlElement(name = "serviceTemplate")
	public List<ServiceTemplateSearchByVariantInfoResp> getServiceTemplateList() {
		return serviceTemplateList;
	}

	public void setServiceTemplateList(List<ServiceTemplateSearchByVariantInfoResp> serviceTemplateList) {
		this.serviceTemplateList = serviceTemplateList;
	}
}
