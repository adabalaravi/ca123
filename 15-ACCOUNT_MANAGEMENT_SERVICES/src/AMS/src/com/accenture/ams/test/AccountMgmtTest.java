package com.accenture.ams.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import junit.framework.TestCase;

import org.junit.Test;

import types.accountmgmt.avs.accenture.AccountMgmtResponse;
import types.accountmgmt.avs.accenture.CrmAccountMgmtRequest;

import com.accenture.ams.db.dao.AccountManagementDAO;
import com.accenture.ams.accountmgmtservice.AccountMgmtServiceConstant;
import com.accenture.ams.accountmgmtservice.DbErrorException;
import com.accenture.ams.accountmgmtservice.GenericException;
import com.accenture.ams.accountmgmtservice.MissingParameterException;
import com.accenture.ams.accountmgmtservice.Utils;
import com.accenture.ams.accountmgmtservice.crmAccountMgmt.ActionExecutor;
import com.accenture.ams.accountmgmtservice.crmAccountMgmt.InsertCrmAccount;
import com.accenture.ams.accountmgmtservice.crmAccountMgmt.UpdateCrmAccount;
import com.accenture.ams.test.dao.ServiceDAOtest;
import com.accenture.ams.db.dao.UserDAO;
import com.accenture.ams.db.util.LogUtil;

import commontypes.accountmgmt.avs.accenture.FlagTypeMF;
import commontypes.accountmgmt.avs.accenture.FlagTypeYN;
import commontypes.accountmgmt.avs.accenture.OperationProfileType;
import commontypes.accountmgmt.avs.accenture.PayloadType;
import commontypes.accountmgmt.avs.accenture.UpdateProfileListType;
import commontypes.accountmgmt.avs.accenture.UpdateProfileType;

/**
 * These tests verify the data inserted or updated into the database by
 * CrmAccountMgmtService
 * 
 * @author Valentina Bonafaccia
 * 
 */
public class AccountMgmtTest extends TestCase {

	AccountManagementDAO dao = new AccountManagementDAO();
	ServiceDAOtest daoTest = new ServiceDAOtest();
	UserDAO userDAO = new UserDAO();
	String tenantName = "";
	
	PayloadType testPayload = null;
	CrmAccountMgmtRequest request = null;

	// Set Variables for Tests
	// Always check whether these data are valid in the tables
	long countBefore = 0l;

	String operationTypeCreate = "1";
	String operationTypeUpdate = "2";

	String crmAccountIdNew = "1200000000";
	String crmAccountIdOld = "TEST_15";

	String channelPCTV = "PCTV";
	String channelIPAD = "IPAD";
	String channelANDROID = "ANDROID";
	String channelGLOBO = "GLOBO";
	String channelOTTSTB = "OTTSTB";

	String channelSTB_HTV = "STB_HTV";
	String channelHBTV = "HBTV";

	String name = "Name01";
	String testName = "TestName";

	String surname = "Surname01";
	String testSurname = "TestSurname";

	String email = "surname.nome1@group.com";
	String testEmailKO = "testtestit";
	String testEmailOK = "test.test@test.com";

	String userName = "useruser";
	String password = "passpass";

	String mobilePhone = "067948397";
	String mobileNumber = "0606060606777777";

	String zipCode = "00041";
	String zipCodeKO = "41";

	String deviceId = "00:00:00:00:00:25";

	XMLGregorianCalendar birthday = null;

	BigDecimal bigDec = new BigDecimal(0.002);

	// It simulates the request
	public AccountMgmtResponse crmAccountMgmtServiceTest(CrmAccountMgmtRequest crmAccountMgmtRequest) {

		String opType = crmAccountMgmtRequest.getOperationType();
		ActionExecutor ae;
		if (opType.equalsIgnoreCase(AccountMgmtServiceConstant.INSERT)) {
			ae = new InsertCrmAccount(tenantName);
		} else {
			ae = new UpdateCrmAccount(tenantName);
		}

		ae.setPayload(crmAccountMgmtRequest.getPayload());

		try {
			return ae.execute();
		} catch (MissingParameterException e) {
			return Utils.getResponse(AccountMgmtServiceConstant.RET_MISSING_PARAM, e.getMessage());
		} catch (DbErrorException dbe) {
			return Utils.getResponse(AccountMgmtServiceConstant.RET_DB_ERROR, dbe.getMessage());
		} catch (GenericException ge) {
			return Utils.getResponse(AccountMgmtServiceConstant.RET_GENERIC_ERROR, ge.getMessage());
		}
	}

	// It simulates ProfileListType
	private UpdateProfileListType getList() {
		UpdateProfileType p1 = new UpdateProfileType();
		UpdateProfileType p2 = new UpdateProfileType();
		UpdateProfileListType l = new UpdateProfileListType();
		p1.setCrmProductId(new Long("1"));
		p2.setCrmProductId(new Long("2"));
		p1.setOperationType(OperationProfileType.ADD);
		p2.setOperationType(OperationProfileType.ADD);

		l.getUpdateCommProfOp().add(p1);
		l.getUpdateCommProfOp().add(p2);

		return l;
	}

	/**
	 * This test check the insertion of a new CRM_ACCOUNT_ID in CRM_ACCOUNT
	 * table, in ACCOUNT_USER table and ACCOUNT_ATTRIBUTE table
	 * 
	 * Table Before test: change value of crmAccountIdNew
	 * 
	 * @throws Exception
	 */
//	@Test
//	public final void testCreateAccountMgmtOK() throws Exception {
//		countBefore = daoTest.counter();
//
//		System.out.println("COUNT CRM_ACCOUNT BEFORE: " + countBefore);
//		testPayload = new PayloadType();
//		UpdateProfileListType list = getList();
//		testPayload.setCrmAccountId(crmAccountIdNew);
//		testPayload.setChannel(channelIPAD);
//		testPayload.setCrmAccountDeviceIdType(5);
//		testPayload.setUsername(userName);
//		testPayload.setPassword(password);
//		testPayload.setName(name);
//		testPayload.setSurname(surname);
//		testPayload.setCrmAccountPurchaseBlockingThresholdValue(bigDec);
//		testPayload.setCrmAccountPurchaseBlockingThresholdPeriod("1");
//		testPayload.setCrmAccountPurchaseBlockingAlertEnabledFlag(FlagTypeYN.Y);
//		testPayload.setCrmAccountPurchaseBlockingAlertChannel("3");
//		testPayload.setCrmAccountPurchaseBlockingAlertMode("2");
//		testPayload.setCrmAccountPurchaseBlockingIntermediateThreshold(bigDec);
//		testPayload.setCrmAccountConsumptionBlockingThresholdValue(10);
//		testPayload.setCrmAccountConsumptionBlockingThresholdPeriod("2");
//		testPayload.setUpdateCommProfileList(list);
//		testPayload.setCrmAccountDeviceId(deviceId);
//		testPayload.setCrmAccountRetailerDomain("");
//		testPayload.setCrmAccountConsumption(10);
//		testPayload.setCrmAccountPurchaseValue(bigDec);
//		testPayload.setCrmAccountRetailerDomain("1");
//		// nel design i campi sottoriportati non sono richiesti ma nel codice
//		// risultano obbligatori
//		testPayload.setUserType("");
//		testPayload.setEmail(email);
//		testPayload.setCrmAccountMobileNumber(mobileNumber);
//		testPayload.setUserStatus(1);
//
//		testPayload.setGender(FlagTypeMF.F);
//
//		request = new CrmAccountMgmtRequest();
//		request.setOperationType(operationTypeCreate);
//		request.setPayload(testPayload);		
//
//		AccountMgmtResponse response = crmAccountMgmtServiceTest(request);
//
//		long countAfter = daoTest.counter();
//		System.out.println("COUNT CRM_ACCOUNT AFTER: " + countAfter);
//
//		assertTrue("Insert error", countAfter == countBefore + 1);
//		assertTrue("Insert error", dao.isCrmAccountIdPresent(crmAccountIdNew));
//		assertTrue("Insert error", userDAO.isCrmAccountIdPresentAccountUser(crmAccountIdNew));
//
//		System.out.println("CRM ACCOUNT ID INSERTED: " + crmAccountIdNew);
//
//	}

	/**
	 * This test check the insertion of a new CRM_ACCOUNT_ID in CRM_ACCOUNT
	 * table, in ACCOUNT_USER table, ACCOUNT_ATTRIBUTE table and ACCOUNT_DEVICE
	 * 
	 * Table Before test: change value of crmAccountIdNew
	 * 
	 * @throws Exception
	 */
//	@Test
//	public final void testCreateChannel() throws Exception {
//		countBefore = daoTest.counter();
//
//		testPayload = new PayloadType();
//		UpdateProfileListType list = getList();
//		testPayload.setCrmAccountId(crmAccountIdNew);
//		testPayload.setChannel(channelPCTV);
//		testPayload.setCrmAccountDeviceIdType(5);
//		testPayload.setUsername(userName);
//		testPayload.setPassword(password);
//		testPayload.setName(name);
//		testPayload.setSurname(surname);
//		testPayload.setCrmAccountPurchaseBlockingThresholdValue(bigDec);
//		testPayload.setCrmAccountPurchaseBlockingThresholdPeriod("1");
//		testPayload.setCrmAccountPurchaseBlockingAlertEnabledFlag(FlagTypeYN.Y);
//		testPayload.setCrmAccountPurchaseBlockingAlertChannel("3");
//		testPayload.setCrmAccountPurchaseBlockingAlertMode("2");
//		testPayload.setCrmAccountPurchaseBlockingIntermediateThreshold(bigDec);
//		testPayload.setCrmAccountConsumptionBlockingThresholdValue(10);
//		testPayload.setCrmAccountConsumptionBlockingThresholdPeriod("2");
//		testPayload.setUpdateCommProfileList(list);
//		testPayload.setCrmAccountDeviceId(deviceId);
//		testPayload.setCrmAccountRetailerDomain("");
//		testPayload.setCrmAccountConsumption(10);
//		testPayload.setCrmAccountPurchaseValue(bigDec);
//		testPayload.setCrmAccountRetailerDomain("1");
//		// nel design i campi sottoriportati non sono richiesti ma nel codice
//		// risultano obbligatori
//		testPayload.setUserType("");
//		testPayload.setEmail(email);
//		testPayload.setCrmAccountMobileNumber("0606060606777777");
//		testPayload.setUserStatus(1);
//
//		request = new CrmAccountMgmtRequest();
//		request.setOperationType(operationTypeCreate);
//		request.setPayload(testPayload);
//
//		System.out.println("COUNT CRM_ACCOUNT BEFORE: " + countBefore);
//
//		AccountMgmtResponse response = crmAccountMgmtServiceTest(request);
//
//		long countAfter = daoTest.counter();
//		System.out.println("COUNT CRM_ACCOUNT AFTER: " + countAfter);
//
//		assertTrue("Insert error", countAfter == countBefore + 1);
//		// It Checks CRM_ACCOUNT
//		assertTrue("Insert error", dao.isCrmAccountIdPresent(crmAccountIdNew));
//		// It Checks ACCOUNT_USER
//		assertTrue("Insert error", userDAO.isCrmAccountIdPresentAccountUser(crmAccountIdNew));
//		// It Checks ACCOUNT_DEVICE
//		assertTrue("Insert error", daoTest.isChannelDevicePresent(crmAccountIdNew));
//		// It Checks ACCOUNT_ATTRIBUTE
//		assertTrue("Insert error", daoTest.isPresentAccountAttribute(crmAccountIdNew));
//
//		System.out.println("CRM ACCOUNT ID INSERTED: " + crmAccountIdNew);
//
//	}

	/**
	 * This test checks the non-inclusion of an existing CRM_ACCOUNT_ID in
	 * CRM_ACCOUNT table, in ACCOUNT_USER table and ACCOUNT_ATTRIBUTE table
	 * 
	 * Table Before test: change value of crmAccountIdNew
	 * 
	 * @throws Exception
	 */
//	@Test
//	public final void testCreateExistingCrmAccount() throws Exception {
//		countBefore = daoTest.counter();
//
//		testPayload = new PayloadType();
//		UpdateProfileListType list = getList();
//		testPayload.setCrmAccountId(crmAccountIdOld);
//		testPayload.setChannel(channelPCTV);
//		testPayload.setCrmAccountDeviceIdType(5);
//		// Nel design c'è scritto che se l'utente non viene specificato,
//		// l'utente di default avrà un nome utente uguale al CrmAccountDeviceId;
//		testPayload.setUsername(userName);
//		testPayload.setPassword(password);
//		testPayload.setName(name);
//		testPayload.setSurname(surname);
//		testPayload.setCrmAccountPurchaseBlockingThresholdValue(bigDec);
//		testPayload.setCrmAccountPurchaseBlockingThresholdPeriod("1");
//		testPayload.setCrmAccountPurchaseBlockingAlertEnabledFlag(FlagTypeYN.Y);
//		testPayload.setCrmAccountPurchaseBlockingAlertChannel("3");
//		testPayload.setCrmAccountPurchaseBlockingAlertMode("2");
//		testPayload.setCrmAccountPurchaseBlockingIntermediateThreshold(bigDec);
//		testPayload.setCrmAccountConsumptionBlockingThresholdValue(10);
//		testPayload.setCrmAccountConsumptionBlockingThresholdPeriod("2");
//		testPayload.setUpdateCommProfileList(list);
//		testPayload.setCrmAccountRetailerDomain("");
//		testPayload.setCrmAccountConsumption(10);
//		testPayload.setCrmAccountPurchaseValue(bigDec);
//		testPayload.setCrmAccountRetailerDomain("1");
//		// nel design i campi sottoriportati non sono richiesti ma nel codice
//		// risultano obbligatori
//		testPayload.setUserType("");
//		testPayload.setEmail(email);
//		testPayload.setCrmAccountMobileNumber("0606060606777777");
//		testPayload.setUserStatus(1);
//
//		request = new CrmAccountMgmtRequest();
//		request.setOperationType(operationTypeCreate);
//		request.setPayload(testPayload);
//
//		System.out.println("REQUEST: " + request);
//
//		AccountMgmtResponse response = crmAccountMgmtServiceTest(request);
//		System.out.println("RESPONSE: " + response);
//
//		long countAfter = daoTest.counter();
//		System.out.println("COUNT CRM_ACCOUNT AFTER: " + countAfter);
//
//		assertTrue("Insert error", countAfter == countBefore);
//		assertTrue("Insert error", dao.isCrmAccountIdPresent(crmAccountIdOld));
//		assertTrue("Insert error", userDAO.isCrmAccountIdPresentAccountUser(crmAccountIdOld));
//
//		System.out.println("Insertion failed. CRM ACCOUNT ID is already inserted: " + crmAccountIdOld);
//
//		// La data create e update non viene inserita correttamente
//	}

	/**
	 * This test check the insertion of a new CrmProductId in ACCOUNT_PRODUCT
	 * table
	 * 
	 * @throws Exception
	 */
//	@Test
//	public final void testCreateAccountProduct() throws Exception {
//		List products = userDAO.isCrmProductPresent(crmAccountIdNew);
//		assertTrue("Insert CrmProductId error", products == null);
//
//	}

	/**
	 * This test checks for changes gender as an CrmAccountId in CRM_ACCOUNT
	 * table
	 * 
	 * 
	 * @throws Exception
	 */
//	@Test
//	public final void testUpdateGender() throws Exception {
//
//		testPayload = new PayloadType();
//		UpdateProfileListType list = getList();
//		testPayload.setCrmAccountId(crmAccountIdNew);
//		testPayload.setChannel(channelPCTV);
//		testPayload.setCrmAccountDeviceIdType(5);
//		testPayload.setCrmAccountPurchaseBlockingThresholdValue(bigDec);
//		testPayload.setCrmAccountPurchaseBlockingThresholdPeriod("1");
//		testPayload.setCrmAccountPurchaseBlockingAlertEnabledFlag(FlagTypeYN.Y);
//		testPayload.setCrmAccountPurchaseBlockingAlertChannel("3");
//		testPayload.setCrmAccountPurchaseBlockingAlertMode("2");
//		testPayload.setCrmAccountPurchaseBlockingIntermediateThreshold(bigDec);
//		testPayload.setCrmAccountConsumptionBlockingThresholdValue(10);
//		testPayload.setCrmAccountConsumptionBlockingThresholdPeriod("2");
//		testPayload.setUpdateCommProfileList(list);
//		testPayload.setCrmAccountRetailerDomain("");
//		testPayload.setCrmAccountConsumption(10);
//		testPayload.setCrmAccountPurchaseValue(bigDec);
//		testPayload.setCrmAccountRetailerDomain("1");
//
//		// Modifica campo Gender
//		testPayload.setGender(FlagTypeMF.M);
//
//		request = new CrmAccountMgmtRequest();
//		request.setOperationType(operationTypeUpdate);
//		request.setPayload(testPayload);
//
//		AccountMgmtResponse response = crmAccountMgmtServiceTest(request);
//
//		assertTrue("Update error", dao.isCrmAccountIdPresent(crmAccountIdNew));
//		assertTrue("Update error", daoTest.isFlagPresent(crmAccountIdNew, "sesso", FlagTypeMF.M));
//
//		System.out.println("CRM ACCOUNT ID UPDATED: " + crmAccountIdNew);
//		System.out.println("FIELD UPDATED: GENDER " + FlagTypeMF.M);
//
//	}

	/**
	 * This test checks for changes name as an CrmAccountId in CRM_ACCOUNT table
	 * 
	 * 
	 * @throws Exception
	 */
//	@Test
//	public final void testUpdateName() throws Exception {
//
//		testPayload = new PayloadType();
//		UpdateProfileListType list = getList();
//		testPayload.setCrmAccountId(crmAccountIdNew);
//		testPayload.setChannel(channelPCTV);
//		testPayload.setCrmAccountDeviceIdType(5);
//		testPayload.setCrmAccountPurchaseBlockingThresholdValue(bigDec);
//		testPayload.setCrmAccountPurchaseBlockingThresholdPeriod("1");
//		testPayload.setCrmAccountPurchaseBlockingAlertEnabledFlag(FlagTypeYN.Y);
//		testPayload.setCrmAccountPurchaseBlockingAlertChannel("3");
//		testPayload.setCrmAccountPurchaseBlockingAlertMode("2");
//		testPayload.setCrmAccountPurchaseBlockingIntermediateThreshold(bigDec);
//		testPayload.setCrmAccountConsumptionBlockingThresholdValue(10);
//		testPayload.setCrmAccountConsumptionBlockingThresholdPeriod("2");
//		testPayload.setUpdateCommProfileList(list);
//		testPayload.setCrmAccountRetailerDomain("");
//		testPayload.setCrmAccountConsumption(10);
//		testPayload.setCrmAccountPurchaseValue(bigDec);
//		testPayload.setCrmAccountRetailerDomain("1");
//		// Modifica campo Name
//		testPayload.setName(testName);
//
//		request = new CrmAccountMgmtRequest();
//		request.setOperationType(operationTypeUpdate);
//		request.setPayload(testPayload);
//
//		AccountMgmtResponse response = crmAccountMgmtServiceTest(request);
//
//		assertTrue("Update error", dao.isCrmAccountIdPresent(crmAccountIdNew));
//		assertTrue("Update error", daoTest.isFieldPresent(crmAccountIdNew, "name", testName));
//
//		System.out.println("CRM ACCOUNT ID UPDATED: " + crmAccountIdNew);
//		System.out.println("FIELD UPDATED: NAME " + testName);
//
//	}

	/**
	 * This test checks for changes email as an CrmAccountId in CRM_ACCOUNT
	 * table
	 * 
	 * 
	 * @throws Exception
	 */
//	@Test
//	public final void testUpdateEmail() throws Exception {
//
//		testPayload = new PayloadType();
//		UpdateProfileListType list = getList();
//		testPayload.setCrmAccountId(crmAccountIdNew);
//		testPayload.setChannel(channelPCTV);
//		testPayload.setCrmAccountDeviceIdType(5);
//		testPayload.setCrmAccountPurchaseBlockingThresholdValue(bigDec);
//		testPayload.setCrmAccountPurchaseBlockingThresholdPeriod("1");
//		testPayload.setCrmAccountPurchaseBlockingAlertEnabledFlag(FlagTypeYN.Y);
//		testPayload.setCrmAccountPurchaseBlockingAlertChannel("3");
//		testPayload.setCrmAccountPurchaseBlockingAlertMode("2");
//		testPayload.setCrmAccountPurchaseBlockingIntermediateThreshold(bigDec);
//		testPayload.setCrmAccountConsumptionBlockingThresholdValue(10);
//		testPayload.setCrmAccountConsumptionBlockingThresholdPeriod("2");
//		testPayload.setUpdateCommProfileList(list);
//		testPayload.setCrmAccountRetailerDomain("");
//		testPayload.setCrmAccountConsumption(10);
//		testPayload.setCrmAccountPurchaseValue(bigDec);
//		testPayload.setCrmAccountRetailerDomain("1");
//		// Modifica campo Email
//		testPayload.setEmail(testEmailOK);
//
//		request = new CrmAccountMgmtRequest();
//		request.setOperationType(operationTypeUpdate);
//		request.setPayload(testPayload);
//
//		AccountMgmtResponse response = crmAccountMgmtServiceTest(request);
//
//		assertTrue("Update error", dao.isCrmAccountIdPresent(crmAccountIdNew));
//		assertTrue("Update error", daoTest.isFieldPresent(crmAccountIdNew, "email", testEmailOK));
//
//		System.out.println("CRM ACCOUNT ID UPDATED: " + crmAccountIdNew);
//		System.out.println("FIELD UPDATED: EMAIL " + testEmailOK);
//
//	}

	/**
	 * This test checks for changes surname as an CrmAccountId in CRM_ACCOUNT
	 * table
	 * 
	 * 
	 * @throws Exception
	 */
//	@Test
//	public final void testUpdateSurname() throws Exception {
//
//		testPayload = new PayloadType();
//		UpdateProfileListType list = getList();
//		testPayload.setCrmAccountId(crmAccountIdNew);
//		testPayload.setChannel(channelPCTV);
//		testPayload.setCrmAccountDeviceIdType(5);
//		testPayload.setCrmAccountPurchaseBlockingThresholdValue(bigDec);
//		testPayload.setCrmAccountPurchaseBlockingThresholdPeriod("1");
//		testPayload.setCrmAccountPurchaseBlockingAlertEnabledFlag(FlagTypeYN.Y);
//		testPayload.setCrmAccountPurchaseBlockingAlertChannel("3");
//		testPayload.setCrmAccountPurchaseBlockingAlertMode("2");
//		testPayload.setCrmAccountPurchaseBlockingIntermediateThreshold(bigDec);
//		testPayload.setCrmAccountConsumptionBlockingThresholdValue(10);
//		testPayload.setCrmAccountConsumptionBlockingThresholdPeriod("2");
//		testPayload.setUpdateCommProfileList(list);
//		testPayload.setCrmAccountRetailerDomain("");
//		testPayload.setCrmAccountConsumption(10);
//		testPayload.setCrmAccountPurchaseValue(bigDec);
//		testPayload.setCrmAccountRetailerDomain("1");
//		// Modifica campo Surname
//		testPayload.setSurname(testSurname);
//
//		request = new CrmAccountMgmtRequest();
//		request.setOperationType(operationTypeUpdate);
//		request.setPayload(testPayload);
//
//		AccountMgmtResponse response = crmAccountMgmtServiceTest(request);
//
//		assertTrue("Update error", dao.isCrmAccountIdPresent(crmAccountIdNew));
//		assertTrue("Update error", daoTest.isFieldPresent(crmAccountIdNew, "surname", testSurname));
//
//		System.out.println("CRM ACCOUNT ID UPDATED: " + crmAccountIdNew);
//		System.out.println("FIELD UPDATED: SURNAME " + testSurname);
//
//	}

	/**
	 * This test checks for changes mobile phone as an CrmAccountId in
	 * CRM_ACCOUNT table
	 * 
	 * 
	 * @throws Exception
	 */
//	@Test
//	public final void testUpdateMobilePhone() throws Exception {
//
//		testPayload = new PayloadType();
//		UpdateProfileListType list = getList();
//		testPayload.setCrmAccountId(crmAccountIdNew);
//		testPayload.setChannel(channelPCTV);
//		testPayload.setCrmAccountDeviceIdType(5);
//		testPayload.setCrmAccountPurchaseBlockingThresholdValue(bigDec);
//		testPayload.setCrmAccountPurchaseBlockingThresholdPeriod("1");
//		testPayload.setCrmAccountPurchaseBlockingAlertEnabledFlag(FlagTypeYN.Y);
//		testPayload.setCrmAccountPurchaseBlockingAlertChannel("3");
//		testPayload.setCrmAccountPurchaseBlockingAlertMode("2");
//		testPayload.setCrmAccountPurchaseBlockingIntermediateThreshold(bigDec);
//		testPayload.setCrmAccountConsumptionBlockingThresholdValue(10);
//		testPayload.setCrmAccountConsumptionBlockingThresholdPeriod("2");
//		testPayload.setUpdateCommProfileList(list);
//		testPayload.setCrmAccountRetailerDomain("");
//		testPayload.setCrmAccountConsumption(10);
//		testPayload.setCrmAccountPurchaseValue(bigDec);
//		testPayload.setCrmAccountRetailerDomain("1");
//		// Modifica campo mobile phone
//		testPayload.setCrmAccountMobileNumber(mobilePhone);
//
//		request = new CrmAccountMgmtRequest();
//		request.setOperationType(operationTypeUpdate);
//		request.setPayload(testPayload);
//
//		AccountMgmtResponse response = crmAccountMgmtServiceTest(request);
//
//		assertTrue("Update error", dao.isCrmAccountIdPresent(crmAccountIdNew));
//		assertTrue("Update error", daoTest.isFieldPresent(crmAccountIdNew, "mobilePhone", mobilePhone));
//
//		System.out.println("CRM ACCOUNT ID UPDATED: " + crmAccountIdNew);
//		System.out.println("FIELD UPDATED: MOBILE PHONE " + mobilePhone);
//
//	}

	/**
	 * This test checks for changes zip code as an CrmAccountId in CRM_ACCOUNT
	 * table
	 * 
	 * 
	 * @throws Exception
	 */
//	@Test
//	public final void testUpdateZipCode() throws Exception {
//
//		testPayload = new PayloadType();
//		UpdateProfileListType list = getList();
//		testPayload.setCrmAccountId(crmAccountIdNew);
//		testPayload.setChannel(channelPCTV);
//		testPayload.setCrmAccountDeviceIdType(5);
//		testPayload.setCrmAccountPurchaseBlockingThresholdValue(bigDec);
//		testPayload.setCrmAccountPurchaseBlockingThresholdPeriod("1");
//		testPayload.setCrmAccountPurchaseBlockingAlertEnabledFlag(FlagTypeYN.Y);
//		testPayload.setCrmAccountPurchaseBlockingAlertChannel("3");
//		testPayload.setCrmAccountPurchaseBlockingAlertMode("2");
//		testPayload.setCrmAccountPurchaseBlockingIntermediateThreshold(bigDec);
//		testPayload.setCrmAccountConsumptionBlockingThresholdValue(10);
//		testPayload.setCrmAccountConsumptionBlockingThresholdPeriod("2");
//		testPayload.setUpdateCommProfileList(list);
//		testPayload.setCrmAccountRetailerDomain("");
//		testPayload.setCrmAccountConsumption(10);
//		testPayload.setCrmAccountPurchaseValue(bigDec);
//		testPayload.setCrmAccountRetailerDomain("1");
//		// Modifica campo zip code
//		testPayload.setCrmAccountZipCode(zipCodeKO);
//
//		request = new CrmAccountMgmtRequest();
//		request.setOperationType(operationTypeUpdate);
//		request.setPayload(testPayload);
//
//		AccountMgmtResponse response = crmAccountMgmtServiceTest(request);
//
//		assertTrue("Update error", dao.isCrmAccountIdPresent(crmAccountIdNew));
//		assertTrue("Update error", daoTest.isFieldPresent(crmAccountIdNew, "zipCode", zipCodeKO));
//
//		System.out.println("CRM ACCOUNT ID UPDATED: " + crmAccountIdNew);
//		System.out.println("FIELD UPDATED: ZIP CODE " + zipCodeKO);
//
//	}

	/**
	 * This test checks for changes birthdate as an CrmAccountId in CRM_ACCOUNT
	 * table
	 * 
	 * 
	 * @throws Exception
	 */
//	@Test
//	public final void testUpdateBirthDay() throws Exception {
//
//		testPayload = new PayloadType();
//		UpdateProfileListType list = getList();
//		testPayload.setCrmAccountId(crmAccountIdNew);
//		testPayload.setChannel(channelPCTV);
//		testPayload.setCrmAccountDeviceIdType(5);
//		testPayload.setCrmAccountPurchaseBlockingThresholdValue(bigDec);
//		testPayload.setCrmAccountPurchaseBlockingThresholdPeriod("1");
//		testPayload.setCrmAccountPurchaseBlockingAlertEnabledFlag(FlagTypeYN.Y);
//		testPayload.setCrmAccountPurchaseBlockingAlertChannel("3");
//		testPayload.setCrmAccountPurchaseBlockingAlertMode("2");
//		testPayload.setCrmAccountPurchaseBlockingIntermediateThreshold(bigDec);
//		testPayload.setCrmAccountConsumptionBlockingThresholdValue(10);
//		testPayload.setCrmAccountConsumptionBlockingThresholdPeriod("2");
//		testPayload.setUpdateCommProfileList(list);
//		testPayload.setCrmAccountRetailerDomain("");
//		testPayload.setCrmAccountConsumption(10);
//		testPayload.setCrmAccountPurchaseValue(bigDec);
//		testPayload.setCrmAccountRetailerDomain("1");
//		testPayload.setCrmAccountZipCode(zipCodeKO);
//		// Modifica campo birthday
//		testPayload.setBirthDate(birthday);
//
//		request = new CrmAccountMgmtRequest();
//		request.setOperationType(operationTypeUpdate);
//		request.setPayload(testPayload);
//
//		AccountMgmtResponse response = crmAccountMgmtServiceTest(request);
//
//		assertTrue("Update error", dao.isCrmAccountIdPresent(crmAccountIdNew));
//		
//		assertTrue("Update error", daoTest.isFieldPresent(crmAccountIdNew, "birthday", birthday.toString()));
//
//		System.out.println("CRM ACCOUNT ID UPDATED: " + crmAccountIdNew);
//		System.out.println("FIELD UPDATED: BIRTHDAY " + birthday);
//	}
	
	/**
	 * This test checks for adds or changes device_id in ACCOUNT_DEVICE
	 * table
	 * 
	 * 
	 * @throws Exception
	 */
//	@Test
//	public final void testUpdateDeviceId() throws Exception {
//
//		testPayload = new PayloadType();
//		UpdateProfileListType list = getList();
//		testPayload.setCrmAccountId("20000000106104");
//		testPayload.setChannel(channelPCTV);
//		testPayload.setCrmAccountDeviceIdType(5);
//		testPayload.setCrmAccountDeviceId(deviceId);
//		testPayload.setCrmAccountPurchaseBlockingThresholdValue(bigDec);
//		testPayload.setCrmAccountPurchaseBlockingThresholdPeriod("1");
//		testPayload.setCrmAccountPurchaseBlockingAlertEnabledFlag(FlagTypeYN.Y);
//		testPayload.setCrmAccountPurchaseBlockingAlertChannel("3");
//		testPayload.setCrmAccountPurchaseBlockingAlertMode("2");
//		testPayload.setCrmAccountPurchaseBlockingIntermediateThreshold(bigDec);
//		testPayload.setCrmAccountConsumptionBlockingThresholdValue(10);
//		testPayload.setCrmAccountConsumptionBlockingThresholdPeriod("2");
//		testPayload.setUpdateCommProfileList(list);
//		testPayload.setCrmAccountRetailerDomain("");
//		testPayload.setCrmAccountConsumption(10);
//		testPayload.setCrmAccountPurchaseValue(bigDec);
//		testPayload.setCrmAccountRetailerDomain("1");
//		testPayload.setCrmAccountZipCode(zipCodeKO);
//		testPayload.setBirthDate(birthday);
//
//		request = new CrmAccountMgmtRequest();
//		request.setOperationType(operationTypeUpdate);
//		request.setPayload(testPayload);
//
//		System.out.println("REQUEST: " + request);
//
//		AccountMgmtResponse response = crmAccountMgmtServiceTest(request);
//		System.out.println("RESPONSE: " + response);
//		String newDeviceId = daoTest.getDeviceId(crmAccountIdNew);
//		assertTrue("Update error", dao.isCrmAccountIdPresent(crmAccountIdNew));
//		L'assert non andrà bene finchè in tabella saranno registrati più deviceId per lo stesso utente 
//		assertTrue("Update error", newDeviceId.equalsIgnoreCase(deviceId));
//
//		System.out.println("CRM ACCOUNT ID UPDATED: " + crmAccountIdNew);
//
//	}

}
