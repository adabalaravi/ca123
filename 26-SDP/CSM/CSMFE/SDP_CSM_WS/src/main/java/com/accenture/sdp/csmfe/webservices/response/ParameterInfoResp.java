package com.accenture.sdp.csmfe.webservices.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class ParameterInfoResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3591910401422505317L;

	private String name;
	private String value;

	@XmlAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
