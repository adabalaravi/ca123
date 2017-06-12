package com.accenture.sdp.csm.dto.requests;

public class SdpDeviceAccessOperationRequestDto {

	private String id;
	private String itemType;
	private String opType;

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

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	@Override
	public String toString() {
		return " id = " + id + " itemType = " + itemType + " opType = " + opType + " ";
	}
}
