package wsclient.commontypes.getpricebyuser.avs.accenture;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FlagTypeMF.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="FlagTypeMF">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="M"/>
 *     &lt;enumeration value="F"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "FlagTypeMF")
@XmlEnum
public enum FlagTypeMF {

    M,
    F;

    public String value() {
        return name();
    }

    public static FlagTypeMF fromValue(String v) {
        return valueOf(v);
    }

}
