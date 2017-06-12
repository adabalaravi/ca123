package com.accenture.ams.test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import types.accountmgmt.avs.accenture.UpdateCrmAccountCommercialProfileRequest;

import com.accenture.ams.db.bean.AccountProductBean;
import com.accenture.ams.db.bean.AccountTechnicalPkg;
import com.accenture.ams.db.bean.AccountUserBean;
import com.accenture.ams.accountmgmtservice.updateCrmAccountCommercialProfile.InsertCrmAccCommProfileManager;
import com.accenture.ams.accountmgmtservice.updateCrmAccountCommercialProfile.UpdateComProfService;
import com.accenture.ams.test.dao.ServiceDAOtest;
import commontypes.accountmgmt.avs.accenture.OperationProfileType;
import commontypes.accountmgmt.avs.accenture.UpdateProfileListType;
import commontypes.accountmgmt.avs.accenture.UpdateProfileType;

/**
 * These tests verify that UpdateCrmAccountCommercialProfile service Update
 * account commercial profile.
 * 
 * @author Valentina Bonafaccia
 * 
 */
public class UpdCrmAccountCommProfTest extends TestCase {

	ServiceDAOtest dao = new ServiceDAOtest();
	String tenantName = "";
	// It simulates ProfileListType for insert
	private List<UpdateProfileType> getList() {
		UpdateProfileType p1 = new UpdateProfileType();
		UpdateProfileType p2 = new UpdateProfileType();
		List<UpdateProfileType> l = new ArrayList();
		p1.setCrmProductId(new Long("1"));
		p2.setCrmProductId(new Long("2"));
		p1.setOperationType(OperationProfileType.ADD);
		p2.setOperationType(OperationProfileType.ADD);

		l.add(p1);
		l.add(p2);

		return l;
	}

	// It simulates ProfileListType for Update
	private UpdateProfileListType getUpdateList() {
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

	// It simulates ProfileListType for Delete
	private UpdateProfileListType getDeleteList() {
		UpdateProfileType p1 = new UpdateProfileType();
		UpdateProfileType p2 = new UpdateProfileType();
		UpdateProfileListType l = new UpdateProfileListType();
		p1.setCrmProductId(new Long("1"));
		p2.setCrmProductId(new Long("2"));
		p1.setOperationType(OperationProfileType.DELETE);
		p2.setOperationType(OperationProfileType.DELETE);

		l.getUpdateCommProfOp().add(p1);
		l.getUpdateCommProfOp().add(p2);

		return l;
	}

	/**
	 * Add more than a commercial package to the user profile. *
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public final void testAddTechPackage() throws Exception {
		InsertCrmAccCommProfileManager insert = new InsertCrmAccCommProfileManager(tenantName);

		ArrayList<AccountProductBean> accProductList = insert.getAccountProductList();
		ArrayList<AccountTechnicalPkg> listOfAccTechPack = insert.getAccountTechnicalPkgList();

		Long username = 856072l;
		String bloccoAcquisti = "N";
		int consumptionThreshold = 1080000;
		String crmaccountid = "TEST_97_HOME";
		String flagInvioSmsAlerting = "N";
		String flagInvioSmsBlocking = "N";
		double purchaseAlerting = 1600;
		double purchaseThresholdBlocking = 2400;
		String smsAlerting = "N";
		Long statusId = 1l;

		AccountUserBean au = new AccountUserBean();
		au.setUsername(username);
		au.setBloccoAcquisti(bloccoAcquisti);
		au.setConsumptionThreshold(consumptionThreshold);
		au.setCrmaccountid(crmaccountid);
		au.setFlagInvioSmsAlerting(flagInvioSmsAlerting);
		au.setFlagInvioSmsBlocking(flagInvioSmsBlocking);
		au.setPurchaseAlerting(purchaseAlerting);
		au.setPurchaseThresholdBlocking(purchaseThresholdBlocking);
		au.setSmsAlerting(smsAlerting);
		au.setStatusId(statusId);
		insert.setAccountUser(au);
		insert.setCrmAccountId(crmaccountid);
		List<UpdateProfileType> list = getList();
		String opType = "ADD";

		System.out.println("STARTING INSERT TEST CRM ACCOUNT COMM PACKAGE FOR USER ID : " + username);
		Long numPkgAfter = dao.countAccPackage(crmaccountid, tenantName);
		System.out.println("numPkgAfter: " + numPkgAfter);
		Long numTecPkgAfter = dao.countTecPkg(crmaccountid, tenantName);
		System.out.println("numTecPkgAfter: " + numTecPkgAfter);
		insert.createBeans(list, opType);
		Long numPkgBefore = dao.countAccPackage(crmaccountid, tenantName);
		System.out.println("numPkgBefore: " + numPkgBefore);
		Long numTecPkgBefore = dao.countTecPkg(crmaccountid, tenantName);
		System.out.println("numTecPkgBefore: " + numTecPkgBefore);

		assertFalse("Error insert in ACCOUNT_PRODUCT ", numPkgAfter == numPkgBefore);
		assertFalse("Error insert in ACCOUNT_TECHNICAL_PKG ", numTecPkgAfter == numTecPkgBefore);

		System.out.println("FINISHED INSERT TEST CRM ACCOUNT COMM PACKAGE FOR USER ID : " + username);

	}

	/**
	 * Update more than a commercial package to the user profile.
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public final void testUpdateTechPackage() throws Exception {

		String crmaccountid = "TEST_97_HOME";
		UpdateProfileListType list = getUpdateList();
		UpdateCrmAccountCommercialProfileRequest request = new UpdateCrmAccountCommercialProfileRequest();

		System.out.println("STARTING UPDATE TEST CRM ACCOUNT COMM PACKAGE FOR CRMACCOUNTID : " + crmaccountid);
		request.setCrmAccountId(crmaccountid);
		request.setUpdateCommProfileList(list);

		UpdateComProfService update = new UpdateComProfService(request, tenantName);
		update.execute();

		assertTrue("Error in ACCOUNT_USER", dao.checkTechPack(crmaccountid, tenantName));

		System.out.println("FINISHED UPDATE TEST CRM ACCOUNT COMM PACKAGE FOR CRMACCOUNTID : " + crmaccountid);

	}

	/**
	 * Delete more than a commercial package to the user profile.
	 * 
	 * @throws Exception
	 * 
	 * 
	 */
	@Test
	public final void testDeleteTechPackage() throws Exception {
		String crmaccountid = "TEST_97_HOME";
		UpdateProfileListType list = getDeleteList();
		UpdateCrmAccountCommercialProfileRequest request = new UpdateCrmAccountCommercialProfileRequest();

		System.out.println("STARTING DELETE TEST CRM ACCOUNT COMM PACKAGE FOR CRMACCOUNTID : " + crmaccountid);
		request.setCrmAccountId(crmaccountid);
		request.setUpdateCommProfileList(list);

		UpdateComProfService delete = new UpdateComProfService(request, tenantName);
		delete.execute();

		assertFalse("Error in ACCOUNT_USER", dao.checkTechPack(crmaccountid, tenantName));

		System.out.println("FINISHED DELETE TEST CRM ACCOUNT COMM PACKAGE FOR CRMACCOUNTID : " + crmaccountid);

	}

}
