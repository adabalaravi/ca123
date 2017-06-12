package wsclient.types.packageingestor.avs.accenture;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the types.packageingestor.avs.accenture package. 
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

    private final static QName _PackageIngestorRequest_QNAME = new QName("http://accenture.avs.packageingestor.types", "PackageIngestorRequest");
    private final static QName _PackageIngestorResponse_QNAME = new QName("http://accenture.avs.packageingestor.types", "PackageIngestorResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: types.packageingestor.avs.accenture
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PackageIngestorResponse }
     * 
     */
    public PackageIngestorResponse createPackageIngestorResponse() {
        return new PackageIngestorResponse();
    }

    /**
     * Create an instance of {@link PackageIngestorRequest }
     * 
     */
    public PackageIngestorRequest createPackageIngestorRequest() {
        return new PackageIngestorRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PackageIngestorRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://accenture.avs.packageingestor.types", name = "PackageIngestorRequest")
    public JAXBElement<PackageIngestorRequest> createPackageIngestorRequest(PackageIngestorRequest value) {
        return new JAXBElement<PackageIngestorRequest>(_PackageIngestorRequest_QNAME, PackageIngestorRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PackageIngestorResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://accenture.avs.packageingestor.types", name = "PackageIngestorResponse")
    public JAXBElement<PackageIngestorResponse> createPackageIngestorResponse(PackageIngestorResponse value) {
        return new JAXBElement<PackageIngestorResponse>(_PackageIngestorResponse_QNAME, PackageIngestorResponse.class, null, value);
    }

}
