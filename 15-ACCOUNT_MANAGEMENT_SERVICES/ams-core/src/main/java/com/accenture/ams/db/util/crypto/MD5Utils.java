package com.accenture.ams.db.util.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.accenture.ams.db.util.LogUtil;

public class MD5Utils {

	public static String getHashPassword(String password) throws NoSuchAlgorithmException {

		byte[] defaultBytes = password.getBytes();
		String resultPassword = "";

		MessageDigest algorithm = MessageDigest.getInstance("MD5");
		algorithm.reset();
		algorithm.update(defaultBytes);
		byte messageDigest[] = algorithm.digest();

		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < messageDigest.length; i++) {
			String hex = Integer.toHexString(0xFF & messageDigest[i]);
			if (hex.length() == 1)
				hexString.append('0');

			hexString.append(hex);
		}

		resultPassword = hexString + "";

		LogUtil.writeCommonLog("DEBUG", MD5Utils.class, "INTERNAL", "convert md5: pass " + password + "   md5 version is " + resultPassword);

		return resultPassword;
	}

}
