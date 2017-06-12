package com.accenture.sdp.csmfe.webservices.response.packages;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class PackageListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8892097401717551999L;

	private List<PackageInfoResp> packageList;

	@XmlElement(name = "package")
	public List<PackageInfoResp> getPackageList() {
		return packageList;
	}

	public void setPackageList(List<PackageInfoResp> packageList) {
		this.packageList = packageList;
	}
}
