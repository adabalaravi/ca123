package wsclient.types.accountmgmt.avs.accenture;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import wsclient.commontypes.accountmgmt.avs.accenture.OperationPwdType;


/**
 * <p>Java class for updateUserPwdRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateUserPwdRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TenantName" type="{http://accenture.avs.accountmgmt.commontypes}StrMin1Max50"/>
 *         &lt;element name="CrmAccountId" type="{http://accenture.avs.accountmgmt.commontypes}StrMin1Max50"/>
 *         &lt;element name="UserName" type="{http://accenture.avs.accountmgmt.commontypes}StrMin1Max256"/>
 *         &lt;element name="OldPassword" type="{http://accenture.avs.accountmgmt.commontypes}StrMin4Max20"/>
 *         &lt;element name="NewPassword" type="{http://accenture.avs.accountmgmt.commontypes}StrMin4Max20"/>
 *         &lt;element name="Type" type="{http://accenture.avs.accountmgmt.commontypes}OperationPwdType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateUserPwdRequest", propOrder = {
    "tenantName",
    "crmAccountId",
    "userName",
    "oldPassword",
    "newPassword",
    "type"
})
public class UpdateUserPwdRequest {

    @XmlElement(name = "TenantName", required = true, nillable = true)
    protected String tenantName;
    @XmlElement(name = "CrmAccountId", required = true)
    protected String crmAccountId;
    @XmlElement(name = "UserName", required = true, nillable = true)
    protected String userName;
    @XmlElement(name = "OldPassword", required = true)
    protected String oldPassword;
    @XmlElement(name = "NewPassword", required = true)
    protected String newPassword;
    @XmlElement(name = "Type", required = true)
    protected OperationPwdType type;

    /**
     * Gets the value of the tenantName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTenantName() {
        return tenantName;
    }

    /**
     * Sets the value of the tenantName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTenantName(String value) {
        this.tenantName = value;
    }

    /**
     * Gets the value of the crmAccountId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCrmAccountId() {
        return crmAccountId;
    }

    /**
     * Sets the value of the crmAccountId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCrmAccountId(String value) {
        this.crmAccountId = value;
    }

    /**
     * Gets the value of the userName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the value of the userName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserName(String value) {
        this.userName = value;
    }

    /**
     * Gets the value of the oldPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOldPassword() {
        return oldPassword;
    }

    /**
     * Sets the value of the oldPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOldPassword(String value) {
        this.oldPassword = value;
    }

    /**
     * Gets the value of the newPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * Sets the value of the newPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewPassword(String value) {
        this.newPassword = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link OperationPwdType }
     *     
     */
    public OperationPwdType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link OperationPwdType }
     *     
     */
    public void setType(OperationPwdType value) {
        this.type = value;
    }

}
