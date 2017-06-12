package com.accenture.sdp.csm.commons;

import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;

public class GenderType {
	public enum Gender {

		M("M"), F("F");

		private String gender;

		Gender(String gender) {
			this.gender = gender;
		}

		public String getValue() {
			return gender;
		}

		public static String getGenderValue(String gender) throws PropertyNotFoundException {
			if (gender == null || gender.length() == 0) {
				return null;
			}
			try {
				return Gender.valueOf(gender.toUpperCase()).getValue();
			} catch (Exception e) {
				throw new PropertyNotFoundException(gender, null, "Unexpected Gender value = " + gender, e);
			}
		}
	}
}
