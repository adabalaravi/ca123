package com.accenture.sdp.csmfe.webservices.request.solutionoffer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class ExternalIdList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1846215844187017662L;

	private List<ExternalIdInfo> externalIdList;

	public ExternalIdList() {
		externalIdList = new ArrayList<ExternalIdInfo>();
	}

	@XmlElement(name = "externalId")
	public List<ExternalIdInfo> getExternalIdList() {
		return externalIdList;
	}

	public void setExternalIdList(List<ExternalIdInfo> externalIdList) {
		this.externalIdList = externalIdList;
	}

}
