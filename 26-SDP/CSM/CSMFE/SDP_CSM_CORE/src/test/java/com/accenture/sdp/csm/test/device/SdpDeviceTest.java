package com.accenture.sdp.csm.test.device;

import java.util.Date;
import java.util.UUID;

import org.junit.Test;

import com.accenture.sdp.csm.commons.DeviceEnums;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.managers.SdpDeviceManager;
import com.accenture.sdp.csm.managers.SdpPartyDeviceManager;
import com.accenture.sdp.csm.test.BaseTestCase;
import com.accenture.sdp.csm.test.utilities.Parameter;
import com.accenture.sdp.csm.test.utilities.TestConstants;
import com.accenture.sdp.csm.test.utilities.Utils;
import com.accenture.sdp.csm.utilities.Utilities;

public class SdpDeviceTest extends BaseTestCase {

	SdpDeviceManager manager = SdpDeviceManager.getInstance();

	@Test
	public void testSearchAllDeviceBrands000() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchAllDeviceBrands();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchAllDeviceBrands() throws PropertyNotFoundException {
		return manager.searchAllDeviceChannels(getTenantName());
	}

	@Test
	public void testSearchDeviceModelsByBrand000() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// me lo preparo, cosi' sono sicuro di averne almeno uno
			Long brandId = DeviceConfigurator.getBrandRandom(tenantName).getBrandId();
			DeviceConfigurator.createModel(Utils.getRandomNamePrefixed(), brandId, tenantName);

			SearchServiceResponse resp = searchDeviceModelsByBrand(brandId);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSearchDeviceModelsByBrand010() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchDeviceModelsByBrand(null);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSearchDeviceModelsByBrand020() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchDeviceModelsByBrand(Utils.getGreaterRandomLong(1000000000));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_020));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSearchDeviceModelsByBrand130() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// faccio un nuovo brand
			Long brandId = DeviceConfigurator.createBrand(Utils.getRandomNamePrefixed(), tenantName);

			SearchServiceResponse resp = searchDeviceModelsByBrand(brandId);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_130));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchDeviceModelsByBrand(Long brandId) throws PropertyNotFoundException {
		return manager.searchDeviceModelsByBrand(brandId, getTenantName());
	}

	@Test
	public void testSearchAllDeviceChannels000() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchAllDeviceChannels();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchAllDeviceChannels() throws PropertyNotFoundException {
		return manager.searchAllDeviceChannels(getTenantName());
	}

	// ///////////////////////////
	// REGISTER DEVICE
	// ///////////////////////////
	@Test
	public void testRegisterDevice000() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = registerDevice();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRegisterDevice000reregister() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// creo un device come prerequisito
			String uuid = UUID.randomUUID().toString();
			registerDevice(new Parameter(uuid));
			// lo deregistro
			unregisterDevice(uuid);
			// e lo registro nuovamente
			DataServiceResponse resp = registerDevice(new Parameter(uuid));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRegisterDevice010() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// esclusi channel, brand, model, ma anche password perche' di default non e' mandatorio
			Parameter[] array = { Parameter.RANDOM, Parameter.RANDOM, Parameter.RANDOM, Parameter.NULL, Parameter.NULL, Parameter.NULL, Parameter.RANDOM };
			for (int i = 0; i < array.length; i++) {
				if (array[i] != Parameter.NULL) {
					array[i] = Parameter.NULL;
					DataServiceResponse resp = registerDevice(array);
					printResponse(resp, Utilities.getCurrentClassAndMethod());
					assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
					array[i] = Parameter.RANDOM;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRegisterDevice020() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// tutti a caso tranne l'alias, che non se lo fila nessuno, e lo username, che da 100
			Parameter[] array = { Parameter.RANDOM, Parameter.RANDOM, Parameter.RANDOM, Parameter.RANDOM, Parameter.RANDOM };
			for (int i = 0; i < array.length; i++) {
				if (array[i] != Parameter.NULL) {
					array[i] = new Parameter(Utils.getRandomNamePrefixed());
					DataServiceResponse resp = registerDevice(array);
					printResponse(resp, Utilities.getCurrentClassAndMethod());
					assertTrue(resp.getResultCode().equals(TestConstants.CODE_020));
					array[i] = Parameter.RANDOM;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRegisterDevice100() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// username casuale
			DataServiceResponse resp = registerDevice(Parameter.RANDOM, Parameter.RANDOM, Parameter.RANDOM, Parameter.RANDOM, Parameter.RANDOM,
					Parameter.RANDOM, new Parameter(Utils.getRandomNamePrefixed()));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_100));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRegisterDevice140() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// creo un device come prerequisito
			Parameter uuid = new Parameter(UUID.randomUUID().toString());
			registerDevice(uuid);
			// e poi lo ricreo
			DataServiceResponse resp = registerDevice(uuid);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_140));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRegisterDevice301() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// creo device ad un party finche' non scoppia
			Long partyId = getRandomPartyId();
			// resetto ogni collegamento coi device, cosi' carichera' policy default
			DeviceConfigurator.resetPartyDevice(partyId, tenantName);
			DataServiceResponse resp = null;
			do {
				String uuid = UUID.randomUUID().toString();
				resp = registerDevice(new Parameter(uuid), Parameter.RANDOM, Parameter.RANDOM, Parameter.RANDOM, Parameter.RANDOM, Parameter.RANDOM,
						new Parameter(PartyConfigurator.getUsernameRandom(partyId, tenantName)));
				// resetto il safetyPeriod ogni volta
				resetDeviceSafetyPeriod(partyId);
			} while (resp.getResultCode().equals(TestConstants.CODE_OK));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_301));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRegisterDevice302() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// creo device e rimuovo ad un party finche' non scoppia
			Long partyId = getRandomPartyId();
			// resetto ogni collegamento coi device, cosi' carichera' policy default
			DeviceConfigurator.resetPartyDevice(partyId, tenantName);
			DataServiceResponse resp = null;
			do {
				String uuid = UUID.randomUUID().toString();
				resp = registerDevice(new Parameter(uuid), Parameter.RANDOM, Parameter.RANDOM, Parameter.RANDOM, Parameter.RANDOM, Parameter.RANDOM,
						new Parameter(PartyConfigurator.getUsernameRandom(partyId, tenantName)));
				unregisterDevice(uuid);
			} while (resp.getResultCode().equals(TestConstants.CODE_OK));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_302));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse registerDevice(Parameter... params) throws PropertyNotFoundException {
		// di default mette SERIAL, perche' sono gli unici facili da generare per la validazione
		String deviceUUID = Utils.readParam(String.class, params, 0, UUID.randomUUID().toString());
		String deviceUUIDType = Utils.readParam(String.class, params, 1, "SERIALNUMBER");
		String deviceChannel = Utils.readParam(String.class, params, 2, DeviceConfigurator.getChannelRandom(tenantName).getDeviceChannelName());
		String deviceBrand = Utils.readParam(String.class, params, 3, DeviceConfigurator.getBrandRandom(tenantName).getBrandName());
		String deviceModel = Utils.readParam(String.class, params, 4, DeviceConfigurator.getModelRandom(tenantName).getModelName());
		String deviceAlias = Utils.readParam(String.class, params, 5, Utils.getRandomNamePrefixed());
		// prendo un qualsiasi party, tanto su avs hanno tutti almeno una credenziale
		String username = Utils.readParam(String.class, params, 6, PartyConfigurator.getUsernameRandom(getRandomPartyId(), tenantName));
		// non posso decriptare le password, quindi tantovale che ne sparo una a caso
		String password = Utils.readParam(String.class, params, 7, Utils.getRandomName(40));
		// casuale , tanto la configurazione in false vince
		boolean pairEnabled = Utils.readParam(Boolean.class, params, 8, Utils.coinFlip());
		return manager.registerDevice(deviceUUID, deviceUUIDType, deviceChannel, deviceBrand, deviceModel, deviceAlias, username, password, pairEnabled,
				tenantName);
	}

	// ///////////////////////////
	// CHECK DEVICE
	// ///////////////////////////
	@Test
	public void testCheckDeviceAccess000() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// creo device ad un party
			Long partyId = getRandomPartyId();
			// resetto ogni collegamento coi device, cosi' carichera' policy default e non avrà WL/BL
			DeviceConfigurator.resetPartyDevice(partyId, tenantName);
			String uuid = UUID.randomUUID().toString();
			// brand e model null, cosi' evito che siano il BL o WL
			registerDevice(new Parameter(uuid), Parameter.RANDOM, Parameter.RANDOM, Parameter.NULL, Parameter.NULL, Parameter.RANDOM, new Parameter(
					PartyConfigurator.getUsernameRandom(partyId, tenantName)));
			// FIXME e poi mi ci loggo... fregandomi di user e PW perche' auth e' false
			DataServiceResponse resp = checkDeviceAccess(uuid);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCheckDeviceAccess010() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// FIXME esclusi username e password, ma in realta' sarebbero mandatori in base all'authMode
			DataServiceResponse resp = checkDeviceAccess(null);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
			// null authMode
			resp = checkDeviceAccess(UUID.randomUUID().toString(), Parameter.RANDOM, Parameter.RANDOM, Parameter.NULL);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCheckDeviceAccess020() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// uuid a caso, tanto non ci deve far nulla
			DataServiceResponse resp = checkDeviceAccess(UUID.randomUUID().toString(), Parameter.RANDOM, Parameter.RANDOM,
					new Parameter(Utils.getRandomNamePrefixed()));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_020));
			// user e pass mandati con pairing
			resp = checkDeviceAccess(UUID.randomUUID().toString(), Parameter.RANDOM, Parameter.RANDOM, new Parameter(DeviceEnums.AuthMode.PAIRING.getValue()));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_020));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCheckDeviceAccess100() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// username casuale
			DataServiceResponse resp = checkDeviceAccess(UUID.randomUUID().toString());
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_100));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCheckDeviceAccess303() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// creo device ad un party
			Long partyId = getRandomPartyId();
			// resetto ogni collegamento coi device, cosi' carichera' policy default e non avrà WL/BL
			DeviceConfigurator.resetPartyDevice(partyId, tenantName);
			String uuid = UUID.randomUUID().toString();
			registerDevice(new Parameter(uuid), Parameter.RANDOM, Parameter.RANDOM, Parameter.RANDOM, Parameter.RANDOM, Parameter.RANDOM, new Parameter(
					PartyConfigurator.getUsernameRandom(partyId, tenantName)));
			// metto l'utente in WL
			DeviceConfigurator.blacklistParty(partyId, tenantName);
			// FIXME e poi mi ci loggo... fregandomi di user e PW perche' auth e' false
			DataServiceResponse resp = checkDeviceAccess(uuid);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_303));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCheckDeviceAccess304() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// creo device ad un party
			Long partyId = getRandomPartyId();
			// resetto ogni collegamento coi device, cosi' carichera' policy default e non avrà WL/BL
			DeviceConfigurator.resetPartyDevice(partyId, tenantName);
			String uuid = UUID.randomUUID().toString();
			registerDevice(new Parameter(uuid), Parameter.RANDOM, Parameter.RANDOM, Parameter.RANDOM, Parameter.RANDOM, Parameter.RANDOM, new Parameter(
					PartyConfigurator.getUsernameRandom(partyId, tenantName)));
			// metto l'utente in WL
			DeviceConfigurator.blacklistDevice(uuid, tenantName);
			// FIXME e poi mi ci loggo... fregandomi di user e PW perche' auth e' false
			DataServiceResponse resp = checkDeviceAccess(uuid);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_304));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse checkDeviceAccess(String deviceUUID, Parameter... params) throws PropertyNotFoundException {
		// UUID in input, perche' non c'e' la searchAll e lo devo creare prima String username, String password,
		// prendo un qualsiasi party, tanto su avs hanno tutti almeno una credenziale
		String username = Utils.readParam(String.class, params, 0, PartyConfigurator.getUsernameRandom(getRandomPartyId(), tenantName));
		// non posso decriptare le password, quindi tantovale che ne sparo una a caso
		String password = Utils.readParam(String.class, params, 1, Utils.getRandomName(40));
		// casuale, tanto la configurazione in false annulla tutto
		// String authMode = Utils.readParam(String.class, params, 2,
		// Utils.coinFlip() ? DeviceEnums.AuthMode.BASIC.getValue() : DeviceEnums.AuthMode.PAIRING.getValue());
		// di default metto basic, altrimenti salta la validazione
		String authMode = Utils.readParam(String.class, params, 2, DeviceEnums.AuthMode.BASIC.getValue());
		return manager.checkDeviceAccess(deviceUUID, username, password, authMode, tenantName);
	}

	// //////////////////////
	// UPDATE DEVICE
	// //////////////////////

	@Test
	public void testUpdateDevice000() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// creo un device
			String uuid = UUID.randomUUID().toString();
			registerDevice(new Parameter(uuid));
			// update
			DataServiceResponse resp = updateDevice(uuid);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateDevice010() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = updateDevice(null);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateDevice020() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// creo un device
			String uuid = UUID.randomUUID().toString();
			registerDevice(new Parameter(uuid));
			// brand e model
			Parameter[] array = { Parameter.RANDOM, Parameter.RANDOM };
			// li testo tutti
			for (int i = 0; i < array.length; i++) {
				array[i] = new Parameter(Utils.getRandomNamePrefixed());
				DataServiceResponse resp = updateDevice(uuid, array);
				printResponse(resp, Utilities.getCurrentClassAndMethod());
				assertTrue(resp.getResultCode().equals(TestConstants.CODE_020));
				array[i] = Parameter.RANDOM;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateDevice100() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = updateDevice(Utils.getRandomNamePrefixed());
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_100));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse updateDevice(String deviceUUID, Parameter... params) throws PropertyNotFoundException {
		// UUID in input, non posso farlo casuale (no search all)
		String deviceBrand = Utils.readParam(String.class, params, 0, DeviceConfigurator.getBrandRandom(tenantName).getBrandName());
		String deviceModel = Utils.readParam(String.class, params, 1, DeviceConfigurator.getModelRandom(tenantName).getModelName());
		String deviceAlias = Utils.readParam(String.class, params, 2, Utils.getRandomNamePrefixed());
		return manager.updateDevice(deviceUUID, deviceBrand, deviceModel, deviceAlias, tenantName);
	}

	@Test
	public void testUpdateDeviceLastFruitionDate000() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// creo un device
			String uuid = UUID.randomUUID().toString();
			registerDevice(new Parameter(uuid));
			// update
			DataServiceResponse resp = updateDeviceLastFruitionDate(uuid);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateDeviceLastFruitionDate010() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// creo un device
			String uuid = UUID.randomUUID().toString();
			registerDevice(new Parameter(uuid));
			// prima manca l'id
			DataServiceResponse resp = updateDeviceLastFruitionDate(null);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
			// poi la data
			resp = updateDeviceLastFruitionDate(uuid, Parameter.NULL);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateDeviceLastFruitionDate100() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = updateDeviceLastFruitionDate(Utils.getRandomNamePrefixed());
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_100));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse updateDeviceLastFruitionDate(String deviceUUID, Parameter... params) throws PropertyNotFoundException {
		// UUID in input, non posso farlo casuale (no search all)
		Date lastFruitionDate = Utils.readParam(Date.class, params, 0, new Date());
		return manager.updateDeviceLastFruitionDate(deviceUUID, lastFruitionDate, tenantName);
	}

	// //////////////////////
	// UNREGISTER DEVICE
	// //////////////////////

	@Test
	public void testUnregisterDevice000() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// creo un device
			String uuid = UUID.randomUUID().toString();
			registerDevice(new Parameter(uuid));
			// unregister
			DataServiceResponse resp = unregisterDevice(uuid);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUnregisterDevice010() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = unregisterDevice(null);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUnregisterDevice100() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = unregisterDevice(Utils.getRandomNamePrefixed());
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_100));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUnregisterDevice140() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// creo un device
			String uuid = UUID.randomUUID().toString();
			registerDevice(new Parameter(uuid));
			// lo disattivo
			unregisterDevice(uuid);
			// e lo ridisattivo
			DataServiceResponse resp = unregisterDevice(uuid);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_140));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse unregisterDevice(String deviceUUID) throws PropertyNotFoundException {
		return manager.unregisterDevice(deviceUUID, tenantName);
	}

	// //////////////////////
	// SEARCH DEVICE
	// //////////////////////

	@Test
	public void testSearchDeviceByDeviceId000() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// creo un device
			String uuid = UUID.randomUUID().toString();
			registerDevice(new Parameter(uuid));
			// ricerca
			DataServiceResponse resp = searchDeviceByDeviceId(uuid);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSearchDeviceByDeviceId010() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = searchDeviceByDeviceId(null);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSearchDeviceByDeviceId100() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = searchDeviceByDeviceId(Utils.getRandomNamePrefixed());
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_100));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse searchDeviceByDeviceId(String deviceUUID) throws PropertyNotFoundException {
		return manager.searchDeviceById(deviceUUID, tenantName);
	}

	@Test
	public void testSearchDevicesByPartyId000() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// creo un device ad un party
			Long partyId = getRandomPartyId();
			createDeviceToParty(partyId);
			// ricerca
			DataServiceResponse resp = searchDevicesByPartyId(partyId);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private DataServiceResponse createDeviceToParty(Long partyId) throws PropertyNotFoundException {
		return registerDevice(Parameter.RANDOM, Parameter.RANDOM, Parameter.RANDOM, Parameter.RANDOM, Parameter.RANDOM, Parameter.RANDOM, new Parameter(
				PartyConfigurator.getUsernameRandom(partyId, tenantName)));
	}

	@Test
	public void testSearchDevicesByPartyId010() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = searchDevicesByPartyId(null);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSearchDevicesByPartyId100() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = searchDevicesByPartyId(Utils.getGreaterRandomLong(1000000000));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_100));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSearchDevicesByPartyId130() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// prendo un party a caso e gli resetto i device
			Long partyId = getRandomPartyId();
			DeviceConfigurator.resetPartyDevice(partyId, tenantName);
			// ricerca
			DataServiceResponse resp = searchDevicesByPartyId(partyId);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_130));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse searchDevicesByPartyId(Long partyId) throws PropertyNotFoundException {
		return manager.searchDevicesByPartyId(partyId, tenantName);
	}

	@Test
	public void testSearchDeviceCountersByPartyId000() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// creo un device ad un party
			Long partyId = getRandomPartyId();
			createDeviceToParty(partyId);
			// ricerca
			DataServiceResponse resp = searchDeviceCountersByPartyId(partyId);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSearchDeviceCountersByPartyId010() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = searchDeviceCountersByPartyId(null);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSearchDeviceCountersByPartyId100() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = searchDeviceCountersByPartyId(Utils.getGreaterRandomLong(1000000000));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_100));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSearchDeviceCountersByPartyId130() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// prendi un party casuale e gli resetto i dati di Ext
			Long partyId = getRandomPartyId();
			DeviceConfigurator.resetPartyDevice(partyId, tenantName);
			// ricerca
			DataServiceResponse resp = searchDeviceCountersByPartyId(partyId);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_130));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse searchDeviceCountersByPartyId(Long partyId) throws PropertyNotFoundException {
		return SdpPartyDeviceManager.getInstance().searchDeviceCountersByPartyId(partyId, tenantName);
	}

	// //////////////////////
	// RESET DEVICE
	// //////////////////////

	@Test
	public void testResetDeviceAssociations000() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// creo un device ad un party
			Long partyId = getRandomPartyId();
			createDeviceToParty(partyId);
			// reset
			DataServiceResponse resp = resetDeviceAssociations(partyId);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testResetDeviceAssociations010() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = resetDeviceAssociations(null);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testResetDeviceAssociations100() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = resetDeviceAssociations(Utils.getGreaterRandomLong(1000000000));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_100));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse resetDeviceAssociations(Long partyId) throws PropertyNotFoundException {
		return SdpPartyDeviceManager.getInstance().resetDeviceAssociations(partyId, tenantName);
	}

	@Test
	public void testResetDeviceSafetyPeriod000() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			// creo device e rimuovo ad un party finche' non scoppia
			Long partyId = getRandomPartyId();
			// resetto ogni collegamento coi device, cosi' carichera' policy default
			DeviceConfigurator.resetPartyDevice(partyId, tenantName);
			DataServiceResponse registerResp = null;
			do {
				String uuid = UUID.randomUUID().toString();
				registerResp = createDeviceToParty(partyId);
				unregisterDevice(uuid);
			} while (registerResp.getResultCode().equals(TestConstants.CODE_OK));
			// reset
			DataServiceResponse resp = resetDeviceSafetyPeriod(partyId);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testResetDeviceSafetyPeriod010() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = resetDeviceSafetyPeriod(null);
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_010));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testResetDeviceSafetyPeriod100() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = resetDeviceSafetyPeriod(Utils.getGreaterRandomLong(1000000000));
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_100));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse resetDeviceSafetyPeriod(Long partyId) throws PropertyNotFoundException {
		return SdpPartyDeviceManager.getInstance().resetDeviceSafetyPeriod(partyId, tenantName);
	}
}
