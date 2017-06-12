package com.accenture.ams.test;

import junit.framework.TestCase;

import org.junit.Test;

import types.accountmgmt.avs.accenture.AccountMgmtResponse;
import types.accountmgmt.avs.accenture.UpdateUserRequest;

import com.accenture.ams.accountmgmtservice.updateUserServices.UpdateUserService;
import com.accenture.ams.test.dao.ServiceDAOtest;
import commontypes.accountmgmt.avs.accenture.FlagTypeYN;

/**
 * These tests verify the data updated into the database by UpdateUserService
 * 
 * @author Valentina Bonafaccia
 * 
 */
public class UpdateUserServiceTest extends TestCase {

	UpdateUserRequest request = null;
	ServiceDAOtest dao = new ServiceDAOtest();

	String crmAccountId = "123123";
	String usernameOld = "utentePIPPO";
	String usernameNew = "userTEST-TEST";
	String tenantName = "";
	/**
	 * This method verifies that the username is changed in the table
	 * ACCOUNT_USER for the user specified in UserName CrmAccountId.
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public final void testUpdateAccountUser() throws Exception {

		request = new UpdateUserRequest();
		request.setCrmAccountId(crmAccountId);
		request.setUsername(usernameOld);
		request.setNewUsername(usernameNew);

		System.out.println("STARTING TEST UPDATE USERNAME ");

		AccountMgmtResponse response = new UpdateUserService(request, tenantName).updateUser();
		String username = dao.getUsername(crmAccountId, tenantName);
		assertTrue("Error in ACCOUNT_USER", username.equalsIgnoreCase(usernameNew));
		System.out.println("ACCOUNT_USER: " + crmAccountId);
		System.out.println("NEW VALUE USERNAME: " + usernameNew);

	}

	/**
	 * Update the attribute status. This data is included in the table
	 * ACCOUNT_USER.
	 * 
	 * @throws Exception
	 * 
	 */
//	@Test
//	public final void testUpdateUserStatus() throws Exception {
//		String status = "5";
//
//		request = new UpdateUserRequest();
//		request.setCrmAccountId(crmAccountId);
//		request.setUsername(usernameOld);
//		request.setNewUsername(usernameNew);
//		request.setUserStatus(status);
//
//		System.out.println("STARTING TEST UPDATE STATUS ID ");
//
//		AccountMgmtResponse response = new UpdateUserService(request).updateUser();
//		String statusRes = dao.getUserStatus(crmAccountId);
//		assertTrue("Error in UPDATE USER SERVICE ", statusRes.equalsIgnoreCase(status));
//
//		System.out.println("ACCOUNT_USER: " + crmAccountId);
//		System.out.println("NEW VALUE STATUS ID: " + status);
//
//	}
//
//	/**
//	 * Update the attribute country. This data is included in the table
//	 * ACCOUNT_ATTRIBUTE.
//	 * 
//	 * @throws Exception
//	 * 
//	 */
//	@Test
//	public final void testUpdateUserCountry() throws Exception {
//		String country = "FRANCE";
//		request = new UpdateUserRequest();
//		request.setCrmAccountId(crmAccountId);
//		request.setUsername(usernameOld);
//		request.setUserCountry(country);
//		System.out.println("STARTING TEST UPDATE COUNTRY");
//		AccountMgmtResponse response = new UpdateUserService(request).updateUser();
//
//		assertTrue("Error in UPDATE USER SERVICE COUNTRY ", dao.isUpdateUser(crmAccountId, 24));
//		System.out.println("ACCOUNT_USER: " + crmAccountId);
//		System.out.println("NEW VALUE COUNTRY: " + country);
//
//	}
//
//	/**
//	 * Update the attribute language. This data is included in the table
//	 * ACCOUNT_ATTRIBUTE.
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public final void testUpdateUserLanguage() throws Exception {
//		String language = "IT";
//		request = new UpdateUserRequest();
//		request.setCrmAccountId(crmAccountId);
//		request.setUsername(usernameOld);
//		request.setUserLanguage(language);
//		System.out.println("STARTING TEST UPDATE LANGUAGE");
//		AccountMgmtResponse response = new UpdateUserService(request).updateUser();
//
//		assertTrue("Error in UPDATE USER SERVICE LANGUAGE ", dao.isUpdateUser(crmAccountId, 40));
//		System.out.println("ACCOUNT_USER: " + crmAccountId);
//		System.out.println("NEW VALUE LANGUAGE: " + language);
//	}
//
//	/**
//	 * Update the attribute UserRememberPinFlag. This data is included in the
//	 * table ACCOUNT_ATTRIBUTE.
//	 * 
//	 * @throws Exception
//	 * 
//	 */
//	@Test
//	public final void testUpdateUserRememberPinFlag() throws Exception {
//
//		request = new UpdateUserRequest();
//		request.setCrmAccountId(crmAccountId);
//		request.setUsername(usernameOld);
//		request.setUserRememberPinFlag(FlagTypeYN.Y);
//		System.out.println("STARTING TEST UPDATE REMEMBER PIN FLAG");
//		AccountMgmtResponse response = new UpdateUserService(request).updateUser();
//
//		assertTrue("Error in UPDATE USER SERVICE REMEMBER PIN FLAG ", dao.isUpdateUser(crmAccountId, 4));
//		System.out.println("ACCOUNT_USER: " + crmAccountId);
//		System.out.println("NEW VALUE REMEMBER PIN FLAG: " + FlagTypeYN.Y);
//	}
//
//	/**
//	 * Update the attribute pcLevel. This data is included in the table
//	 * ACCOUNT_ATTRIBUTE.
//	 * 
//	 * @throws Exception
//	 * 
//	 */
//	@Test
//	public final void testUpdateUserPcLevel() throws Exception {
//		int pcLevel = 100;
//		request = new UpdateUserRequest();
//		request.setCrmAccountId(crmAccountId);
//		request.setUsername(usernameOld);
//		request.setUserPcLevel(pcLevel);
//		System.out.println("STARTING TEST UPDATE PC LEVEL");
//		AccountMgmtResponse response = new UpdateUserService(request).updateUser();
//
//		assertTrue("Error in UPDATE USER SERVICE PC LEVEL ", dao.isUpdateUser(crmAccountId, 3));
//		System.out.println("ACCOUNT_USER: " + crmAccountId);
//		System.out.println("NEW VALUE REMEMBER PC LEVEL: " + pcLevel);
//	}
//
//	/**
//	 * Update the attribute NewPinPurchase. This data is included in the table
//	 * ACCOUNT_ATTRIBUTE.
//	 * 
//	 * @throws Exception
//	 * 
//	 */
//	@Test
//	public final void testUpdateNewPinPurchase() throws Exception {
//		String NewPinPurchase = "1122";
//		request = new UpdateUserRequest();
//		request.setCrmAccountId(crmAccountId);
//		request.setUsername(usernameOld);
//		request.setNewPinPurchase(NewPinPurchase);
//		System.out.println("STARTING TEST UPDATE NEW PIN PURCHASE ");
//		AccountMgmtResponse response = new UpdateUserService(request).updateUser();
//
//		assertTrue("Error in UPDATE USER SERVICE NEW PIN PURCHASE ", dao.isUpdateUser(crmAccountId, 53));
//		System.out.println("ACCOUNT_USER: " + crmAccountId);
//		System.out.println("NEW VALUE REMEMBER NEW PIN PURCHASE: " + NewPinPurchase);
//	}
//
//	/**
//	 * Update the attribute newPinParentalControl. This data is included in the
//	 * table ACCOUNT_ATTRIBUTE.
//	 * 
//	 * @throws Exception
//	 * 
//	 */
//	@Test
//	public final void testUpdateNewPinParentalControl() throws Exception {
//		String newPinParentalControl = "2345";
//		request = new UpdateUserRequest();
//		request.setCrmAccountId(crmAccountId);
//		request.setUsername(usernameOld);
//		request.setNewPinParentalControl(newPinParentalControl);
//
//		System.out.println("STARTING TEST UPDATE NEW PIN PARENTAL CONTROL ");
//
//		AccountMgmtResponse response = new UpdateUserService(request).updateUser();
//
//		assertTrue("Error in UPDATE USER SERVICE NEW PIN PARENTAL CONTROL ", dao.isUpdateUser(crmAccountId, 1));
//		System.out.println("ACCOUNT_USER: " + crmAccountId);
//		System.out.println("NEW VALUE REMEMBER NEW PIN PARENTAL CONTROL: " + newPinParentalControl);
//	}
//
//	/**
//	 * Update the attribute UserPcExtendedRatings. This data is included in the
//	 * table ACCOUNT_ATTRIBUTE.
//	 * 
//	 * @throws Exception
//	 * 
//	 */
//	@Test
//	public final void testUpdateNewPcExtendedRatings() throws Exception {
//		String newPcExtendedRatings = "S;H";
//		request = new UpdateUserRequest();
//		request.setCrmAccountId(crmAccountId);
//		request.setUsername(usernameOld);
////		request.setNewPcExtendedRatings(newPcExtendedRatings);
//
//		System.out.println("STARTING TEST UPDATE NEW PC EXTENDED RATINGS ");
//
//		AccountMgmtResponse response = new UpdateUserService(request).updateUser();
//
//		assertTrue("Error in UPDATE USER SERVICE USER PC EXTENDED RATINGS", dao.isUpdateUser(crmAccountId, 48));
//		System.out.println("ACCOUNT_USER: " + crmAccountId);
//		System.out.println("NEW VALUE REMEMBER NEW PC EXTENDED RATINGS: " + newPcExtendedRatings);
//	}
}
