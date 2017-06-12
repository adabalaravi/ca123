package com.accenture.sdp.csm.commons;

import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;

public class LinkUpdateOperation {
	public enum Operation {

		NEW("New"), DELETE("Delete");

		private String operation;

		Operation(String operation) {
			this.operation = operation;
		}

		public String getValue() {
			return operation;
		}

		public static String getOperationValue(String operation) throws PropertyNotFoundException {
			try {
				return Operation.valueOf(operation.toUpperCase()).getValue();
			} catch (Exception e) {
				throw new PropertyNotFoundException(operation, null, "Unexpected Operation value = " + operation, e);
			}
		}
	}
}
