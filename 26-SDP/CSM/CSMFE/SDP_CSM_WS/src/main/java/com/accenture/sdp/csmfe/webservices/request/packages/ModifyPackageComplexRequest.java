package com.accenture.sdp.csmfe.webservices.request.packages;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class ModifyPackageComplexRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6147409922392198659L;

	private Long solutionOfferId;
	private ModifyPackageListRequest packages;
	
	public ModifyPackageComplexRequest() {
		packages = new ModifyPackageListRequest();
	}

	public Long getSolutionOfferId() {
		return solutionOfferId;
	}

	public void setSolutionOfferId(Long solutionOfferId) {
		this.solutionOfferId = solutionOfferId;
	}

	public ModifyPackageListRequest getPackages() {
		return packages;
	}

	public void setPackages(ModifyPackageListRequest packages) {
		this.packages = packages;
	}
}
