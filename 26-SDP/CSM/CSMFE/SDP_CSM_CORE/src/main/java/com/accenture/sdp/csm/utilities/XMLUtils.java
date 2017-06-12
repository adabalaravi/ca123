package com.accenture.sdp.csm.utilities;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import com.accenture.sdp.csm.utilities.logging.LoggerWrapper;

public abstract class XMLUtils {

	private static final LoggerWrapper log = new LoggerWrapper(XMLUtils.class);

	public static Object unmarshall(Class<?> type, String inputXml) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(type);
		Unmarshaller u = jc.createUnmarshaller();
		u.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
		ByteArrayInputStream bais = new ByteArrayInputStream(inputXml.getBytes());
		Source input = new StreamSource(bais);
		return u.unmarshal(input);

	}

	public static String marshall(Class<?> type, Object document) throws JAXBException {

		ByteArrayOutputStream outStream = null;
		try {
			JAXBContext context = JAXBContext.newInstance(type);
			javax.xml.bind.Marshaller m = context.createMarshaller();
			m.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
			m.setProperty(javax.xml.bind.Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			outStream = new ByteArrayOutputStream();
			m.marshal(document, outStream);
			return outStream.toString();
		} catch (Exception e) {
			log.logError(e);
		} finally {
			Utilities.closeStream(outStream);
		}
		return null;
	}

}
