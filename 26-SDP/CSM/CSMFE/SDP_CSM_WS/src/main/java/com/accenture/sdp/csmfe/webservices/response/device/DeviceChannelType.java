//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.03.15 at 03:03:47 PM CET 
//

package com.accenture.sdp.csmfe.webservices.response.device;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for deviceChannelType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deviceChannelType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="deviceChannelId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="deviceChannelName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="portable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
@XmlType(name = "deviceChannelType", propOrder = { "deviceChannelId", "deviceChannelName", "portable", "isBlacklisted", "isWhitelisted", "blacklistReason" })
public class DeviceChannelType {

	@XmlElement(required = true)
	private Long deviceChannelId;
	@XmlElement(required = true)
	private String deviceChannelName;
	private boolean portable;
	private boolean isBlacklisted;
	private boolean isWhitelisted;
	private String blacklistReason;

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
	 * Gets the value of the portable property.
	 * 
	 */
	public boolean isPortable() {
		return portable;
	}

	/**
	 * Sets the value of the portable property.
	 * 
	 */
	public void setPortable(boolean value) {
		this.portable = value;
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
	 * @return possible object is {@link String }
	 * 
	 */
	public String getBlacklistReason() {
		return blacklistReason;
	}

	/**
	 * Sets the value of the blacklistReason property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setBlacklistReason(String value) {
		this.blacklistReason = value;
	}

}
