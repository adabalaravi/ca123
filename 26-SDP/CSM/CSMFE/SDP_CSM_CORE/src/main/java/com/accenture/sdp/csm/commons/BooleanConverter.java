package com.accenture.sdp.csm.commons;

public abstract class BooleanConverter {

	public enum BooleanAdapter {
		TRUE((byte) 1), FALSE((byte) 0);

		private Byte booleanByte;

		BooleanAdapter(Byte booleanByte) {
			this.booleanByte = booleanByte;
		}

		public Byte getValue() {
			return booleanByte;
		}
	}

	public static byte getByteValue(boolean value) {
		if (value) {
			return BooleanAdapter.TRUE.getValue();
		} else {
			return BooleanAdapter.FALSE.getValue();
		}
	}

	public static boolean getBooleanValue(Byte b) {
		return (b != null && b != 0);
	}

}
