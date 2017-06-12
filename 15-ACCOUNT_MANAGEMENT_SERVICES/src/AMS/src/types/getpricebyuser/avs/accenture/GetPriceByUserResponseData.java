
package types.getpricebyuser.avs.accenture;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetPriceByUserResponseData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetPriceByUserResponseData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Price" type="{http://accenture.avs.getpricebyuser.commontypes}StrMin1Max50"/>
 *         &lt;element name="PriceDiscount" type="{http://accenture.avs.getpricebyuser.commontypes}StrMin1Max50"/>
 *         &lt;element name="Currency" type="{http://accenture.avs.getpricebyuser.commontypes}StrMin1Max50"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetPriceByUserResponseData", propOrder = {
    "price",
    "priceDiscount",
    "currency"
})
public class GetPriceByUserResponseData {

    @XmlElement(name = "Price", required = true, nillable = true)
    protected String price;
    @XmlElement(name = "PriceDiscount", required = true)
    protected String priceDiscount;
    @XmlElement(name = "Currency", required = true)
    protected String currency;

    /**
     * Gets the value of the price property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrice(String value) {
        this.price = value;
    }

    /**
     * Gets the value of the priceDiscount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPriceDiscount() {
        return priceDiscount;
    }

    /**
     * Sets the value of the priceDiscount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPriceDiscount(String value) {
        this.priceDiscount = value;
    }

    /**
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

}
