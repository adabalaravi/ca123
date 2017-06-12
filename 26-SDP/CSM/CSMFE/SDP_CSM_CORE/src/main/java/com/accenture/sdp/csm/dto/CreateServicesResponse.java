package com.accenture.sdp.csm.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.accenture.sdp.csm.dto.responses.ParameterDto;
/**
 * 
 * 
 */

public class CreateServicesResponse extends DataServiceResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	private Map<String,Long> entities;

	/**
	 * @return the entityId
	 */
	public Map<String,Long> getEntityId() {
		return entities;
	}

	/**
	 * @param entityId the entityId to set
	 */
	public void addEntityId(String entityName,long entityId) {
		entities.put(entityName,entityId);
	}

	/**
	 * @param description
	 * @param resultCode

	 */
	public CreateServicesResponse(String resultCode, String description,ParameterDto... parameters) {
		super(resultCode,description,parameters);
		entities=new HashMap<String, Long>();

	}
	
}