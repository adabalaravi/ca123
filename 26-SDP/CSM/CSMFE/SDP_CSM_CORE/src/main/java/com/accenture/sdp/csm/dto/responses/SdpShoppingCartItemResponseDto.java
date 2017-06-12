package com.accenture.sdp.csm.dto.responses;

import java.io.Serializable;
import java.math.BigDecimal;

public class SdpShoppingCartItemResponseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long itemId;
	private String itemDescription;
	private String itemType;
	private Long quantity;
	private BigDecimal itemPrice;
	private BigDecimal subTotalPrice;

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
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

	public BigDecimal getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}

	public BigDecimal getSubTotalPrice() {
		return subTotalPrice;
	}

	public void setSubTotalPrice(BigDecimal subTotalPrice) {
		this.subTotalPrice = subTotalPrice;
	}
}
