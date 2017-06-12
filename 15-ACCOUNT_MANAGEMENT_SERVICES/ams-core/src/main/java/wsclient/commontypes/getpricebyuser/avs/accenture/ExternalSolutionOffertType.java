package wsclient.commontypes.getpricebyuser.avs.accenture;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExternalSolutionOffertType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExternalSolutionOffertType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ExternalSolutionOfferId" type="{http://accenture.avs.getpricebyuser.commontypes}NumMin1Max11Digit"/>
 *         &lt;element name="ExternalOfferList" type="{http://accenture.avs.getpricebyuser.commontypes}ExternalOfferListType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExternalSolutionOffertType", propOrder = {
    "externalSolutionOfferId",
    "externalOfferList"
})
public class ExternalSolutionOffertType {

    @XmlElement(name = "ExternalSolutionOfferId")
    protected long externalSolutionOfferId;
    @XmlElement(name = "ExternalOfferList", required = true)
    protected ExternalOfferListType externalOfferList;

    /**
     * Gets the value of the externalSolutionOfferId property.
     * 
     */
    public long getExternalSolutionOfferId() {
        return externalSolutionOfferId;
    }

    /**
     * Sets the value of the externalSolutionOfferId property.
     * 
     */
    public void setExternalSolutionOfferId(long value) {
        this.externalSolutionOfferId = value;
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
