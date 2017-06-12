/**
 * 
 */
package com.accenture.sdp.csm.helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.dto.requests.SdpSecretQuestionRequestDto;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.model.jpa.SdpCredential;
import com.accenture.sdp.csm.model.jpa.SdpSecretQuestion;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.validation.ValidationResult;
import com.accenture.sdp.csm.validation.ValidationUtils;

/**
 * @author patrizio.pontecorvi
 * 
 */
public final class SdpSecretQuestionHelper extends SdpBaseHelper {

	private static SdpSecretQuestionHelper instance;

	private SdpSecretQuestionHelper() {
		super();
	}

	public static SdpSecretQuestionHelper getInstance() {
		if (instance == null) {
			instance = new SdpSecretQuestionHelper();
		}
		return instance;
	}

	private void validateSecretQuestionNumber(List<SdpSecretQuestionRequestDto> secretQuestions) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		long elementNum = Constants.SECRET_QUESTION_NUMBER;
		if (secretQuestions == null || secretQuestions.isEmpty() || secretQuestions.size() != elementNum) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, "secretQuestions");
		}
	}

	public void validateSecretQuestions(List<SdpSecretQuestionRequestDto> secretQuestions) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		log.logDebug("Validating mandatory fields");
		validateSecretQuestionNumber(secretQuestions);
		HashMap<String, Object> parameterMap = new HashMap<String, Object>();
		for (int i = 0; i < secretQuestions.size(); i++) {
			parameterMap.put("secretQuestionDescription " + (i + 1), secretQuestions.get(i).getSecretQuestionDescription());
			parameterMap.put("secretAnswer " + (i + 1), secretQuestions.get(i).getAnswer());
		}
		ValidationResult res = ValidationUtils.validateMandatoryFields(parameterMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateModifySecretQuestions(List<SdpSecretQuestionRequestDto> secretQuestions) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		log.logDebug("Validating mandatory fields");
		validateSecretQuestionNumber(secretQuestions);
		HashMap<String, Object> parameterMap = new HashMap<String, Object>();
		for (int i = 0; i < secretQuestions.size(); i++) {
			parameterMap.put("secretQuestionId " + (i + 1), secretQuestions.get(i).getSecretQuestionId());
			parameterMap.put("secretQuestionDescription " + (i + 1), secretQuestions.get(i).getSecretQuestionDescription());
			parameterMap.put("answer " + (i + 1), secretQuestions.get(i).getAnswer());
		}
		ValidationResult res = ValidationUtils.validateMandatoryFields(parameterMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void createSecretQuestions(SdpCredential newCredential, List<SdpSecretQuestionRequestDto> secretQuestions, String createdBy, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		for (SdpSecretQuestionRequestDto secretQuestion : secretQuestions) {
			createSecretQuestion(newCredential, secretQuestion.getSecretQuestionDescription(), secretQuestion.getAnswer(), createdBy, em);
		}
	}

	private SdpSecretQuestion createSecretQuestion(SdpCredential sdpCredential, String secretQuestion, String answer, String modifyBy, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpSecretQuestion question = new SdpSecretQuestion();
		question.setCreatedById(modifyBy);
		question.setCreatedDate(new Date());
		question.setSdpCredential(sdpCredential);
		question.setSecretAnswer(answer);
		question.setSecretQuestionDescription(secretQuestion);
		sdpCredential.getSdpSecretQuestions().add(question);
		em.persist(question);
		return question;

	}

	private void modifySecretQuestion(SdpSecretQuestion sdpSecretQuestion, SdpCredential sdpCredential, String secretQuestion, String answer,
			String modifyBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		sdpSecretQuestion.setCreatedById(modifyBy);
		sdpSecretQuestion.setCreatedDate(new Date());
		sdpSecretQuestion.setSdpCredential(sdpCredential);
		sdpSecretQuestion.setSecretAnswer(answer);
		sdpSecretQuestion.setSecretQuestionDescription(secretQuestion);
	}

	public void modifySecretQuestions(List<SdpSecretQuestion> oldSecretQuestions, List<SdpSecretQuestionRequestDto> newSecretQuestions,
			SdpCredential modifyCredential, String modifyBy) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		for (SdpSecretQuestionRequestDto secretQuestion : newSecretQuestions) {
			boolean found = false;
			for (SdpSecretQuestion sdpSecretQuestion : oldSecretQuestions) {
				if (secretQuestion.getSecretQuestionId().longValue() == sdpSecretQuestion.getSecretQuestionId().longValue()) {
					found = true;
					modifySecretQuestion(sdpSecretQuestion, modifyCredential, secretQuestion.getSecretQuestionDescription(), secretQuestion.getAnswer(),
							modifyBy);
				}
			}
			if (!found) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, "secretQuestionId", String.valueOf(secretQuestion.getSecretQuestionId()));
			}
		}

	}

}
