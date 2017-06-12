package wsclient.commontypes.accountmgmt.avs.accenture;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExtendedRatingType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ExtendedRatingType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="S"/>
 *     &lt;enumeration value="T"/>
 *     &lt;enumeration value="H"/>
 *     &lt;enumeration value="D"/>
 *     &lt;enumeration value="A"/>
 *     &lt;enumeration value="G"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ExtendedRatingType")
@XmlEnum
public enum ExtendedRatingType {

    S,
    T,
    H,
    D,
    A,
    G;

    public String value() {
        return name();
    }

    public static ExtendedRatingType fromValue(String v) {
        return valueOf(v);
    }

}
