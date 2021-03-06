//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.03.15 at 03:03:47 PM CET 
//

package com.accenture.sdp.csmfe.webservices.request.device;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for UpdateDeviceRequest complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UpdateDeviceRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="deviceUUID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="deviceBrand" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="deviceModel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="deviceAlias" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "UpdateDeviceRequest", propOrder = { "deviceUUID", "deviceBrand", "deviceModel", "deviceAlias" })
public class UpdateDeviceRequest {

	@XmlElement(required = true)
	private String deviceUUID;
	private String deviceBrand;
	private String deviceModel;
	private String deviceAlias;

	/**
	 * Gets the value of the deviceUUID property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDeviceUUID() {
		return deviceUUID;
	}

	/**
	 * Sets the value of the deviceUUID property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDeviceUUID(String value) {
		this.deviceUUID = value;
	}

	/**
	 * Gets the value of the deviceBrand property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDeviceBrand() {
		return deviceBrand;
	}

	/**
	 * Sets the value of the deviceBrand property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDeviceBrand(String value) {
		this.deviceBrand = value;
	}

	/**
	 * Gets the value of the deviceModel property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDeviceModel() {
		return deviceModel;
	}

	/**
	 * Sets the value of the deviceModel property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDeviceModel(String value) {
		this.deviceModel = value;
	}

	/**
	 * Gets the value of the deviceAlias property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDeviceAlias() {
		return deviceAlias;
	}

	/**
	 * Sets the value of the deviceAlias property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDeviceAlias(String value) {
		this.deviceAlias = value;
	}

}
