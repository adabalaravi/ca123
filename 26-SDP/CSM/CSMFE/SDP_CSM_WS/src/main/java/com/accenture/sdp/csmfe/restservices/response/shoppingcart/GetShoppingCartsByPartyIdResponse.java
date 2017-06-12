//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.04.11 at 03:52:18 PM CEST 
//


package com.accenture.sdp.csmfe.restservices.response.shoppingcart;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;


/**
 * <p>Java class for GetShoppingCartsByPartyIdResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetShoppingCartsByPartyIdResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{}baseResp">
 *       &lt;sequence>
 *         &lt;element name="shoppingCartList">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="shoppingCart" type="{}shoppingCartType" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetShoppingCartsByPartyIdResponse", propOrder = {
    "shoppingCartList"
})
public class GetShoppingCartsByPartyIdResponse
    extends BaseResp
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(required = true)
    protected GetShoppingCartsByPartyIdResponse.ShoppingCartList shoppingCartList;

    /**
     * Gets the value of the shoppingCartList property.
     * 
     * @return
     *     possible object is
     *     {@link GetShoppingCartsByPartyIdResponse.ShoppingCartList }
     *     
     */
    public GetShoppingCartsByPartyIdResponse.ShoppingCartList getShoppingCartList() {
        return shoppingCartList;
    }

    /**
     * Sets the value of the shoppingCartList property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetShoppingCartsByPartyIdResponse.ShoppingCartList }
     *     
     */
    public void setShoppingCartList(GetShoppingCartsByPartyIdResponse.ShoppingCartList value) {
        this.shoppingCartList = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="shoppingCart" type="{}shoppingCartType" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "shoppingCart"
    })
    public static class ShoppingCartList {

        protected List<ShoppingCartType> shoppingCart;

        /**
         * Gets the value of the shoppingCart property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the shoppingCart property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getShoppingCart().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ShoppingCartType }
         * 
         * 
         */
        public List<ShoppingCartType> getShoppingCart() {
            if (shoppingCart == null) {
                shoppingCart = new ArrayList<ShoppingCartType>();
            }
            return this.shoppingCart;
        }

    }

}
