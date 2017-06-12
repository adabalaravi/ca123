package wsclient.commontypes.getpricebyuser.avs.accenture;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OperationProfileType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="OperationProfileType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ADD"/>
 *     &lt;enumeration value="DELETE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "OperationProfileType")
@XmlEnum
public enum OperationProfileType {

    ADD,
    DELETE;

    public String value() {
        return name();
    }

    public static OperationProfileType fromValue(String v) {
        return valueOf(v);
    }

}
