package com.accenture.ams.test;

import junit.framework.TestCase;

import org.junit.Test;

import com.accenture.ams.db.util.crypto.MD5Utils;
import com.accenture.ams.accountmgmtservice.updatePwdService.UpdateUserPasswordService;
import com.accenture.ams.test.dao.ServiceDAOtest;

/**
 * These tests verify the data insert or reset into the database by
 * UpdateUserPasswordService
 * 
 * @author Valentina Bonafaccia
 * 
 */
public class UpdatePasswordTest extends TestCase {

	Long userId = 756101l;
	String userName = "756101";
	String oldPassword = "1234";
	String newPassword = "TEST";
	String newPasswordReset = "DEFAULT";
	String typeChange = "CHANGE";
	String pswMin = "PRO";
	String pswMax = "PASSWORDPASSWORDPASSWORDPASSWORD";
	String tenantName = "";
	ServiceDAOtest dao = new ServiceDAOtest();

	/**
	 * This method verifies that the password is changed as a user in the table
	 * ACCOUNT_ATTRIBUTE
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public final void testChangePassword() throws Exception {

		String pwdBefore = dao.getPassword(userId, tenantName);
		System.out.println("pwdBefore: " + pwdBefore);
		UpdateUserPasswordService uppwd = new UpdateUserPasswordService(tenantName);
		uppwd.changePassword(userName, oldPassword, newPassword, typeChange);
		String pwdAfter = dao.getPassword(userId, tenantName);

		System.out.println("pwdAfter: " + pwdAfter);

		assertFalse("Error in testChangePassword", pwdBefore.equalsIgnoreCase(pwdAfter) || pwdAfter.equalsIgnoreCase(pwdBefore));

	}

	/**
	 * This method verifies that the password be reset with a new password as a
	 * user in the table ACCOUNT_ATTRIBUTE
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public final void testResetPassword() throws Exception {

		UpdateUserPasswordService uppwd = new UpdateUserPasswordService(tenantName);
		uppwd.resetPassword(userName, newPasswordReset);
		String pwdAfter = dao.getPassword(userId, tenantName);
		System.out.println("pwdAfter: " + pwdAfter);

		assertTrue("Error in testResetPassword", pwdAfter.equalsIgnoreCase(MD5Utils.getHashPassword(newPasswordReset)));

	}

}
