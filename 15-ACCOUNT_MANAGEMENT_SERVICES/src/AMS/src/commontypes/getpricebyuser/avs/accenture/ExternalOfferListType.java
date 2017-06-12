
package commontypes.getpricebyuser.avs.accenture;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExternalOfferListType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExternalOfferListType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ExternalOffer" type="{http://accenture.avs.getpricebyuser.commontypes}ExternalOfferType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExternalOfferListType", propOrder = {
    "externalOffer"
})
public class ExternalOfferListType {

    @XmlElement(name = "ExternalOffer", required = true)
    protected List<ExternalOfferType> externalOffer;

    /**
     * Gets the value of the externalOffer property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the externalOffer property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExternalOffer().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExternalOfferType }
     * 
     * 
     */
    public List<ExternalOfferType> getExternalOffer() {
        if (externalOffer == null) {
            externalOffer = new ArrayList<ExternalOfferType>();
        }
        return this.externalOffer;
    }

}
