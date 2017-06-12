package com.accenture.sdp.csm.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.accenture.sdp.csm.dto.responses.ParameterDto;

public class DataServiceResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	private String description;
	private String resultCode;
	private List<ParameterDto> parameters;

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the resultCode
	 */
	public String getResultCode() {
		return resultCode;
	}

	/**
	 * @param resultCode
	 *            the resultCode to set
	 */
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	/**
	 * @param description
	 * @param resultCode
	 */
	public DataServiceResponse(String resultCode, String description, ParameterDto... parameters) {
		this.description = description;
		this.resultCode = resultCode;
		if (parameters != null) {
			this.parameters = Arrays.asList(parameters);
		}
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Response code: ");
		buffer.append(resultCode);
		buffer.append(" - Response description: ");
		buffer.append(description);
		if (parameters != null && !parameters.isEmpty()) {
			buffer.append(" - Parameters: ");
			Iterator<ParameterDto> i = parameters.iterator();
			if (i.hasNext()) {
				buffer.append(i.next().toString());
			}
			while (i.hasNext()) {
				buffer.append(" , ");
				buffer.append(i.next().toString());
			}
		}
		return buffer.toString();
	}

	/**
	 * @return the parameters
	 */
	public List<ParameterDto> getParameters() {
		return parameters;
	}

	/**
	 * @param parameters
	 *            the parameters to set
	 */
	public void setParameters(List<ParameterDto> parameters) {
		this.parameters = parameters;
	}

}