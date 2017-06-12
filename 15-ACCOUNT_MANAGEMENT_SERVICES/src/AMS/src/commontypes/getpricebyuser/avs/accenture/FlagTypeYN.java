
package commontypes.getpricebyuser.avs.accenture;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FlagTypeYN.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="FlagTypeYN">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Y"/>
 *     &lt;enumeration value="N"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "FlagTypeYN")
@XmlEnum
public enum FlagTypeYN {

    Y,
    N;

    public String value() {
        return name();
    }

    public static FlagTypeYN fromValue(String v) {
        return valueOf(v);
    }

}
