package com.accenture.sdp.csmfe.webservices.response.servicetemplate;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchServiceTemplateResp extends BaseResp{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8114610044818805612L;
	
	private ServiceTemplateListResp serviceTemplates;
	
	public SearchServiceTemplateResp() {
		super();
		serviceTemplates = new ServiceTemplateListResp();
	}

	public ServiceTemplateListResp getServiceTemplates()  {
		return serviceTemplates;
	}

	public void setServiceTemplates(ServiceTemplateListResp serviceTemplates) {
		this.serviceTemplates = serviceTemplates;
	}
}
