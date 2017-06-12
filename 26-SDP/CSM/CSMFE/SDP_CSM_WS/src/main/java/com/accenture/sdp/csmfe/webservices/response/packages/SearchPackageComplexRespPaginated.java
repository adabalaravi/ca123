package com.accenture.sdp.csmfe.webservices.response.packages;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseRespPaginated;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SearchPackageComplexRespPaginated extends BaseRespPaginated {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3549988309380279875L;

	private PackageComplexListResp packages;

	public SearchPackageComplexRespPaginated() {
		super();
		packages = new PackageComplexListResp();
	}

	public PackageComplexListResp getPackages() {
		return packages;
	}

	public void setPackages(PackageComplexListResp packages) {
		this.packages = packages;
	}
}
