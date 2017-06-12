package com.accenture.sdp.csm.commons;

public class FlagValue {

	public enum Flag {

		YES("Y"), NO("N");

		private String value;

		Flag(String flagValue) {
			this.value = flagValue;
		}

		public String getValue() {
			return value;
		}

		public static boolean checkAllowedValue(String flagValue) {
			return YES.getValue().equalsIgnoreCase(flagValue) || NO.getValue().equalsIgnoreCase(flagValue);
		}
	}

}
