/**
 * 
 */
package com.accenture.sdp.csm.dto.requests;

/**
 * @author patrizio.pontecorvi
 *
 */
public class SdpSecretQuestionRequestDto {
	
	private String secretQuestionDescription;
	private String answer;
	private Long  secretQuestionId;
	
	
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
	 * @param secretQuestionDescription
	 * @param answer
	 */
	public SdpSecretQuestionRequestDto(String secretQuestionDescription, String answer) {
		super();
		this.secretQuestionDescription = secretQuestionDescription;
		this.answer = answer;
	}
	/**
	 * 
	 */
	public SdpSecretQuestionRequestDto() {
		super();
	}
	
	@Override
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		if (secretQuestionId!=null) {
			buffer.append(String.format(" %s = %s", "secretQuestionId",secretQuestionId));
		}
		buffer.append(String.format(" %s = %s", "secretQuestionDescription",secretQuestionDescription));
		buffer.append(String.format(" %s = %s ", "secretAnswer ",answer));
		return buffer.toString();
	}
	
	
	

}
