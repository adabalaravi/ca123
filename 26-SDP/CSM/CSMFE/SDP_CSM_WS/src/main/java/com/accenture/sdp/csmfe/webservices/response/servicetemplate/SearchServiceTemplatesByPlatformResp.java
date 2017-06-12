package com.accenture.sdp.csmfe.webservices.response.servicetemplate;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseRespPaginated;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchServiceTemplatesByPlatformResp extends BaseRespPaginated {

	/**
	 * 
	 */
	private static final long serialVersionUID = -234211301293370465L;
	private ServiceTemplateSearchByPlatformListResp serviceTemplates;
	
	public SearchServiceTemplatesByPlatformResp() {
		super();
		serviceTemplates = new ServiceTemplateSearchByPlatformListResp();
	}

	public ServiceTemplateSearchByPlatformListResp getServiceTemplates() {
		return serviceTemplates;
	}

	public void setServiceTemplates(ServiceTemplateSearchByPlatformListResp serviceTemplates) {
		this.serviceTemplates = serviceTemplates;
	}
}
