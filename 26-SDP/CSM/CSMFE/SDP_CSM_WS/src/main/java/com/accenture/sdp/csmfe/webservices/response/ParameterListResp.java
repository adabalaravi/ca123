package com.accenture.sdp.csmfe.webservices.response;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class ParameterListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8676340625239687100L;

	private List<ParameterInfoResp> parameterList;

	@XmlElement(name = "parameter")
	public List<ParameterInfoResp> getParameterList() {
		return parameterList;
	}

	public void setParameterList(List<ParameterInfoResp> parameterList) {
		this.parameterList = parameterList;
	}
}
