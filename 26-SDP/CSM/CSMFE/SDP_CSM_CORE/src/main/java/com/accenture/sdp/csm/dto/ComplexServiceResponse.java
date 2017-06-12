package com.accenture.sdp.csm.dto;

import java.io.Serializable;

import com.accenture.sdp.csm.dto.responses.ParameterDto;
/**
 * 
 * 
 */

public class ComplexServiceResponse extends DataServiceResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	private Object complexDto;

	
	public Object getComplexObject() {
		return complexDto;
	}

	
	public void setComplexObject(Object complexDto) {
		this.complexDto = complexDto;
	}

	/**
	 * @param resultCode
	 * @param description
	 */
	public ComplexServiceResponse(String resultCode, String description,ParameterDto... parameters) {
		super(resultCode,description,parameters);
	}
	
}