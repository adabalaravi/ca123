package com.accenture.sdp.csmfe.webservices.request.packages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class ModifyPackageListRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1563384141504322901L;

	private List<ModifyPackageInfoRequest> packageList;

	public ModifyPackageListRequest() {
		packageList = new ArrayList<ModifyPackageInfoRequest>();
	}

	@XmlElement(name = "package")
	public List<ModifyPackageInfoRequest> getPackageList() {
		return packageList;
	}

	public void setPackageList(List<ModifyPackageInfoRequest> packageList) {
		this.packageList = packageList;
	}
}
