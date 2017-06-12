package com.accenture.sdp.csm.commons;

import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;

public class IsMandatory {

	public enum Mandatory {

		Y("1"), N("0");

		private String mandatory;

		Mandatory(String mandatory) {
			this.mandatory = mandatory;
		}

		public String getValue() {
			return mandatory;
		}

		public static String getMandatoryValue(String g) throws PropertyNotFoundException {
			try {
				return Mandatory.valueOf(g.toUpperCase()).getValue();
			} catch (Exception e) {
				throw new PropertyNotFoundException(g, null, "Unexpected isMandatory value = " + g, e);
			}
		}

		public static String getMandatoryString(String b) throws PropertyNotFoundException {
			if (b.equals(Y.getValue())) {
				return "Y";
			}
			if (b.equals(N.getValue())) {
				return "N";
			}
			throw new PropertyNotFoundException(b, null, "Unexpected isMandatory value = " + b);
		}
	}

}
