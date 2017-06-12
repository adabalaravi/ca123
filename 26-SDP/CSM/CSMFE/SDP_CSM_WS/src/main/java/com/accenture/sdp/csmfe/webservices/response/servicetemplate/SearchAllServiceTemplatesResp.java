package com.accenture.sdp.csmfe.webservices.response.servicetemplate;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseRespPaginated;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchAllServiceTemplatesResp extends BaseRespPaginated {

	/**
	 * 
	 */
	private static final long serialVersionUID = -234211301293370465L;
	private ServiceTemplateSearchAllListResp serviceTemplates;
	
	public SearchAllServiceTemplatesResp() {
		super();
		serviceTemplates = new ServiceTemplateSearchAllListResp();
	}

	public ServiceTemplateSearchAllListResp getServiceTemplates() {
		return serviceTemplates;
	}

	public void setServiceTemplates(ServiceTemplateSearchAllListResp serviceTemplates) {
		this.serviceTemplates = serviceTemplates;
	}
}
