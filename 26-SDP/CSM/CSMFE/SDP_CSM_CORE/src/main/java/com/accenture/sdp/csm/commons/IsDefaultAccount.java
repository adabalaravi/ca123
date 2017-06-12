package com.accenture.sdp.csm.commons;

import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;

public class IsDefaultAccount {

	public enum DefaultAccount {

		TRUE("1"), FALSE("0");

		private String isDefault;

		DefaultAccount(String isDefault) {
			this.isDefault = isDefault;
		}

		public String getValue() {
			return isDefault;
		}

		public static String getIsDefaultAccountValue(boolean value) {
			if (value) {
				return DefaultAccount.TRUE.getValue();
			} else {
				return DefaultAccount.FALSE.getValue();
			}
		}

		public static boolean getDefaultAccountBool(String b) throws PropertyNotFoundException {
			if (b.equals(TRUE.getValue())) {
				return true;
			}
			if (b.equals(FALSE.getValue())) {
				return false;
			}
			throw new PropertyNotFoundException(b, null, "Unexpected IsDefaultAccount value = " + b);
		}
	}

}
