package com.accenture.sdp.devicemetering.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "REF_EVENT_TYPE")
public class RefEventType implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "EVENT_TYPE_ID")
	private long partyTypeId;

	@Column(name = "EVENT_TYPE_NAME")
	private String partyTypeName;

	public long getPartyTypeId() {
		return partyTypeId;
	}

	public void setPartyTypeId(long partyTypeId) {
		this.partyTypeId = partyTypeId;
	}

	public String getPartyTypeName() {
		return partyTypeName;
	}

	public void setPartyTypeName(String partyTypeName) {
		this.partyTypeName = partyTypeName;
	}

}
