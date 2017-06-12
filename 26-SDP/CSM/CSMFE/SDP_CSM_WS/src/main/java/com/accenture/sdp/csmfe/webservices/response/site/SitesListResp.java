package com.accenture.sdp.csmfe.webservices.response.site;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SitesListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4579719722138368143L;

	private List<SiteInfoResp> siteList;

	@XmlElement(name = "site")
	public List<SiteInfoResp> getSiteList() {
		return siteList;
	}

	public void setSiteList(List<SiteInfoResp> siteList) {
		this.siteList = siteList;
	}
}
