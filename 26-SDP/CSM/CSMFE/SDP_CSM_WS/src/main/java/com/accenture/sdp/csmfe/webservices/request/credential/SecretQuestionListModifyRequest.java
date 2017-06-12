package com.accenture.sdp.csmfe.webservices.request.credential;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SecretQuestionListModifyRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8981002162624031006L;

	private List<SecretQuestionInfoModifyRequest> secretQuestionList;

	public SecretQuestionListModifyRequest() {
		secretQuestionList = new ArrayList<SecretQuestionInfoModifyRequest>();
	}

	@XmlElement(name = "secretQuestion")
	public List<SecretQuestionInfoModifyRequest> getSecretQuestionList() {
		return secretQuestionList;
	}

	public void setSecretQuestionList(List<SecretQuestionInfoModifyRequest> secretQuestionList) {
		this.secretQuestionList = secretQuestionList;
	}

}
