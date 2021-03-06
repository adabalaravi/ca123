//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.04.11 at 03:52:18 PM CEST 
//


package com.accenture.sdp.csmfe.restservices.response.shoppingcart;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import com.accenture.sdp.csmfe.webservices.response.BaseResp;
import com.accenture.sdp.csmfe.webservices.response.ParameterInfoResp;
import com.accenture.sdp.csmfe.webservices.response.ParameterListResp;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.accenture.sdp.csmfe.restservices.responses.shoppingcart package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

	private final static QName _RemoveItemsFromShoppingCartResponse_QNAME = new QName("", "RemoveItemsFromShoppingCartResponse");
    private final static QName _GetShoppingCartByIdResponse_QNAME = new QName("", "GetShoppingCartByIdResponse");
    private final static QName _AddItemsToShoppingCartResponse_QNAME = new QName("", "AddItemsToShoppingCartResponse");
    private final static QName _GetShoppingCartsByPartyIdResponse_QNAME = new QName("", "GetShoppingCartsByPartyIdResponse");
    private final static QName _ShoppingCartChangeStatusResponse_QNAME = new QName("", "ShoppingCartChangeStatusResponse");
    private final static QName _RemoveShoppingCartResponse_QNAME = new QName("", "RemoveShoppingCartResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.accenture.sdp.csmfe.restservices.responses.shoppingcart
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ShoppingCartType }
     * 
     */
    public ShoppingCartType createShoppingCartType() {
        return new ShoppingCartType();
    }

    /**
     * Create an instance of {@link GetShoppingCartsByPartyIdResponse }
     * 
     */
    public GetShoppingCartsByPartyIdResponse createGetShoppingCartsByPartyIdResponse() {
        return new GetShoppingCartsByPartyIdResponse();
    }

    /**
     * Create an instance of {@link BaseResp }
     * 
     */
    public BaseResp createBaseResp() {
        return new BaseResp();
    }

    /**
     * Create an instance of {@link GetShoppingCartByIdResponse }
     * 
     */
    public GetShoppingCartByIdResponse createGetShoppingCartByIdResponse() {
        return new GetShoppingCartByIdResponse();
    }

    /**
     * Create an instance of {@link RemoveShoppingCartItemType }
     * 
     */
    public RemoveShoppingCartItemType createRemoveShoppingCartItemType() {
        return new RemoveShoppingCartItemType();
    }

    /**
     * Create an instance of {@link ParameterInfoResp }
     * 
     */
    public ParameterInfoResp createParameterInfoResp() {
        return new ParameterInfoResp();
    }

    /**
     * Create an instance of {@link ShoppingCartItemExtendedType }
     * 
     */
    public ShoppingCartItemExtendedType createShoppingCartItemExtendedType() {
        return new ShoppingCartItemExtendedType();
    }

    /**
     * Create an instance of {@link ShoppingCartItemBaseType }
     * 
     */
    public ShoppingCartItemBaseType createShoppingCartItemBaseType() {
        return new ShoppingCartItemBaseType();
    }

    /**
     * Create an instance of {@link ParameterListResp }
     * 
     */
    public ParameterListResp createParameterListResp() {
        return new ParameterListResp();
    }

    /**
     * Create an instance of {@link ShoppingCartType.Items }
     * 
     */
    public ShoppingCartType.Items createShoppingCartTypeItems() {
        return new ShoppingCartType.Items();
    }

    /**
     * Create an instance of {@link GetShoppingCartsByPartyIdResponse.ShoppingCartList }
     * 
     */
    public GetShoppingCartsByPartyIdResponse.ShoppingCartList createGetShoppingCartsByPartyIdResponseShoppingCartList() {
        return new GetShoppingCartsByPartyIdResponse.ShoppingCartList();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BaseResp }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "RemoveItemsFromShoppingCartResponse")
    public JAXBElement<RemoveItemsFromShoppingCartResponse> createRemoveItemsFromShoppingCartResponse(RemoveItemsFromShoppingCartResponse value) {
        return new JAXBElement<RemoveItemsFromShoppingCartResponse>(_RemoveItemsFromShoppingCartResponse_QNAME, RemoveItemsFromShoppingCartResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetShoppingCartByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "GetShoppingCartByIdResponse")
    public JAXBElement<GetShoppingCartByIdResponse> createGetShoppingCartByIdResponse(GetShoppingCartByIdResponse value) {
        return new JAXBElement<GetShoppingCartByIdResponse>(_GetShoppingCartByIdResponse_QNAME, GetShoppingCartByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BaseResp }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "AddItemsToShoppingCartResponse")
    public JAXBElement<AddItemsToShoppingCartResponse> createAddItemsToShoppingCartResponse(AddItemsToShoppingCartResponse value) {
        return new JAXBElement<AddItemsToShoppingCartResponse>(_AddItemsToShoppingCartResponse_QNAME, AddItemsToShoppingCartResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetShoppingCartsByPartyIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "GetShoppingCartsByPartyIdResponse")
    public JAXBElement<GetShoppingCartsByPartyIdResponse> createGetShoppingCartsByPartyIdResponse(GetShoppingCartsByPartyIdResponse value) {
        return new JAXBElement<GetShoppingCartsByPartyIdResponse>(_GetShoppingCartsByPartyIdResponse_QNAME, GetShoppingCartsByPartyIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BaseResp }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ShoppingCartChangeStatusResponse")
    public JAXBElement<ShoppingCartChangeStatusResponse> createShoppingCartChangeStatusResponse(ShoppingCartChangeStatusResponse value) {
        return new JAXBElement<ShoppingCartChangeStatusResponse>(_ShoppingCartChangeStatusResponse_QNAME, ShoppingCartChangeStatusResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BaseResp }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "RemoveShoppingCartResponse")
    public JAXBElement<RemoveShoppingCartResponse> createRemoveShoppingCartResponse(RemoveShoppingCartResponse value) {
        return new JAXBElement<RemoveShoppingCartResponse>(_RemoveShoppingCartResponse_QNAME, RemoveShoppingCartResponse.class, null, value);
    }

}
