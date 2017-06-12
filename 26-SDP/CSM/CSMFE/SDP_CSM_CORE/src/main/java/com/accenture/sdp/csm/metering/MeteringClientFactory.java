package com.accenture.sdp.csm.metering;

import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csm.utilities.logging.LoggerWrapper;

public final class MeteringClientFactory {

	private static LoggerWrapper log = new LoggerWrapper(MeteringQueueClient.class);

	private MeteringClientFactory() {

	}

	public static MeteringClient getClient() {
		try {
			if (ConstantsHandler.getInstance().retrieveBooleanConstant(Constants.DEVICE_METERING_ENABLED)) {
				return MeteringQueueClient.getInstance();
			}
		} catch (PropertyNotFoundException e) {
			log.logError(e);
		}
		return new MeteringDummyClient();
	}
}
