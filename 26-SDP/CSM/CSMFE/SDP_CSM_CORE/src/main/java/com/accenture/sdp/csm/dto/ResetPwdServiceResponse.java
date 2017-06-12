package com.accenture.sdp.csm.dto;

import java.io.Serializable;

import com.accenture.sdp.csm.dto.responses.ParameterDto;


/**
 * 
 * 
 */

public class ResetPwdServiceResponse extends DataServiceResponse implements
		Serializable {
	private static final long serialVersionUID = 1L;

	private String resetPassword;


	public String getResetPassword() {
		return resetPassword;
	}

	/**
	 * @param resetPassword
	 *            the password to set
	 */
	public void setResetPassword(String resetPassword) {
		this.resetPassword = resetPassword;
	}

	/**
	 * @param resultCode
	 * @param description
	 */
	public ResetPwdServiceResponse(String resultCode, String description,ParameterDto... parameters) {
		super(resultCode, description,parameters);
	}

}