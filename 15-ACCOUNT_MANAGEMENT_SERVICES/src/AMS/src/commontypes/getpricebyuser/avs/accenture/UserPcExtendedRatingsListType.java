
package commontypes.getpricebyuser.avs.accenture;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UserPcExtendedRatingsListType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UserPcExtendedRatingsListType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UserPcExtendedRating" type="{http://accenture.avs.getpricebyuser.commontypes}ExtendedRatingType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserPcExtendedRatingsListType", propOrder = {
    "userPcExtendedRating"
})
public class UserPcExtendedRatingsListType {

    @XmlElement(name = "UserPcExtendedRating")
    protected List<ExtendedRatingType> userPcExtendedRating;

    /**
     * Gets the value of the userPcExtendedRating property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the userPcExtendedRating property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUserPcExtendedRating().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExtendedRatingType }
     * 
     * 
     */
    public List<ExtendedRatingType> getUserPcExtendedRating() {
        if (userPcExtendedRating == null) {
            userPcExtendedRating = new ArrayList<ExtendedRatingType>();
        }
        return this.userPcExtendedRating;
    }

}
