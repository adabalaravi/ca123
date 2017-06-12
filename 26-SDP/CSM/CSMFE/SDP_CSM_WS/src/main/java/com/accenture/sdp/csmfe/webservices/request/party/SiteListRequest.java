package com.accenture.sdp.csmfe.webservices.request.party;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SiteListRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2570017618679519704L;

	private List<SiteInfoRequest> siteList;

	public SiteListRequest() {
		siteList = new ArrayList<SiteInfoRequest>();
	}

	@XmlElement(name = "site")
	public List<SiteInfoRequest> getSiteList() {
		return siteList;
	}

	public void setSiteList(List<SiteInfoRequest> siteList) {
		this.siteList = siteList;
	}

}
