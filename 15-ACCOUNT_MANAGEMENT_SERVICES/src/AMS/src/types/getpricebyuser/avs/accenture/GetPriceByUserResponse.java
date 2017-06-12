
package types.getpricebyuser.avs.accenture;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetPriceByUserResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetPriceByUserResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Result" type="{http://accenture.avs.getpricebyuser.types}GetPriceByUserResultData"/>
 *         &lt;element name="ResultData" type="{http://accenture.avs.getpricebyuser.types}GetPriceByUserResponseData" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetPriceByUserResponse", propOrder = {
    "result",
    "resultData"
})
public class GetPriceByUserResponse {

    @XmlElement(name = "Result", required = true)
    protected GetPriceByUserResultData result;
    @XmlElement(name = "ResultData")
    protected GetPriceByUserResponseData resultData;


    /**
     * Gets the value of the result property.
     * 
     * @return
     *     possible object is
     *     {@link GetPriceByUserResultData }
     *     
     */
    public GetPriceByUserResultData getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetPriceByUserResultData }
     *     
     */
    public void setResult(GetPriceByUserResultData value) {
        this.result = value;
    }

    /**
     * Gets the value of the resultData property.
     * 
     * @return
     *     possible object is
     *     {@link GetPriceByUserResponseData }
     *     
     */
    public GetPriceByUserResponseData getResultData() {
        return resultData;
    }

    /**
     * Sets the value of the resultData property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetPriceByUserResponseData }
     *     
     */
    public void setResultData(GetPriceByUserResponseData value) {
        this.resultData = value;
    }

}
