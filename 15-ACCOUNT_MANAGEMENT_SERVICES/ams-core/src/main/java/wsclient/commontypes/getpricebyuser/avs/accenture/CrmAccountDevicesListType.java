package wsclient.commontypes.getpricebyuser.avs.accenture;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CrmAccountDevicesListType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CrmAccountDevicesListType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CrmAccountDevice" type="{http://accenture.avs.getpricebyuser.commontypes}CrmAccountDeviceType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CrmAccountDevicesListType", propOrder = {
    "crmAccountDevice"
})
public class CrmAccountDevicesListType {

    @XmlElement(name = "CrmAccountDevice")
    protected List<CrmAccountDeviceType> crmAccountDevice;

    /**
     * Gets the value of the crmAccountDevice property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the crmAccountDevice property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCrmAccountDevice().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CrmAccountDeviceType }
     * 
     * 
     */
    public List<CrmAccountDeviceType> getCrmAccountDevice() {
        if (crmAccountDevice == null) {
            crmAccountDevice = new ArrayList<CrmAccountDeviceType>();
        }
        return this.crmAccountDevice;
    }

}
