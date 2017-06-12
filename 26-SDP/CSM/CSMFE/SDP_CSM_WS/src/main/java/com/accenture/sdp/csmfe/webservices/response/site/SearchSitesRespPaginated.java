package com.accenture.sdp.csmfe.webservices.response.site;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseRespPaginated;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchSitesRespPaginated extends BaseRespPaginated {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8166649507882808272L;
	
	private SitesListResp sites;
	
	public SearchSitesRespPaginated() {
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
