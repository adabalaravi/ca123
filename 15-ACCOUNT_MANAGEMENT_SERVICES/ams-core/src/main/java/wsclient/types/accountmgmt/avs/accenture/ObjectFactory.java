package wsclient.types.accountmgmt.avs.accenture;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the types.accountmgmt.avs.accenture package. 
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

    private final static QName _SdpDataRequestParty_QNAME = new QName("http://accenture.avs.accountmgmt.types", "SdpDataRequestParty");
    private final static QName _CrmContentPurchaseRequest_QNAME = new QName("http://accenture.avs.accountmgmt.types", "CrmContentPurchaseRequest");
    private final static QName _UpdateUserPwdRequest_QNAME = new QName("http://accenture.avs.accountmgmt.types", "UpdateUserPwdRequest");
    private final static QName _UpdateCrmAccountCommercialProfileRequest_QNAME = new QName("http://accenture.avs.accountmgmt.types", "UpdateCrmAccountCommercialProfileRequest");
    private final static QName _GetSdpPartyProfileDataResponse_QNAME = new QName("http://accenture.avs.accountmgmt.types", "GetSdpPartyProfileDataResponse");
    private final static QName _CrmAccountMgmtRequest_QNAME = new QName("http://accenture.avs.accountmgmt.types", "CrmAccountMgmtRequest");
    private final static QName _GetSdpAccountProfileDataResponse_QNAME = new QName("http://accenture.avs.accountmgmt.types", "GetSdpAccountProfileDataResponse");
    private final static QName _AccountMgmtResponse_QNAME = new QName("http://accenture.avs.accountmgmt.types", "AccountMgmtResponse");
    private final static QName _SdpDataRequestProfile_QNAME = new QName("http://accenture.avs.accountmgmt.types", "SdpDataRequestProfile");
    private final static QName _UpdateUserRequest_QNAME = new QName("http://accenture.avs.accountmgmt.types", "UpdateUserRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: types.accountmgmt.avs.accenture
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CrmAccountMgmtRequest }
     * 
     */
    public CrmAccountMgmtRequest createCrmAccountMgmtRequest() {
        return new CrmAccountMgmtRequest();
    }

    /**
     * Create an instance of {@link SdpPartyProfileDataResponse }
     * 
     */
    public SdpPartyProfileDataResponse createSdpPartyProfileDataResponse() {
        return new SdpPartyProfileDataResponse();
    }

    /**
     * Create an instance of {@link UpdateCrmAccountCommercialProfileRequest }
     * 
     */
    public UpdateCrmAccountCommercialProfileRequest createUpdateCrmAccountCommercialProfileRequest() {
        return new UpdateCrmAccountCommercialProfileRequest();
    }

    /**
     * Create an instance of {@link UpdateUserPwdRequest }
     * 
     */
    public UpdateUserPwdRequest createUpdateUserPwdRequest() {
        return new UpdateUserPwdRequest();
    }

    /**
     * Create an instance of {@link CrmContentPurchaseRequest }
     * 
     */
    public CrmContentPurchaseRequest createCrmContentPurchaseRequest() {
        return new CrmContentPurchaseRequest();
    }

    /**
     * Create an instance of {@link SdpDataRequest }
     * 
     */
    public SdpDataRequest createSdpDataRequest() {
        return new SdpDataRequest();
    }

    /**
     * Create an instance of {@link AccountMgmtResponse }
     * 
     */
    public AccountMgmtResponse createAccountMgmtResponse() {
        return new AccountMgmtResponse();
    }

    /**
     * Create an instance of {@link SdpAccountProfileDataResponse }
     * 
     */
    public SdpAccountProfileDataResponse createSdpAccountProfileDataResponse() {
        return new SdpAccountProfileDataResponse();
    }

    /**
     * Create an instance of {@link UpdateUserRequest }
     * 
     */
    public UpdateUserRequest createUpdateUserRequest() {
        return new UpdateUserRequest();
    }

    /**
     * Create an instance of {@link GetSdpAccountProfileDataResponse }
     * 
     */
    public GetSdpAccountProfileDataResponse createGetSdpAccountProfileDataResponse() {
        return new GetSdpAccountProfileDataResponse();
    }

    /**
     * Create an instance of {@link GetSdpPartyProfileDataResponse }
     * 
     */
    public GetSdpPartyProfileDataResponse createGetSdpPartyProfileDataResponse() {
        return new GetSdpPartyProfileDataResponse();
    }

    /**
     * Create an instance of {@link SdpResult }
     * 
     */
    public SdpResult createSdpResult() {
        return new SdpResult();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SdpDataRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://accenture.avs.accountmgmt.types", name = "SdpDataRequestParty")
    public JAXBElement<SdpDataRequest> createSdpDataRequestParty(SdpDataRequest value) {
        return new JAXBElement<SdpDataRequest>(_SdpDataRequestParty_QNAME, SdpDataRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CrmContentPurchaseRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://accenture.avs.accountmgmt.types", name = "CrmContentPurchaseRequest")
    public JAXBElement<CrmContentPurchaseRequest> createCrmContentPurchaseRequest(CrmContentPurchaseRequest value) {
        return new JAXBElement<CrmContentPurchaseRequest>(_CrmContentPurchaseRequest_QNAME, CrmContentPurchaseRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateUserPwdRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://accenture.avs.accountmgmt.types", name = "UpdateUserPwdRequest")
    public JAXBElement<UpdateUserPwdRequest> createUpdateUserPwdRequest(UpdateUserPwdRequest value) {
        return new JAXBElement<UpdateUserPwdRequest>(_UpdateUserPwdRequest_QNAME, UpdateUserPwdRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCrmAccountCommercialProfileRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://accenture.avs.accountmgmt.types", name = "UpdateCrmAccountCommercialProfileRequest")
    public JAXBElement<UpdateCrmAccountCommercialProfileRequest> createUpdateCrmAccountCommercialProfileRequest(UpdateCrmAccountCommercialProfileRequest value) {
        return new JAXBElement<UpdateCrmAccountCommercialProfileRequest>(_UpdateCrmAccountCommercialProfileRequest_QNAME, UpdateCrmAccountCommercialProfileRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SdpPartyProfileDataResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://accenture.avs.accountmgmt.types", name = "GetSdpPartyProfileDataResponse")
    public JAXBElement<SdpPartyProfileDataResponse> createGetSdpPartyProfileDataResponse(SdpPartyProfileDataResponse value) {
        return new JAXBElement<SdpPartyProfileDataResponse>(_GetSdpPartyProfileDataResponse_QNAME, SdpPartyProfileDataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CrmAccountMgmtRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://accenture.avs.accountmgmt.types", name = "CrmAccountMgmtRequest")
    public JAXBElement<CrmAccountMgmtRequest> createCrmAccountMgmtRequest(CrmAccountMgmtRequest value) {
        return new JAXBElement<CrmAccountMgmtRequest>(_CrmAccountMgmtRequest_QNAME, CrmAccountMgmtRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SdpAccountProfileDataResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://accenture.avs.accountmgmt.types", name = "GetSdpAccountProfileDataResponse")
    public JAXBElement<SdpAccountProfileDataResponse> createGetSdpAccountProfileDataResponse(SdpAccountProfileDataResponse value) {
        return new JAXBElement<SdpAccountProfileDataResponse>(_GetSdpAccountProfileDataResponse_QNAME, SdpAccountProfileDataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountMgmtResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://accenture.avs.accountmgmt.types", name = "AccountMgmtResponse")
    public JAXBElement<AccountMgmtResponse> createAccountMgmtResponse(AccountMgmtResponse value) {
        return new JAXBElement<AccountMgmtResponse>(_AccountMgmtResponse_QNAME, AccountMgmtResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SdpDataRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://accenture.avs.accountmgmt.types", name = "SdpDataRequestProfile")
    public JAXBElement<SdpDataRequest> createSdpDataRequestProfile(SdpDataRequest value) {
        return new JAXBElement<SdpDataRequest>(_SdpDataRequestProfile_QNAME, SdpDataRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateUserRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://accenture.avs.accountmgmt.types", name = "UpdateUserRequest")
    public JAXBElement<UpdateUserRequest> createUpdateUserRequest(UpdateUserRequest value) {
        return new JAXBElement<UpdateUserRequest>(_UpdateUserRequest_QNAME, UpdateUserRequest.class, null, value);
    }

}
