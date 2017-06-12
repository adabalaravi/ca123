package com.accenture.sdp.csmfe.webservices.response.credential;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
public class SecretQuestionInfoResp implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7998069130878066502L;
	
	private Long secretQuestionId;
	private String secretQuestionDescription; 
	private String secretAnswer;
	private String createdById;
	private Date createdDate;
	private String updatedById;
	private Date updatedDate;
	
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
	public String getCreatedById() {
		return createdById;
	}
	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getUpdatedById() {
		return updatedById;
	}
	public void setUpdatedById(String updatedById) {
		this.updatedById = updatedById;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	
}
