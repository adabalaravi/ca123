package com.accenture.sdp.csm.metering;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

import com.accenture.sdp.csm.dto.metering.EventType;
import com.accenture.sdp.csm.dto.metering.SdpDeviceTraceMessage;
import com.accenture.sdp.csm.dto.metering.SdpDeviceTraceMessage.EventList;
import com.accenture.sdp.csm.exceptions.InitException;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.Utilities;
import com.accenture.sdp.csm.utilities.XMLUtils;
import com.accenture.sdp.csm.utilities.logging.LoggerWrapper;

public final class MeteringQueueClient implements MeteringClient {

	private static LoggerWrapper log = new LoggerWrapper(MeteringQueueClient.class);
	private static final String QUEUE_NAME = "QUEUE_NAME";
	private static final String CONNECTION_FACTORY_NAME = "CONNECTION_FACTORY_NAME";

	private QueueConnection conn;
	private Queue queue;

	private static MeteringQueueClient instance;

	private MeteringQueueClient() {

	}

	public static MeteringQueueClient getInstance() {
		if (instance == null) {
			instance = new MeteringQueueClient();
		}
		return instance;
	}

	public void init() throws InitException {
		FileInputStream meteringFileStream = null;
		try {
			String meteringFile = Constants.CSM_CONFIGURATION_PATH + "metering.properties";
			log.logInfo("METERING PROPERTIES PATH %s", meteringFile);

			meteringFileStream = new FileInputStream(meteringFile);
			Properties meteringProperties = new Properties();
			meteringProperties.load(meteringFileStream);

			for (Entry<Object, Object> entry : meteringProperties.entrySet()) {
				log.logInfo("Metering properties: %s = %s", entry.getKey(), entry.getValue());
			}

			InitialContext iniCtx = new InitialContext(meteringProperties);
			Object tmp = iniCtx.lookup(meteringProperties.getProperty(CONNECTION_FACTORY_NAME));
			QueueConnectionFactory qcf = (QueueConnectionFactory) tmp;
			queue = (Queue) iniCtx.lookup(meteringProperties.getProperty(QUEUE_NAME));
			conn = qcf.createQueueConnection();
			conn.start();
		} catch (Exception e) {
			throw new InitException(String.format("Unable to init Metering Client. Exception: %s", e.getMessage()), e);
		} finally {
			Utilities.closeStream(meteringFileStream);
		}
	}

	public void closeConnection() throws JMSException {
		if (conn != null) {
			conn.stop();
			conn.close();
		}
	}

	public void sendTraceMessage(String tenant, List<EventType> eventList) {
		log.logDebug(Utilities.getCurrentMethodName());
		QueueSession session = null;
		QueueSender sender = null;
		try {
			// auto-initialize the connection if never called before
			// useful for hot switch of metering enablement
			if (conn == null) {
				init();
			}
			try {
				session = conn.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
			} catch (JMSException e) {
				// restart and retry
				closeConnection();
				init();
				session = conn.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
			}
			sender = session.createSender(queue);
			SdpDeviceTraceMessage message = new SdpDeviceTraceMessage();
			EventList list = new EventList();
			list.getEvent().addAll(eventList);
			message.setEventList(list);
			String msg = XMLUtils.marshall(SdpDeviceTraceMessage.class, message);
			TextMessage tm = session.createTextMessage(msg);
			tm.setStringProperty("tenantName", tenant);
			sender.send(tm);
		} catch (Exception e) {
			log.logError(e);
		} finally {
			if (sender != null) {
				try {
					sender.close();
				} catch (JMSException e) {
					log.logError(e);
				}
			}
			if (session != null) {
				try {
					session.close();
				} catch (JMSException e) {
					log.logError(e);
				}
			}
		}
	}
}
