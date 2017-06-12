package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the ref_item_type database table.
 * 
 */
@Entity
@Table(name = "ref_item_type")
@NamedQueries({ @NamedQuery(name = RefItemType.QUERY_RETRIEVE_BY_NAME, query = "select r from RefItemType r where r.itemTypeName=:itemTypeName") })
public class RefItemType implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String QUERY_RETRIEVE_BY_NAME = "selectShoppingCartItemTypeByName";
	
	public static final String PARAM_TYPE_NAME = "itemTypeName";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ITEM_TYPE_ID")
	private Long itemTypeId;

	@Column(name = "ITEM_TYPE_NAME")
	private String itemTypeName;

	// bi-directional many-to-one association to SdpShoppingCartItem
	@OneToMany(mappedBy = "refItemType")
	private List<SdpShoppingCartItem> sdpShoppingCartItems;

	public RefItemType() {
		sdpShoppingCartItems = new ArrayList<SdpShoppingCartItem>();
	}

	public Long getItemTypeId() {
		return this.itemTypeId;
	}

	public void setItemTypeId(Long itemTypeId) {
		this.itemTypeId = itemTypeId;
	}

	public String getItemTypeName() {
		return this.itemTypeName;
	}

	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
	}

	public List<SdpShoppingCartItem> getSdpShoppingCartItems() {
		return this.sdpShoppingCartItems;
	}

	public void setSdpShoppingCartItems(List<SdpShoppingCartItem> sdpShoppingCartItems) {
		this.sdpShoppingCartItems = sdpShoppingCartItems;
	}

}