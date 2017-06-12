package com.accenture.sdp.csmfe.webservices.response.servicetemplate;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchServiceTemplateByVariantResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -234211301293370465L;
	private ServiceTemplateSearchByVariantListResp serviceTemplates;
	
	public SearchServiceTemplateByVariantResp() {
		super();
		serviceTemplates = new ServiceTemplateSearchByVariantListResp();
	}

	public ServiceTemplateSearchByVariantListResp getServiceTemplates() {
		return serviceTemplates;
	}

	public void setServiceTemplates(ServiceTemplateSearchByVariantListResp serviceTemplates) {
		this.serviceTemplates = serviceTemplates;
	}
}
