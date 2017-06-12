package com.accenture.sdp.csm.helpers;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.database.NamedQueryHelper;
import com.accenture.sdp.csm.dto.requests.SdpShoppingCartItemAddRequestDto;
import com.accenture.sdp.csm.dto.requests.SdpShoppingCartItemRequestDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.model.jpa.RefItemType;
import com.accenture.sdp.csm.model.jpa.SdpParty;
import com.accenture.sdp.csm.model.jpa.SdpShoppingCart;
import com.accenture.sdp.csm.model.jpa.SdpShoppingCartItem;
import com.accenture.sdp.csm.model.jpa.SdpShoppingCartItemPK;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.validation.ValidationResult;
import com.accenture.sdp.csm.validation.ValidationUtils;

public final class SdpShoppingCartHelper extends SdpBaseHelper {

	private static SdpShoppingCartHelper instance;

	private SdpShoppingCartHelper() {
		super();
	}

	public static SdpShoppingCartHelper getInstance() {
		if (instance == null) {
			instance = new SdpShoppingCartHelper();
		}
		return instance;
	}

	public void validateShoppingCartItem(Long itemId, String itemType, Long quantity) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("itemId", itemId);
		validationMap.put(SHOPPING_CART_ITEM_TYPE_NAME, itemType);
		validationMap.put("quantity", quantity);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		// verifica positivo senza 0 : non faccio validatore specifico
		if (quantity <= 0) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, "quantity", quantity);
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateShoppingCartItems(List<SdpShoppingCartItemRequestDto> items) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		if (items == null || items.isEmpty()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, "items");
		}
		for (SdpShoppingCartItemRequestDto dto : items) {
			validateShoppingCartItem(dto.getItemId(), dto.getItemType(), dto.getQuantity());
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateShoppingCartAddItem(String itemDescription, BigDecimal itemPrice) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put("itemDescription", itemDescription);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		// verifica positivo
		HashMap<String, BigDecimal> positives = new HashMap<String, BigDecimal>();
		positives.put("itemPrice", itemPrice);
		res = ValidationUtils.validatePositiveDecimalFields(positives);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void validateShoppingCartAddItems(List<SdpShoppingCartItemAddRequestDto> items) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		if (items == null || items.isEmpty()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, "items");
		}
		for (SdpShoppingCartItemAddRequestDto dto : items) {
			validateShoppingCartItem(dto.getItemId(), dto.getItemType(), dto.getQuantity());
			validateShoppingCartAddItem(dto.getItemDescription(), dto.getItemPrice());
		}
		log.logDebug(VALIDATION_END);
	}

	public SdpShoppingCart createShoppingCart(SdpParty party, String createdById, EntityManager em) throws ValidationException, PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpShoppingCart cart = new SdpShoppingCart();
		// Model valorization
		cart.setSdpParty(party);
		cart.setStatusId(ConstantsHandler.getInstance().retrieveLongConstant(Constants.NEW));
		cart.setCreatedById(createdById);
		cart.setCreatedDate(new Date());
		em.persist(cart);
		return cart;
	}

	public SdpShoppingCartItem createShoppingCartItem(SdpShoppingCart cart, RefItemType type, Long itemId, String itemDescription, Long quantity,
			BigDecimal price, String createdById, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpShoppingCartItem item = new SdpShoppingCartItem();
		SdpShoppingCartItemPK id = new SdpShoppingCartItemPK();
		id.setItemId(itemId);
		id.setItemTypeId(type.getItemTypeId());
		id.setShoppingCartId(cart.getShoppingCartId());
		item.setId(id);
		// Model valorization
		item.setItemDescription(itemDescription);
		item.setItemPrice(price);
		item.setQuantity(quantity);
		// calcolo il totale
		item.setItemSubtotal(price.multiply(new BigDecimal(quantity)));
		item.setRefItemType(type);
		item.setSdpShoppingCart(cart);
		item.setCreatedById(createdById);
		item.setCreatedDate(new Date());
		em.persist(item);
		cart.getSdpShoppingCartItems().add(item);
		return item;
	}

	public SdpShoppingCart searchShoppingCartById(Long shoppingCartId, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (shoppingCartId == null) {
			return null;
		}
		return em.find(SdpShoppingCart.class, shoppingCartId);
	}

	public RefItemType searchShoppingCartItemTypeByName(String itemType, EntityManager em) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (Utilities.isNull(itemType)) {
			return null;
		}
		HashMap<String, Object> parameHashMap = new HashMap<String, Object>();
		parameHashMap.put(RefItemType.PARAM_TYPE_NAME, itemType);
		return NamedQueryHelper.executeNamedQuerySingle(RefItemType.class, RefItemType.QUERY_RETRIEVE_BY_NAME, parameHashMap, em);
	}

	public SdpShoppingCartItem searchShoppingCartItemById(Long shoppingCartId, Long itemId, Long itemTypeId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (shoppingCartId == null || itemId == null || itemTypeId == null) {
			return null;
		}
		SdpShoppingCartItemPK id = new SdpShoppingCartItemPK();
		id.setItemId(itemId);
		id.setItemTypeId(itemTypeId);
		id.setShoppingCartId(shoppingCartId);
		return em.find(SdpShoppingCartItem.class, id);
	}
	
	public void modifyShoppingCart(SdpShoppingCart cart, String updatedById) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		cart.setUpdatedById(updatedById);
		cart.setUpdatedDate(new Date());
	}

	public void modifyShoppingCartItem(SdpShoppingCartItem item, String itemDescription, Long quantity, BigDecimal price, String updatedById) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		// Model valorization
		item.setItemDescription(itemDescription);
		item.setQuantity(quantity);
		item.setItemPrice(price);
		// ricalcolo il totale
		item.setItemSubtotal(price.multiply(new BigDecimal(quantity)));
		item.setUpdatedById(updatedById);
		item.setUpdatedDate(new Date());
	}

	public void deleteShoppingCartItem(SdpShoppingCartItem item, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		// cancellazione fisica
		item.getSdpShoppingCart().getSdpShoppingCartItems().remove(item);
		em.remove(item);
	}

	public void deleteShoppingCart(SdpShoppingCart cart, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		// cancellazione fisica
		em.remove(cart);
	}

	public void validateSearchShoppingCartById(Long shoppingCartId) throws ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug(VALIDATION_START);
		HashMap<String, Object> validationMap = new HashMap<String, Object>();
		validationMap.put(SHOPPING_CART_ID, shoppingCartId);
		ValidationResult res = ValidationUtils.validateMandatoryFields(validationMap);
		if (!res.isValid()) {
			throw new ValidationException(Constants.CODE_MANDATORY_FIELD_MISSING, res.getParams());
		}
		log.logDebug(VALIDATION_END);
	}

	public void changeStatus(SdpShoppingCart cart, Long nextStatus, String changedBy) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		cart.setStatusId(nextStatus);
		cart.setChgStatusById(changedBy);
		cart.setChgStatusDate(new Date());
	}
}