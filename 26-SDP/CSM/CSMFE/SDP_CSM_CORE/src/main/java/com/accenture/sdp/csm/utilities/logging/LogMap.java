package com.accenture.sdp.csm.utilities.logging;

import java.util.LinkedHashMap;

public class LogMap extends LinkedHashMap<String, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3294121130127623092L;

	@Override
	public Object put(String key, Object value) {
		if (value == null) {
			return super.put(key, "");
		}
		return super.put(key, value);
	}

}
