
package commontypes.getpricebyuser.avs.accenture;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UpdateProfileType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UpdateProfileType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CrmProductId" type="{http://accenture.avs.getpricebyuser.commontypes}NumMin1Max11Digit"/>
 *         &lt;element name="OperationType" type="{http://accenture.avs.getpricebyuser.commontypes}OperationProfileType"/>
 *         &lt;element name="ExternalOfferList" type="{http://accenture.avs.getpricebyuser.commontypes}ExternalOfferListType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpdateProfileType", propOrder = {
    "crmProductId",
    "operationType",
    "externalOfferList"
})
public class UpdateProfileType {

    @XmlElement(name = "CrmProductId")
    protected long crmProductId;
    @XmlElement(name = "OperationType", required = true)
    protected OperationProfileType operationType;
    @XmlElement(name = "ExternalOfferList")
    protected ExternalOfferListType externalOfferList;

    /**
     * Gets the value of the crmProductId property.
     * 
     */
    public long getCrmProductId() {
        return crmProductId;
    }

    /**
     * Sets the value of the crmProductId property.
     * 
     */
    public void setCrmProductId(long value) {
        this.crmProductId = value;
    }

    /**
     * Gets the value of the operationType property.
     * 
     * @return
     *     possible object is
     *     {@link OperationProfileType }
     *     
     */
    public OperationProfileType getOperationType() {
        return operationType;
    }

    /**
     * Sets the value of the operationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link OperationProfileType }
     *     
     */
    public void setOperationType(OperationProfileType value) {
        this.operationType = value;
    }

    /**
     * Gets the value of the externalOfferList property.
     * 
     * @return
     *     possible object is
     *     {@link ExternalOfferListType }
     *     
     */
    public ExternalOfferListType getExternalOfferList() {
        return externalOfferList;
    }

    /**
     * Sets the value of the externalOfferList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExternalOfferListType }
     *     
     */
    public void setExternalOfferList(ExternalOfferListType value) {
        this.externalOfferList = value;
    }

}
