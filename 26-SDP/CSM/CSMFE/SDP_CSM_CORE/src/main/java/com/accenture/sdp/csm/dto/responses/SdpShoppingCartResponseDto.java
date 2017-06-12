package com.accenture.sdp.csm.dto.responses;

import java.math.BigDecimal;
import java.util.List;

public class SdpShoppingCartResponseDto extends SdpBaseResponseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long shoppingCartId;
	private BigDecimal totalPrice;
	private String status;

	private List<SdpShoppingCartItemResponseDto> items;

	public Long getShoppingCartId() {
		return shoppingCartId;
	}

	public void setShoppingCartId(Long shoppingCartId) {
		this.shoppingCartId = shoppingCartId;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<SdpShoppingCartItemResponseDto> getItems() {
		return items;
	}

	public void setItems(List<SdpShoppingCartItemResponseDto> items) {
		this.items = items;
	}

}
