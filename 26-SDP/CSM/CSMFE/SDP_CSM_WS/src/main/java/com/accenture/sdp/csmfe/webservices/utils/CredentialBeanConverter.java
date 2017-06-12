package com.accenture.sdp.csmfe.webservices.utils;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csm.dto.requests.SdpSecretQuestionRequestDto;
import com.accenture.sdp.csm.dto.responses.SdpCredentialDto;
import com.accenture.sdp.csmfe.webservices.request.credential.SecretQuestionInfoModifyRequest;
import com.accenture.sdp.csmfe.webservices.request.credential.SecretQuestionInfoRequest;
import com.accenture.sdp.csmfe.webservices.response.credential.CredentialInfoResp;
import com.accenture.sdp.csmfe.webservices.response.credential.SecretQuestionInfoResp;

public class CredentialBeanConverter extends BaseBeanConverter {

	public static List<CredentialInfoResp> convertCredentials(List<SdpCredentialDto> cdtos) {
		if (cdtos == null) {
			return null;
		}
		List<CredentialInfoResp> cinfos = new ArrayList<CredentialInfoResp>();
		for (SdpCredentialDto c : cdtos) {
			CredentialInfoResp cinfo = new CredentialInfoResp();
			convertBaseInfo(c, cinfo);
			cinfo.setUsername(c.getUsername());
			cinfo.setPartyId(c.getPartyId());
			cinfo.setPartyName(c.getPartyName());
			cinfo.setParentPartyId(c.getParentPartyId());
			cinfo.setPartyParentName(c.getParentPartyName());
			cinfo.setStatusId(c.getStatusID());
			cinfo.setStatus(c.getStatusName());
			cinfo.setRoleName(c.getRoleName());
			cinfo.setExternalId(c.getExternalId());
			cinfo.getSecretQuestions().setSecretQuestionList(convertSecretQuestionsResp(c.getSecretQuestions()));
			cinfos.add(cinfo);
		}
		return cinfos;
	}

	public static List<SecretQuestionInfoResp> convertSecretQuestionsResp(List<com.accenture.sdp.csm.dto.responses.SdpSecretQuestionDto> sqdtos) {
		if (sqdtos == null) {
			return null;
		}
		List<SecretQuestionInfoResp> sqinfos = new ArrayList<SecretQuestionInfoResp>();
		for (com.accenture.sdp.csm.dto.responses.SdpSecretQuestionDto sq : sqdtos) {
			SecretQuestionInfoResp sqinfo = new SecretQuestionInfoResp();
			sqinfo.setSecretQuestionId(sq.getSecretQuestionId());
			sqinfo.setSecretQuestionDescription(sq.getSecretQuestionDescription());
			sqinfo.setSecretAnswer(sq.getAnswer());
			sqinfo.setCreatedById(sq.getCreatedById());
			sqinfo.setCreatedDate(sq.getCreatedDate());
			sqinfo.setUpdatedById(sq.getUpdatedById());
			sqinfo.setUpdatedDate(sq.getUpdatedDate());
			sqinfos.add(sqinfo);
		}
		return sqinfos;
	}

	public static List<SdpSecretQuestionRequestDto> convertSecretQuestionsModify(List<SecretQuestionInfoModifyRequest> sqinfos) {
		if (sqinfos == null) {
			return null;
		}
		List<SdpSecretQuestionRequestDto> sqdtos = new ArrayList<SdpSecretQuestionRequestDto>();
		for (SecretQuestionInfoModifyRequest sq : sqinfos) {
			SdpSecretQuestionRequestDto sqdto = new SdpSecretQuestionRequestDto();
			sqdto.setSecretQuestionId(sq.getSecretQuestionId());
			sqdto.setAnswer(trim(sq.getSecretAnswer()));
			sqdto.setSecretQuestionDescription(trim(sq.getSecretQuestionDescription()));
			sqdtos.add(sqdto);
		}
		return sqdtos;
	}

	public static List<SdpSecretQuestionRequestDto> convertSecretQuestions(List<SecretQuestionInfoRequest> sqinfos) {
		if (sqinfos == null) {
			return null;
		}
		List<SdpSecretQuestionRequestDto> sqdtos = new ArrayList<SdpSecretQuestionRequestDto>();
		for (SecretQuestionInfoRequest sq : sqinfos) {
			SdpSecretQuestionRequestDto sqdto = new SdpSecretQuestionRequestDto();
			sqdto.setAnswer(trim(sq.getSecretAnswer()));
			sqdto.setSecretQuestionDescription(trim(sq.getSecretQuestionDescription()));
			sqdtos.add(sqdto);
		}
		return sqdtos;
	}
}
