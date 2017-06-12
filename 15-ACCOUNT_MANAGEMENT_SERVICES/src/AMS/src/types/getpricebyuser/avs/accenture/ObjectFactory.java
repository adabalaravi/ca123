
package types.getpricebyuser.avs.accenture;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the types.getpricebyuser.avs.accenture package. 
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

    private final static QName _GetContentPriceByUserRequest_QNAME = new QName("http://accenture.avs.getpricebyuser.types", "GetContentPriceByUserRequest");
    private final static QName _GetBundlePriceByUserRequest_QNAME = new QName("http://accenture.avs.getpricebyuser.types", "GetBundlePriceByUserRequest");
    private final static QName _GetPpvPriceByUserRequest_QNAME = new QName("http://accenture.avs.getpricebyuser.types", "GetPpvPriceByUserRequest");
    private final static QName _GetProductPriceByUserRequest_QNAME = new QName("http://accenture.avs.getpricebyuser.types", "GetProductPriceByUserRequest");
    private final static QName _GetPriceByUserResponse_QNAME = new QName("http://accenture.avs.getpricebyuser.types", "GetPriceByUserResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: types.getpricebyuser.avs.accenture
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetPriceByUserResponseData }
     * 
     */
    public GetPriceByUserResponseData createGetPriceByUserResponseData() {
        return new GetPriceByUserResponseData();
    }

    /**
     * Create an instance of {@link GetPriceByUserRequest }
     * 
     */
    public GetPriceByUserRequest createGetPriceByUserRequest() {
        return new GetPriceByUserRequest();
    }

    /**
     * Create an instance of {@link GetPriceByUserResultData }
     * 
     */
    public GetPriceByUserResultData createGetPriceByUserResultData() {
        return new GetPriceByUserResultData();
    }

    /**
     * Create an instance of {@link GetPriceByUserResponse }
     * 
     */
    public GetPriceByUserResponse createGetPriceByUserResponse() {
        return new GetPriceByUserResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPriceByUserRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://accenture.avs.getpricebyuser.types", name = "GetContentPriceByUserRequest")
    public JAXBElement<GetPriceByUserRequest> createGetContentPriceByUserRequest(GetPriceByUserRequest value) {
        return new JAXBElement<GetPriceByUserRequest>(_GetContentPriceByUserRequest_QNAME, GetPriceByUserRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPriceByUserRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://accenture.avs.getpricebyuser.types", name = "GetBundlePriceByUserRequest")
    public JAXBElement<GetPriceByUserRequest> createGetBundlePriceByUserRequest(GetPriceByUserRequest value) {
        return new JAXBElement<GetPriceByUserRequest>(_GetBundlePriceByUserRequest_QNAME, GetPriceByUserRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPriceByUserRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://accenture.avs.getpricebyuser.types", name = "GetPpvPriceByUserRequest")
    public JAXBElement<GetPriceByUserRequest> createGetPpvPriceByUserRequest(GetPriceByUserRequest value) {
        return new JAXBElement<GetPriceByUserRequest>(_GetPpvPriceByUserRequest_QNAME, GetPriceByUserRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPriceByUserRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://accenture.avs.getpricebyuser.types", name = "GetProductPriceByUserRequest")
    public JAXBElement<GetPriceByUserRequest> createGetProductPriceByUserRequest(GetPriceByUserRequest value) {
        return new JAXBElement<GetPriceByUserRequest>(_GetProductPriceByUserRequest_QNAME, GetPriceByUserRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPriceByUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://accenture.avs.getpricebyuser.types", name = "GetPriceByUserResponse")
    public JAXBElement<GetPriceByUserResponse> createGetPriceByUserResponse(GetPriceByUserResponse value) {
        return new JAXBElement<GetPriceByUserResponse>(_GetPriceByUserResponse_QNAME, GetPriceByUserResponse.class, null, value);
    }

}
