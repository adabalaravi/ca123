package com.accenture.sdp.csm.dto.responses;

import java.io.Serializable;

public class ParameterDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7779829474570610108L;

	private String name;
	private String value;

	public ParameterDto() {
		super();
	}

	public ParameterDto(String name, Object value) {
		super();
		this.name = name;
		if (value != null) {
			this.value = String.valueOf(value);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "name = " + name + " value = " + (value != null ? value : "");
	}

}
