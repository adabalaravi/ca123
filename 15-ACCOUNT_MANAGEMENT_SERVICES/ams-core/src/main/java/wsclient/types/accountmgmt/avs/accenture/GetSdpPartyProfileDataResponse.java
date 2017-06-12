package wsclient.types.accountmgmt.avs.accenture;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import wsclient.commontypes.accountmgmt.avs.accenture.FlagTypeYN;


/**
 * <p>Java class for getSdpPartyProfileDataResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getSdpPartyProfileDataResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UserType" type="{http://accenture.avs.accountmgmt.commontypes}FlagType123"/>
 *         &lt;element name="UserLanguage" type="{http://accenture.avs.accountmgmt.commontypes}StrMax512"/>
 *         &lt;element name="QualitySetting" type="{http://accenture.avs.accountmgmt.commontypes}FlagType12"/>
 *         &lt;element name="UserPaymentMethod" type="{http://accenture.avs.accountmgmt.commontypes}Number2Digit"/>
 *         &lt;element name="CrmAccountPurchaseBlockingFlag" type="{http://accenture.avs.accountmgmt.commontypes}FlagTypeYN"/>
 *         &lt;element name="CrmAccountPurchaseBlockingThresholdCurrency" type="{http://accenture.avs.accountmgmt.commontypes}Str3"/>
 *         &lt;element name="CrmAccountPurchaseBlockingThresholdValue" type="{http://accenture.avs.accountmgmt.commontypes}DecimalType"/>
 *         &lt;element name="CrmAccountPurchaseBlockingThresholdPeriod" type="{http://accenture.avs.accountmgmt.commontypes}FlagType1234"/>
 *         &lt;element name="CrmAccountPurchaseBlockingAlertEnabledFlag" type="{http://accenture.avs.accountmgmt.commontypes}FlagTypeYN"/>
 *         &lt;element name="CrmAccountPurchaseBlockingAlertChannel" type="{http://accenture.avs.accountmgmt.commontypes}FlagType123"/>
 *         &lt;element name="CrmAccountPurchaseBlockingAlertMode" type="{http://accenture.avs.accountmgmt.commontypes}FlagType123"/>
 *         &lt;element name="CrmAccountPurchaseBlockingIntermediateThreshold" type="{http://accenture.avs.accountmgmt.commontypes}DecimalType"/>
 *         &lt;element name="CrmAccountConsumptionBlockingFlag" type="{http://accenture.avs.accountmgmt.commontypes}FlagTypeYN"/>
 *         &lt;element name="CrmAccountConsumptionBlockingThresholdValue" type="{http://accenture.avs.accountmgmt.commontypes}NumMin0Max7Digit"/>
 *         &lt;element name="CrmAccountConsumptionBlockingThresholdPeriod" type="{http://accenture.avs.accountmgmt.commontypes}FlagType1234"/>
 *         &lt;element name="CrmAccountConsumptionBlockingEnabledFlag" type="{http://accenture.avs.accountmgmt.commontypes}FlagTypeYN"/>
 *         &lt;element name="CrmAccountConsumptionBlockingAlertChannel" type="{http://accenture.avs.accountmgmt.commontypes}FlagType123"/>
 *         &lt;element name="CrmAccountConsumptionBlockingAlertMode" type="{http://accenture.avs.accountmgmt.commontypes}FlagType123"/>
 *         &lt;element name="CrmAccountConsumptionBlockingIntermediateThreshold" type="{http://accenture.avs.accountmgmt.commontypes}NumMin0Max7Digit"/>
 *         &lt;element name="CrmAccountPurchaseValue" type="{http://accenture.avs.accountmgmt.commontypes}DecimalType"/>
 *         &lt;element name="CrmAccountConsumption" type="{http://accenture.avs.accountmgmt.commontypes}Number5Digit"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getSdpPartyProfileDataResponse", propOrder = {
    "userType",
    "userLanguage",
    "qualitySetting",
    "userPaymentMethod",
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
    "crmAccountConsumption"
})
public class GetSdpPartyProfileDataResponse {

    @XmlElement(name = "UserType", required = true, nillable = true)
    protected String userType;
    @XmlElement(name = "UserLanguage", required = true, nillable = true)
    protected String userLanguage;
    @XmlElement(name = "QualitySetting", required = true, nillable = true)
    protected String qualitySetting;
    @XmlElement(name = "UserPaymentMethod", required = true, type = Integer.class, nillable = true)
    protected Integer userPaymentMethod;
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

}
