package com.accenture.sdp.csm.utilities;

import java.security.MessageDigest;

import com.accenture.sdp.csm.exceptions.EncryptionException;

public abstract class MD5Utils {

	private static final String ALGORITHM = "MD5";

	private static final String CHARSET = "UTF-8";

	public static String hash(String hashing) throws EncryptionException {
		String hash = null;
		try {
			byte[] bytesOfMessage = hashing.getBytes(CHARSET);

			MessageDigest md = MessageDigest.getInstance(ALGORITHM);
			byte[] digest = md.digest(bytesOfMessage);
			hash = bytes2String(digest);
		} catch (Exception e) {
			throw new EncryptionException(String.format("Unable to hash input data. Exception: %s", e.getMessage()), e);
		}
		return hash;
	}

	private static String bytes2String(byte[] input) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < input.length; ++i) {
			// non so che fa, ma funziona
			sb.append(Integer.toHexString((input[i] & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString();
	}
}
