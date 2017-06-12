package com.accenture.sdp.csm.test.utilities;

public class Parameter {

	private boolean random;
	private Object value;

	public static final Parameter NULL = new Parameter(false, null);
	public static final Parameter RANDOM = new Parameter(true);

	public Parameter(boolean random) {
		this.random = random;
	}

	public Parameter(Object value) {
		this.value = value;
		this.random = false;
	}

	private Parameter(boolean random, Object value) {
		this.random = random;
		this.value = value;
	}

	public boolean isRandom() {
		return random;
	}

	public Object getValue() {
		return value;
	}

}
