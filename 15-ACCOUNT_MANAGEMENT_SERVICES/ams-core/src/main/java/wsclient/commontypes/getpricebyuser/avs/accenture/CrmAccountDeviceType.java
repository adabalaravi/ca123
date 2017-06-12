package wsclient.commontypes.getpricebyuser.avs.accenture;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CrmAccountDeviceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CrmAccountDeviceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CrmAccountDeviceId" type="{http://accenture.avs.getpricebyuser.commontypes}StrMin1Max200"/>
 *         &lt;element name="CrmAccountDeviceIdType" type="{http://accenture.avs.getpricebyuser.commontypes}Number2Digit"/>
 *         &lt;element name="Channel" type="{http://accenture.avs.getpricebyuser.commontypes}StrMin1Max10"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CrmAccountDeviceType", propOrder = {
    "crmAccountDeviceId",
    "crmAccountDeviceIdType",
    "channel"
})
public class CrmAccountDeviceType {

    @XmlElement(name = "CrmAccountDeviceId", required = true)
    protected String crmAccountDeviceId;
    @XmlElement(name = "CrmAccountDeviceIdType")
    protected int crmAccountDeviceIdType;
    @XmlElement(name = "Channel", required = true, nillable = true)
    protected String channel;

    /**
     * Gets the value of the crmAccountDeviceId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCrmAccountDeviceId() {
        return crmAccountDeviceId;
    }

    /**
     * Sets the value of the crmAccountDeviceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCrmAccountDeviceId(String value) {
        this.crmAccountDeviceId = value;
    }

    /**
     * Gets the value of the crmAccountDeviceIdType property.
     * 
     */
    public int getCrmAccountDeviceIdType() {
        return crmAccountDeviceIdType;
    }

    /**
     * Sets the value of the crmAccountDeviceIdType property.
     * 
     */
    public void setCrmAccountDeviceIdType(int value) {
        this.crmAccountDeviceIdType = value;
    }

    /**
     * Gets the value of the channel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChannel() {
        return channel;
    }

    /**
     * Sets the value of the channel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannel(String value) {
        this.channel = value;
    }

}
