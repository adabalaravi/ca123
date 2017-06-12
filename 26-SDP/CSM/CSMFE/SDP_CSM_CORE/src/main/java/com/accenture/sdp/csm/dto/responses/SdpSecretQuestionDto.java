package com.accenture.sdp.csm.dto.responses;

import java.io.Serializable;
import java.util.Date;

public class SdpSecretQuestionDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7689592574932690720L;
	
	private String secretQuestionDescription;
	private String answer;
	private Long  secretQuestionId;
	private String createdById;	
	private Date createdDate;
	private String updatedById;
	private Date updatedDate;

	/**
	 * @return the secretQuestionDescription
	 */
	public String getSecretQuestionDescription() {
		return secretQuestionDescription;
	}
	/**
	 * @param secretQuestionDescription the secretQuestionDescription to set
	 */
	public void setSecretQuestionDescription(String secretQuestionDescription) {
		this.secretQuestionDescription = secretQuestionDescription;
	}
	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}
	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	/**
	 * @return the secretQuestionId
	 */
	public Long getSecretQuestionId() {
		return secretQuestionId;
	}
	/**
	 * @param secretQuestionId the secretQuestionId to set
	 */
	public void setSecretQuestionId(Long secretQuestionId) {
		this.secretQuestionId = secretQuestionId;
	}
	/**
	 * @return the createdById
	 */
	public String getCreatedById() {
		return createdById;
	}
	/**
	 * @param createdById the createdById to set
	 */
	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the updatedById
	 */
	public String getUpdatedById() {
		return updatedById;
	}
	/**
	 * @param updatedById the updatedById to set
	 */
	public void setUpdatedById(String updatedById) {
		this.updatedById = updatedById;
	}
	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}
	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
}
