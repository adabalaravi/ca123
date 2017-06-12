package com.accenture.sdp.csmfe.webservices.request.credential;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class SecretQuestionInfoRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6993034186660521164L;

	private String secretQuestionDescription; 
	private String secretAnswer;
	
	
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