//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.03.15 at 03:03:47 PM CET 
//

package com.accenture.sdp.csmfe.webservices.request.device;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ManageWhiteListRequest complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ManageWhiteListRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="operations">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="operation" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="itemType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="opType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ManageWhiteListRequest", propOrder = { "operations" })
public class ManageWhiteListRequest {

	@XmlElement(required = true)
	private ManageWhiteListRequest.Operations operations;

	/**
	 * Gets the value of the operations property.
	 * 
	 * @return possible object is {@link ManageWhiteListRequest.Operations }
	 * 
	 */
	public ManageWhiteListRequest.Operations getOperations() {
		return operations;
	}

	/**
	 * Sets the value of the operations property.
	 * 
	 * @param value
	 *            allowed object is {@link ManageWhiteListRequest.Operations }
	 * 
	 */
	public void setOperations(ManageWhiteListRequest.Operations value) {
		this.operations = value;
	}

	/**
	 * <p>
	 * Java class for anonymous complex type.
	 * 
	 * <p>
	 * The following schema fragment specifies the expected content contained within this class.
	 * 
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;sequence>
	 *         &lt;element name="operation" maxOccurs="unbounded" minOccurs="0">
	 *           &lt;complexType>
	 *             &lt;complexContent>
	 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *                 &lt;sequence>
	 *                   &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="itemType" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="opType" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                 &lt;/sequence>
	 *               &lt;/restriction>
	 *             &lt;/complexContent>
	 *           &lt;/complexType>
	 *         &lt;/element>
	 *       &lt;/sequence>
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 * 
	 * 
	 */
	@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "operation" })
	public static class Operations {

		private List<ManageWhiteListRequest.Operations.Operation> operation;

		/**
		 * Gets the value of the operation property.
		 * 
		 * <p>
		 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present
		 * inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the operation property.
		 * 
		 * <p>
		 * For example, to add a new item, do as follows:
		 * 
		 * <pre>
		 * getOperation().add(newItem);
		 * </pre>
		 * 
		 * 
		 * <p>
		 * Objects of the following type(s) are allowed in the list {@link ManageWhiteListRequest.Operations.Operation }
		 * 
		 * 
		 */
		public List<ManageWhiteListRequest.Operations.Operation> getOperation() {
			if (operation == null) {
				operation = new ArrayList<ManageWhiteListRequest.Operations.Operation>();
			}
			return this.operation;
		}

		/**
		 * <p>
		 * Java class for anonymous complex type.
		 * 
		 * <p>
		 * The following schema fragment specifies the expected content contained within this class.
		 * 
		 * <pre>
		 * &lt;complexType>
		 *   &lt;complexContent>
		 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
		 *       &lt;sequence>
		 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="itemType" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="opType" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *       &lt;/sequence>
		 *     &lt;/restriction>
		 *   &lt;/complexContent>
		 * &lt;/complexType>
		 * </pre>
		 * 
		 * 
		 */
		@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = { "id", "itemType", "opType" })
		public static class Operation {

			@XmlElement(required = true)
			private String id;
			@XmlElement(required = true)
			private String itemType;
			@XmlElement(required = true)
			private String opType;

			/**
			 * Gets the value of the id property.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getId() {
				return id;
			}

			/**
			 * Sets the value of the id property.
			 * 
			 * @param value
			 *            allowed object is {@link String }
			 * 
			 */
			public void setId(String value) {
				this.id = value;
			}

			/**
			 * Gets the value of the itemType property.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getItemType() {
				return itemType;
			}

			/**
			 * Sets the value of the itemType property.
			 * 
			 * @param value
			 *            allowed object is {@link String }
			 * 
			 */
			public void setItemType(String value) {
				this.itemType = value;
			}

			/**
			 * Gets the value of the opType property.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getOpType() {
				return opType;
			}

			/**
			 * Sets the value of the opType property.
			 * 
			 * @param value
			 *            allowed object is {@link String }
			 * 
			 */
			public void setOpType(String value) {
				this.opType = value;
			}

		}

	}

}
