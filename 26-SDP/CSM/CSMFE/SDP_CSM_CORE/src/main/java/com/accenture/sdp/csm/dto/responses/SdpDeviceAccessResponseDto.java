package com.accenture.sdp.csm.dto.responses;

import java.io.Serializable;

public class SdpDeviceAccessResponseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -239897556896906111L;

	private String id;
	private String itemType;
	private String reason;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
