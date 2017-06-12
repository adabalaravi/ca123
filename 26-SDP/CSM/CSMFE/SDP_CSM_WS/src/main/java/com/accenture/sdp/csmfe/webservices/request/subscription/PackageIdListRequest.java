package com.accenture.sdp.csmfe.webservices.request.subscription;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class PackageIdListRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -229960666058095139L;

	private List<Long> packageIdList;

	public PackageIdListRequest() {
		packageIdList = new ArrayList<Long>();
	}

	@XmlElement(name = "packageId")
	public List<Long> getPackageIdList() {
		return packageIdList;
	}

	public void setPackageIdList(List<Long> packageIdList) {
		this.packageIdList = packageIdList;
	}

}