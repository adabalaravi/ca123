package com.accenture.sdp.csmfe.webservices.utils;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csm.dto.requests.SdpShoppingCartItemAddRequestDto;
import com.accenture.sdp.csm.dto.requests.SdpShoppingCartItemRequestDto;
import com.accenture.sdp.csm.dto.responses.SdpShoppingCartItemResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpShoppingCartResponseDto;
import com.accenture.sdp.csmfe.restservices.request.shoppingcart.RemoveShoppingCartItemType;
import com.accenture.sdp.csmfe.restservices.request.shoppingcart.ShoppingCartItemBaseType;
import com.accenture.sdp.csmfe.restservices.response.shoppingcart.GetShoppingCartsByPartyIdResponse.ShoppingCartList;
import com.accenture.sdp.csmfe.restservices.response.shoppingcart.ShoppingCartItemExtendedType;
import com.accenture.sdp.csmfe.restservices.response.shoppingcart.ShoppingCartType;
import com.accenture.sdp.csmfe.restservices.response.shoppingcart.ShoppingCartType.Items;

public class ShoppingCartConverter extends BaseBeanConverter {

	public static SdpShoppingCartItemAddRequestDto convertShoppingCartItem(ShoppingCartItemBaseType p) {
		if (p == null) {
			return null;
		}
		SdpShoppingCartItemAddRequestDto pinfo = new SdpShoppingCartItemAddRequestDto();
		pinfo.setItemDescription(trim(p.getItemDescription()));
		pinfo.setItemId(p.getItemId());
		pinfo.setItemPrice(p.getItemPrice());
		pinfo.setItemType(trim(p.getItemType()));
		pinfo.setQuantity(p.getQuantity());
		return pinfo;
	}

	public static List<SdpShoppingCartItemAddRequestDto> convertShoppingCartItems(List<ShoppingCartItemBaseType> pdtos) {
		if (pdtos == null) {
			return null;
		}
		List<SdpShoppingCartItemAddRequestDto> pinfos = new ArrayList<SdpShoppingCartItemAddRequestDto>();
		for (ShoppingCartItemBaseType p : pdtos) {
			pinfos.add(convertShoppingCartItem(p));
		}
		return pinfos;
	}

	public static SdpShoppingCartItemRequestDto convertRemoveShoppingCartItem(RemoveShoppingCartItemType p) {
		if (p == null) {
			return null;
		}
		SdpShoppingCartItemRequestDto pinfo = new SdpShoppingCartItemRequestDto();
		pinfo.setItemId(p.getItemId());
		pinfo.setItemType(trim(p.getItemType()));
		pinfo.setQuantity(p.getQuantity());
		return pinfo;
	}

	public static List<SdpShoppingCartItemRequestDto> convertRemoveShoppingCartItems(List<RemoveShoppingCartItemType> pdtos) {
		if (pdtos == null) {
			return null;
		}
		List<SdpShoppingCartItemRequestDto> pinfos = new ArrayList<SdpShoppingCartItemRequestDto>();
		for (RemoveShoppingCartItemType p : pdtos) {
			pinfos.add(convertRemoveShoppingCartItem(p));
		}
		return pinfos;
	}

	public static ShoppingCartItemExtendedType convertGetShoppingCartItem(SdpShoppingCartItemResponseDto p) {
		if (p == null) {
			return null;
		}
		ShoppingCartItemExtendedType pinfo = new ShoppingCartItemExtendedType();
		pinfo.setItemId(p.getItemId());
		pinfo.setItemType(p.getItemType());
		pinfo.setItemPrice(p.getItemPrice());
		pinfo.setQuantity(p.getQuantity());
		pinfo.setItemDescription(p.getItemDescription());
		pinfo.setSubTotalPrice(p.getSubTotalPrice());
		return pinfo;
	}

	public static ShoppingCartType convertGetShoppingCart(SdpShoppingCartResponseDto pdtos) {
		if (pdtos == null) {
			return null;
		}

		ShoppingCartType cart = new ShoppingCartType();
		cart.setShoppingCartId(pdtos.getShoppingCartId());
		cart.setStatus(pdtos.getStatus());
		cart.setTotalPrice(pdtos.getTotalPrice());
		cart.setCreatedById(pdtos.getCreatedById());
		cart.setCreatedDate(pdtos.getCreatedDate());
		cart.setUpdatedById(pdtos.getUpdatedById());
		cart.setUpdatedDate(pdtos.getUpdatedDate());
		cart.setChangeStatusById(pdtos.getChangeStatusById());
		cart.setChangeStatusDate(pdtos.getChangeStatusDate());
		Items sublist = new Items();
		for (SdpShoppingCartItemResponseDto p : pdtos.getItems()) {
			sublist.getItem().add(convertGetShoppingCartItem(p));
		}
		cart.setItems(sublist);
		return cart;
	}

	public static ShoppingCartList convertGetShoppingCartItems(List<SdpShoppingCartResponseDto> pdtos) {
		if (pdtos == null) {
			return null;
		}
		ShoppingCartList list = new ShoppingCartList();
		for (SdpShoppingCartResponseDto p : pdtos) {
			list.getShoppingCart().add(convertGetShoppingCart(p));
		}
		return list;
	}

}
