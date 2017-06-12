package wsclient.commontypes.getpricebyuser.avs.accenture;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UpdateProfileListType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UpdateProfileListType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UpdateCommProfOp" type="{http://accenture.avs.getpricebyuser.commontypes}UpdateProfileType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpdateProfileListType", propOrder = {
    "updateCommProfOp"
})
public class UpdateProfileListType {

    @XmlElement(name = "UpdateCommProfOp")
    protected List<UpdateProfileType> updateCommProfOp;

    /**
     * Gets the value of the updateCommProfOp property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the updateCommProfOp property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUpdateCommProfOp().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UpdateProfileType }
     * 
     * 
     */
    public List<UpdateProfileType> getUpdateCommProfOp() {
        if (updateCommProfOp == null) {
            updateCommProfOp = new ArrayList<UpdateProfileType>();
        }
        return this.updateCommProfOp;
    }

}
