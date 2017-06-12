package com.accenture.sdp.csm.commons;

import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;

public abstract class DeviceEnums {

	public enum Operation {
		ADD("ADD"), REMOVE("REMOVE");

		private String operation;

		Operation(String operation) {
			this.operation = operation;
		}

		public String getValue() {
			return operation;
		}
	}

	public enum Item {
		USER("USER"), DEVICE("DEVICE"), DEVICE_CHANNEL("DEVICE_CHANNEL"), DEVICE_MODEL("DEVICE_MODEL"), DEVICE_BRAND("DEVICE_BRAND");

		private String item;

		Item(String item) {
			this.item = item;
		}

		public String getValue() {
			return item;
		}
	}

	public enum Filter {
		USER("USER"), DEVICE("DEVICE"), DEVICE_CHANNEL("DEVICE_CHANNEL"), DEVICE_MODEL("DEVICE_MODEL"), DEVICE_BRAND("DEVICE_BRAND"), ALL("ALL");

		private String filter;

		Filter(String filter) {
			this.filter = filter;
		}

		public String getValue() {
			return filter;
		}
	}

	public enum AuthMode {
		BASIC("BASIC"), PAIRING("PAIRING");

		private String mode;

		AuthMode(String mode) {
			this.mode = mode;
		}

		public String getValue() {
			return mode;
		}
	}

	public static Operation getOperation(String operation) throws PropertyNotFoundException {
		try {
			return Operation.valueOf(operation.toUpperCase());
		} catch (Exception e) {
			throw new PropertyNotFoundException(operation, null, "Unexpected Operation value = " + operation, e);
		}
	}

	public static Item getItem(String itemType) throws PropertyNotFoundException {
		try {
			return Item.valueOf(itemType.toUpperCase());
		} catch (Exception e) {
			throw new PropertyNotFoundException(itemType, null, "Unexpected Item value = " + itemType, e);
		}
	}

	public static Filter getFilter(String filter) throws PropertyNotFoundException {
		try {
			return Filter.valueOf(filter.toUpperCase());
		} catch (Exception e) {
			throw new PropertyNotFoundException(filter, null, "Unexpected Filter value = " + filter, e);
		}
	}

	public static AuthMode getAuthMode(String mode) throws PropertyNotFoundException {
		try {
			return AuthMode.valueOf(mode.toUpperCase());
		} catch (Exception e) {
			throw new PropertyNotFoundException(mode, null, "Unexpected AuthMode value = " + mode, e);
		}
	}
}
