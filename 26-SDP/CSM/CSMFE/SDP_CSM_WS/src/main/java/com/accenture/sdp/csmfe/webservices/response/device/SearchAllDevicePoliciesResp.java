//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.03.15 at 04:05:26 PM CET 
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
 * Java class for SearchAllDevicePoliciesResp complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchAllDevicePoliciesResp">
 *   &lt;complexContent>
 *     &lt;extension base="{}baseResp">
 *       &lt;element name="policies">
 *         &lt;complexType>
 *           &lt;complexContent>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *               &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *                 &lt;element name="policy" type="{}policyType"/>
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
@XmlType(name = "SearchAllDevicePoliciesResp", propOrder = { "policies" })
public class SearchAllDevicePoliciesResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1854382332384359870L;

	@XmlElement(required = true)
	private SearchAllDevicePoliciesResp.Policies policies;

	/**
	 * Gets the value of the policies property.
	 * 
	 * @return possible object is {@link SearchAllDevicePoliciesResp.Policies }
	 * 
	 */
	public SearchAllDevicePoliciesResp.Policies getPolicies() {
		return policies;
	}

	/**
	 * Sets the value of the policies property.
	 * 
	 * @param value
	 *            allowed object is {@link SearchAllDevicePoliciesResp.Policies }
	 * 
	 */
	public void setPolicies(SearchAllDevicePoliciesResp.Policies value) {
		this.policies = value;
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
	 *         &lt;element name="policy" type="{}policyType"/>
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
	@XmlType(name = "", propOrder = { "policy" })
	public static class Policies {

		private List<PolicyType> policy;

		/**
		 * Gets the value of the policy property.
		 * 
		 * <p>
		 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present
		 * inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the policy property.
		 * 
		 * <p>
		 * For example, to add a new item, do as follows:
		 * 
		 * <pre>
		 * getPolicy().add(newItem);
		 * </pre>
		 * 
		 * 
		 * <p>
		 * Objects of the following type(s) are allowed in the list {@link PolicyType }
		 * 
		 * 
		 */
		public List<PolicyType> getPolicy() {
			if (policy == null) {
				policy = new ArrayList<PolicyType>();
			}
			return this.policy;
		}

	}

}