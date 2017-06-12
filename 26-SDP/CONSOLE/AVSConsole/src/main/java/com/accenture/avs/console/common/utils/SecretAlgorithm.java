package com.accenture.avs.console.common.utils;

public class SecretAlgorithm {

	private int x;

	public SecretAlgorithm(String seed1, String seed2) {
		String seed = seed1 + seed2;
		x = 1;
		for (int i = 0; i < seed.length(); i++) {
			x = (x + seed.charAt(i)) * 965660327;
		}
	}

	private int operator() {
		x *= 123456791;
		x = (x << 16) | (x >> 16);
		x *= 234567891;
		int result = (Math.abs(x >> 24) - (Math.abs(x >> 24) / 10) * 10);
		result = result > 10 ? result - 10 : result;
		return result;
	}

	public String generateToken(int length) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			buffer.append(operator());
		}
		return buffer.toString();
	}
}
