package com.accenture.avs.console.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class MWalletSSO {

	private static final String TIMESTAMP_FORMAT = "yyyyMMddHHmmss";
	private static final int TOKEN_LENGTH = 14;

	public static String generateGetUrl(String username) {
		String timestamp = generateTimestamp();
		StringBuffer buffer = new StringBuffer("?userId=");
		buffer.append(username);
		buffer.append("&t=");
		buffer.append(timestamp);
		buffer.append("&f=");
		buffer.append(generateToken(username, timestamp));
		return buffer.toString();
	}

	public static String generateTimestamp() {
		SimpleDateFormat sdf = new SimpleDateFormat(TIMESTAMP_FORMAT);
		return sdf.format(new Date());
	}

	public static String generateToken(String username, String timestamp) {
		return new SecretAlgorithm(username, timestamp).generateToken(TOKEN_LENGTH);
	}
}
