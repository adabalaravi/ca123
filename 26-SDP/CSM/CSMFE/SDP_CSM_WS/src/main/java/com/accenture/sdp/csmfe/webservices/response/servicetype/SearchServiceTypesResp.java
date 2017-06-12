package com.accenture.sdp.csmfe.webservices.response.servicetype;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchServiceTypesResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7882796710688563379L;

	private ServiceTypeListResp serviceTypeList;
	
	public SearchServiceTypesResp() {
		super();
		serviceTypeList = new ServiceTypeListResp();
	}

	public ServiceTypeListResp getServiceTypeList() {
		return serviceTypeList;
	}

	public void setServiceTypeList(ServiceTypeListResp serviceTypeList) {
		this.serviceTypeList = serviceTypeList;
	}
}
