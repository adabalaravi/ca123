package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the sdp_shopping_cart database table.
 * 
 */
@Entity
@Table(name="sdp_shopping_cart")
public class SdpShoppingCart implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="SHOPPING_CART_ID")
	private Long shoppingCartId;

	@Column(name="CHG_STATUS_BY_ID")
	private String chgStatusById;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="CHG_STATUS_DATE")
	private Date chgStatusDate;

	@Column(name="CREATED_BY_ID")
	private String createdById;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE")
	private Date createdDate;

	// bi-directional many-to-one association to SdpParty
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARTY_ID")
	private SdpParty sdpParty;

	@Column(name="STATUS_ID")
	private Long statusId;

	@Column(name="UPDATED_BY_ID")
	private String updatedById;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="UPDATED_DATE")
	private Date updatedDate;

	//bi-directional many-to-one association to SdpShoppingCartItem
	@OneToMany(mappedBy="sdpShoppingCart", cascade = CascadeType.REMOVE)
	private List<SdpShoppingCartItem> sdpShoppingCartItems;

    public SdpShoppingCart() {
    	sdpShoppingCartItems = new ArrayList<SdpShoppingCartItem>();
    }

	public Long getShoppingCartId() {
		return this.shoppingCartId;
	}

	public void setShoppingCartId(Long shoppingCartId) {
		this.shoppingCartId = shoppingCartId;
	}

	public String getChgStatusById() {
		return this.chgStatusById;
	}

	public void setChgStatusById(String chgStatusById) {
		this.chgStatusById = chgStatusById;
	}

	public Date getChgStatusDate() {
		return this.chgStatusDate;
	}

	public void setChgStatusDate(Date chgStatusDate) {
		this.chgStatusDate = chgStatusDate;
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

	public SdpParty getSdpParty() {
		return sdpParty;
	}

	public void setSdpParty(SdpParty sdpParty) {
		this.sdpParty = sdpParty;
	}

	public Long getStatusId() {
		return this.statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
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

	public List<SdpShoppingCartItem> getSdpShoppingCartItems() {
		return this.sdpShoppingCartItems;
	}

	public void setSdpShoppingCartItems(List<SdpShoppingCartItem> sdpShoppingCartItems) {
		this.sdpShoppingCartItems = sdpShoppingCartItems;
	}
	
}