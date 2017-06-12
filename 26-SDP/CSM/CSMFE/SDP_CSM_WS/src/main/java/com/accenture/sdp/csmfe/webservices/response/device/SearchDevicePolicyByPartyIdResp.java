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
 * Java class for SearchDevicePolicyByPartyIdResp complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchDevicePolicyByPartyIdResp">
 *   &lt;complexContent>
 *     &lt;extension base="{}baseResp">
 *       &lt;element name="policy" type="{}policyType"/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(namespace = "http://com.accenture.sdp.csm.webservices.types")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchDevicePolicyByPartyIdResp", propOrder = { "policy" })
public class SearchDevicePolicyByPartyIdResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2676219042242398489L;

	@XmlElement(required = true)
	private PolicyType policy;

	/**
	 * Gets the value of the policy property.
	 * 
	 * @return possible object is {@link PolicyType }
	 * 
	 */
	public PolicyType getPolicy() {
		return policy;
	}

	/**
	 * Sets the value of the policy property.
	 * 
	 * @param value
	 *            allowed object is {@link PolicyType }
	 * 
	 */
	public void setPolicy(PolicyType value) {
		this.policy = value;
	}

}