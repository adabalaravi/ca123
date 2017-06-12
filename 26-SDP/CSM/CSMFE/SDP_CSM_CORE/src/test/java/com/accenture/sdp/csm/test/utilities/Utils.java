package com.accenture.sdp.csm.test.utilities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.accenture.sdp.csm.dto.requests.SdpSecretQuestionRequestDto;

public class Utils {

	public static final String CODE_OK = "000";

	public static List<SdpSecretQuestionRequestDto> prepareSecretQuestions() {
		List<SdpSecretQuestionRequestDto> result = new ArrayList<SdpSecretQuestionRequestDto>();
		for (int i = 0; i < 3; i++) {
			SdpSecretQuestionRequestDto dto = new SdpSecretQuestionRequestDto();
			dto.setSecretQuestionDescription("question");
			dto.setAnswer("answer");
			result.add(dto);
		}
		return result;
	}

	public static String getRandomName(int length) {
		Random random = new Random();
		char[] ach = new char[length];

		ach[0] = (char) ('A' + random.nextInt(26));
		for (int of = 1; of < length; ++of) {
			ach[of] = (char) ('a' + random.nextInt(26));
		}
		return new String(ach);
	}

	public static String getRandomNamePrefixed() {
		Random random = new Random();

		return TestConstants.TEST_BEANS_PREFIX + getRandomName(20 + random.nextInt(20));
	}

	/**
	 * @param maxExclusive
	 * @return an integer in [0;maxExclusive)
	 */
	public static int getRandomInt(int maxExclusive) {
		Random random = new Random();
		return random.nextInt(maxExclusive);
	}

	/**
	 * @param maxExclusive
	 * @return a Long in [0;maxExclusive)
	 */
	public static Long getRandomLong(int maxExclusive) {
		return new Long(getRandomInt(maxExclusive));
	}

	/**
	 * @param maxExclusive
	 * @return a Long greater than minInclusive
	 */
	public static Long getGreaterRandomLong(int minInclusive) {
		Random random = new Random();
		int rnd = random.nextInt();
		if (rnd < 0) {
			rnd = -rnd;
		}
		return new Long(minInclusive) + rnd;
	}

	/**
	 * @param maxExclusive
	 * @return a BigDecimal in [0;maxExclusive)
	 */
	public static BigDecimal getRandomDecimal(double maxExclusive) {
		Random random = new Random();
		return new BigDecimal(random.nextDouble() * maxExclusive);
	}

	public static boolean coinFlip() {
		Random random = new Random();
		return random.nextBoolean();
	}

	public static boolean[] removeHead(boolean[] valued) {
		if (valued.length > 1) {
			return Arrays.copyOfRange(valued, 1, valued.length);
		}
		return new boolean[0];
	}

	public static <T> T readParam(Class<T> type, Parameter[] params, int position, Object defaultValue) {
		return type.cast(params.length > position && !params[position].isRandom() ? params[position].getValue() : defaultValue);
	}
}
