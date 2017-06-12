
package commontypes.getpricebyuser.avs.accenture;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for PayloadType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PayloadType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TenantName" type="{http://accenture.avs.getpricebyuser.commontypes}StrMin1Max50"/>
 *         &lt;element name="CrmAccountId" type="{http://accenture.avs.getpricebyuser.commontypes}Str10"/>
 *         &lt;element name="UserType" type="{http://accenture.avs.getpricebyuser.commontypes}FlagType123"/>
 *         &lt;element name="UserStatus" type="{http://accenture.avs.getpricebyuser.commontypes}Number2Digit"/>
 *         &lt;element name="CrmAccountDevices" type="{http://accenture.avs.getpricebyuser.commontypes}CrmAccountDevicesListType"/>
 *         &lt;element name="Username" type="{http://accenture.avs.getpricebyuser.commontypes}StrMin4Max32"/>
 *         &lt;element name="Password" type="{http://accenture.avs.getpricebyuser.commontypes}StrMin4Max20"/>
 *         &lt;element name="Name" type="{http://accenture.avs.getpricebyuser.commontypes}StrMin1Max256"/>
 *         &lt;element name="Surname" type="{http://accenture.avs.getpricebyuser.commontypes}StrMin1Max256"/>
 *         &lt;element name="BirthDate" type="{http://accenture.avs.getpricebyuser.commontypes}DateType"/>
 *         &lt;element name="Gender" type="{http://accenture.avs.getpricebyuser.commontypes}FlagTypeMF"/>
 *         &lt;element name="CrmAccountZipCode" type="{http://accenture.avs.getpricebyuser.commontypes}ZipCodeType"/>
 *         &lt;element name="Email" type="{http://accenture.avs.getpricebyuser.commontypes}StrMin1Max100"/>
 *         &lt;element name="CrmAccountMobileNumber" type="{http://accenture.avs.getpricebyuser.commontypes}StrMin1Max20"/>
 *         &lt;element name="UserCountry" type="{http://accenture.avs.getpricebyuser.commontypes}Str2"/>
 *         &lt;element name="UserLanguage" type="{http://accenture.avs.getpricebyuser.commontypes}Str2"/>
 *         &lt;element name="QualitySetting" type="{http://accenture.avs.getpricebyuser.commontypes}FlagType12"/>
 *         &lt;element name="UserPaymentMethod" type="{http://accenture.avs.getpricebyuser.commontypes}Number2Digit"/>
 *         &lt;element name="UserRememberPinFlag" type="{http://accenture.avs.getpricebyuser.commontypes}FlagTypeYN"/>
 *         &lt;element name="UserPcLevel" type="{http://accenture.avs.getpricebyuser.commontypes}Number2DigitWith0"/>
 *         &lt;element name="UserPinPurchase" type="{http://accenture.avs.getpricebyuser.commontypes}Str4"/>
 *         &lt;element name="UserPinParentalControl" type="{http://accenture.avs.getpricebyuser.commontypes}Str4"/>
 *         &lt;element name="UserPcExtendedRatings" type="{http://accenture.avs.getpricebyuser.commontypes}UserPcExtendedRatingsListType"/>
 *         &lt;element name="CrmAccountPurchaseBlockingFlag" type="{http://accenture.avs.getpricebyuser.commontypes}FlagTypeYN"/>
 *         &lt;element name="CrmAccountPurchaseBlockingThresholdCurrency" type="{http://accenture.avs.getpricebyuser.commontypes}Str3"/>
 *         &lt;element name="CrmAccountPurchaseBlockingThresholdValue" type="{http://accenture.avs.getpricebyuser.commontypes}DecimalType"/>
 *         &lt;element name="CrmAccountPurchaseBlockingThresholdPeriod" type="{http://accenture.avs.getpricebyuser.commontypes}FlagType1234"/>
 *         &lt;element name="CrmAccountPurchaseBlockingAlertEnabledFlag" type="{http://accenture.avs.getpricebyuser.commontypes}FlagTypeYN"/>
 *         &lt;element name="CrmAccountPurchaseBlockingAlertChannel" type="{http://accenture.avs.getpricebyuser.commontypes}FlagType123"/>
 *         &lt;element name="CrmAccountPurchaseBlockingAlertMode" type="{http://accenture.avs.getpricebyuser.commontypes}FlagType123"/>
 *         &lt;element name="CrmAccountPurchaseBlockingIntermediateThreshold" type="{http://accenture.avs.getpricebyuser.commontypes}DecimalType"/>
 *         &lt;element name="CrmAccountConsumptionBlockingFlag" type="{http://accenture.avs.getpricebyuser.commontypes}FlagTypeYN"/>
 *         &lt;element name="CrmAccountConsumptionBlockingThresholdValue" type="{http://accenture.avs.getpricebyuser.commontypes}Number5Digit"/>
 *         &lt;element name="CrmAccountConsumptionBlockingThresholdPeriod" type="{http://accenture.avs.getpricebyuser.commontypes}FlagType1234"/>
 *         &lt;element name="CrmAccountConsumptionBlockingEnabledFlag" type="{http://accenture.avs.getpricebyuser.commontypes}FlagTypeYN"/>
 *         &lt;element name="CrmAccountConsumptionBlockingAlertChannel" type="{http://accenture.avs.getpricebyuser.commontypes}FlagType123"/>
 *         &lt;element name="CrmAccountConsumptionBlockingAlertMode" type="{http://accenture.avs.getpricebyuser.commontypes}FlagType123"/>
 *         &lt;element name="CrmAccountConsumptionBlockingIntermediateThreshold" type="{http://accenture.avs.getpricebyuser.commontypes}Number5Digit"/>
 *         &lt;element name="CrmAccountPurchaseValue" type="{http://accenture.avs.getpricebyuser.commontypes}DecimalType"/>
 *         &lt;element name="CrmAccountConsumption" type="{http://accenture.avs.getpricebyuser.commontypes}Number5Digit"/>
 *         &lt;element name="CrmAccountRetailerDomain" type="{http://accenture.avs.getpricebyuser.commontypes}StrMin1Max50"/>
 *         &lt;element name="UpdateCommProfileList" type="{http://accenture.avs.getpricebyuser.commontypes}UpdateProfileListType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PayloadType", propOrder = {
    "tenantName",
    "crmAccountId",
    "userType",
    "userStatus",
    "crmAccountDevices",
    "username",
    "password",
    "name",
    "surname",
    "birthDate",
    "gender",
    "crmAccountZipCode",
    "email",
    "crmAccountMobileNumber",
    "userCountry",
    "userLanguage",
    "qualitySetting",
    "userPaymentMethod",
    "userRememberPinFlag",
    "userPcLevel",
    "userPinPurchase",
    "userPinParentalControl",
    "userPcExtendedRatings",
    "crmAccountPurchaseBlockingFlag",
    "crmAccountPurchaseBlockingThresholdCurrency",
    "crmAccountPurchaseBlockingThresholdValue",
    "crmAccountPurchaseBlockingThresholdPeriod",
    "crmAccountPurchaseBlockingAlertEnabledFlag",
    "crmAccountPurchaseBlockingAlertChannel",
    "crmAccountPurchaseBlockingAlertMode",
    "crmAccountPurchaseBlockingIntermediateThreshold",
    "crmAccountConsumptionBlockingFlag",
    "crmAccountConsumptionBlockingThresholdValue",
    "crmAccountConsumptionBlockingThresholdPeriod",
    "crmAccountConsumptionBlockingEnabledFlag",
    "crmAccountConsumptionBlockingAlertChannel",
    "crmAccountConsumptionBlockingAlertMode",
    "crmAccountConsumptionBlockingIntermediateThreshold",
    "crmAccountPurchaseValue",
    "crmAccountConsumption",
    "crmAccountRetailerDomain",
    "updateCommProfileList"
})
public class PayloadType {

    @XmlElement(name = "TenantName", required = true, nillable = true)
    protected String tenantName;
    @XmlElement(name = "CrmAccountId", required = true, nillable = true)
    protected String crmAccountId;
    @XmlElement(name = "UserType", required = true, nillable = true)
    protected String userType;
    @XmlElement(name = "UserStatus", required = true, type = Integer.class, nillable = true)
    protected Integer userStatus;
    @XmlElement(name = "CrmAccountDevices", required = true, nillable = true)
    protected CrmAccountDevicesListType crmAccountDevices;
    @XmlElement(name = "Username", required = true, nillable = true)
    protected String username;
    @XmlElement(name = "Password", required = true, nillable = true)
    protected String password;
    @XmlElement(name = "Name", required = true, nillable = true)
    protected String name;
    @XmlElement(name = "Surname", required = true, nillable = true)
    protected String surname;
    @XmlElement(name = "BirthDate", required = true, nillable = true)
    protected XMLGregorianCalendar birthDate;
    @XmlElement(name = "Gender", required = true, nillable = true)
    protected FlagTypeMF gender;
    @XmlElement(name = "CrmAccountZipCode", required = true, nillable = true)
    protected String crmAccountZipCode;
    @XmlElement(name = "Email", required = true, nillable = true)
    protected String email;
    @XmlElement(name = "CrmAccountMobileNumber", required = true, nillable = true)
    protected String crmAccountMobileNumber;
    @XmlElement(name = "UserCountry", required = true, nillable = true)
    protected String userCountry;
    @XmlElement(name = "UserLanguage", required = true, nillable = true)
    protected String userLanguage;
    @XmlElement(name = "QualitySetting", required = true, nillable = true)
    protected String qualitySetting;
    @XmlElement(name = "UserPaymentMethod", required = true, type = Integer.class, nillable = true)
    protected Integer userPaymentMethod;
    @XmlElement(name = "UserRememberPinFlag", required = true, nillable = true)
    protected FlagTypeYN userRememberPinFlag;
    @XmlElement(name = "UserPcLevel", required = true, type = Integer.class, nillable = true)
    protected Integer userPcLevel;
    @XmlElement(name = "UserPinPurchase", required = true, nillable = true)
    protected String userPinPurchase;
    @XmlElement(name = "UserPinParentalControl", required = true, nillable = true)
    protected String userPinParentalControl;
    @XmlElement(name = "UserPcExtendedRatings", required = true, nillable = true)
    protected UserPcExtendedRatingsListType userPcExtendedRatings;
    @XmlElement(name = "CrmAccountPurchaseBlockingFlag", required = true, nillable = true)
    protected FlagTypeYN crmAccountPurchaseBlockingFlag;
    @XmlElement(name = "CrmAccountPurchaseBlockingThresholdCurrency", required = true, nillable = true)
    protected String crmAccountPurchaseBlockingThresholdCurrency;
    @XmlElement(name = "CrmAccountPurchaseBlockingThresholdValue", required = true, nillable = true)
    protected BigDecimal crmAccountPurchaseBlockingThresholdValue;
    @XmlElement(name = "CrmAccountPurchaseBlockingThresholdPeriod", required = true, nillable = true)
    protected String crmAccountPurchaseBlockingThresholdPeriod;
    @XmlElement(name = "CrmAccountPurchaseBlockingAlertEnabledFlag", required = true)
    protected FlagTypeYN crmAccountPurchaseBlockingAlertEnabledFlag;
    @XmlElement(name = "CrmAccountPurchaseBlockingAlertChannel", required = true, nillable = true)
    protected String crmAccountPurchaseBlockingAlertChannel;
    @XmlElement(name = "CrmAccountPurchaseBlockingAlertMode", required = true, nillable = true)
    protected String crmAccountPurchaseBlockingAlertMode;
    @XmlElement(name = "CrmAccountPurchaseBlockingIntermediateThreshold", required = true)
    protected BigDecimal crmAccountPurchaseBlockingIntermediateThreshold;
    @XmlElement(name = "CrmAccountConsumptionBlockingFlag", required = true, nillable = true)
    protected FlagTypeYN crmAccountConsumptionBlockingFlag;
    @XmlElement(name = "CrmAccountConsumptionBlockingThresholdValue", required = true, type = Integer.class, nillable = true)
    protected Integer crmAccountConsumptionBlockingThresholdValue;
    @XmlElement(name = "CrmAccountConsumptionBlockingThresholdPeriod", required = true, nillable = true)
    protected String crmAccountConsumptionBlockingThresholdPeriod;
    @XmlElement(name = "CrmAccountConsumptionBlockingEnabledFlag", required = true, nillable = true)
    protected FlagTypeYN crmAccountConsumptionBlockingEnabledFlag;
    @XmlElement(name = "CrmAccountConsumptionBlockingAlertChannel", required = true, nillable = true)
    protected String crmAccountConsumptionBlockingAlertChannel;
    @XmlElement(name = "CrmAccountConsumptionBlockingAlertMode", required = true, nillable = true)
    protected String crmAccountConsumptionBlockingAlertMode;
    @XmlElement(name = "CrmAccountConsumptionBlockingIntermediateThreshold", required = true, type = Integer.class, nillable = true)
    protected Integer crmAccountConsumptionBlockingIntermediateThreshold;
    @XmlElement(name = "CrmAccountPurchaseValue", required = true)
    protected BigDecimal crmAccountPurchaseValue;
    @XmlElement(name = "CrmAccountConsumption")
    protected int crmAccountConsumption;
    @XmlElement(name = "CrmAccountRetailerDomain", required = true)
    protected String crmAccountRetailerDomain;
    @XmlElement(name = "UpdateCommProfileList", required = true)
    protected UpdateProfileListType updateCommProfileList;

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
     *     {@link Integer }
     *     
     */
    public Integer getUserStatus() {
        return userStatus;
    }

    /**
     * Sets the value of the userStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setUserStatus(Integer value) {
        this.userStatus = value;
    }

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
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the surname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the value of the surname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSurname(String value) {
        this.surname = value;
    }

    /**
     * Gets the value of the birthDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBirthDate() {
        return birthDate;
    }

    /**
     * Sets the value of the birthDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBirthDate(XMLGregorianCalendar value) {
        this.birthDate = value;
    }

    /**
     * Gets the value of the gender property.
     * 
     * @return
     *     possible object is
     *     {@link FlagTypeMF }
     *     
     */
    public FlagTypeMF getGender() {
        return gender;
    }

    /**
     * Sets the value of the gender property.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagTypeMF }
     *     
     */
    public void setGender(FlagTypeMF value) {
        this.gender = value;
    }

    /**
     * Gets the value of the crmAccountZipCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCrmAccountZipCode() {
        return crmAccountZipCode;
    }

    /**
     * Sets the value of the crmAccountZipCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCrmAccountZipCode(String value) {
        this.crmAccountZipCode = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the crmAccountMobileNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCrmAccountMobileNumber() {
        return crmAccountMobileNumber;
    }

    /**
     * Sets the value of the crmAccountMobileNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCrmAccountMobileNumber(String value) {
        this.crmAccountMobileNumber = value;
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
     * Gets the value of the qualitySetting property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQualitySetting() {
        return qualitySetting;
    }

    /**
     * Sets the value of the qualitySetting property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQualitySetting(String value) {
        this.qualitySetting = value;
    }

    /**
     * Gets the value of the userPaymentMethod property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getUserPaymentMethod() {
        return userPaymentMethod;
    }

    /**
     * Sets the value of the userPaymentMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setUserPaymentMethod(Integer value) {
        this.userPaymentMethod = value;
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

    /**
     * Gets the value of the crmAccountPurchaseBlockingFlag property.
     * 
     * @return
     *     possible object is
     *     {@link FlagTypeYN }
     *     
     */
    public FlagTypeYN getCrmAccountPurchaseBlockingFlag() {
        return crmAccountPurchaseBlockingFlag;
    }

    /**
     * Sets the value of the crmAccountPurchaseBlockingFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagTypeYN }
     *     
     */
    public void setCrmAccountPurchaseBlockingFlag(FlagTypeYN value) {
        this.crmAccountPurchaseBlockingFlag = value;
    }

    /**
     * Gets the value of the crmAccountPurchaseBlockingThresholdCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCrmAccountPurchaseBlockingThresholdCurrency() {
        return crmAccountPurchaseBlockingThresholdCurrency;
    }

    /**
     * Sets the value of the crmAccountPurchaseBlockingThresholdCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCrmAccountPurchaseBlockingThresholdCurrency(String value) {
        this.crmAccountPurchaseBlockingThresholdCurrency = value;
    }

    /**
     * Gets the value of the crmAccountPurchaseBlockingThresholdValue property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCrmAccountPurchaseBlockingThresholdValue() {
        return crmAccountPurchaseBlockingThresholdValue;
    }

    /**
     * Sets the value of the crmAccountPurchaseBlockingThresholdValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCrmAccountPurchaseBlockingThresholdValue(BigDecimal value) {
        this.crmAccountPurchaseBlockingThresholdValue = value;
    }

    /**
     * Gets the value of the crmAccountPurchaseBlockingThresholdPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCrmAccountPurchaseBlockingThresholdPeriod() {
        return crmAccountPurchaseBlockingThresholdPeriod;
    }

    /**
     * Sets the value of the crmAccountPurchaseBlockingThresholdPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCrmAccountPurchaseBlockingThresholdPeriod(String value) {
        this.crmAccountPurchaseBlockingThresholdPeriod = value;
    }

    /**
     * Gets the value of the crmAccountPurchaseBlockingAlertEnabledFlag property.
     * 
     * @return
     *     possible object is
     *     {@link FlagTypeYN }
     *     
     */
    public FlagTypeYN getCrmAccountPurchaseBlockingAlertEnabledFlag() {
        return crmAccountPurchaseBlockingAlertEnabledFlag;
    }

    /**
     * Sets the value of the crmAccountPurchaseBlockingAlertEnabledFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagTypeYN }
     *     
     */
    public void setCrmAccountPurchaseBlockingAlertEnabledFlag(FlagTypeYN value) {
        this.crmAccountPurchaseBlockingAlertEnabledFlag = value;
    }

    /**
     * Gets the value of the crmAccountPurchaseBlockingAlertChannel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCrmAccountPurchaseBlockingAlertChannel() {
        return crmAccountPurchaseBlockingAlertChannel;
    }

    /**
     * Sets the value of the crmAccountPurchaseBlockingAlertChannel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCrmAccountPurchaseBlockingAlertChannel(String value) {
        this.crmAccountPurchaseBlockingAlertChannel = value;
    }

    /**
     * Gets the value of the crmAccountPurchaseBlockingAlertMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCrmAccountPurchaseBlockingAlertMode() {
        return crmAccountPurchaseBlockingAlertMode;
    }

    /**
     * Sets the value of the crmAccountPurchaseBlockingAlertMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCrmAccountPurchaseBlockingAlertMode(String value) {
        this.crmAccountPurchaseBlockingAlertMode = value;
    }

    /**
     * Gets the value of the crmAccountPurchaseBlockingIntermediateThreshold property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCrmAccountPurchaseBlockingIntermediateThreshold() {
        return crmAccountPurchaseBlockingIntermediateThreshold;
    }

    /**
     * Sets the value of the crmAccountPurchaseBlockingIntermediateThreshold property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCrmAccountPurchaseBlockingIntermediateThreshold(BigDecimal value) {
        this.crmAccountPurchaseBlockingIntermediateThreshold = value;
    }

    /**
     * Gets the value of the crmAccountConsumptionBlockingFlag property.
     * 
     * @return
     *     possible object is
     *     {@link FlagTypeYN }
     *     
     */
    public FlagTypeYN getCrmAccountConsumptionBlockingFlag() {
        return crmAccountConsumptionBlockingFlag;
    }

    /**
     * Sets the value of the crmAccountConsumptionBlockingFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagTypeYN }
     *     
     */
    public void setCrmAccountConsumptionBlockingFlag(FlagTypeYN value) {
        this.crmAccountConsumptionBlockingFlag = value;
    }

    /**
     * Gets the value of the crmAccountConsumptionBlockingThresholdValue property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCrmAccountConsumptionBlockingThresholdValue() {
        return crmAccountConsumptionBlockingThresholdValue;
    }

    /**
     * Sets the value of the crmAccountConsumptionBlockingThresholdValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCrmAccountConsumptionBlockingThresholdValue(Integer value) {
        this.crmAccountConsumptionBlockingThresholdValue = value;
    }

    /**
     * Gets the value of the crmAccountConsumptionBlockingThresholdPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCrmAccountConsumptionBlockingThresholdPeriod() {
        return crmAccountConsumptionBlockingThresholdPeriod;
    }

    /**
     * Sets the value of the crmAccountConsumptionBlockingThresholdPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCrmAccountConsumptionBlockingThresholdPeriod(String value) {
        this.crmAccountConsumptionBlockingThresholdPeriod = value;
    }

    /**
     * Gets the value of the crmAccountConsumptionBlockingEnabledFlag property.
     * 
     * @return
     *     possible object is
     *     {@link FlagTypeYN }
     *     
     */
    public FlagTypeYN getCrmAccountConsumptionBlockingEnabledFlag() {
        return crmAccountConsumptionBlockingEnabledFlag;
    }

    /**
     * Sets the value of the crmAccountConsumptionBlockingEnabledFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagTypeYN }
     *     
     */
    public void setCrmAccountConsumptionBlockingEnabledFlag(FlagTypeYN value) {
        this.crmAccountConsumptionBlockingEnabledFlag = value;
    }

    /**
     * Gets the value of the crmAccountConsumptionBlockingAlertChannel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCrmAccountConsumptionBlockingAlertChannel() {
        return crmAccountConsumptionBlockingAlertChannel;
    }

    /**
     * Sets the value of the crmAccountConsumptionBlockingAlertChannel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCrmAccountConsumptionBlockingAlertChannel(String value) {
        this.crmAccountConsumptionBlockingAlertChannel = value;
    }

    /**
     * Gets the value of the crmAccountConsumptionBlockingAlertMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCrmAccountConsumptionBlockingAlertMode() {
        return crmAccountConsumptionBlockingAlertMode;
    }

    /**
     * Sets the value of the crmAccountConsumptionBlockingAlertMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCrmAccountConsumptionBlockingAlertMode(String value) {
        this.crmAccountConsumptionBlockingAlertMode = value;
    }

    /**
     * Gets the value of the crmAccountConsumptionBlockingIntermediateThreshold property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCrmAccountConsumptionBlockingIntermediateThreshold() {
        return crmAccountConsumptionBlockingIntermediateThreshold;
    }

    /**
     * Sets the value of the crmAccountConsumptionBlockingIntermediateThreshold property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCrmAccountConsumptionBlockingIntermediateThreshold(Integer value) {
        this.crmAccountConsumptionBlockingIntermediateThreshold = value;
    }

    /**
     * Gets the value of the crmAccountPurchaseValue property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCrmAccountPurchaseValue() {
        return crmAccountPurchaseValue;
    }

    /**
     * Sets the value of the crmAccountPurchaseValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCrmAccountPurchaseValue(BigDecimal value) {
        this.crmAccountPurchaseValue = value;
    }

    /**
     * Gets the value of the crmAccountConsumption property.
     * 
     */
    public int getCrmAccountConsumption() {
        return crmAccountConsumption;
    }

    /**
     * Sets the value of the crmAccountConsumption property.
     * 
     */
    public void setCrmAccountConsumption(int value) {
        this.crmAccountConsumption = value;
    }

    /**
     * Gets the value of the crmAccountRetailerDomain property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCrmAccountRetailerDomain() {
        return crmAccountRetailerDomain;
    }

    /**
     * Sets the value of the crmAccountRetailerDomain property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCrmAccountRetailerDomain(String value) {
        this.crmAccountRetailerDomain = value;
    }

    /**
     * Gets the value of the updateCommProfileList property.
     * 
     * @return
     *     possible object is
     *     {@link UpdateProfileListType }
     *     
     */
    public UpdateProfileListType getUpdateCommProfileList() {
        return updateCommProfileList;
    }

    /**
     * Sets the value of the updateCommProfileList property.
     * 
     * @param value
     *     allowed object is
     *     {@link UpdateProfileListType }
     *     
     */
    public void setUpdateCommProfileList(UpdateProfileListType value) {
        this.updateCommProfileList = value;
    }

}
