package com.accenture.sdp.csmfe.webservices.response.credential;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class AlternativeUsernameListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -602400517370783243L;

	private List<String> usernameList;

	public AlternativeUsernameListResp() {
		usernameList = new ArrayList<String>();
	}

	@XmlElement(name = "username")
	public List<String> getUsernameList() {
		return usernameList;
	}

	public void setUsernameList(List<String> usernameList) {
		this.usernameList = usernameList;
	}
}
