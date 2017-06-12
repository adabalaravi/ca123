package wsclient.commontypes.accountmgmt.avs.accenture;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OperationPwdType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="OperationPwdType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CHANGE"/>
 *     &lt;enumeration value="RESET"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "OperationPwdType")
@XmlEnum
public enum OperationPwdType {

    CHANGE,
    RESET;

    public String value() {
        return name();
    }

    public static OperationPwdType fromValue(String v) {
        return valueOf(v);
    }

}
