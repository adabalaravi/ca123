package wsclient.types.accountmgmt.avs.accenture;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SdpAccountProfileDataResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SdpAccountProfileDataResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Result" type="{http://accenture.avs.accountmgmt.types}sdpResult"/>
 *         &lt;element name="ResultData" type="{http://accenture.avs.accountmgmt.types}getSdpAccountProfileDataResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SdpAccountProfileDataResponse", propOrder = {
    "result",
    "resultData"
})
public class SdpAccountProfileDataResponse {

    @XmlElement(name = "Result", required = true)
    protected SdpResult result;
    @XmlElement(name = "ResultData")
    protected GetSdpAccountProfileDataResponse resultData;

    /**
     * Gets the value of the result property.
     * 
     * @return
     *     possible object is
     *     {@link SdpResult }
     *     
     */
    public SdpResult getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     * @param value
     *     allowed object is
     *     {@link SdpResult }
     *     
     */
    public void setResult(SdpResult value) {
        this.result = value;
    }

    /**
     * Gets the value of the resultData property.
     * 
     * @return
     *     possible object is
     *     {@link GetSdpAccountProfileDataResponse }
     *     
     */
    public GetSdpAccountProfileDataResponse getResultData() {
        return resultData;
    }

    /**
     * Sets the value of the resultData property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetSdpAccountProfileDataResponse }
     *     
     */
    public void setResultData(GetSdpAccountProfileDataResponse value) {
        this.resultData = value;
    }

}
