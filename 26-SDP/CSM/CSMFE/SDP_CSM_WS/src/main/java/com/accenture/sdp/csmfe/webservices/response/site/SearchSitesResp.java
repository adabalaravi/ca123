package com.accenture.sdp.csmfe.webservices.response.site;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchSitesResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5949472570123010006L;

	private SitesListResp sites;
	
	public SearchSitesResp() {
		super();
		sites = new SitesListResp();
	}

	public SitesListResp getSites() {
		return sites;
	}

	public void setSites(SitesListResp sites) {
		this.sites = sites;
	}
}
