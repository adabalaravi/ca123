//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.03.15 at 04:09:44 PM CET 
//

package com.accenture.sdp.csmfe.webservices.response.device;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

/**
 * <p>
 * Java class for SearchDeviceModelsByBrandResp complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchDeviceModelsByBrandResp">
 *   &lt;complexContent>
 *     &lt;extension base="{}baseResp">
 *       &lt;sequence>
 *         &lt;element name="models">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *                   &lt;element name="model">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="modelId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *                             &lt;element name="modelName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="isBlacklisted" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                             &lt;element name="isWhitelisted" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                             &lt;element name="blacklistReason" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchDeviceModelsByBrandResp", propOrder = { "models" })
public class SearchDeviceModelsByBrandResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5285812325812604399L;

	@XmlElement(required = true)
	private SearchDeviceModelsByBrandResp.Models models;

	/**
	 * Gets the value of the models property.
	 * 
	 * @return possible object is {@link SearchDeviceModelsByBrandResp.Models }
	 * 
	 */
	public SearchDeviceModelsByBrandResp.Models getModels() {
		return models;
	}

	/**
	 * Sets the value of the models property.
	 * 
	 * @param value
	 *            allowed object is {@link SearchDeviceModelsByBrandResp.Models }
	 * 
	 */
	public void setModels(SearchDeviceModelsByBrandResp.Models value) {
		this.models = value;
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
	 *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
	 *         &lt;element name="model">
	 *           &lt;complexType>
	 *             &lt;complexContent>
	 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *                 &lt;sequence>
	 *                   &lt;element name="modelId" type="{http://www.w3.org/2001/XMLSchema}long"/>
	 *                   &lt;element name="modelName" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="isBlacklisted" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
	 *                   &lt;element name="isWhitelisted" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
	 *                   &lt;element name="blacklistReason" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
	@XmlType(name = "", propOrder = { "model" })
	public static class Models {

		private List<SearchDeviceModelsByBrandResp.Models.Model> model;

		/**
		 * Gets the value of the model property.
		 * 
		 * <p>
		 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present
		 * inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the model property.
		 * 
		 * <p>
		 * For example, to add a new item, do as follows:
		 * 
		 * <pre>
		 * getModel().add(newItem);
		 * </pre>
		 * 
		 * 
		 * <p>
		 * Objects of the following type(s) are allowed in the list {@link SearchDeviceModelsByBrandResp.Models.Model }
		 * 
		 * 
		 */
		public List<SearchDeviceModelsByBrandResp.Models.Model> getModel() {
			if (model == null) {
				model = new ArrayList<SearchDeviceModelsByBrandResp.Models.Model>();
			}
			return this.model;
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
		 *         &lt;element name="modelId" type="{http://www.w3.org/2001/XMLSchema}long"/>
		 *         &lt;element name="modelName" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="isBlacklisted" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
		 *         &lt;element name="isWhitelisted" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
		 *         &lt;element name="blacklistReason" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
		@XmlType(name = "", propOrder = { "modelId", "modelName", "isBlacklisted", "isWhitelisted", "blacklistReason" })
		public static class Model {

			@XmlElement(required = true)
			private Long modelId;
			@XmlElement(required = true)
			private String modelName;
			private boolean isBlacklisted;
			private boolean isWhitelisted;
			private String blacklistReason;

			/**
			 * Gets the value of the modelId property.
			 * 
			 */
			public Long getModelId() {
				return modelId;
			}

			/**
			 * Sets the value of the modelId property.
			 * 
			 */
			public void setModelId(Long value) {
				this.modelId = value;
			}

			/**
			 * Gets the value of the modelName property.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getModelName() {
				return modelName;
			}

			/**
			 * Sets the value of the modelName property.
			 * 
			 * @param value
			 *            allowed object is {@link String }
			 * 
			 */
			public void setModelName(String value) {
				this.modelName = value;
			}

			/**
			 * Gets the value of the isBlacklisted property.
			 * 
			 */
			public boolean isIsBlacklisted() {
				return isBlacklisted;
			}

			/**
			 * Sets the value of the isBlacklisted property.
			 * 
			 */
			public void setIsBlacklisted(boolean value) {
				this.isBlacklisted = value;
			}

			/**
			 * Gets the value of the isWhitelisted property.
			 * 
			 */
			public boolean isIsWhitelisted() {
				return isWhitelisted;
			}

			/**
			 * Sets the value of the isWhitelisted property.
			 * 
			 */
			public void setIsWhitelisted(boolean value) {
				this.isWhitelisted = value;
			}

			/**
			 * Gets the value of the blacklistReason property.
			 * 
			 */
			public String getBlacklistReason() {
				return blacklistReason;
			}

			/**
			 * Sets the value of the blacklistReason property.
			 * 
			 */
			public void setBlacklistReason(String value) {
				this.blacklistReason = value;
			}
		}

	}

}
