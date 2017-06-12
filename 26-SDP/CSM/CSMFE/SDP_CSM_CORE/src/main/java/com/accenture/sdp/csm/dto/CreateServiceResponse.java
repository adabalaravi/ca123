package com.accenture.sdp.csm.dto;

import java.io.Serializable;

import com.accenture.sdp.csm.dto.responses.ParameterDto;

public class CreateServiceResponse extends DataServiceResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long entityId;

	/**
	 * @return the entityId
	 */
	public Long getEntityId() {
		return entityId;
	}

	/**
	 * @param entityId
	 *            the entityId to set
	 */
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	/**
	 * @param resultCode
	 * @param description
	 */
	public CreateServiceResponse(String resultCode, String description, ParameterDto... parameters) {
		super(resultCode, description, parameters);
	}

}