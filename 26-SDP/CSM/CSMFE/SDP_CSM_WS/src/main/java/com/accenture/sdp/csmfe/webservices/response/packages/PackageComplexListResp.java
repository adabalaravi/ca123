package com.accenture.sdp.csmfe.webservices.response.packages;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class PackageComplexListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 332342805350046230L;

	private List<PackageComplexInfoResp> packageList;

	@XmlElement(name = "package")
	public List<PackageComplexInfoResp> getPackageList() {
		return packageList;
	}

	public void setPackageList(List<PackageComplexInfoResp> packageList) {
		this.packageList = packageList;
	}
}
