package com.accenture.sdp.csmfe.webservices.request.credential;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SecretQuestionListRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5357635136550832613L;

	private List<SecretQuestionInfoRequest> secretQuestionList;

	public SecretQuestionListRequest() {
		secretQuestionList = new ArrayList<SecretQuestionInfoRequest>();
	}

	@XmlElement(name = "secretQuestion")
	public List<SecretQuestionInfoRequest> getSecretQuestionList() {
		return secretQuestionList;
	}

	public void setSecretQuestionList(List<SecretQuestionInfoRequest> secretQuestionList) {
		this.secretQuestionList = secretQuestionList;
	}
}
