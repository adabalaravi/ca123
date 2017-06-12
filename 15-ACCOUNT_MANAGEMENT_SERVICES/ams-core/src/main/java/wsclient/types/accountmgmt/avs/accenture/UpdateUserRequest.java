package wsclient.types.accountmgmt.avs.accenture;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import wsclient.commontypes.accountmgmt.avs.accenture.FlagTypeYN;
import wsclient.commontypes.accountmgmt.avs.accenture.UserPcExtendedRatingsListType;


/**
 * <p>Java class for updateUserRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateUserRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TenantName" type="{http://accenture.avs.accountmgmt.commontypes}StrMin1Max50"/>
 *         &lt;element name="CrmAccountId" type="{http://accenture.avs.accountmgmt.commontypes}StrMin1Max50"/>
 *         &lt;element name="UserType" type="{http://accenture.avs.accountmgmt.commontypes}FlagType123"/>
 *         &lt;element name="UserStatus" type="{http://accenture.avs.accountmgmt.commontypes}UserStatusType"/>
 *         &lt;element name="Username" type="{http://accenture.avs.accountmgmt.commontypes}StrMin1Max256"/>
 *         &lt;element name="NewUsername" type="{http://accenture.avs.accountmgmt.commontypes}StrMin4Max32"/>
 *         &lt;element name="UserCountry" type="{http://accenture.avs.accountmgmt.commontypes}Str2"/>
 *         &lt;element name="UserLanguage" type="{http://accenture.avs.accountmgmt.commontypes}Str2"/>
 *         &lt;element name="UserRememberPinFlag" type="{http://accenture.avs.accountmgmt.commontypes}FlagTypeYN"/>
 *         &lt;element name="UserPcLevel" type="{http://accenture.avs.accountmgmt.commontypes}Number2Digit"/>
 *         &lt;element name="UserPinPurchase" type="{http://accenture.avs.accountmgmt.commontypes}Str4"/>
 *         &lt;element name="NewPinPurchase" type="{http://accenture.avs.accountmgmt.commontypes}Str4"/>
 *         &lt;element name="UserPinParentalControl" type="{http://accenture.avs.accountmgmt.commontypes}Str4"/>
 *         &lt;element name="NewPinParentalControl" type="{http://accenture.avs.accountmgmt.commontypes}Str4"/>
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
@XmlType(name = "updateUserRequest", propOrder = {
    "tenantName",
    "crmAccountId",
    "userType",
    "userStatus",
    "username",
    "newUsername",
    "userCountry",
    "userLanguage",
    "userRememberPinFlag",
    "userPcLevel",
    "userPinPurchase",
    "newPinPurchase",
    "userPinParentalControl",
    "newPinParentalControl",
    "userPcExtendedRatings"
})
public class UpdateUserRequest {

    @XmlElement(name = "TenantName", required = true, nillable = true)
    protected String tenantName;
    @XmlElement(name = "CrmAccountId", required = true)
    protected String crmAccountId;
    @XmlElement(name = "UserType", required = true, nillable = true)
    protected String userType;
    @XmlElement(name = "UserStatus", required = true, nillable = true)
    protected String userStatus;
    @XmlElement(name = "Username", required = true)
    protected String username;
    @XmlElement(name = "NewUsername", required = true, nillable = true)
    protected String newUsername;
    @XmlElement(name = "UserCountry", required = true, nillable = true)
    protected String userCountry;
    @XmlElement(name = "UserLanguage", required = true, nillable = true)
    protected String userLanguage;
    @XmlElement(name = "UserRememberPinFlag", required = true, nillable = true)
    protected FlagTypeYN userRememberPinFlag;
    @XmlElement(name = "UserPcLevel", required = true, type = Integer.class, nillable = true)
    protected Integer userPcLevel;
    @XmlElement(name = "UserPinPurchase", required = true, nillable = true)
    protected String userPinPurchase;
    @XmlElement(name = "NewPinPurchase", required = true, nillable = true)
    protected String newPinPurchase;
    @XmlElement(name = "UserPinParentalControl", required = true, nillable = true)
    protected String userPinParentalControl;
    @XmlElement(name = "NewPinParentalControl", required = true, nillable = true)
    protected String newPinParentalControl;
    @XmlElement(name = "UserPcExtendedRatings", required = true, nillable = true)
    protected UserPcExtendedRatingsListType userPcExtendedRatings;

    /**
     * Gets the value of the tenantName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTenantName() {
        return tenantName;
    }

    /**
     * Sets the value of the tenantName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTenantName(String value) {
        this.tenantName = value;
    }

    /**
     * Gets the value of the crmAccountId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCrmAccountId() {
        return crmAccountId;
    }

    /**
     * Sets the value of the crmAccountId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCrmAccountId(String value) {
        this.crmAccountId = value;
    }

    /**
     * Gets the value of the userType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserType() {
        return userType;
    }

    /**
     * Sets the value of the userType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserType(String value) {
        this.userType = value;
    }

    /**
     * Gets the value of the userStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserStatus() {
        return userStatus;
    }

    /**
     * Sets the value of the userStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserStatus(String value) {
        this.userStatus = value;
    }

    /**
     * Gets the value of the username property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the value of the username property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * Gets the value of the newUsername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewUsername() {
        return newUsername;
    }

    /**
     * Sets the value of the newUsername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewUsername(String value) {
        this.newUsername = value;
    }

    /**
     * Gets the value of the userCountry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserCountry() {
        return userCountry;
    }

    /**
     * Sets the value of the userCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserCountry(String value) {
        this.userCountry = value;
    }

    /**
     * Gets the value of the userLanguage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserLanguage() {
        return userLanguage;
    }

    /**
     * Sets the value of the userLanguage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserLanguage(String value) {
        this.userLanguage = value;
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
     * Gets the value of the newPinPurchase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewPinPurchase() {
        return newPinPurchase;
    }

    /**
     * Sets the value of the newPinPurchase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewPinPurchase(String value) {
        this.newPinPurchase = value;
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
     * Gets the value of the newPinParentalControl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewPinParentalControl() {
        return newPinParentalControl;
    }

    /**
     * Sets the value of the newPinParentalControl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewPinParentalControl(String value) {
        this.newPinParentalControl = value;
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
