package com.accenture.sdp.csmcc.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.sun.org.apache.xml.internal.utils.PrefixResolver;
import com.sun.org.apache.xml.internal.utils.PrefixResolverDefault;

public final class XmlHelper {

	private static final String ENCODING = "UTF-8";
	private static LoggerWrapper log = new LoggerWrapper(XmlHelper.class);
	
	private XmlHelper(){
		
	}

	public static String processXPath(String xpathExpression, Document doc)
			throws SAXException {

		String result = "";
		try {
			final PrefixResolver resolver = new PrefixResolverDefault(
					doc.getDocumentElement());

			NamespaceContext ctx = new NamespaceContext() {
				public String getNamespaceURI(String prefix) {
					return resolver.getNamespaceForPrefix(prefix);
				}

				// Dummy implementation - not used!
				public Iterator<?> getPrefixes(String val) {
					return null;
				}

				// Dummy implemenation - not used!
				public String getPrefix(String uri) {
					return null;
				}
			};

			XPathFactory xpathFact = XPathFactory.newInstance();
			XPath xpath = xpathFact.newXPath();
			xpath.setNamespaceContext(ctx);

			result = xpath.evaluate(xpathExpression, doc);

		} catch (Exception e) {
			log.logException(e.getMessage(), e);
		}

		return result;
	}

	public static NodeList selectNodes(String xpathExpression, Document doc)
			throws SAXException {

		NodeList nodeList = null;
		try {

			final PrefixResolver resolver = new PrefixResolverDefault(
					doc.getDocumentElement());

			NamespaceContext ctx = new NamespaceContext() {
				public String getNamespaceURI(String prefix) {
					return resolver.getNamespaceForPrefix(prefix);
				}

				// Dummy implementation - not used!
				public Iterator<?> getPrefixes(String val) {
					return null;
				}

				// Dummy implemenation - not used!
				public String getPrefix(String uri) {
					return null;
				}
			};

			XPathFactory xpathFact = XPathFactory.newInstance();
			XPath xpath = xpathFact.newXPath();
			xpath.setNamespaceContext(ctx);

			nodeList = (NodeList) xpath.evaluate(xpathExpression, doc,
					XPathConstants.NODESET);

		} catch (Exception e) {
			log.logException(e.getMessage(), e);
		}

		return nodeList;
	}

	public static Document createXMLDocument(String documentString) {

		Document doc = null;
		try {
			DocumentBuilderFactory domFactory = DocumentBuilderFactory
					.newInstance();
			domFactory.setNamespaceAware(true);
			DocumentBuilder builder = domFactory.newDocumentBuilder();

			InputStream is = new ByteArrayInputStream(
					documentString.getBytes(ENCODING));

			doc = builder.parse(is);
		} catch (Exception e) {
			log.logException(e.getMessage(), e);
		}
		return doc;
	}

	public static String getXmlStringFromDocument(Document document)
			throws TransformerException, IOException

	{
		String xml = null;
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer();
		StringWriter writer = new StringWriter();
		Result result = new StreamResult(writer);
		Source source = new DOMSource(document);
		transformer.transform(source, result);
		writer.close();
		xml = writer.toString();
		return xml;
	}

	public static <T> T unmarshall(Class<T> type, String inputXml)
			throws JAXBException, UnsupportedEncodingException {
		JAXBContext jc = JAXBContext.newInstance(type);
		Unmarshaller u = jc.createUnmarshaller();
		u.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
		ByteArrayInputStream bais = new ByteArrayInputStream(inputXml.getBytes(ENCODING));
		Source input = new StreamSource(bais);
		return type.cast(u.unmarshal(input));
	}

	public static <T> T unmarshallIs(Class<T> type, InputStream is)
			throws JAXBException {

		JAXBContext jc = JAXBContext.newInstance(type);
		Unmarshaller u = jc.createUnmarshaller();
		u.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
		Source input = new StreamSource(is);
		return type.cast(u.unmarshal(input));
	}

	public static String marshall(Class<?> type, Object document)
			throws JAXBException, UnsupportedEncodingException {

		JAXBContext context = JAXBContext.newInstance(type);
		javax.xml.bind.Marshaller m = context.createMarshaller();
		m.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
		m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		m.marshal(document, outStream);
		return outStream.toString(ENCODING);

	}

	public static String applyXsltTransformation(String sourceXml,
			String xsltTransformation) {

		try {
			InputStream isXmlFile = new ByteArrayInputStream(
					sourceXml.getBytes(ENCODING));
			InputStream isXsltTransformation = new ByteArrayInputStream(
					xsltTransformation.getBytes(ENCODING));
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();

			javax.xml.transform.Source xmlSource = new javax.xml.transform.stream.StreamSource(
					isXmlFile);
			javax.xml.transform.Source xsltSource = new javax.xml.transform.stream.StreamSource(
					isXsltTransformation);
			javax.xml.transform.Result result = new javax.xml.transform.stream.StreamResult(
					outStream);

			// create an instance of TransformerFactory
			javax.xml.transform.TransformerFactory transFact = javax.xml.transform.TransformerFactory
					.newInstance();

			javax.xml.transform.Transformer trans = transFact
					.newTransformer(xsltSource);

			trans.transform(xmlSource, result);

			return outStream.toString(ENCODING);
		} catch (Exception e) {
			log.logException(e.getMessage(), e);
		}

		return null;

	}
}
