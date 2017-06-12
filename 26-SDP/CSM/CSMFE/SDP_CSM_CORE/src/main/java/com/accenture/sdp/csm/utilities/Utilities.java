package com.accenture.sdp.csm.utilities;

import java.io.Closeable;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

import com.accenture.sdp.csm.utilities.logging.LoggerWrapper;

public abstract class Utilities {

	private static LoggerWrapper log = new LoggerWrapper(Utilities.class);

	private static final char[] PASSWORD_SPECIAL_CHARS = { '.', '_', '#', '@', '?', '!' };

	public static String getCurrentMethodName() {
		StackTraceElement stackTraceElements[] = (new Exception()).getStackTrace();
		return stackTraceElements[1].getMethodName();
	}

	/**
	 * @param stackLevel
	 *            (the call to this method is excluded)
	 * @return class.method of stackLevel
	 */
	public static String getClassAndMethod(int stackLevel) {
		StackTraceElement stackTraceElements[] = (new Exception()).getStackTrace();
		StackTraceElement caller = stackTraceElements[stackLevel + 1];
		return String.format("%s.%s", caller.getClassName().substring(caller.getClassName().lastIndexOf(".") + 1), caller.getMethodName());
	}

	public static String getCurrentClassAndMethod() {
		return getClassAndMethod(1);
	}

	public static boolean isNull(String s) {
		return (s == null || s.length() == 0);
	}

	public static boolean isNull(Object s) {
		return (s == null || s.toString().length() == 0);
	}

	public static Long parseLong(String input) {
		try {
			return Long.valueOf(input);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static String pwdGenerator() {
		Random rand = new Random();
		final int cCh = 8;
		char[] ach = new char[cCh];
		int pos = rand.nextInt(PASSWORD_SPECIAL_CHARS.length);
		int posSpecial = rand.nextInt(cCh);
		ach[posSpecial] = PASSWORD_SPECIAL_CHARS[pos];
		int posNumber = rand.nextInt(cCh);
		while (posNumber == posSpecial) {
			posNumber = rand.nextInt(cCh);
		}
		int posNumber2 = rand.nextInt(cCh);
		while (posNumber2 == posSpecial || posNumber2 == posNumber) {
			posNumber2 = rand.nextInt(cCh);
		}
		ach[posNumber] = (char) ('0' + rand.nextInt(10));
		ach[posNumber2] = (char) ('0' + rand.nextInt(10));
		for (int of = 0; of < cCh; of++) {
			if (of != posSpecial && of != posNumber && of != posNumber2) {
				int poslettere = 'A' + rand.nextInt(57);
				while (poslettere <= 96 && poslettere >= 91) {
					poslettere = 'A' + rand.nextInt(57);
				}
				ach[of] = (char) (poslettere);
			}
		}
		return String.valueOf(ach);
	}

	public static String formatDate(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT);
		return df.format(date);
	}

	private static String cleanUsername(String username) {
		String result = username.replaceAll(Constants.REGEX_USERNAME_EMAIL_BAD_CHARS, "");
		while (!result.substring(0, 1).matches("[a-zA-Z0-9]")) {
			if (result.length() > 1) {
				result = result.substring(1, result.length());
			} else {
				return null;
			}
		}
		return result;
	}

	public static String usernameRuledGenerator(int i, String firstName, String lastName, String domain) {
		String result = null;
		try {
			switch (i) {
			case 1:
				result = firstName + "." + lastName;
				break;
			case 2:
				result = firstName.substring(0, 1) + "." + lastName;
				break;
			case 3:
				result = firstName + lastName;
				break;
			case 4:
				result = firstName.substring(0, 1) + lastName;
				break;
			case 5:
				result = lastName + firstName;
				break;
			case 6:
				result = lastName + "." + lastName;
				break;
			case 7:
				result = firstName.substring(0, 1) + lastName + Utilities.createRandomNumericString(3);
				break;
			case 8:
				result = firstName + lastName + Utilities.createRandomNumericString(3);
				break;
			case 9:
				result = firstName.substring(0, 1) + lastName + Utilities.createRandomNumericString(3);
				break;
			case 10:
				result = firstName + lastName + Utilities.createRandomNumericString(3);
				break;
			default:
				result = "";
				break;
			}
			result = cleanUsername(result);
		} catch (Exception e) {
			log.logError("Rule " + i + "As generated an error ", e.getMessage());
		}

		if (result != null) {
			result = result.toLowerCase() + Constants.AT + domain;
		}
		return result;
	}

	public static String createRandomNumericString(int length) {
		Random rand = new Random();
		StringBuffer result = new StringBuffer("");
		for (int i = 0; i < length; i++) {
			result.append(rand.nextInt(10));
		}
		return result.toString();
	}

	public static boolean checkRightEmailFormat(String userName) {
		log.logDebug(getCurrentMethodName());
		return Pattern.matches(Constants.REGEX_EMAIL, userName);
	}

	public static boolean checkRightUsernameFormat(String userName) {
		log.logDebug(getCurrentMethodName());
		return Pattern.matches(Constants.REGEX_USERNAME, userName);
	}

	public static String extractDomain(String userName) {
		log.logDebug(getCurrentMethodName());
		return userName.substring(userName.indexOf(Constants.AT) + 1);
	}

	public static void closeStream(Closeable stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException ioe) {
				log.logError(ioe);
			}
		}
	}

}
