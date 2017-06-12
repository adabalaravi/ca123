package com.accenture.sdp.csmfe.webservices.response.operators;

import javax.xml.bind.annotation.XmlRootElement;

import com.accenture.sdp.csmfe.webservices.response.BaseInfoResp;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class OperatorInfoResp extends BaseInfoResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	private String firstName;
	private String lastName;
	private Long statusId;
	private String statusName;
	private String email;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
