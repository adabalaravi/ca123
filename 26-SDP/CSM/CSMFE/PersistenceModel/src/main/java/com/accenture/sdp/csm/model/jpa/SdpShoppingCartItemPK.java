package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the sdp_shopping_cart_item database table.
 * 
 */
@Embeddable
public class SdpShoppingCartItemPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "ITEM_ID")
	private Long itemId;

	@Column(name = "SHOPPING_CART_ID")
	private Long shoppingCartId;

	@Column(name = "ITEM_TYPE_ID")
	private Long itemTypeId;

	public SdpShoppingCartItemPK() {
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getShoppingCartId() {
		return shoppingCartId;
	}

	public void setShoppingCartId(Long shoppingCartId) {
		this.shoppingCartId = shoppingCartId;
	}

	public Long getItemTypeId() {
		return itemTypeId;
	}

	public void setItemTypeId(Long itemTypeId) {
		this.itemTypeId = itemTypeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
		result = prime * result + ((itemTypeId == null) ? 0 : itemTypeId.hashCode());
		result = prime * result + ((shoppingCartId == null) ? 0 : shoppingCartId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SdpShoppingCartItemPK other = (SdpShoppingCartItemPK) obj;
		if (itemId == null) {
			if (other.itemId != null) {
				return false;
			}
		} else if (!itemId.equals(other.itemId)) {
			return false;
		}
		if (itemTypeId == null) {
			if (other.itemTypeId != null) {
				return false;
			}
		} else if (!itemTypeId.equals(other.itemTypeId)) {
			return false;
		}
		if (shoppingCartId == null) {
			if (other.shoppingCartId != null) {
				return false;
			}
		} else if (!shoppingCartId.equals(other.shoppingCartId)) {
			return false;
		}
		return true;
	}

}