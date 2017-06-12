//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.03.18 at 04:02:19 PM CET 
//

package com.accenture.sdp.csmfe.webservices.response.device;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for deviceCounterType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deviceCounterType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="deviceChannelId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="deviceChannelName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="registeredDevices" type="{http://www.w3.org/2001/XMLSchema}long"/>
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
@XmlType(name = "deviceCounterType", propOrder = { "deviceChannelId", "deviceChannelName", "registeredDevices" })
public class DeviceCounterType {

	@XmlElement(required = true)
	private Long deviceChannelId;
	@XmlElement(required = true)
	private String deviceChannelName;
	@XmlElement(required = true)
	private Long registeredDevices;

	/**
	 * Gets the value of the deviceChannelId property.
	 * 
	 */
	public Long getDeviceChannelId() {
		return deviceChannelId;
	}

	/**
	 * Sets the value of the deviceChannelId property.
	 * 
	 */
	public void setDeviceChannelId(Long value) {
		this.deviceChannelId = value;
	}

	/**
	 * Gets the value of the deviceChannelName property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDeviceChannelName() {
		return deviceChannelName;
	}

	/**
	 * Sets the value of the deviceChannelName property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDeviceChannelName(String value) {
		this.deviceChannelName = value;
	}

	/**
	 * Gets the value of the registeredDevices property.
	 * 
	 */
	public Long getRegisteredDevices() {
		return registeredDevices;
	}

	/**
	 * Sets the value of the registeredDevices property.
	 * 
	 */
	public void setRegisteredDevices(Long value) {
		this.registeredDevices = value;
	}

}
