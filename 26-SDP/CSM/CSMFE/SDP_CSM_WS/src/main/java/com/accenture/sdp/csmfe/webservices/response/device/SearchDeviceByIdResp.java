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

import com.accenture.sdp.csmfe.webservices.response.BaseResp;

/**
 * <p>
 * Java class for SearchDeviceByIdResp complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchDeviceByIdResp">
 *   &lt;complexContent>
 *     &lt;extension base="{}baseResp">
 *       &lt;element name="device" type="{}deviceType"/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchDeviceByIdResp", propOrder = { "device" })
public class SearchDeviceByIdResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 939739251513817029L;

	@XmlElement(required = true)
	private DeviceType device;

	/**
	 * Gets the value of the device property.
	 * 
	 * @return possible object is {@link DeviceType }
	 * 
	 */
	public DeviceType getDevice() {
		return device;
	}

	/**
	 * Sets the value of the device property.
	 * 
	 * @param value
	 *            allowed object is {@link DeviceType }
	 * 
	 */
	public void setDevice(DeviceType value) {
		this.device = value;
	}

}