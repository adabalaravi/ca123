package com.accenture.sdp.csmfe.restservices.response.shoppingcart;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddItemsToShoppingCartResponse", propOrder = {
    "shoppingCartId"
})
public class AddItemsToShoppingCartResponse  extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long shoppingCartId;

	public Long getShoppingCartId() {
		return shoppingCartId;
	}

	public void setShoppingCartId(Long shoppingCartId) {
		this.shoppingCartId = shoppingCartId;
	}
	

}
