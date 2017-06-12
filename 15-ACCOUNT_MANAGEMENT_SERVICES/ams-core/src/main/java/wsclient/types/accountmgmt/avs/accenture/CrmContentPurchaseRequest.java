package wsclient.types.accountmgmt.avs.accenture;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import wsclient.commontypes.accountmgmt.avs.accenture.ExternalSolutionOffertType;
import wsclient.commontypes.accountmgmt.avs.accenture.OperationType;


/**
 * <p>Java class for crmContentPurchaseRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="crmContentPurchaseRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TenantName" type="{http://accenture.avs.accountmgmt.commontypes}StrMin1Max50"/>
 *         &lt;element name="OperationType" type="{http://accenture.avs.accountmgmt.commontypes}OperationType"/>
 *         &lt;element name="OperationDescription" type="{http://accenture.avs.accountmgmt.commontypes}StrMax50"/>
 *         &lt;element name="CrmAccountId" type="{http://accenture.avs.accountmgmt.commontypes}StrMin1Max50"/>
 *         &lt;element name="PurchaseChannel" type="{http://accenture.avs.accountmgmt.commontypes}StrMin1Max10"/>
 *         &lt;element name="ExternalSolutionOffer" type="{http://accenture.avs.accountmgmt.commontypes}ExternalSolutionOffertType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "crmContentPurchaseRequest", propOrder = {
    "tenantName",
    "operationType",
    "operationDescription",
    "crmAccountId",
    "purchaseChannel",
    "externalSolutionOffer"
})
public class CrmContentPurchaseRequest {

    @XmlElement(name = "TenantName", required = true, nillable = true)
    protected String tenantName;
    @XmlElement(name = "OperationType", required = true)
    protected OperationType operationType;
    @XmlElement(name = "OperationDescription", required = true)
    protected String operationDescription;
    @XmlElement(name = "CrmAccountId", required = true)
    protected String crmAccountId;
    @XmlElement(name = "PurchaseChannel", required = true)
    protected String purchaseChannel;
    @XmlElement(name = "ExternalSolutionOffer", required = true)
    protected ExternalSolutionOffertType externalSolutionOffer;

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
     * Gets the value of the operationType property.
     * 
     * @return
     *     possible object is
     *     {@link OperationType }
     *     
     */
    public OperationType getOperationType() {
        return operationType;
    }

    /**
     * Sets the value of the operationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link OperationType }
     *     
     */
    public void setOperationType(OperationType value) {
        this.operationType = value;
    }

    /**
     * Gets the value of the operationDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperationDescription() {
        return operationDescription;
    }

    /**
     * Sets the value of the operationDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperationDescription(String value) {
        this.operationDescription = value;
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
     * Gets the value of the externalSolutionOffer property.
     * 
     * @return
     *     possible object is
     *     {@link ExternalSolutionOffertType }
     *     
     */
    public ExternalSolutionOffertType getExternalSolutionOffer() {
        return externalSolutionOffer;
    }

    /**
     * Sets the value of the externalSolutionOffer property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExternalSolutionOffertType }
     *     
     */
    public void setExternalSolutionOffer(ExternalSolutionOffertType value) {
        this.externalSolutionOffer = value;
    }

}
