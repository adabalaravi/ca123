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
 * Java class for UpdateDevicePolicyRequest complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UpdateDevicePolicyRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="policyId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="policyName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="maxAssociationsNumber" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="safetyPeriodDuration" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="maximumAllowedDevices" type="{}maximumAllowedDevicesType"/>
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
@XmlType(name = "UpdateDevicePolicyRequest", propOrder = { "policyId", "policyName", "maxAssociationsNumber", "safetyPeriodDuration", "maximumAllowedDevices" })
public class UpdateDevicePolicyRequest {

	@XmlElement(required = true)
	private Long policyId;
	@XmlElement(required = true)
	private String policyName;
	@XmlElement(required = true)
	private Long maxAssociationsNumber;
	@XmlElement(required = true)
	private Long safetyPeriodDuration;
	@XmlElement(required = true)
	private MaximumAllowedDevicesType maximumAllowedDevices;

	/**
	 * Gets the value of the policyId property.
	 * 
	 */
	public Long getPolicyId() {
		return policyId;
	}

	/**
	 * Sets the value of the policyId property.
	 * 
	 */
	public void setPolicyId(Long value) {
		this.policyId = value;
	}

	/**
	 * Gets the value of the policyName property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPolicyName() {
		return policyName;
	}

	/**
	 * Sets the value of the policyName property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPolicyName(String value) {
		this.policyName = value;
	}

	/**
	 * Gets the value of the maxAssociationsNumber property.
	 * 
	 */
	public Long getMaxAssociationsNumber() {
		return maxAssociationsNumber;
	}

	/**
	 * Sets the value of the maxAssociationsNumber property.
	 * 
	 */
	public void setMaxAssociationsNumber(Long value) {
		this.maxAssociationsNumber = value;
	}

	/**
	 * Gets the value of the safetyPeriodDuration property.
	 * 
	 */
	public Long getSafetyPeriodDuration() {
		return safetyPeriodDuration;
	}

	/**
	 * Sets the value of the safetyPeriodDuration property.
	 * 
	 */
	public void setSafetyPeriodDuration(Long value) {
		this.safetyPeriodDuration = value;
	}

	/**
	 * Gets the value of the maximumAllowedDevices property.
	 * 
	 * @return possible object is {@link MaximumAllowedDevicesType }
	 * 
	 */
	public MaximumAllowedDevicesType getMaximumAllowedDevices() {
		return maximumAllowedDevices;
	}

	/**
	 * Sets the value of the maximumAllowedDevices property.
	 * 
	 * @param value
	 *            allowed object is {@link MaximumAllowedDevicesType }
	 * 
	 */
	public void setMaximumAllowedDevices(MaximumAllowedDevicesType value) {
		this.maximumAllowedDevices = value;
	}

}