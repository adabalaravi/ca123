package com.accenture.sdp.csmfe.webservices.response.credential;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SecretQuestionListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8074542713338042324L;

	private List<SecretQuestionInfoResp> secretQuestionList;

	@XmlElement(name = "secretQuestion")
	public List<SecretQuestionInfoResp> getSecretQuestionList() {
		return secretQuestionList;
	}

	public void setSecretQuestionList(List<SecretQuestionInfoResp> secretQuestionList) {
		this.secretQuestionList = secretQuestionList;
	}
}
