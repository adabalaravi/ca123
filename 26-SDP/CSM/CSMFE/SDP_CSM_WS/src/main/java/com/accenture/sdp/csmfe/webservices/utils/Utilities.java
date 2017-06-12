package com.accenture.sdp.csmfe.webservices.utils;

public abstract class Utilities {

	public static String trim(String s){
		if (s == null) {
			return null;
		}
		String result = s.trim();
		if (result.length()==0) {
			return null;
		}
		return result;
	}
}
