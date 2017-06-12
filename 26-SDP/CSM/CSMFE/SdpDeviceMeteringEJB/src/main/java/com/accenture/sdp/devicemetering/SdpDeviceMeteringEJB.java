package com.accenture.sdp.devicemetering;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import com.accenture.sdp.devicemetering.managers.DeviceMeteringManager;
import com.accenture.sdp.devicemetering.model.SdpDeviceTraceMessage;
import com.accenture.sdp.devicemetering.utilities.Constants;
import com.accenture.sdp.devicemetering.utilities.XMLUtils;
import com.accenture.sdp.devicemetering.utilities.logging.LoggerWrapper;

/**
 * Message-Driven Bean implementation class for: SdpDeviceMeteringEJB
 * 
 */

@MessageDriven(name = "SdpDeviceMeteringEJB", activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/sdp_device_metering_queue"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "AUTO_ACKNOWLEDGE"),
		@ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
		@ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "SdpDeviceMeteringSubscription"),
		@ActivationConfigProperty(propertyName = "clientId", propertyValue = "SdpDeviceMeteringEJBClient") })
@TransactionManagement(TransactionManagementType.BEAN)
public class SdpDeviceMeteringEJB implements MessageListener {

	private JAXBContext context;
	private LoggerWrapper log;

	public SdpDeviceMeteringEJB() {
		try {
			LoggerWrapper.initialize();
			log = new LoggerWrapper(SdpDeviceMeteringEJB.class);
			if (context == null) {
				context = JAXBContext.newInstance(SdpDeviceTraceMessage.class);
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see MessageListener#onMessage(Message)
	 */
	public void onMessage(Message message) {
		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);
		TextMessage msg = (TextMessage) message;
		try {
			log.logDebug(msg.getText());
			String tenantName = msg.getStringProperty(Constants.TENANT_NAME);
			SdpDeviceTraceMessage deviceTraceMessage = (SdpDeviceTraceMessage) XMLUtils.unmarshall(context, msg.getText());
			DeviceMeteringManager.getInstance().insertDeviceEvents(tenantName, deviceTraceMessage.getEventList().getEvent());
			log.logDebug("MESSAGE RECEIVED");
			
		} catch (JAXBException e) {
			log.logError(e);
		} catch (JMSException e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());

	}

}
