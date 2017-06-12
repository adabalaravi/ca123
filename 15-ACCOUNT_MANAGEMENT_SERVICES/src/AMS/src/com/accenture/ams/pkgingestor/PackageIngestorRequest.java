
package com.accenture.ams.pkgingestor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for packageIngestorRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="packageIngestorRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TenantName" type="{http://accenture.avs.packageingestor.commontypes}StrMin1Max50"/>
 *         &lt;element name="ExternalOfferId" type="{http://accenture.avs.packageingestor.commontypes}NumMin1Max11Digit"/>
 *         &lt;element name="PackageName" type="{http://accenture.avs.packageingestor.commontypes}StrMax100"/>
 *         &lt;element name="PackageType" type="{http://accenture.avs.packageingestor.commontypes}StrMin1Max50"/>
 *         &lt;element name="PackageDescription" type="{http://accenture.avs.packageingestor.commontypes}StrMax100"/>
 *         &lt;element name="IsEnabled" type="{http://accenture.avs.packageingestor.commontypes}FlagTypeYN"/>
 *         &lt;element name="ValidityStartDate" type="{http://accenture.avs.packageingestor.commontypes}DateType" minOccurs="0"/>
 *         &lt;element name="ValidityEndDate" type="{http://accenture.avs.packageingestor.commontypes}DateType" minOccurs="0"/>
 *         &lt;element name="ValidityPeriod" type="{http://accenture.avs.packageingestor.commontypes}NumMin1Max11Digit" minOccurs="0"/>
 *         &lt;element name="ViewNumber" type="{http://accenture.avs.packageingestor.commontypes}NumMin1Max11Digit" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "packageIngestorRequest", propOrder = {
    "tenantName",
    "externalOfferId",
    "packageName",
    "packageType",
    "packageDescription",
    "isEnabled",
    "validityStartDate",
    "validityEndDate",
    "validityPeriod",
    "viewNumber"
})
public class PackageIngestorRequest {

    @XmlElement(name = "TenantName", required = true, nillable = true)
    protected String tenantName;
    @XmlElement(name = "ExternalOfferId")
    protected long externalOfferId;
    @XmlElement(name = "PackageName", required = true)
    protected String packageName;
    @XmlElement(name = "PackageType", required = true)
    protected String packageType;
    @XmlElement(name = "PackageDescription", required = true)
    protected String packageDescription;
    @XmlElement(name = "IsEnabled", required = true)
    protected FlagTypeYN isEnabled;
    @XmlElement(name = "ValidityStartDate")
    protected XMLGregorianCalendar validityStartDate;
    @XmlElement(name = "ValidityEndDate")
    protected XMLGregorianCalendar validityEndDate;
    @XmlElement(name = "ValidityPeriod")
    protected Long validityPeriod;
    @XmlElement(name = "ViewNumber")
    protected Long viewNumber;

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
     * Gets the value of the externalOfferId property.
     * 
     */
    public long getExternalOfferId() {
        return externalOfferId;
    }

    /**
     * Sets the value of the externalOfferId property.
     * 
     */
    public void setExternalOfferId(long value) {
        this.externalOfferId = value;
    }

    /**
     * Gets the value of the packageName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * Sets the value of the packageName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPackageName(String value) {
        this.packageName = value;
    }

    /**
     * Gets the value of the packageType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPackageType() {
        return packageType;
    }

    /**
     * Sets the value of the packageType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPackageType(String value) {
        this.packageType = value;
    }

    /**
     * Gets the value of the packageDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPackageDescription() {
        return packageDescription;
    }

    /**
     * Sets the value of the packageDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPackageDescription(String value) {
        this.packageDescription = value;
    }

    /**
     * Gets the value of the isEnabled property.
     * 
     * @return
     *     possible object is
     *     {@link FlagTypeYN }
     *     
     */
    public FlagTypeYN getIsEnabled() {
        return isEnabled;
    }

    /**
     * Sets the value of the isEnabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagTypeYN }
     *     
     */
    public void setIsEnabled(FlagTypeYN value) {
        this.isEnabled = value;
    }

    /**
     * Gets the value of the validityStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getValidityStartDate() {
        return validityStartDate;
    }

    /**
     * Sets the value of the validityStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setValidityStartDate(XMLGregorianCalendar value) {
        this.validityStartDate = value;
    }

    /**
     * Gets the value of the validityEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getValidityEndDate() {
        return validityEndDate;
    }

    /**
     * Sets the value of the validityEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setValidityEndDate(XMLGregorianCalendar value) {
        this.validityEndDate = value;
    }

    /**
     * Gets the value of the validityPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getValidityPeriod() {
        return validityPeriod;
    }

    /**
     * Sets the value of the validityPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setValidityPeriod(Long value) {
        this.validityPeriod = value;
    }

    /**
     * Gets the value of the viewNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getViewNumber() {
        return viewNumber;
    }

    /**
     * Sets the value of the viewNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setViewNumber(Long value) {
        this.viewNumber = value;
    }

}
