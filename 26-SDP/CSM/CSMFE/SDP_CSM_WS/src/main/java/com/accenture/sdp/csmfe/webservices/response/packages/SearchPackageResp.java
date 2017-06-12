package com.accenture.sdp.csmfe.webservices.response.packages;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchPackageResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3549988309380279875L;

	private PackageListResp packages;
	
	public SearchPackageResp() {
		super();
		packages = new PackageListResp();
	}

	public PackageListResp getPackages() {
		return packages;
	}

	public void setPackages(PackageListResp packages) {
		this.packages = packages;
	}
}
