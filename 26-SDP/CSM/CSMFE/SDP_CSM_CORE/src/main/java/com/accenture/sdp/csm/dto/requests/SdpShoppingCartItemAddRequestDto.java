package com.accenture.sdp.csm.dto.requests;

import java.math.BigDecimal;

public class SdpShoppingCartItemAddRequestDto extends SdpShoppingCartItemRequestDto {

	private String itemDescription;
	private BigDecimal itemPrice;

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public BigDecimal getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}

	@Override
	public String toString() {
		return super.toString()+
				"itemDescription = " + itemDescription + 
				" itemPrice = " + itemPrice.toPlainString() +
				" ";
	}

	
}
