package com.accenture.sdp.csm.test.shoppingcart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.accenture.sdp.csm.dto.ComplexServiceResponse;
import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.requests.SdpShoppingCartItemAddRequestDto;
import com.accenture.sdp.csm.dto.requests.SdpShoppingCartItemRequestDto;
import com.accenture.sdp.csm.dto.responses.SdpShoppingCartItemResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpShoppingCartResponseDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.managers.SdpShoppingCartManager;
import com.accenture.sdp.csm.test.BaseTestCase;
import com.accenture.sdp.csm.test.utilities.Parameter;
import com.accenture.sdp.csm.test.utilities.TestConstants;
import com.accenture.sdp.csm.test.utilities.Utils;
import com.accenture.sdp.csm.utilities.Utilities;

public class SdpShoppinCartTest extends BaseTestCase {

	private SdpShoppingCartManager manager = SdpShoppingCartManager.getInstance();

	@Test
	public void testAddItemsToShoppingCart000new() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			CreateServiceResponse resp = addItemsToShoppingCart();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAddItemsToShoppingCart000update() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// prima crea il carrello
			Long partyId = getRandomPartyId();
			CreateServiceResponse resp = addItemsToShoppingCart(new Parameter(partyId));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
			// quindi lo aggiorna
			resp = addItemsToShoppingCart(new Parameter(partyId), new Parameter(resp.getEntityId()));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAddItemsToShoppingCart010() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// partyId mandatorio
			CreateServiceResponse resp = addItemsToShoppingCart(Parameter.NULL);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
			// parti dell'item mandatori
			// prima il type
			resp = addItemsToShoppingCart(Parameter.RANDOM, Parameter.RANDOM, new Parameter(prepareSdpShoppingCartItemRequestDto(null)));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
			// quindi il resto, escluso price che non e' mandatorio
			Parameter[] array = { Parameter.RANDOM, Parameter.RANDOM, Parameter.RANDOM };
			List<String> itemTypes = ShoppingCartConfigurator.getShoppingCartItemTypes(tenantName);
			for (int i = 0; i < array.length; i++) {
				array[i] = Parameter.NULL;
				String type = itemTypes.get(Utils.getRandomInt(itemTypes.size()));
				resp = addItemsToShoppingCart(Parameter.RANDOM, Parameter.RANDOM, new Parameter(prepareSdpShoppingCartItemRequestDto(type, array)));
				printResponse(resp, Utilities.getCurrentClassAndMethod());
				assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
				array[i] = Parameter.RANDOM;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAddItemsToShoppingCart020party() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// prima creo un carrello ad un party
			Long partyId = getRandomPartyId();
			CreateServiceResponse resp = addItemsToShoppingCart(new Parameter(partyId));
			// quindi lo faccio modificare ad un party casuale
			resp = addItemsToShoppingCart(Parameter.RANDOM, new Parameter(resp.getEntityId()));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_020));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAddItemsToShoppingCart020others() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// itemType
			// metto type a caso anziche' prenderlo dalla lista
			String type = Utils.getRandomNamePrefixed();
			CreateServiceResponse resp = addItemsToShoppingCart(Parameter.RANDOM, Parameter.RANDOM, new Parameter(prepareSdpShoppingCartItemRequestDto(type)));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_020));
			// quantity
			List<String> itemTypes = ShoppingCartConfigurator.getShoppingCartItemTypes(tenantName);
			type = itemTypes.get(Utils.getRandomInt(itemTypes.size()));
			// valore negativo
			Long quantity = -Utils.getRandomLong(100);
			resp = addItemsToShoppingCart(Parameter.RANDOM, Parameter.RANDOM,
					new Parameter(prepareSdpShoppingCartItemRequestDto(type, Parameter.RANDOM, Parameter.RANDOM, new Parameter(quantity))));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_020));
			// itemPrice
			// valore negativo
			BigDecimal price = Utils.getRandomDecimal(100).negate();
			resp = addItemsToShoppingCart(Parameter.RANDOM, Parameter.RANDOM,
					new Parameter(prepareSdpShoppingCartItemRequestDto(type, Parameter.RANDOM, Parameter.RANDOM, Parameter.RANDOM, new Parameter(price))));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_020));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAddItemsToShoppingCart100() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// party
			CreateServiceResponse resp = addItemsToShoppingCart(new Parameter(Utils.getGreaterRandomLong(1000000000)));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_100));
			// cart
			resp = addItemsToShoppingCart(Parameter.RANDOM, new Parameter(Utils.getGreaterRandomLong(1000000000)));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_100));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CreateServiceResponse addItemsToShoppingCart(Parameter... params) throws PropertyNotFoundException {
		Long partyId = Utils.readParam(Long.class, params, 0, getRandomPartyId());
		// di default e' null, cosi' crea uno nuovo
		Long shoppingCartId = Utils.readParam(Long.class, params, 1, null);

		List<SdpShoppingCartItemAddRequestDto> items = new ArrayList<SdpShoppingCartItemAddRequestDto>();
		List<String> itemTypes = ShoppingCartConfigurator.getShoppingCartItemTypes(tenantName);
		// iteratore per leggere eventuali dto in input
		int iterator = 2;
		for (String itemType : itemTypes) {
			SdpShoppingCartItemAddRequestDto dto = Utils.readParam(SdpShoppingCartItemAddRequestDto.class, params, iterator++,
					prepareSdpShoppingCartItemRequestDto(itemType));
			items.add(dto);
		}
		return manager.addItemsToShoppingCart(partyId, shoppingCartId, items, tenantName);
	}

	private SdpShoppingCartItemAddRequestDto prepareSdpShoppingCartItemRequestDto(String itemType, Parameter... params) {
		SdpShoppingCartItemAddRequestDto dto = new SdpShoppingCartItemAddRequestDto();
		dto.setItemType(itemType);
		dto.setItemId(Utils.readParam(Long.class, params, 0, Utils.getRandomLong(1000000000)));
		dto.setItemDescription(Utils.readParam(String.class, params, 1, Utils.getRandomNamePrefixed()));
		dto.setQuantity(Utils.readParam(Long.class, params, 2, Utils.getRandomLong(1000)));
		dto.setItemPrice(Utils.readParam(BigDecimal.class, params, 3, Utils.getRandomDecimal(10000)));
		return dto;
	}

	@Test
	public void testGetShoppingCartById000() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// creo
			CreateServiceResponse resp1 = addItemsToShoppingCart();
			// e ricerco
			ComplexServiceResponse resp = getShoppingCartById(resp1.getEntityId());
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetShoppingCartById010() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			ComplexServiceResponse resp = getShoppingCartById(null);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetShoppingCartById100() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			ComplexServiceResponse resp = getShoppingCartById(Utils.getGreaterRandomLong(1000000000));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_100));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ComplexServiceResponse getShoppingCartById(Long cartId) throws PropertyNotFoundException {
		return manager.getShoppingCartById(cartId, getTenantName());
	}

	@Test
	public void testGetShoppingCartsByPartyId000() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// prima crea un carrello ad un party
			Long partyId = getRandomPartyId();
			addItemsToShoppingCart(new Parameter(partyId));
			// e poi ricerco
			SearchServiceResponse resp = getShoppingCartsByPartyId(partyId);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetShoppingCartsByPartyId010() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = getShoppingCartsByPartyId(null);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetShoppingCartsByPartyId100() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = getShoppingCartsByPartyId(Utils.getGreaterRandomLong(1000000000));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_100));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetShoppingCartsByPartyId130() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			Long partyId = getRandomPartyId();
			// cancello eventuali carrelli al party
			ShoppingCartConfigurator.resetPartyShoppingCarts(partyId, tenantName);
			SearchServiceResponse resp = getShoppingCartsByPartyId(partyId);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_130));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse getShoppingCartsByPartyId(Long partyId) throws PropertyNotFoundException {
		return manager.getShoppingCartsByPartyId(partyId, getTenantName());
	}

	@Test
	public void testShoppingCartChangeStatus000() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// creo
			CreateServiceResponse resp1 = addItemsToShoppingCart();
			// e modifico
			DataServiceResponse resp = shoppingCartChangeStatus(resp1.getEntityId());
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testShoppingCartChangeStatus010() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// cartId
			DataServiceResponse resp = shoppingCartChangeStatus(null);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
			// status
			// cartId lo metto a caso, tanto non lo usera'
			resp = shoppingCartChangeStatus(Utils.getRandomLong(1000000), Parameter.NULL);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testShoppingCartChangeStatus020() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// creo
			CreateServiceResponse resp1 = addItemsToShoppingCart();
			// e modifico a caso
			DataServiceResponse resp = shoppingCartChangeStatus(resp1.getEntityId(), new Parameter(Utils.getRandomNamePrefixed()));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_020));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testShoppingCartChangeStatus050() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// creo
			CreateServiceResponse resp1 = addItemsToShoppingCart();
			// e modifico a new
			DataServiceResponse resp = shoppingCartChangeStatus(resp1.getEntityId(), new Parameter("New"));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_050));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testShoppingCartChangeStatus100() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = shoppingCartChangeStatus(Utils.getGreaterRandomLong(1000000000));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_100));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse shoppingCartChangeStatus(Long cartId, Parameter... params) throws PropertyNotFoundException {
		String status = Utils.readParam(String.class, params, 0, "CheckedOut");
		return manager.shoppingCartChangeStatus(cartId, status, getTenantName());
	}

	@Test
	public void testRemoveShoppingCart000() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// creo
			CreateServiceResponse resp1 = addItemsToShoppingCart();
			// e cancello
			DataServiceResponse resp = removeShoppingCart(resp1.getEntityId());
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRemoveShoppingCart010() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = removeShoppingCart(null);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRemoveShoppingCart100() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = removeShoppingCart(Utils.getGreaterRandomLong(1000000000));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_100));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRemoveShoppingCart140() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// creo
			CreateServiceResponse resp1 = addItemsToShoppingCart();
			// cambio stato in checkedOut (default)
			shoppingCartChangeStatus(resp1.getEntityId());
			// e cancello
			DataServiceResponse resp = removeShoppingCart(resp1.getEntityId());
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_140));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse removeShoppingCart(Long cartId) throws PropertyNotFoundException {
		return manager.removeShoppingCart(cartId, getTenantName());
	}

	@Test
	public void testRemoveItemsFromShoppingCart000single() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			List<String> itemTypes = ShoppingCartConfigurator.getShoppingCartItemTypes(tenantName);
			// prima creo il carrello con un item che mi piace tanto
			SdpShoppingCartItemAddRequestDto dto = prepareSdpShoppingCartItemRequestDto(itemTypes.get(Utils.getRandomInt(itemTypes.size())));
			// creazione carrello
			CreateServiceResponse resp1 = addItemsToShoppingCart(Parameter.RANDOM, Parameter.RANDOM, new Parameter(dto));
			// quindi cancello un po' dell'item che non mi piace piu' tanto
			// posso usare lo stesso dto, di cui uso i campi della superclasse
			dto.setQuantity(dto.getQuantity() - Utils.getRandomLong(dto.getQuantity().intValue()));
			DataServiceResponse resp = removeItemsFromShoppingCart(resp1.getEntityId(), dto);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRemoveItemsFromShoppingCart000everything() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// prima creo un carrello casuale
			CreateServiceResponse resp1 = addItemsToShoppingCart();
			// quindi lo ricerco per recuperare gli items
			ComplexServiceResponse resp2 = getShoppingCartById(resp1.getEntityId());
			SdpShoppingCartResponseDto respdto = (SdpShoppingCartResponseDto) resp2.getComplexObject();
			// prepara le richieste di rimozione completa a partire dalla risposta della ricerca
			SdpShoppingCartItemRequestDto[] dtos = prepareSdpShoppingCartItemRemoveRequestDtos(respdto.getItems());
			DataServiceResponse resp = removeItemsFromShoppingCart(resp1.getEntityId(), dtos);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private SdpShoppingCartItemRequestDto[] prepareSdpShoppingCartItemRemoveRequestDtos(List<SdpShoppingCartItemResponseDto> items) {
		SdpShoppingCartItemRequestDto[] dtos = new SdpShoppingCartItemRequestDto[items.size()];
		for (int i = 0; i < items.size(); i++) {
			SdpShoppingCartItemRequestDto dto = new SdpShoppingCartItemRequestDto();
			SdpShoppingCartItemResponseDto item = items.get(i);
			dto.setItemId(item.getItemId());
			dto.setItemType(item.getItemType());
			dto.setQuantity(item.getQuantity());
			dtos[i] = dto;
		}
		return dtos;
	}

	@Test
	public void testRemoveItemsFromShoppingCart010cartId() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			List<String> itemTypes = ShoppingCartConfigurator.getShoppingCartItemTypes(tenantName);
			// uso il dto della creazione, di cui considero solo i campi della superclasse
			SdpShoppingCartItemRequestDto dto = prepareSdpShoppingCartItemRequestDto(itemTypes.get(Utils.getRandomInt(itemTypes.size())));
			// cartId mandatorio
			DataServiceResponse resp = removeItemsFromShoppingCart(null, dto);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRemoveItemsFromShoppingCart010others() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			List<String> itemTypes = ShoppingCartConfigurator.getShoppingCartItemTypes(tenantName);
			// uso il dto della creazione, di cui considero solo i campi della superclasse
			// type mandatorio
			SdpShoppingCartItemRequestDto dto = prepareSdpShoppingCartItemRequestDto(null);
			// cartId lo metto a caso, tanto non lo usera'
			DataServiceResponse resp = removeItemsFromShoppingCart(Utils.getRandomLong(10000000), dto);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));

			// itemId mandatorio
			dto = prepareSdpShoppingCartItemRequestDto(itemTypes.get(Utils.getRandomInt(itemTypes.size())));
			dto.setItemId(null);
			resp = removeItemsFromShoppingCart(Utils.getRandomLong(10000000), dto);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));

			// quantity mandatorio
			dto = prepareSdpShoppingCartItemRequestDto(itemTypes.get(Utils.getRandomInt(itemTypes.size())));
			dto.setQuantity(null);
			resp = removeItemsFromShoppingCart(Utils.getRandomLong(10000000), dto);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRemoveItemsFromShoppingCart020() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// prerequisito: creo carrello
			CreateServiceResponse resp1 = addItemsToShoppingCart(new Parameter(getRandomPartyId()));
			// itemType
			// metto type a caso anziche' prenderlo dalla lista
			String type = Utils.getRandomNamePrefixed();
			DataServiceResponse resp = removeItemsFromShoppingCart(resp1.getEntityId(), prepareSdpShoppingCartItemRequestDto(type));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_020));
			// quantity
			List<String> itemTypes = ShoppingCartConfigurator.getShoppingCartItemTypes(tenantName);
			type = itemTypes.get(Utils.getRandomInt(itemTypes.size()));
			// valore negativo
			Long quantity = -Utils.getRandomLong(100);
			resp = removeItemsFromShoppingCart(resp1.getEntityId(),
					prepareSdpShoppingCartItemRequestDto(type, Parameter.RANDOM, Parameter.RANDOM, new Parameter(quantity)));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_020));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRemoveItemsFromShoppingCart100() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// prerequisito: creo carrello
			CreateServiceResponse resp1 = addItemsToShoppingCart(new Parameter(getRandomPartyId()));
			// e preparo la lista delle cancellazioni
			ComplexServiceResponse resp2 = getShoppingCartById(resp1.getEntityId());
			SdpShoppingCartResponseDto respdto = (SdpShoppingCartResponseDto) resp2.getComplexObject();
			SdpShoppingCartItemRequestDto[] dtos = prepareSdpShoppingCartItemRemoveRequestDtos(respdto.getItems());
			// cart non esistente
			DataServiceResponse resp = removeItemsFromShoppingCart(Utils.getGreaterRandomLong(1000000000), dtos);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_100));
			// item a caso
			List<String> itemTypes = ShoppingCartConfigurator.getShoppingCartItemTypes(tenantName);
			String type = itemTypes.get(Utils.getRandomInt(itemTypes.size()));
			resp = removeItemsFromShoppingCart(resp1.getEntityId(), prepareSdpShoppingCartItemRequestDto(type));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_100));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRemoveItemsFromShoppingCart140() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// prerequisito: creo carrello
			CreateServiceResponse resp1 = addItemsToShoppingCart(new Parameter(getRandomPartyId()));
			// e preparo la lista delle cancellazioni
			ComplexServiceResponse resp2 = getShoppingCartById(resp1.getEntityId());
			SdpShoppingCartResponseDto respdto = (SdpShoppingCartResponseDto) resp2.getComplexObject();
			SdpShoppingCartItemRequestDto[] dtos = prepareSdpShoppingCartItemRemoveRequestDtos(respdto.getItems());
			// cambio lo stato
			shoppingCartChangeStatus(resp1.getEntityId());
			// rimozione
			DataServiceResponse resp = removeItemsFromShoppingCart(resp1.getEntityId(), dtos);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_140));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse removeItemsFromShoppingCart(Long cartId, SdpShoppingCartItemRequestDto... items) throws PropertyNotFoundException {
		return manager.removeItemsFromShoppingCart(cartId, Arrays.asList(items), tenantName);
	}

}
