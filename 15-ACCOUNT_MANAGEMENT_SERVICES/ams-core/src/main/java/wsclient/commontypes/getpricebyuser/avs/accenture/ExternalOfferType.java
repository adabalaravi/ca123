package wsclient.commontypes.getpricebyuser.avs.accenture;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExternalOfferType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExternalOfferType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ExternalOfferId" type="{http://accenture.avs.getpricebyuser.commontypes}NumMin1Max11Digit"/>
 *         &lt;element name="PackageId" type="{http://accenture.avs.getpricebyuser.commontypes}StrMin1Max50"/>
 *         &lt;element name="Price" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExternalOfferType", propOrder = {
    "externalOfferId",
    "packageId",
    "price"
})
public class ExternalOfferType {

    @XmlElement(name = "ExternalOfferId")
    protected long externalOfferId;
    @XmlElement(name = "PackageId", required = true)
    protected String packageId;
    @XmlElement(name = "Price")
    protected BigDecimal price;

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
     * Gets the value of the packageId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPackageId() {
        return packageId;
    }

    /**
     * Sets the value of the packageId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPackageId(String value) {
        this.packageId = value;
    }

    /**
     * Gets the value of the price property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPrice(BigDecimal value) {
        this.price = value;
    }

}
