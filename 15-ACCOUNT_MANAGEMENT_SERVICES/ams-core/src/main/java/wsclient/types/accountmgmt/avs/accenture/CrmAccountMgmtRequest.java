package wsclient.types.accountmgmt.avs.accenture;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import wsclient.commontypes.accountmgmt.avs.accenture.PayloadType;


/**
 * <p>Java class for crmAccountMgmtRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="crmAccountMgmtRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OperationType" type="{http://accenture.avs.accountmgmt.commontypes}FlagType12"/>
 *         &lt;element name="Payload" type="{http://accenture.avs.accountmgmt.commontypes}PayloadType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "crmAccountMgmtRequest", propOrder = {
    "operationType",
    "payload"
})
public class CrmAccountMgmtRequest {

    @XmlElement(name = "OperationType", required = true)
    protected String operationType;
    @XmlElement(name = "Payload", required = true)
    protected PayloadType payload;

    /**
     * Gets the value of the operationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperationType() {
        return operationType;
    }

    /**
     * Sets the value of the operationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperationType(String value) {
        this.operationType = value;
    }

    /**
     * Gets the value of the payload property.
     * 
     * @return
     *     possible object is
     *     {@link PayloadType }
     *     
     */
    public PayloadType getPayload() {
        return payload;
    }

    /**
     * Sets the value of the payload property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayloadType }
     *     
     */
    public void setPayload(PayloadType value) {
        this.payload = value;
    }

}
