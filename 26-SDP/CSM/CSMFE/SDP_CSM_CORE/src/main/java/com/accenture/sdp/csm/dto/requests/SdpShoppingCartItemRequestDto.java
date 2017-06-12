package com.accenture.sdp.csm.dto.requests;


public class SdpShoppingCartItemRequestDto {

	private Long itemId;
	private String itemType;
	private Long quantity;

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return " itemId = " + itemId +
				" itemType = " + itemType +
				" quantity = " + quantity +
				" ";
	}

	
}
