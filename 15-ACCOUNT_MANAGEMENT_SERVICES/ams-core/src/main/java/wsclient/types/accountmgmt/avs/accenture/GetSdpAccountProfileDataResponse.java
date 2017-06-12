package wsclient.types.accountmgmt.avs.accenture;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import wsclient.commontypes.accountmgmt.avs.accenture.CrmAccountDevicesListType;
import wsclient.commontypes.accountmgmt.avs.accenture.FlagTypeYN;
import wsclient.commontypes.accountmgmt.avs.accenture.UserPcExtendedRatingsListType;


/**
 * <p>Java class for getSdpAccountProfileDataResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getSdpAccountProfileDataResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CrmAccountDevices" type="{http://accenture.avs.accountmgmt.commontypes}CrmAccountDevicesListType"/>
 *         &lt;element name="UserRememberPinFlag" type="{http://accenture.avs.accountmgmt.commontypes}FlagTypeYN"/>
 *         &lt;element name="RetailerDomain" type="{http://accenture.avs.accountmgmt.commontypes}Str4"/>
 *         &lt;element name="UserPcLevel" type="{http://accenture.avs.accountmgmt.commontypes}Number2DigitWith0"/>
 *         &lt;element name="UserPinPurchase" type="{http://accenture.avs.accountmgmt.commontypes}Str4"/>
 *         &lt;element name="UserPinParentalControl" type="{http://accenture.avs.accountmgmt.commontypes}Str4"/>
 *         &lt;element name="UserPcExtendedRatings" type="{http://accenture.avs.accountmgmt.commontypes}UserPcExtendedRatingsListType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getSdpAccountProfileDataResponse", propOrder = {
    "crmAccountDevices",
    "userRememberPinFlag",
    "retailerDomain",
    "userPcLevel",
    "userPinPurchase",
    "userPinParentalControl",
    "userPcExtendedRatings"
})
public class GetSdpAccountProfileDataResponse {

    @XmlElement(name = "CrmAccountDevices", required = true, nillable = true)
    protected CrmAccountDevicesListType crmAccountDevices;
    @XmlElement(name = "UserRememberPinFlag", required = true, nillable = true)
    protected FlagTypeYN userRememberPinFlag;
    @XmlElement(name = "RetailerDomain", required = true, nillable = true)
    protected String retailerDomain;
    @XmlElement(name = "UserPcLevel", required = true, type = Integer.class, nillable = true)
    protected Integer userPcLevel;
    @XmlElement(name = "UserPinPurchase", required = true, nillable = true)
    protected String userPinPurchase;
    @XmlElement(name = "UserPinParentalControl", required = true, nillable = true)
    protected String userPinParentalControl;
    @XmlElement(name = "UserPcExtendedRatings", required = true, nillable = true)
    protected UserPcExtendedRatingsListType userPcExtendedRatings;

    /**
     * Gets the value of the crmAccountDevices property.
     * 
     * @return
     *     possible object is
     *     {@link CrmAccountDevicesListType }
     *     
     */
    public CrmAccountDevicesListType getCrmAccountDevices() {
        return crmAccountDevices;
    }

    /**
     * Sets the value of the crmAccountDevices property.
     * 
     * @param value
     *     allowed object is
     *     {@link CrmAccountDevicesListType }
     *     
     */
    public void setCrmAccountDevices(CrmAccountDevicesListType value) {
        this.crmAccountDevices = value;
    }

    /**
     * Gets the value of the userRememberPinFlag property.
     * 
     * @return
     *     possible object is
     *     {@link FlagTypeYN }
     *     
     */
    public FlagTypeYN getUserRememberPinFlag() {
        return userRememberPinFlag;
    }

    /**
     * Sets the value of the userRememberPinFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagTypeYN }
     *     
     */
    public void setUserRememberPinFlag(FlagTypeYN value) {
        this.userRememberPinFlag = value;
    }

    /**
     * Gets the value of the retailerDomain property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRetailerDomain() {
        return retailerDomain;
    }

    /**
     * Sets the value of the retailerDomain property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRetailerDomain(String value) {
        this.retailerDomain = value;
    }

    /**
     * Gets the value of the userPcLevel property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getUserPcLevel() {
        return userPcLevel;
    }

    /**
     * Sets the value of the userPcLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setUserPcLevel(Integer value) {
        this.userPcLevel = value;
    }

    /**
     * Gets the value of the userPinPurchase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserPinPurchase() {
        return userPinPurchase;
    }

    /**
     * Sets the value of the userPinPurchase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserPinPurchase(String value) {
        this.userPinPurchase = value;
    }

    /**
     * Gets the value of the userPinParentalControl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserPinParentalControl() {
        return userPinParentalControl;
    }

    /**
     * Sets the value of the userPinParentalControl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserPinParentalControl(String value) {
        this.userPinParentalControl = value;
    }

    /**
     * Gets the value of the userPcExtendedRatings property.
     * 
     * @return
     *     possible object is
     *     {@link UserPcExtendedRatingsListType }
     *     
     */
    public UserPcExtendedRatingsListType getUserPcExtendedRatings() {
        return userPcExtendedRatings;
    }

    /**
     * Sets the value of the userPcExtendedRatings property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserPcExtendedRatingsListType }
     *     
     */
    public void setUserPcExtendedRatings(UserPcExtendedRatingsListType value) {
        this.userPcExtendedRatings = value;
    }

}
