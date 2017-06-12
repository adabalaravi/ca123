package com.accenture.sdp.csmfe.webservices.response.solutiontype;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SolutionTypeListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6701399818077832055L;

	private List<SolutionTypeInfoResp> solutionTypeList;

	@XmlElement(name = "solutionType")
	public List<SolutionTypeInfoResp> getSolutionTypeList() {
		return solutionTypeList;
	}

	public void setSolutionTypeList(List<SolutionTypeInfoResp> solutionTypeList) {
		this.solutionTypeList = solutionTypeList;
	}
}
