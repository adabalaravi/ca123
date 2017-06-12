package com.accenture.sdp.devicemetering.utilities;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

public class XMLUtils {

	public static Object unmarshall(JAXBContext jc, String inputXml) throws JAXBException {

		Unmarshaller u = jc.createUnmarshaller();
		u.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
		ByteArrayInputStream bais = new ByteArrayInputStream(inputXml.getBytes());
		Source input = new StreamSource(bais);
		return u.unmarshal(input);

	}


	public static String marshall(JAXBContext jc, Object document) throws JAXBException {
		ByteArrayOutputStream outStream = null;
		try {
			javax.xml.bind.Marshaller m = jc.createMarshaller();
			m.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
			m.setProperty(javax.xml.bind.Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			outStream = new ByteArrayOutputStream();
			m.marshal(document, outStream);
			return outStream.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
			throw e;
		} finally {
			Utilities.closeStream(outStream);
		}

	}


}
