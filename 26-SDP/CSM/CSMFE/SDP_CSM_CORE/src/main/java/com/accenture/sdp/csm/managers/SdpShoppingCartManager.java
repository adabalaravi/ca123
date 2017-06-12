package com.accenture.sdp.csm.managers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import com.accenture.sdp.csm.commons.StatusIdConverter;
import com.accenture.sdp.csm.database.PersistenceManager;
import com.accenture.sdp.csm.dto.ComplexServiceResponse;
import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.requests.SdpShoppingCartItemAddRequestDto;
import com.accenture.sdp.csm.dto.requests.SdpShoppingCartItemRequestDto;
import com.accenture.sdp.csm.dto.responses.ParameterDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.exceptions.ValidationException;
import com.accenture.sdp.csm.helpers.SdpPartyHelper;
import com.accenture.sdp.csm.helpers.SdpShoppingCartHelper;
import com.accenture.sdp.csm.model.jpa.RefItemType;
import com.accenture.sdp.csm.model.jpa.SdpParty;
import com.accenture.sdp.csm.model.jpa.SdpShoppingCart;
import com.accenture.sdp.csm.model.jpa.SdpShoppingCartItem;
import com.accenture.sdp.csm.utilities.BeanConverter;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.logging.LogMap;

public final class SdpShoppingCartManager extends SdpBaseManager {

	private SdpShoppingCartManager() {
		super();
	}

	private static SdpShoppingCartManager instance;

	public static SdpShoppingCartManager getInstance() {
		if (instance == null) {
			instance = new SdpShoppingCartManager();
		}
		return instance;
	}

	protected boolean isStatusNew(Long status) throws PropertyNotFoundException {
		return status.equals(ConstantsHandler.getInstance().retrieveLongConstant(Constants.NEW));
	}

	public CreateServiceResponse addItemsToShoppingCart(Long partyId, Long shoppingCartId, List<SdpShoppingCartItemAddRequestDto> items, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		CreateServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(PARTY_ID, partyId);
		logMap.put(SHOPPING_CART_ID, shoppingCartId);
		logMap.put("item", items);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			// shopping cart
			SdpShoppingCart cart = addItemsToShoppingCart(partyId, shoppingCartId, items, Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildCreateResponse(Constants.CODE_OK);
			resp.setEntityId(cart.getShoppingCartId());
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_INSERT_FAILED);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildCreateResponse(validationException.getDescription(), validationException.getParameters());
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_GENERIC_ERROR);
		} finally {
			if (tx != null && tx.isActive()) {
				log.logDebug(TRANSACTION_ROLLBACK);
				tx.rollback();
				log.logDebug(TRANSACTION_ROLLBACKED);
			}
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		return resp;
	}

	SdpShoppingCart addItemsToShoppingCart(Long partyId, Long shoppingCartId, List<SdpShoppingCartItemAddRequestDto> items, String createdBy, EntityManager em)
			throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpShoppingCartHelper helper = SdpShoppingCartHelper.getInstance();
		SdpPartyHelper partyHelper = SdpPartyHelper.getInstance();
		partyHelper.validateSearchPartyById(partyId);
		helper.validateShoppingCartAddItems(items);

		SdpParty party = partyHelper.searchPartyById(partyId, em);
		if (party == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PARTY_ID, partyId);
		}

		SdpShoppingCart cart = null;
		if (shoppingCartId != null) {
			// recupero shopping cart
			cart = helper.searchShoppingCartById(shoppingCartId, em);
			if (cart == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SHOPPING_CART_ID, shoppingCartId);
			}
			if (!party.equals(cart.getSdpParty())) {
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, SHOPPING_CART_ID, shoppingCartId);
			}
			if (!isStatusNew(cart.getStatusId())) {
				throw new ValidationException(Constants.CODE_UPDATE_NOT_ALLOWED_FOR_STATUS, SHOPPING_CART_ID, shoppingCartId);
			}
			helper.modifyShoppingCart(cart, createdBy);
		} else {
			// creazione shopping cart
			cart = helper.createShoppingCart(party, createdBy, em);
		}

		for (SdpShoppingCartItemAddRequestDto dto : items) {
			RefItemType type = helper.searchShoppingCartItemTypeByName(dto.getItemType(), em);
			if (type == null) {
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, SHOPPING_CART_ITEM_TYPE_NAME, dto.getItemType());
			}
			SdpShoppingCartItem item = helper.searchShoppingCartItemById(cart.getShoppingCartId(), dto.getItemId(), type.getItemTypeId(), em);
			if (item == null) {
				// se non esiste lo creo nuovo
				helper.createShoppingCartItem(cart, type, dto.getItemId(), dto.getItemDescription(), dto.getQuantity(), dto.getItemPrice(), createdBy, em);
			} else {
				// altrimenti aggiorno descrizione, prezzo e quantita'
				helper.modifyShoppingCartItem(item, dto.getItemDescription(), item.getQuantity() + dto.getQuantity(), dto.getItemPrice(), createdBy);
			}
		}
		return cart;
	}

	public DataServiceResponse removeItemsFromShoppingCart(Long shoppingCartId, List<SdpShoppingCartItemRequestDto> items, String tenantName)
			throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(SHOPPING_CART_ID, shoppingCartId);
		logMap.put("item", items);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			// shopping cart
			removeItemsFromShoppingCart(shoppingCartId, items, Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildUpdateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildUpdateResponse(Constants.CODE_UPDATE_FAILED);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildUpdateResponse(validationException.getDescription(), validationException.getParameters());
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildUpdateResponse(Constants.CODE_GENERIC_ERROR);
		} finally {
			if (tx != null && tx.isActive()) {
				log.logDebug(TRANSACTION_ROLLBACK);
				tx.rollback();
				log.logDebug(TRANSACTION_ROLLBACKED);
			}
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		return resp;
	}

	void removeItemsFromShoppingCart(Long shoppingCartId, List<SdpShoppingCartItemRequestDto> items, String updatedBy, EntityManager em)
			throws PropertyNotFoundException, ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpShoppingCartHelper helper = SdpShoppingCartHelper.getInstance();
		helper.validateSearchShoppingCartById(shoppingCartId);
		helper.validateShoppingCartItems(items);

		SdpShoppingCart cart = helper.searchShoppingCartById(shoppingCartId, em);
		if (cart == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SHOPPING_CART_ID, shoppingCartId);
		}
		if (!isStatusNew(cart.getStatusId())) {
			throw new ValidationException(Constants.CODE_UPDATE_NOT_ALLOWED_FOR_STATUS, SHOPPING_CART_ID, shoppingCartId);
		}

		for (SdpShoppingCartItemRequestDto dto : items) {
			RefItemType type = helper.searchShoppingCartItemTypeByName(dto.getItemType(), em);
			if (type == null) {
				throw new ValidationException(Constants.CODE_VALUE_NOT_ALLOWED, SHOPPING_CART_ITEM_TYPE_NAME, dto.getItemType());
			}
			SdpShoppingCartItem item = helper.searchShoppingCartItemById(cart.getShoppingCartId(), dto.getItemId(), type.getItemTypeId(), em);
			if (item == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, new ParameterDto(SHOPPING_CART_ITEM_TYPE_NAME, dto.getItemType()),
						new ParameterDto("itemId", dto.getItemId()));
			}
			long quantity = item.getQuantity() - dto.getQuantity();
			if (quantity > 0) {
				// descrizione e prezzo rimangono gli stessi
				helper.modifyShoppingCartItem(item, item.getItemDescription(), quantity, item.getItemPrice(), updatedBy);
			} else {
				// rimozione item
				helper.deleteShoppingCartItem(item, em);
			}
		}
		if (cart.getSdpShoppingCartItems().isEmpty()) {
			// rimozione carrello
			helper.deleteShoppingCart(cart, em);
		} else {
			helper.modifyShoppingCart(cart, updatedBy);
		}
	}

	public DataServiceResponse removeShoppingCart(Long shoppingCartId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		DataServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(SHOPPING_CART_ID, shoppingCartId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			removeShoppingCart(shoppingCartId, Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);
			resp = buildCreateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_DELETE_FAILED);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildCreateResponse(validationException.getDescription(), validationException.getParameters());
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_GENERIC_ERROR);
		} finally {
			if (tx != null && tx.isActive()) {
				log.logDebug(TRANSACTION_ROLLBACK);
				tx.rollback();
				log.logDebug(TRANSACTION_ROLLBACKED);
			}
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		return resp;
	}

	void removeShoppingCart(Long shoppingCartId, String deletedBy, EntityManager em) throws ValidationException, PropertyNotFoundException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpShoppingCartHelper helper = SdpShoppingCartHelper.getInstance();
		helper.validateSearchShoppingCartById(shoppingCartId);

		SdpShoppingCart cart = helper.searchShoppingCartById(shoppingCartId, em);
		if (cart == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SHOPPING_CART_ID, shoppingCartId);
		}
		if (!isStatusNew(cart.getStatusId())) {
			throw new ValidationException(Constants.CODE_UPDATE_NOT_ALLOWED_FOR_STATUS, SHOPPING_CART_ID, shoppingCartId);
		}

		helper.deleteShoppingCart(cart, em);
	}

	public SearchServiceResponse getShoppingCartsByPartyId(Long partyId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(PARTY_ID, partyId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			SdpPartyHelper helper = SdpPartyHelper.getInstance();
			helper.validateSearchPartyById(partyId);

			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);

			SdpParty party = helper.searchPartyById(partyId, em);
			if (party == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, PARTY_ID, partyId);
			}

			List<SdpShoppingCart> carts = party.getSdpShoppingCarts();
			if (carts == null || carts.isEmpty()) {
				throw new ValidationException(Constants.CODE_ZERO_RECORD_FOUND);
			}

			resp = buildSearchResponse(Constants.CODE_OK);
			resp.setSearchResult(BeanConverter.convertShoppingCarts(carts));
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException e) {
			log.logDebug(e.getMessage());
			resp = buildSearchResponse(e.getDescription(), e.getParameters());
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildSearchResponse(Constants.CODE_GENERIC_ERROR);
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		return resp;
	}

	public ComplexServiceResponse getShoppingCartById(Long shoppingCartId, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		ComplexServiceResponse resp;
		EntityManager em = null;
		LogMap logMap = new LogMap();
		logMap.put(SHOPPING_CART_ID, shoppingCartId);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		try {
			SdpShoppingCartHelper helper = SdpShoppingCartHelper.getInstance();
			helper.validateSearchShoppingCartById(shoppingCartId);

			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(ENTITY_MANAGER_OPENED);

			SdpShoppingCart cart = helper.searchShoppingCartById(shoppingCartId, em);
			if (cart == null) {
				throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SHOPPING_CART_ID, shoppingCartId);
			}

			resp = buildComplexResponse(Constants.CODE_OK);
			resp.setComplexObject(BeanConverter.convertShoppingCart(cart));
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException e) {
			log.logDebug(e.getMessage());
			resp = buildComplexResponse(e.getDescription(), e.getParameters());
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildComplexResponse(Constants.CODE_GENERIC_ERROR);
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		return resp;
	}

	public DataServiceResponse shoppingCartChangeStatus(Long shoppingCartId, String status, String tenantName) throws PropertyNotFoundException {
		long startTime = System.currentTimeMillis();
		log.logDebug(Utilities.getCurrentClassAndMethod());
		LogMap logMap = new LogMap();
		logMap.put(SHOPPING_CART_ID, shoppingCartId);
		logMap.put(STATUS, status);
		logMap.put(TENANT_NAME, tenantName);
		log.logStartMethod(startTime, logMap);
		logMap.clear();
		EntityManager em = null;
		DataServiceResponse resp = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			log.logDebug(TRANSACTION_START);
			tx = em.getTransaction();
			tx.begin();

			shoppingCartChangeStatus(shoppingCartId, status, Utilities.getCurrentClassAndMethod(), em);

			log.logDebug(TRANSACTION_COMMIT);
			tx.commit();
			log.logDebug(TRANSACTION_COMMITED);

			resp = buildUpdateResponse(Constants.CODE_OK);
			log.logDebug(RESULT_STRING, Utilities.getCurrentClassAndMethod(), Constants.CODE_OK);
		} catch (ValidationException validationException) {
			log.logDebug(validationException.getMessage());
			resp = buildCreateResponse(validationException.getDescription(), validationException.getParameters());
		} catch (RollbackException e) {
			log.logError(ROLLBACK_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_UPDATE_FAILED);
		} catch (Exception e) {
			log.logError(EXCEPTION_WARNING_STRING, e);
			resp = buildCreateResponse(Constants.CODE_GENERIC_ERROR);
		} finally {
			if (tx != null && tx.isActive()) {
				log.logDebug(TRANSACTION_ROLLBACK);
				tx.rollback();
				log.logDebug(TRANSACTION_ROLLBACKED);
			}
			if (em != null && em.isOpen()) {
				em.close();
				log.logDebug(ENTITY_MANAGER_CLOSED);
			}
		}
		log.logEndMethod(startTime, System.currentTimeMillis(), resp);
		return resp;
	}

	SdpShoppingCart shoppingCartChangeStatus(Long shoppingCartId, String status, String changedStatusBy, EntityManager em) throws PropertyNotFoundException,
			ValidationException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpShoppingCartHelper helper = SdpShoppingCartHelper.getInstance();
		// formal validation
		helper.validateSearchShoppingCartById(shoppingCartId);
		helper.validateChangeStatus(status);

		SdpShoppingCart cart = helper.searchShoppingCartById(shoppingCartId, em);
		if (cart == null) {
			throw new ValidationException(Constants.CODE_ELEMENT_NOT_FOUND, SHOPPING_CART_ID, shoppingCartId);
		}
		helper.checkAllowedChangeStatus(Constants.ENTITY_TYPE_SHOPPINGCART, cart.getStatusId(), StatusIdConverter.getStatusValue(status), em);

		helper.changeStatus(cart, StatusIdConverter.getStatusValue(status), changedStatusBy);
		return cart;
	}

}
