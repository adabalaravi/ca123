package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the sdp_shopping_cart_item database table.
 * 
 */
@Entity
@Table(name = "sdp_shopping_cart_item")
public class SdpShoppingCartItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SdpShoppingCartItemPK id;

	@Column(name = "CREATED_BY_ID")
	private String createdById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "ITEM_DESCRIPTION")
	private String itemDescription;

	@Column(name = "ITEM_PRICE")
	private BigDecimal itemPrice;

	@Column(name = "ITEM_SUBTOTAL")
	private BigDecimal itemSubtotal;

	private Long quantity;

	@Column(name = "UPDATED_BY_ID")
	private String updatedById;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	// bi-directional many-to-one association to RefItemType
	@ManyToOne
	@JoinColumn(name = "ITEM_TYPE_ID", insertable = false, updatable = false)
	private RefItemType refItemType;

	// bi-directional many-to-one association to SdpShoppingCart
	@ManyToOne
	@JoinColumn(name = "SHOPPING_CART_ID", insertable = false, updatable = false)
	private SdpShoppingCart sdpShoppingCart;

	public SdpShoppingCartItem() {
	}

	public SdpShoppingCartItemPK getId() {
		return id;
	}

	public void setId(SdpShoppingCartItemPK id) {
		this.id = id;
	}

	public String getCreatedById() {
		return this.createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getItemDescription() {
		return this.itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public BigDecimal getItemPrice() {
		return this.itemPrice;
	}

	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}

	public BigDecimal getItemSubtotal() {
		return this.itemSubtotal;
	}

	public void setItemSubtotal(BigDecimal itemSubtotal) {
		this.itemSubtotal = itemSubtotal;
	}

	public Long getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getUpdatedById() {
		return this.updatedById;
	}

	public void setUpdatedById(String updatedById) {
		this.updatedById = updatedById;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public RefItemType getRefItemType() {
		return this.refItemType;
	}

	public void setRefItemType(RefItemType refItemType) {
		this.refItemType = refItemType;
	}

	public SdpShoppingCart getSdpShoppingCart() {
		return this.sdpShoppingCart;
	}

	public void setSdpShoppingCart(SdpShoppingCart sdpShoppingCart) {
		this.sdpShoppingCart = sdpShoppingCart;
	}

}