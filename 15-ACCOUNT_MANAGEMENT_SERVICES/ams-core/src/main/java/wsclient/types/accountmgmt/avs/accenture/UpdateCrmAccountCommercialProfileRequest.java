package wsclient.types.accountmgmt.avs.accenture;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import wsclient.commontypes.accountmgmt.avs.accenture.UpdateProfileListType;


/**
 * <p>Java class for updateCrmAccountCommercialProfileRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateCrmAccountCommercialProfileRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TenantName" type="{http://accenture.avs.accountmgmt.commontypes}StrMin1Max50"/>
 *         &lt;element name="CrmAccountId" type="{http://accenture.avs.accountmgmt.commontypes}StrMin1Max50"/>
 *         &lt;element name="PurchaseChannel" type="{http://accenture.avs.accountmgmt.commontypes}StrMin1Max10" minOccurs="0"/>
 *         &lt;element name="UpdateCommProfileList" type="{http://accenture.avs.accountmgmt.commontypes}UpdateProfileListType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateCrmAccountCommercialProfileRequest", propOrder = {
    "tenantName",
    "crmAccountId",
    "purchaseChannel",
    "updateCommProfileList"
})
public class UpdateCrmAccountCommercialProfileRequest {

    @XmlElement(name = "TenantName", required = true, nillable = true)
    protected String tenantName;
    @XmlElement(name = "CrmAccountId", required = true)
    protected String crmAccountId;
    @XmlElement(name = "PurchaseChannel")
    protected String purchaseChannel;
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
     * Gets the value of the purchaseChannel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPurchaseChannel() {
        return purchaseChannel;
    }

    /**
     * Sets the value of the purchaseChannel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPurchaseChannel(String value) {
        this.purchaseChannel = value;
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
