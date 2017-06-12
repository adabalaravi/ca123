package com.accenture.sdp.csm.validation;

import java.util.Arrays;

import com.accenture.sdp.csm.dto.responses.ParameterDto;

public class ValidationResult {

	private boolean valid;
	private ParameterDto[] params;

	public ValidationResult() {
		super();
		this.valid = true;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public ParameterDto[] getParams() {
		return params.clone();
	}

	public void setParams(ParameterDto[] params) {
		if (params != null) {
			this.params = params.clone();
		}
	}

	public void addParam(String invalidParamName, String invalidParamValue) {
		// initialize or enlarge length of array
		if (this.params == null) {
			this.params = new ParameterDto[1];
		} else {
			this.params = Arrays.copyOf(this.params, this.params.length + 1);
		}
		this.params[this.params.length - 1] = new ParameterDto(invalidParamName, invalidParamValue);
	}
}
