//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.03.15 at 04:07:57 PM CET 
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
 * Java class for SearchDevicesByPartyIdResp complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchDevicesByPartyIdResp">
 *   &lt;complexContent>
 *     &lt;extension base="{}baseResp">
 *       &lt;sequence>
 *         &lt;element name="devices">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *                   &lt;element name="device" type="{}deviceType"/>
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
@XmlType(name = "SearchDevicesByPartyIdResp", propOrder = { "devices" })
public class SearchDevicesByPartyIdResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3791127530057327570L;

	@XmlElement(required = true)
	private SearchDevicesByPartyIdResp.Devices devices;

	/**
	 * Gets the value of the devices property.
	 * 
	 * @return possible object is {@link SearchDevicesByPartyIdResp.Devices }
	 * 
	 */
	public SearchDevicesByPartyIdResp.Devices getDevices() {
		return devices;
	}

	/**
	 * Sets the value of the devices property.
	 * 
	 * @param value
	 *            allowed object is {@link SearchDevicesByPartyIdResp.Devices }
	 * 
	 */
	public void setDevices(SearchDevicesByPartyIdResp.Devices value) {
		this.devices = value;
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
	 *         &lt;element name="device" type="{}deviceType"/>
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
	@XmlType(name = "", propOrder = { "device" })
	public static class Devices {

		private List<DeviceType> device;

		/**
		 * Gets the value of the device property.
		 * 
		 * <p>
		 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present
		 * inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the device property.
		 * 
		 * <p>
		 * For example, to add a new item, do as follows:
		 * 
		 * <pre>
		 * getDevice().add(newItem);
		 * </pre>
		 * 
		 * 
		 * <p>
		 * Objects of the following type(s) are allowed in the list {@link DeviceType }
		 * 
		 * 
		 */
		public List<DeviceType> getDevice() {
			if (device == null) {
				device = new ArrayList<DeviceType>();
			}
			return this.device;
		}

	}

}
