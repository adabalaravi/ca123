package com.accenture.sdp.csmfe.webservices.request.credential;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SecretQuestionInfoModifyRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -966911553957332153L;

	private Long secretQuestionId;
	private String secretQuestionDescription;
	private String secretAnswer;
	public Long getSecretQuestionId() {
		return secretQuestionId;
	}
	public void setSecretQuestionId(Long secretQuestionId) {
		this.secretQuestionId = secretQuestionId;
	}
	public String getSecretQuestionDescription() {
		return secretQuestionDescription;
	}
	public void setSecretQuestionDescription(String secretQuestionDescription) {
		this.secretQuestionDescription = secretQuestionDescription;
	}
	public String getSecretAnswer() {
		return secretAnswer;
	}
	public void setSecretAnswer(String secretAnswer) {
		this.secretAnswer = secretAnswer;
	}
	
	

}
