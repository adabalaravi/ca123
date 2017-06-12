package com.accenture.avs.ca.be.util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;
import com.accenture.avs.be.configurator.TenantConfigurator;
import com.accenture.avs.be.gateway.GenericGateway;
import com.accenture.avs.be.util.Constants;

public class SendMailGateway extends GenericGateway {
	private static final Logger LOGGER = Logger.getLogger(SendMailGateway.class);

	/**
	 * Constructor which accepts TenantConfigurator as an input.
	 * @param tenantConfigurator
	 */
	public SendMailGateway(TenantConfigurator tenantConfigurator) {
		super();
		setConfiguratorValues(tenantConfigurator);
	}

	/**
	 * Sends mail using javax mail api.
	 * @param input
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void sendMail(String[] input) throws AddressException,
			MessagingException {
		LOGGER.info("sendMail:: Start");
		Transport.send(getMessage(input));
		LOGGER.info("sendMail:: End");
	}

	/**
	 * This method creates message for sending mail
	 * @param input
	 * @return message
	 * @throws AddressException
	 * @throws MessagingException
	 */
	private Message getMessage(String[] input) throws AddressException,
			MessagingException {
		LOGGER.debug("getMessage:: Start");
		Message message = new MimeMessage(getMailSession());

		message.setFrom(new InternetAddress(input[3]));
		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(input[2]));
		message.setSubject(input[0]);
		message.setContent(input[1], CAConstants.CONTENT_TYPE);
		message.saveChanges();
		
		LOGGER.debug("getMessage:: End");
		return message;
	}

	/**
	 * This method creates a mail session 
	 * @return session
	 */
	private Session getMailSession() {
		LOGGER.debug("getMailSession:: Start");
		final String userName = constantsParameter.MAIL_USER_NAME;
		final String password = constantsParameter.MAIL_PASSWORD;
		Session session = Session.getInstance(getProperties(),
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(userName, password);
					}
				});

		LOGGER.debug("getMailSession:: End");
		return session;
	}

	/**
	 * This method sets all properties which are used to create a mail session
	 * @return props
	 */
	private Properties getProperties() {
		LOGGER.debug("getProperties:: Start");
		Properties props = new Properties();
		props.put("mail.smtp.host", constantsParameter.MAIL_SMTP_HOST_VALUE);
		if (Constants.MAIL_SMTP_SOCKETFACTORY_CLASS_VALUE != null
				&& Constants.MAIL_SMTP_SOCKETFACTORY_CLASS_VALUE
						.equalsIgnoreCase("")) {
			props.put("mail.smtp.socketFactory.port",
					constantsParameter.MAIL_SMTP_SOCKETFACTORY_PORT_VALUE);
			props.put("mail.smtp.socketFactory.class",
					Constants.MAIL_SMTP_SOCKETFACTORY_CLASS_VALUE);
		}

		props.put("mail.smtp.auth", Constants.MAIL_SMTP_AUTH_VALUE);
		props.put("mail.smtp.port", constantsParameter.MAIL_SMTP_PORT_VALUE);
		LOGGER.debug("getProperties:: End");
		return props;
	}

	@Override
	protected Object clientToAvsBean(Object input) {
		return null;
	}

	@Override
	public Object retrieveData(Object[] input) {
		return null;
	}
}
