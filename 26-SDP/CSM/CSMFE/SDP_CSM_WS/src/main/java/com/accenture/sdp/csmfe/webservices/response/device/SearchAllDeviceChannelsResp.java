//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.03.15 at 04:06:31 PM CET 
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
 * Java class for SearchAllDeviceChannelsResp complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchAllDeviceChannelsResp">
 *   &lt;complexContent>
 *     &lt;extension base="{}baseResp">
 *       &lt;element name="deviceChannels">
 *         &lt;complexType>
 *           &lt;complexContent>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *               &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *                 &lt;element name="deviceChannel" type="{}deviceChannelType"/>
 *               &lt;/sequence>
 *             &lt;/restriction>
 *           &lt;/complexContent>
 *         &lt;/complexType>
 *       &lt;/element>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchAllDeviceChannelsResp", propOrder = { "deviceChannels" })
public class SearchAllDeviceChannelsResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1392394142731258694L;

	@XmlElement(required = true)
	private SearchAllDeviceChannelsResp.DeviceChannels deviceChannels;

	/**
	 * Gets the value of the deviceChannels property.
	 * 
	 * @return possible object is {@link SearchAllDeviceChannelsResp.DeviceChannels }
	 * 
	 */
	public SearchAllDeviceChannelsResp.DeviceChannels getDeviceChannels() {
		return deviceChannels;
	}

	/**
	 * Sets the value of the deviceChannels property.
	 * 
	 * @param value
	 *            allowed object is {@link SearchAllDeviceChannelsResp.DeviceChannels }
	 * 
	 */
	public void setDeviceChannels(SearchAllDeviceChannelsResp.DeviceChannels value) {
		this.deviceChannels = value;
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
	 *         &lt;element name="deviceChannel" type="{}deviceChannelType"/>
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
	@XmlType(name = "", propOrder = { "deviceChannel" })
	public static class DeviceChannels {

		private List<DeviceChannelType> deviceChannel;

		/**
		 * Gets the value of the deviceChannel property.
		 * 
		 * <p>
		 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present
		 * inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the deviceChannel property.
		 * 
		 * <p>
		 * For example, to add a new item, do as follows:
		 * 
		 * <pre>
		 * getDeviceChannel().add(newItem);
		 * </pre>
		 * 
		 * 
		 * <p>
		 * Objects of the following type(s) are allowed in the list {@link DeviceChannelType }
		 * 
		 * 
		 */
		public List<DeviceChannelType> getDeviceChannel() {
			if (deviceChannel == null) {
				deviceChannel = new ArrayList<DeviceChannelType>();
			}
			return this.deviceChannel;
		}

	}

}
